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

import org.minbox.framework.on.security.authorization.server.oauth2.web.OnSecurityOAuth2UsernamePasswordAuthenticationFilter;
import org.minbox.framework.on.security.authorization.server.oauth2.web.converter.OnSecurityOAuth2UsernamePasswordAuthenticationConverter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.util.Assert;

import java.util.Collections;

/**
 * 用户名密码认证方式请求认证封装实体
 *
 * @author 恒宇少年
 * @see OnSecurityOAuth2UsernamePasswordAuthenticationFilter
 * @see OnSecurityOAuth2UsernamePasswordAuthenticationConverter
 * @see OnSecurityOAuth2UsernamePasswordAuthenticationProvider
 * @since 0.0.1
 */
public class OnSecurityOAuth2UsernamePasswordAuthenticationToken extends AbstractAuthenticationToken {
    private String username;
    private String password;
    private UserDetails userDetails;
    private RegisteredClient registeredClient;

    public OnSecurityOAuth2UsernamePasswordAuthenticationToken(String username, String password, RegisteredClient registeredClient, boolean authenticated) {
        super(Collections.emptyList());
        Assert.hasText(username, "username cannot be empty");
        Assert.hasText(password, "password cannot be empty");
        Assert.notNull(registeredClient, "registeredClient cannot be null");
        setAuthenticated(authenticated);
        this.username = username;
        this.password = password;
        this.registeredClient = registeredClient;
    }

    public void setUserDetails(UserDetails userDetails) {
        Assert.notNull(userDetails, "userDetails cannot be null");
        this.userDetails = userDetails;
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public Object getPrincipal() {
        return this.userDetails == null ? this.username : this.userDetails;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public RegisteredClient getRegisteredClient() {
        return registeredClient;
    }
}
