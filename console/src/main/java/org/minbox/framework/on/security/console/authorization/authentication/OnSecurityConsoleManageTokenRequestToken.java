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

package org.minbox.framework.on.security.console.authorization.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.util.MultiValueMap;

import java.util.Collections;

/**
 * 控制台管理员管理令牌认证请求实体
 *
 * @author 恒宇少年
 * @since 0.0.9
 */
public class OnSecurityConsoleManageTokenRequestToken extends AbstractAuthenticationToken {
    private ManageTokenAuthenticateType authenticateType;
    private MultiValueMap<String, String> parameters;

    public OnSecurityConsoleManageTokenRequestToken(ManageTokenAuthenticateType authenticateType, MultiValueMap<String, String> parameters) {
        super(Collections.emptyList());
        this.authenticateType = authenticateType;
        this.parameters = parameters;
    }

    @Override
    public Object getCredentials() {
        return authenticateType;
    }

    @Override
    public Object getPrincipal() {
        return authenticateType;
    }

    public ManageTokenAuthenticateType getAuthenticateType() {
        return authenticateType;
    }

    public MultiValueMap<String, String> getParameters() {
        return parameters;
    }
}
