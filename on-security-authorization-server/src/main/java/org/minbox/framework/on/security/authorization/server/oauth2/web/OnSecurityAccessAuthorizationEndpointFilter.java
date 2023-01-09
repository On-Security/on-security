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

import com.fasterxml.jackson.databind.ObjectMapper;
import org.minbox.framework.on.security.authorization.server.oauth2.authentication.OnSecurityDefaultAuthenticationFailureHandler;
import org.minbox.framework.on.security.authorization.server.oauth2.authentication.support.OnSecurityAccessAuthorizationAuthenticationToken;
import org.minbox.framework.on.security.authorization.server.oauth2.authentication.support.OnSecurityAccessAuthorizationRequestToken;
import org.minbox.framework.on.security.authorization.server.oauth2.web.converter.OnSecurityAccessAuthorizationRequestConverter;
import org.minbox.framework.on.security.core.authorization.endpoint.AccessAuthorizationEndpointResponse;
import org.minbox.framework.on.security.core.authorization.jackson2.OnSecurityTimeModule;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 获取请求令牌所属用户的访问授权端点过滤器
 *
 * @author 恒宇少年
 * @since 0.0.5
 */
public final class OnSecurityAccessAuthorizationEndpointFilter extends OncePerRequestFilter {
    private RequestMatcher requestMatcher;
    private AuthenticationManager authenticationManager;
    private AuthenticationConverter converter;
    private AuthenticationSuccessHandler successHandler;
    private AuthenticationFailureHandler failureHandler;

    public OnSecurityAccessAuthorizationEndpointFilter(RequestMatcher requestMatcher, AuthenticationManager authenticationManager) {
        this.requestMatcher = requestMatcher;
        this.authenticationManager = authenticationManager;
        this.converter = new OnSecurityAccessAuthorizationRequestConverter();
        this.successHandler = new AccessAuthorizationSuccessHandler();
        this.failureHandler = new OnSecurityDefaultAuthenticationFailureHandler();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!this.requestMatcher.matches(request)) {
            filterChain.doFilter(request, response);
        } else {
            try {
                // @formatter:off
                OnSecurityAccessAuthorizationRequestToken accessAuthorizationRequestToken =
                        (OnSecurityAccessAuthorizationRequestToken) converter.convert(request);
                // @formatter:on
                Authentication authenticationResult = authenticationManager.authenticate(accessAuthorizationRequestToken);
                successHandler.onAuthenticationSuccess(request, response, authenticationResult);
            } catch (AuthenticationException e) {
                failureHandler.onAuthenticationFailure(request, response, e);
            }
        }
    }

    /**
     * 认证成功后的处理器
     */
    private static class AccessAuthorizationSuccessHandler implements AuthenticationSuccessHandler {
        private ObjectMapper objectMapper;

        public AccessAuthorizationSuccessHandler() {
            this.objectMapper = new ObjectMapper();
            // Register OnSecurityTimeModule
            this.objectMapper.registerModule(new OnSecurityTimeModule());
        }

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            OnSecurityAccessAuthorizationAuthenticationToken accessAuthorizationAuthenticationToken =
                    (OnSecurityAccessAuthorizationAuthenticationToken) authentication;
            AccessAuthorizationEndpointResponse endpointResponse = accessAuthorizationAuthenticationToken.toEndpointResponse();
            String responseJson = objectMapper.writeValueAsString(endpointResponse);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setContentType(MediaType.APPLICATION_JSON.toString());
            response.getWriter().write(responseJson);
        }
    }
}
