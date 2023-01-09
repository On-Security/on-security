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

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

import java.util.Collections;

/**
 * IdP "Endpoint" 请求转发认证{@link Authentication}实例
 *
 * @author 恒宇少年
 * @since 0.0.3
 */
public class OnSecurityIdentityProviderBrokerEndpointRequestToken extends AbstractAuthenticationToken {
    private String regionId;
    private String registrationId;
    private OAuth2AuthorizationRequest authorizationRequest;

    public OnSecurityIdentityProviderBrokerEndpointRequestToken(String regionId, String registrationId, OAuth2AuthorizationRequest authorizationRequest) {
        super(Collections.emptyList());
        this.regionId = regionId;
        this.registrationId = registrationId;
        this.authorizationRequest = authorizationRequest;
    }

    public OAuth2AuthorizationRequest getAuthorizationRequest() {
        return authorizationRequest;
    }

    public String getRegionId() {
        return regionId;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return authorizationRequest.getClientId();
    }
}
