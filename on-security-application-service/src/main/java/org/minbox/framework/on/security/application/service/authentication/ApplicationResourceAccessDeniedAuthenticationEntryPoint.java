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

package org.minbox.framework.on.security.application.service.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.minbox.framework.on.security.application.service.exception.OnSecurityApplicationResourceAuthenticationException;
import org.minbox.framework.on.security.application.service.web.OnSecurityApplicationResourceAccessFailedResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 资源拒绝访问的认证端点
 *
 * @author 恒宇少年
 * @see OnSecurityApplicationResourceAuthorizationFailureHandler
 * @since 0.0.6
 */
public class ApplicationResourceAccessDeniedAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private ObjectMapper objectMapper;

    public ApplicationResourceAccessDeniedAuthenticationEntryPoint() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (authException instanceof OnSecurityApplicationResourceAuthenticationException) {
            OnSecurityApplicationResourceAuthenticationException resourceAuthenticationException =
                    (OnSecurityApplicationResourceAuthenticationException) authException;
            // @formatter:off
            OnSecurityApplicationResourceAccessFailedResponse accessFailedResponse =
                    new OnSecurityApplicationResourceAccessFailedResponse(
                            request.getRequestURI(),
                            resourceAuthenticationException.getAuthenticationErrorCode(),
                            resourceAuthenticationException.getFormatParams()
                    );
            // @formatter:on
            String responseJson = objectMapper.writeValueAsString(accessFailedResponse);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setContentType(MediaType.APPLICATION_JSON.toString());
            response.getWriter().write(responseJson);
        }
    }
}
