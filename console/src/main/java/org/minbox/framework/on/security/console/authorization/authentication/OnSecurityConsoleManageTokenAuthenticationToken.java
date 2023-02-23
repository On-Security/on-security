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

import org.minbox.framework.on.security.console.authorization.token.ConsoleManageToken;
import org.minbox.framework.on.security.console.authorization.token.ConsoleManageTokenContext;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;

/**
 * 控制台管理员管理令牌认证实体
 *
 * @author 恒宇少年
 * @since 0.0.9
 */
public class OnSecurityConsoleManageTokenAuthenticationToken extends AbstractAuthenticationToken {
    private ConsoleManageTokenContext tokenContext;
    private ConsoleManageToken token;

    public OnSecurityConsoleManageTokenAuthenticationToken(ConsoleManageTokenContext tokenContext, ConsoleManageToken token) {
        super(Collections.emptyList());
        this.tokenContext = tokenContext;
        this.token = token;
    }

    @Override
    public Object getCredentials() {
        return tokenContext;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    public ConsoleManageTokenContext getTokenContext() {
        return tokenContext;
    }

    public ConsoleManageToken getToken() {
        return token;
    }
}
