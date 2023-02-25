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

package org.minbox.framework.on.security.application.service.web;

import org.minbox.framework.on.security.application.service.authentication.ApplicationResourceRoleBasedAccessControlAuthenticationProvider;
import org.minbox.framework.on.security.application.service.authentication.ApplicationResourceRoleBasedAccessControlAuthenticationToken;
import org.minbox.framework.on.security.application.service.authentication.context.OnSecurityApplicationContext;
import org.minbox.framework.on.security.application.service.authentication.context.OnSecurityApplicationContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 应用资源"Role-Based Access Control（RBAC）"访问控制过滤器
 * <p>
 * 该过滤器拦截所有的访问地址，在{@link OnSecurityAccessTokenAuthorizationFilter}之后执行
 * 根据线程副本中的{@link OnSecurityApplicationContext}实例来验证令牌使用用户是否授权了本次访问的资源地址
 *
 * @author 恒宇少年
 * @see OnSecurityApplicationContext
 * @see ApplicationResourceRoleBasedAccessControlAuthenticationProvider
 * @see ApplicationResourceRoleBasedAccessControlAuthenticationToken
 * @since 0.0.7
 */
public final class ApplicationResourceRoleBasedAccessControlFilter extends OncePerRequestFilter {
    private AuthenticationManager authenticationManager;
    private AuthenticationFailureHandler authenticationFailureHandler;

    public ApplicationResourceRoleBasedAccessControlFilter(AuthenticationManager authenticationManager) {
        this(authenticationManager, new OnSecurityApplicationResourceAuthorizationFailureHandler());
    }

    public ApplicationResourceRoleBasedAccessControlFilter(AuthenticationManager authenticationManager,
                                                           AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationManager = authenticationManager;
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            OnSecurityApplicationContext applicationContext = OnSecurityApplicationContextHolder.getContext();
            // @formatter:off
            ApplicationResourceRoleBasedAccessControlAuthenticationToken roleBasedAccessControlAuthenticationToken =
                    new ApplicationResourceRoleBasedAccessControlAuthenticationToken(
                            request,
                            applicationContext.getUserAuthorizationResource());
            // @formatter:on
            authenticationManager.authenticate(roleBasedAccessControlAuthenticationToken);
            filterChain.doFilter(request, response);
        } catch (AuthenticationException e) {
            this.authenticationFailureHandler.onAuthenticationFailure(request, response, e);
        }
    }
}
