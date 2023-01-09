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

package org.minbox.framework.on.security.identity.provider.convert;

import org.minbox.framework.on.security.core.authorization.data.idp.SecurityIdentityProvider;
import org.minbox.framework.on.security.core.authorization.data.region.SecurityRegionIdentityProvider;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.util.ObjectUtils;

/**
 * {@link ClientRegistration}数据实体转换类
 *
 * @author 恒宇少年
 * @since 0.0.2
 */
public final class ClientRegistrationConvert implements Converter<ClientRegistrationAdapter, ClientRegistration> {
    @Override
    public ClientRegistration convert(ClientRegistrationAdapter source) {
        SecurityRegionIdentityProvider regionIdentityProvider = source.getRegionIdentityProvider();
        SecurityIdentityProvider identityProvider = source.getIdentityProvider();
        if (regionIdentityProvider == null || identityProvider == null || ObjectUtils.isEmpty(regionIdentityProvider.getRegistrationId())) {
            return null;
        }
        // @formatter:off
        ClientRegistration.Builder builder = ClientRegistration
                .withRegistrationId(regionIdentityProvider.getRegistrationId())
                .clientId(regionIdentityProvider.getApplicationId())
                .clientSecret(regionIdentityProvider.getApplicationSecret())
                .clientName(regionIdentityProvider.getUniqueIdentifier())
                .clientAuthenticationMethod(identityProvider.getClientAuthenticationMethod())
                .authorizationGrantType(identityProvider.getAuthorizationGrantType())
                .redirectUri(regionIdentityProvider.getCallbackUrl())
                .authorizationUri(identityProvider.getAuthorizationUri())
                .tokenUri(identityProvider.getTokenUri())
                .issuerUri(identityProvider.getIssuerUri())
                .userInfoUri(identityProvider.getUserInfoUri())
                .userInfoAuthenticationMethod(identityProvider.getUserInfoAuthenticationMethod())
                .userNameAttributeName(identityProvider.getUserNameAttribute())
                .jwkSetUri(identityProvider.getJwkSetUri())
                .scope(regionIdentityProvider.getAuthorizationScopes())
                .providerConfigurationMetadata(regionIdentityProvider.getExpandMetadata());
        // @formatter:on
        return builder.build();
    }
}
