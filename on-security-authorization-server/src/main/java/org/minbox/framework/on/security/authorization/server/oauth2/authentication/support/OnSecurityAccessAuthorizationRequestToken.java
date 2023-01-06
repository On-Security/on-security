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

package org.minbox.framework.on.security.authorization.server.oauth2.authentication.support;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;

/**
 * 获取访问授权信息请求实体
 *
 * @author 恒宇少年
 * @since 0.0.5
 */
public class OnSecurityAccessAuthorizationRequestToken extends AbstractAuthenticationToken {
    private String accessTokenValue;

    public OnSecurityAccessAuthorizationRequestToken(String accessTokenValue) {
        super(Collections.emptyList());
        this.accessTokenValue = accessTokenValue;
    }

    public String getAccessTokenValue() {
        return accessTokenValue;
    }

    @Override
    public Object getCredentials() {
        return this.accessTokenValue;
    }

    @Override
    public Object getPrincipal() {
        return this.accessTokenValue;
    }
}
