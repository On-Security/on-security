/*
 *     Copyright (C) 2022  恒宇少年
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.minbox.framework.on.security.authorization.server.oauth2.web;

import org.minbox.framework.on.security.authorization.server.oauth2.authentication.OnSecurityDefaultAuthenticationFailureHandler;
import org.minbox.framework.on.security.core.authorization.exception.OnSecurityErrorCodes;
import org.minbox.framework.on.security.authorization.server.oauth2.authentication.support.OnSecurityOAuth2UsernamePasswordAuthenticationToken;
import org.minbox.framework.on.security.authorization.server.oauth2.web.converter.OnSecurityOAuth2UsernamePasswordAuthenticationConverter;
import org.minbox.framework.on.security.core.authorization.util.OnSecurityThrowErrorUtils;
import org.minbox.framework.on.security.core.authorization.util.RequestParameterUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * 用户名密码身份认证过滤器
 *
 * @author 恒宇少年
 * @see org.springframework.security.oauth2.core.AuthorizationGrantType#PASSWORD
 * @since 0.0.1
 */
public class OnSecurityOAuth2UsernamePasswordAuthenticationFilter extends OncePerRequestFilter {
    private RequestMatcher requestMatcher;
    private AuthenticationManager authenticationManager;
    private AuthenticationConverter converter;
    private AuthenticationSuccessHandler successHandler;
    private AuthenticationFailureHandler failureHandler;

    public OnSecurityOAuth2UsernamePasswordAuthenticationFilter(RequestMatcher requestMatcher, AuthenticationManager authenticationManager) {
        this.requestMatcher = requestMatcher;
        this.authenticationManager = authenticationManager;
        this.converter = new OnSecurityOAuth2UsernamePasswordAuthenticationConverter();
        this.failureHandler = new OnSecurityDefaultAuthenticationFailureHandler();
        this.successHandler = new UsernamePasswordDefaultAuthenticationSuccessHandler();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!this.requestMatcher.matches(request)) {
            filterChain.doFilter(request, response);
        } else {
            MultiValueMap<String, String> parameters = RequestParameterUtils.getParameters(request);
            // Skip the filter when there are no username and password parameters
            String grantType = parameters.getFirst(OAuth2ParameterNames.GRANT_TYPE);
            if (!StringUtils.hasText(grantType)) {
                OnSecurityThrowErrorUtils.throwError(OnSecurityErrorCodes.INVALID_GRANT_TYPE,
                        OAuth2ParameterNames.GRANT_TYPE);
            }
            if (!AuthorizationGrantType.PASSWORD.getValue().equals(grantType)) {
                filterChain.doFilter(request, response);
            } else {
                try {
                    OnSecurityOAuth2UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            (OnSecurityOAuth2UsernamePasswordAuthenticationToken) converter.convert(request);
                    Authentication authenticationResult = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
                    successHandler.onAuthenticationSuccess(request, response, authenticationResult);
                } catch (AuthenticationException exception) {
                    logger.error("An exception was encountered during username and password authentication: " + exception.getMessage(), exception);
                    failureHandler.onAuthenticationFailure(request, response, exception);
                }
            }
        }
    }

    public void setSuccessHandler(AuthenticationSuccessHandler successHandler) {
        Assert.notNull(successHandler, "successHandler cannot be null.");
        this.successHandler = successHandler;
    }

    public void setFailureHandler(AuthenticationFailureHandler failureHandler) {
        Assert.notNull(failureHandler, "failureHandler cannot be null.");
        this.failureHandler = failureHandler;
    }

    /**
     * 用户名密码认证成功默认处理器
     *
     * @see OAuth2AccessTokenResponseHttpMessageConverter
     * @see OAuth2AccessTokenAuthenticationToken
     * @see OAuth2AccessTokenResponse
     */
    private class UsernamePasswordDefaultAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
        private final HttpMessageConverter<OAuth2AccessTokenResponse> accessTokenHttpResponseConverter =
                new OAuth2AccessTokenResponseHttpMessageConverter();

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            OAuth2AccessTokenAuthenticationToken accessTokenAuthentication =
                    (OAuth2AccessTokenAuthenticationToken) authentication;

            OAuth2AccessToken accessToken = accessTokenAuthentication.getAccessToken();
            OAuth2RefreshToken refreshToken = accessTokenAuthentication.getRefreshToken();
            Map<String, Object> additionalParameters = accessTokenAuthentication.getAdditionalParameters();

            OAuth2AccessTokenResponse.Builder builder =
                    OAuth2AccessTokenResponse.withToken(accessToken.getTokenValue())
                            .tokenType(accessToken.getTokenType())
                            .scopes(accessToken.getScopes());
            if (accessToken.getIssuedAt() != null && accessToken.getExpiresAt() != null) {
                builder.expiresIn(ChronoUnit.SECONDS.between(accessToken.getIssuedAt(), accessToken.getExpiresAt()));
            }
            if (refreshToken != null) {
                builder.refreshToken(refreshToken.getTokenValue());
            }
            if (!CollectionUtils.isEmpty(additionalParameters)) {
                builder.additionalParameters(additionalParameters);
            }
            OAuth2AccessTokenResponse accessTokenResponse = builder.build();
            ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
            this.accessTokenHttpResponseConverter.write(accessTokenResponse, null, httpResponse);
        }
    }
}
