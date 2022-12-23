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

package org.minbox.framework.on.security.identity.provider.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 身份供应商认证成功后处理类
 *
 * @author 恒宇少年
 * @since 0.0.3
 */
public class OnSecurityIdentityProviderAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final AuthenticationSuccessHandler delegateSuccessHandler;

    public OnSecurityIdentityProviderAuthenticationSuccessHandler() {
        this.delegateSuccessHandler = new SavedRequestAwareAuthenticationSuccessHandler();
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        this.delegateSuccessHandler.onAuthenticationSuccess(request, response, authentication);
    }
}
