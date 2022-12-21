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
import org.springframework.security.oauth2.client.registration.ClientRegistration;

/**
 * {@link ClientRegistration}适配转换类
 *
 * @author 恒宇少年
 * @see SecurityRegionIdentityProvider
 * @see SecurityIdentityProvider
 * @since 0.0.2
 */
public final class ClientRegistrationAdapter {
    private SecurityRegionIdentityProvider regionIdentityProvider;
    private SecurityIdentityProvider identityProvider;

    public ClientRegistrationAdapter(SecurityRegionIdentityProvider regionIdentityProvider, SecurityIdentityProvider identityProvider) {
        this.regionIdentityProvider = regionIdentityProvider;
        this.identityProvider = identityProvider;
    }

    public SecurityRegionIdentityProvider getRegionIdentityProvider() {
        return regionIdentityProvider;
    }

    public SecurityIdentityProvider getIdentityProvider() {
        return identityProvider;
    }
}
