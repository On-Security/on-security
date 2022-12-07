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

package org.minbox.framework.on.security.core.authorization.jackson2;

import org.minbox.framework.on.security.core.authorization.adapter.OnSecurityUserDetails;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;

/**
 * On-Security提供的Jackson Module
 *
 * @author 恒宇少年
 * @see OAuth2AuthorizationServerJackson2Module
 * @since 0.0.1
 */
public class OnSecurityAuthorizationServerJackson2Module extends OAuth2AuthorizationServerJackson2Module {
    @Override
    public void setupModule(SetupContext context) {
        super.setupModule(context);
        context.setMixInAnnotations(OnSecurityUserDetails.class, OnSecurityUserDetailsMixin.class);
    }
}
