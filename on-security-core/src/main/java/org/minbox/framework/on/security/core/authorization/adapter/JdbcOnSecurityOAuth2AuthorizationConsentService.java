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

package org.minbox.framework.on.security.core.authorization.adapter;

import org.minbox.framework.on.security.core.authorization.data.user.*;
import org.minbox.framework.on.security.core.authorization.data.user.convert.AuthorizationConsentToUserAuthorizeConsentConverter;
import org.minbox.framework.on.security.core.authorization.data.user.convert.UserAuthorizeConsentToAuthorizationConsentConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.util.Assert;

/**
 * On-Security提供的{@link OAuth2AuthorizationConsentService}JDBC数据存储实现类
 *
 * @author 恒宇少年
 * @see OAuth2AuthorizationConsentService
 * @see JdbcOperations
 * @since 0.0.1
 */
public class JdbcOnSecurityOAuth2AuthorizationConsentService implements OAuth2AuthorizationConsentService {
    private SecurityUserAuthorizeConsentRepository userAuthorizeConsentRepository;
    private SecurityUserRepository userRepository;
    private Converter<OAuth2AuthorizationConsent, SecurityUserAuthorizeConsent> authorizationConsentToUserAuthorizeConsentConverter;
    private Converter<SecurityUserAuthorizeConsent, OAuth2AuthorizationConsent> userAuthorizeConsentToAuthorizationConsentConverter;

    public JdbcOnSecurityOAuth2AuthorizationConsentService(JdbcOperations jdbcOperations) {
        Assert.notNull(jdbcOperations, "jdbcOperations cannot be null");
        this.userAuthorizeConsentRepository = new SecurityUserAuthorizeConsentJdbcRepository(jdbcOperations);
        this.userRepository = new SecurityUserJdbcRepository(jdbcOperations);
        this.authorizationConsentToUserAuthorizeConsentConverter =
                new AuthorizationConsentToUserAuthorizeConsentConverter(userRepository);
        this.userAuthorizeConsentToAuthorizationConsentConverter = new UserAuthorizeConsentToAuthorizationConsentConverter();
    }

    @Override
    public void save(OAuth2AuthorizationConsent authorizationConsent) {
        Assert.notNull(authorizationConsent, "authorizationConsent cannot be null");
        Assert.hasText(authorizationConsent.getPrincipalName(), "principalName cannot be empty");
        Assert.hasText(authorizationConsent.getRegisteredClientId(), "registeredClientId cannot be empty");
        Assert.notEmpty(authorizationConsent.getAuthorities(), "authorities cannot be empty");
        SecurityUserAuthorizeConsent userAuthorizeConsent =
                authorizationConsentToUserAuthorizeConsentConverter.convert(authorizationConsent);
        userAuthorizeConsentRepository.save(userAuthorizeConsent);
    }

    @Override
    public void remove(OAuth2AuthorizationConsent authorizationConsent) {
        Assert.notNull(authorizationConsent, "authorizationConsent cannot be null");
        Assert.hasText(authorizationConsent.getPrincipalName(), "principalName cannot be empty");
        Assert.hasText(authorizationConsent.getRegisteredClientId(), "registeredClientId cannot be empty");
        Assert.notEmpty(authorizationConsent.getAuthorities(), "authorities cannot be empty");
        SecurityUser securityUser = userRepository.findByUsername(authorizationConsent.getPrincipalName());
        Assert.notNull(securityUser, "Based on username: " + authorizationConsent.getPrincipalName() + ", no user was retrieved.");
        userAuthorizeConsentRepository.remove(securityUser.getId(), authorizationConsent.getRegisteredClientId());
    }

    @Override
    public OAuth2AuthorizationConsent findById(String registeredClientId, String principalName) {
        Assert.hasText(principalName, "principalName cannot be empty");
        Assert.hasText(registeredClientId, "registeredClientId cannot be empty");
        SecurityUser securityUser = userRepository.findByUsername(principalName);
        Assert.notNull(securityUser, "Based on username: " + principalName + ", no user was retrieved.");
        SecurityUserAuthorizeConsent userAuthorizeConsent =
                userAuthorizeConsentRepository.findByUserIdAndClientId(securityUser.getId(), registeredClientId);
        return userAuthorizeConsent != null ? userAuthorizeConsentToAuthorizationConsentConverter.convert(userAuthorizeConsent)
                : null;
    }
}
