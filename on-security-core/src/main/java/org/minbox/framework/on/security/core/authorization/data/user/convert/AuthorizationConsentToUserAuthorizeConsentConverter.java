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

import org.minbox.framework.on.security.core.authorization.data.user.SecurityUser;
import org.minbox.framework.on.security.core.authorization.data.user.SecurityUserAuthorizeConsent;
import org.minbox.framework.on.security.core.authorization.data.user.SecurityUserRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * 将{@link OAuth2AuthorizationConsent} 转换为{@link SecurityUserAuthorizeConsent}
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class AuthorizationConsentToUserAuthorizeConsentConverter implements Converter<OAuth2AuthorizationConsent, SecurityUserAuthorizeConsent> {
    private SecurityUserRepository userRepository;

    public AuthorizationConsentToUserAuthorizeConsentConverter(SecurityUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public SecurityUserAuthorizeConsent convert(OAuth2AuthorizationConsent authorizationConsent) {
        SecurityUser securityUser = userRepository.findByUsername(authorizationConsent.getPrincipalName());
        Assert.notNull(securityUser, "Based on username: " + authorizationConsent.getPrincipalName() + ", no user was retrieved.");
        // @formatter:off
        SecurityUserAuthorizeConsent.Builder builder = SecurityUserAuthorizeConsent.withUserId(securityUser.getId())
                .username(authorizationConsent.getPrincipalName())
                .clientId(authorizationConsent.getRegisteredClientId())
                .authorizeTime(LocalDateTime.now());
        if (!ObjectUtils.isEmpty(authorizationConsent.getAuthorities())) {
            builder.authorities(authorizationConsent.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet()));
        }
        // @formatter:on
        return builder.build();
    }
}
