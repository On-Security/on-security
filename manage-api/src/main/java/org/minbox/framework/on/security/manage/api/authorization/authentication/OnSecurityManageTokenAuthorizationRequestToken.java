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

package org.minbox.framework.on.security.manage.api.authorization.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;

/**
 * 管理令牌（Manage Token）获取授权认证信息请求实体
 *
 * @author 恒宇少年
 * @since 0.1.0
 */
public class OnSecurityManageTokenAuthorizationRequestToken extends AbstractAuthenticationToken {
    private String manageToken;

    public OnSecurityManageTokenAuthorizationRequestToken(String manageToken) {
        super(Collections.emptyList());
        this.manageToken = manageToken;
    }

    @Override
    public Object getCredentials() {
        return manageToken;
    }

    @Override
    public Object getPrincipal() {
        return manageToken;
    }

    public String getManageToken() {
        return manageToken;
    }
}
