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

package org.minbox.framework.on.security.core.authorization.data.user.convert;

import org.minbox.framework.on.security.core.authorization.data.user.SecurityUserAuthorizeConsent;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;

/**
 * 将{@link SecurityUserAuthorizeConsent}转换为{@link OAuth2AuthorizationConsent}
 *
 * @author 恒宇少年
 */
public class UserAuthorizeConsentToAuthorizationConsentConverter implements Converter<SecurityUserAuthorizeConsent, OAuth2AuthorizationConsent> {

    @Override
    public OAuth2AuthorizationConsent convert(SecurityUserAuthorizeConsent userAuthorizeConsent) {
        return OAuth2AuthorizationConsent.withId(userAuthorizeConsent.getClientId(), userAuthorizeConsent.getUsername())
                .authorities(authorities -> {
                    for (String authority : userAuthorizeConsent.getAuthorities()) {
                        authorities.add(new SimpleGrantedAuthority(authority));
                    }
                }).build();
    }
}
