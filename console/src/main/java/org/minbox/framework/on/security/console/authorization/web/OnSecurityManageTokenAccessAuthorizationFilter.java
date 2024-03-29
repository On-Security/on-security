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
import org.minbox.framework.on.security.core.authorization.manage.context.OnSecurityManageContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 管理令牌访问认证过滤器
 * <p>
 * 拦截访问控制台的全部请求，验证请求Header为"on-security-manage-token"中所携带的管理令牌（Manage Token）
 *
 * @author 恒宇少年
 * @since 0.0.9
 */
public class OnSecurityManageTokenAccessAuthorizationFilter extends OncePerRequestFilter {
    private AuthenticationManager authenticationManager;
    private AuthenticationFailureHandler failureHandler;
    private AuthenticationConverter authenticationConverter;

    public OnSecurityManageTokenAccessAuthorizationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.failureHandler = new OnSecurityDefaultAuthenticationFailureHandler();
        this.authenticationConverter = new OnSecurityManageTokenAccessAuthorizationConvert();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // @formatter:off
            OnSecurityManageTokenAccessRequestAuthorizationToken manageTokenAccessRequestAuthorizationToken =
                    (OnSecurityManageTokenAccessRequestAuthorizationToken) this.authenticationConverter.convert(request);
            this.authenticationManager.authenticate(manageTokenAccessRequestAuthorizationToken);
            // @formatter:on
            filterChain.doFilter(request, response);
        } catch (AuthenticationException e) {
            failureHandler.onAuthenticationFailure(request, response, e);
        } finally {
            OnSecurityManageContextHolder.clearContext();
        }
    }
}
