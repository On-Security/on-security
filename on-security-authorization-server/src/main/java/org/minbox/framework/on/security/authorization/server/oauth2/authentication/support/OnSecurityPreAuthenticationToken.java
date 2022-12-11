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
import org.minbox.framework.on.security.core.authorization.data.client.SecurityClient;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Collections;

/**
 * 身份认证前置认证请求实体
 *
 * @author 恒宇少年
 */
public class OnSecurityPreAuthenticationToken extends AbstractAuthenticationToken {
    private String clientId;
    private RegisteredClient registeredClient;
    private Authentication principal;

    /**
     * 实例化OnSecurityPreAuthenticationToken构造函数
     *
     * @param clientId         客户端ID {@link RegisteredClient#getId()}
     * @param registeredClient 客户端实例 {@link SecurityClient}
     * @param principal        资源所有者，用户对象实例 {@link OnSecurityUserDetails}
     */
    public OnSecurityPreAuthenticationToken(String clientId, RegisteredClient registeredClient, Authentication principal) {
        super(Collections.emptyList());
        this.clientId = clientId;
        Assert.notNull(clientId, "clientId cannot be null");
        this.registeredClient = registeredClient;
        this.principal = principal;
    }

    /**
     * Creates a token with the supplied array of authorities.
     *
     * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal
     *                    represented by this authentication object.
     */
    public OnSecurityPreAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    public String getClientId() {
        return clientId;
    }

    public RegisteredClient getRegisteredClient() {
        return registeredClient;
    }
}
