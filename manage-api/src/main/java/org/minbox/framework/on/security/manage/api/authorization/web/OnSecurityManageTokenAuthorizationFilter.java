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

package org.minbox.framework.on.security.manage.api.authorization.web;

import org.minbox.framework.on.security.core.authorization.OnSecurityDefaultAuthenticationFailureHandler;
import org.minbox.framework.on.security.core.authorization.manage.context.OnSecurityManageContextHolder;
import org.minbox.framework.on.security.manage.api.authorization.authentication.OnSecurityManageTokenAuthorizationRequestToken;
import org.minbox.framework.on.security.manage.api.authorization.web.convert.OnSecurityManageTokenAuthorizationConvert;
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
 * 管理令牌（Manage Token）授权认证信息过滤器
 * <p>
 * 拦截全部访问请求，从请求Header中提取{@link org.springframework.http.HttpHeaders#AUTHORIZATION}携带的管理令牌（Manage Token）
 * 携带提取到的管理令牌访问控制台（Console Service）所给提供的授权信息端点"/on-security/manage/access/authorization"获取管理令牌
 * 所授权的相关信息
 *
 * @author 恒宇少年
 * @see AuthenticationManager
 * @see OnSecurityDefaultAuthenticationFailureHandler
 * @see OnSecurityManageTokenAuthorizationConvert
 * @see OnSecurityManageTokenAuthorizationRequestToken
 * @since 0.1.0
 */
public class OnSecurityManageTokenAuthorizationFilter extends OncePerRequestFilter {
    private AuthenticationManager authenticationManager;
    private AuthenticationFailureHandler failureHandler;
    private AuthenticationConverter authenticationConverter;

    public OnSecurityManageTokenAuthorizationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.failureHandler = new OnSecurityDefaultAuthenticationFailureHandler();
        this.authenticationConverter = new OnSecurityManageTokenAuthorizationConvert();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // @formatter:off
            OnSecurityManageTokenAuthorizationRequestToken authorizationRequestToken =
                    (OnSecurityManageTokenAuthorizationRequestToken) this.authenticationConverter.convert(request);
            // @formatter:on
            this.authenticationManager.authenticate(authorizationRequestToken);
            filterChain.doFilter(request, response);
        } catch (AuthenticationException exception) {
            this.failureHandler.onAuthenticationFailure(request, response, exception);
        } finally {
            OnSecurityManageContextHolder.clearContext();
        }
    }
}
