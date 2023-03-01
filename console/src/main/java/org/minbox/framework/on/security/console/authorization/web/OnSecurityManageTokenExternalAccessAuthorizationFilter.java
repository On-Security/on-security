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

import org.minbox.framework.on.security.console.authorization.authentication.OnSecurityManageTokenAccessRequestAuthorizationToken;
import org.minbox.framework.on.security.console.authorization.web.converter.OnSecurityManageTokenAccessAuthorizationConvert;
import org.minbox.framework.on.security.core.authorization.OnSecurityDefaultAuthenticationFailureHandler;
import org.minbox.framework.on.security.core.authorization.jackson2.OnSecurityJsonMapper;
import org.minbox.framework.on.security.core.authorization.manage.ManageTokenAccessAuthorization;
import org.minbox.framework.on.security.core.authorization.manage.context.OnSecurityManageContextHolder;
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
 * 管理令牌（ManageToken）外部服务访问的授权认证过滤器
 * <p>
 * 携带管理令牌（ManageToken）访问"/on-security/manage/access/authorization"时，获取携带管理令牌的所授权的信息
 *
 * @author 恒宇少年
 * @since 0.0.9
 */
public class OnSecurityManageTokenExternalAccessAuthorizationFilter extends OncePerRequestFilter {
    private RequestMatcher requestMatcher;
    private AuthenticationManager authenticationManager;
    private AuthenticationSuccessHandler successHandler;
    private AuthenticationFailureHandler failureHandler;
    private AuthenticationConverter authenticationConverter;

    public OnSecurityManageTokenExternalAccessAuthorizationFilter(RequestMatcher requestMatcher, AuthenticationManager authenticationManager) {
        this.requestMatcher = requestMatcher;
        this.authenticationManager = authenticationManager;
        this.successHandler = new ManageTokenExternalAccessAuthorizationSuccessHandler();
        this.failureHandler = new OnSecurityDefaultAuthenticationFailureHandler();
        this.authenticationConverter = new OnSecurityManageTokenAccessAuthorizationConvert();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!this.requestMatcher.matches(request)) {
            filterChain.doFilter(request, response);
        } else {
            try {
                // @formatter:off
                OnSecurityManageTokenAccessRequestAuthorizationToken manageTokenAccessRequestAuthorizationToken =
                        (OnSecurityManageTokenAccessRequestAuthorizationToken) this.authenticationConverter.convert(request);
                Authentication authentication = this.authenticationManager.authenticate(manageTokenAccessRequestAuthorizationToken);
                this.successHandler.onAuthenticationSuccess(request, response, authentication);
                // @formatter:on
            } catch (AuthenticationException e) {
                failureHandler.onAuthenticationFailure(request, response, e);
            } finally {
                OnSecurityManageContextHolder.clearContext();
            }
        }
    }

    /**
     * 认证成功后的处理器
     */
    private static class ManageTokenExternalAccessAuthorizationSuccessHandler implements AuthenticationSuccessHandler {
        private OnSecurityJsonMapper jsonMapper = new OnSecurityJsonMapper();

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            ManageTokenAccessAuthorization accessAuthorization = (ManageTokenAccessAuthorization) authentication;
            String responseJson = jsonMapper.writeValueAsString(accessAuthorization);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setContentType(MediaType.APPLICATION_JSON.toString());
            response.getWriter().write(responseJson);
        }
    }
}
