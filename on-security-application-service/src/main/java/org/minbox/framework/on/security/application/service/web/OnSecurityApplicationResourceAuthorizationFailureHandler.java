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

import org.minbox.framework.on.security.application.service.authentication.ApplicationResourceAccessDeniedAuthenticationEntryPoint;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 应用资源验证失败执行的处理器
 *
 * @author 恒宇少年
 * @see ApplicationResourceAccessDeniedAuthenticationEntryPoint
 * @since 0.0.6
 */
public class OnSecurityApplicationResourceAuthorizationFailureHandler implements AuthenticationFailureHandler {
    // @formatter:off
    private ApplicationResourceAccessDeniedAuthenticationEntryPoint accessDeniedAuthenticationEntryPoint =
            new ApplicationResourceAccessDeniedAuthenticationEntryPoint();
    // @formatter:on
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (exception instanceof AuthenticationServiceException) {
            throw exception;
        }
        this.accessDeniedAuthenticationEntryPoint.commence(request, response, exception);
    }
}
