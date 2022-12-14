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

import org.minbox.framework.on.security.core.authorization.adapter.OnSecurityUserDetails;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.Collections;

/**
 * 授权码方式前置认证请求实体
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class OnSecurityPreAuthorizationCodeAuthenticationToken extends AbstractAuthenticationToken {
    private String clientId;
    private String grantType;
    private OnSecurityUserDetails userDetails;

    /**
     * 实例化OnSecurityPreAuthenticationToken构造函数
     *
     * @param clientId    客户端ID {@link RegisteredClient#getId()}
     * @param userDetails 资源所有者，用户对象实例 {@link OnSecurityUserDetails}
     */
    public OnSecurityPreAuthorizationCodeAuthenticationToken(String clientId, String grantType, OnSecurityUserDetails userDetails) {
        super(Collections.emptyList());
        this.clientId = clientId;
        this.grantType = grantType;
        this.userDetails = userDetails;
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public Object getPrincipal() {
        return this.userDetails;
    }

    public String getClientId() {
        return clientId;
    }

    public OnSecurityUserDetails getUserDetails() {
        return userDetails;
    }

    public String getGrantType() {
        return grantType;
    }
}
