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

package org.minbox.framework.on.security.federal.identity.web;

import org.minbox.framework.on.security.federal.identity.authentication.OnSecurityIdentityProviderBrokerEndpointRequestToken;
import org.springframework.core.log.LogMessage;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.client.ClientAuthorizationRequiredException;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * IdP（"Identity Provider"）身份供应商代理认证地址转发过滤器
 * <p>
 * 该过滤器会拦截前缀"/identity/broker/endpoint"的请求地址，从路径中提取本次请求的变量值，对应获取Idp的目标认证地址拼接相关参数后进行进行跳转
 * <p>
 * 完整的路径模版为："/identity/broker/endpoint/regions/{regionId}/registrations/{registrationId}"
 *
 * @author 恒宇少年
 * @since 0.0.3
 */
public class OnSecurityIdentityProviderBrokerEndpointFilter extends OncePerRequestFilter {
    private final ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();
    private final RedirectStrategy authorizationRedirectStrategy = new DefaultRedirectStrategy();
    private final AuthenticationManager authenticationManager;
    private RequestCache requestCache = new HttpSessionRequestCache();
    private AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository = new HttpSessionOAuth2AuthorizationRequestRepository();
    private OAuth2AuthorizationRequestResolver authorizationRequestResolver;

    public OnSecurityIdentityProviderBrokerEndpointFilter(AuthenticationManager authenticationManager,
                                                          ClientRegistrationRepository clientRegistrationRepository,
                                                          String endpointPrefixUri) {
        this.authenticationManager = authenticationManager;
        this.authorizationRequestResolver = new OnSecurityIdentityProviderBrokerEndpointRequestResolver(
                clientRegistrationRepository, endpointPrefixUri);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            OAuth2AuthorizationRequest authorizationRequest = this.authorizationRequestResolver.resolve(request);
            if (authorizationRequest != null) {
                String regionId = String.valueOf(authorizationRequest.getAdditionalParameters().get(OnSecurityIdentityProviderBrokerEndpointRequestResolver.REGION_ID_URI_VARIABLE_NAME));
                String registrationId = String.valueOf(authorizationRequest.getAdditionalParameters().get(OnSecurityIdentityProviderBrokerEndpointRequestResolver.REGISTRATION_ID_URI_VARIABLE_NAME));
                OnSecurityIdentityProviderBrokerEndpointRequestToken brokerEndpointRequestToken =
                        new OnSecurityIdentityProviderBrokerEndpointRequestToken(regionId, registrationId, authorizationRequest);
                authenticationManager.authenticate(brokerEndpointRequestToken);
                this.sendRedirectForAuthorization(request, response, authorizationRequest);
                return;
            }
        } catch (Exception ex) {
            this.unsuccessfulRedirectForAuthorization(request, response, ex);
            return;
        }
        try {
            filterChain.doFilter(request, response);
        } catch (IOException ex) {
            throw ex;
        } catch (Exception ex) {
            // Check to see if we need to handle ClientAuthorizationRequiredException
            Throwable[] causeChain = this.throwableAnalyzer.determineCauseChain(ex);
            ClientAuthorizationRequiredException authzEx = (ClientAuthorizationRequiredException) this.throwableAnalyzer.getFirstThrowableOfType(ClientAuthorizationRequiredException.class, causeChain);
            if (authzEx != null) {
                try {
                    OAuth2AuthorizationRequest authorizationRequest = this.authorizationRequestResolver.resolve(request, authzEx.getClientRegistrationId());
                    if (authorizationRequest == null) {
                        throw authzEx;
                    }
                    this.sendRedirectForAuthorization(request, response, authorizationRequest);
                    this.requestCache.saveRequest(request, response);
                } catch (Exception failed) {
                    this.unsuccessfulRedirectForAuthorization(request, response, failed);
                }
                return;
            }
            if (ex instanceof ServletException) {
                throw (ServletException) ex;
            }
            if (ex instanceof RuntimeException) {
                throw (RuntimeException) ex;
            }
            throw new RuntimeException(ex);
        }
    }

    private void sendRedirectForAuthorization(HttpServletRequest request, HttpServletResponse response, OAuth2AuthorizationRequest authorizationRequest) throws IOException {
        if (AuthorizationGrantType.AUTHORIZATION_CODE.equals(authorizationRequest.getGrantType())) {
            this.authorizationRequestRepository.saveAuthorizationRequest(authorizationRequest, request, response);
        }
        this.authorizationRedirectStrategy.sendRedirect(request, response, authorizationRequest.getAuthorizationRequestUri());
    }

    private void unsuccessfulRedirectForAuthorization(HttpServletRequest request, HttpServletResponse response, Exception ex) throws IOException {
        this.logger.error(LogMessage.format("Authorization Request failed: %s", ex), ex);
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    private static final class DefaultThrowableAnalyzer extends ThrowableAnalyzer {

        @Override
        protected void initExtractorMap() {
            super.initExtractorMap();
            registerExtractor(ServletException.class, (throwable) -> {
                ThrowableAnalyzer.verifyThrowableHierarchy(throwable, ServletException.class);
                return ((ServletException) throwable).getRootCause();
            });
        }

    }
}
