/*
 *   Copyright (C) 2022  恒宇少年
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.minbox.framework.on.security.console.authorization.web;

import org.minbox.framework.on.security.console.authorization.authentication.ManageTokenAuthenticateType;
import org.minbox.framework.on.security.console.authorization.authentication.OnSecurityConsoleManageTokenAuthenticationProvider;
import org.minbox.framework.on.security.console.authorization.authentication.OnSecurityConsoleManageTokenAuthenticationToken;
import org.minbox.framework.on.security.console.authorization.authentication.OnSecurityConsoleManageTokenRequestToken;
import org.minbox.framework.on.security.console.authorization.web.converter.ConsoleManageTokenResponseHttpMessageConverter;
import org.minbox.framework.on.security.core.authorization.OnSecurityDefaultAuthenticationFailureHandler;
import org.minbox.framework.on.security.core.authorization.exception.OnSecurityError;
import org.minbox.framework.on.security.core.authorization.exception.OnSecurityOAuth2AuthenticationException;
import org.minbox.framework.on.security.core.authorization.util.OnSecurityThrowErrorUtils;
import org.minbox.framework.on.security.core.authorization.util.RequestParameterUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.MultiValueMap;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 控制台管理令牌（Manage Token）接口请求拦截器
 * <p>
 * 该拦截器仅处理"/on-security/manage/token"地址请求，根据携带的参数生成并返回管理令牌（Manage Token）
 *
 * @author 恒宇少年
 * @see OnSecurityConsoleManageTokenAuthenticationProvider
 * @see AuthenticationManager
 * @since 0.0.9
 */
public class OnSecurityConsoleManageTokenFilter extends OncePerRequestFilter {
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String REGION_ID = "region_id";
    public static final String REGION_SECRET = "region_secret";

    private RequestMatcher requestMatcher;
    private AuthenticationManager authenticationManager;
    private AuthenticationSuccessHandler successHandler;
    private AuthenticationFailureHandler failureHandler;

    public OnSecurityConsoleManageTokenFilter(RequestMatcher requestMatcher, AuthenticationManager authenticationManager) {
        this.requestMatcher = requestMatcher;
        this.authenticationManager = authenticationManager;
        this.successHandler = new ManageTokenAuthenticationSuccessHandler();
        this.failureHandler = new OnSecurityDefaultAuthenticationFailureHandler();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!requestMatcher.matches(request)) {
            filterChain.doFilter(request, response);
        } else {
            try {
                MultiValueMap<String, String> parameters = RequestParameterUtils.getParameters(request);
                ManageTokenAuthenticateType authenticateType = this.getAuthenticateType(parameters);
                if (authenticateType == null) {
                    // @formatter:off
                    OnSecurityError onSecurityError = new OnSecurityError(OAuth2ErrorCodes.INVALID_REQUEST,
                            null,
                            "Incorrect request, please confirm whether the parameters are valid.",
                            OnSecurityThrowErrorUtils.DEFAULT_HELP_URI);
                    // @formatter:on
                    throw new OnSecurityOAuth2AuthenticationException(onSecurityError);
                }
                OnSecurityConsoleManageTokenRequestToken manageTokenAuthenticationToken =
                        new OnSecurityConsoleManageTokenRequestToken(authenticateType, parameters);
                Authentication authenticationResult = this.authenticationManager.authenticate(manageTokenAuthenticationToken);
                successHandler.onAuthenticationSuccess(request, response, authenticationResult);
            } catch (AuthenticationException e) {
                failureHandler.onAuthenticationFailure(request, response, e);
            }
        }
    }

    /**
     * 根据参数获取认证类型
     *
     * @param parameters 本次访问的参数集合 {@link MultiValueMap}
     * @return ManageToken认证类型 {@link ManageTokenAuthenticateType}
     */
    private ManageTokenAuthenticateType getAuthenticateType(MultiValueMap<String, String> parameters) {
        ManageTokenAuthenticateType authenticateType = null;
        if (parameters.containsKey(USERNAME) && parameters.containsKey(PASSWORD)) {
            authenticateType = ManageTokenAuthenticateType.username_password;
        } else if (parameters.containsKey(REGION_ID) && parameters.containsKey(REGION_SECRET)) {
            authenticateType = ManageTokenAuthenticateType.id_secret;
        }
        return authenticateType;
    }

    /**
     * 认证成功响应处理器
     */
    private static class ManageTokenAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
        private final HttpMessageConverter<ConsoleManageTokenResponse> manageTokenHttpResponseConverter =
                new ConsoleManageTokenResponseHttpMessageConverter();

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            OnSecurityConsoleManageTokenAuthenticationToken manageTokenAuthenticationToken = (OnSecurityConsoleManageTokenAuthenticationToken) authentication;

            // @formatter:off
            ConsoleManageTokenResponse.Builder builder = ConsoleManageTokenResponse
                    .withConsoleManageToken(manageTokenAuthenticationToken.getToken())
                    .regionId(manageTokenAuthenticationToken.getTokenContext().getRegionId())
                    .authenticateType(manageTokenAuthenticationToken.getTokenContext().getAuthenticateType());
            // @formatter:on

            ConsoleManageTokenResponse manageTokenResponse = builder.build();
            ServletServerHttpResponse serverHttpResponse = new ServletServerHttpResponse(response);
            this.manageTokenHttpResponseConverter.write(manageTokenResponse, null, serverHttpResponse);
        }
    }
}
