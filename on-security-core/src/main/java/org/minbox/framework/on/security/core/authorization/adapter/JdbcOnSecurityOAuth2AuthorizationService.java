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

import org.minbox.framework.on.security.core.authorization.SessionState;
import org.minbox.framework.on.security.core.authorization.data.client.SecurityClient;
import org.minbox.framework.on.security.core.authorization.data.client.SecurityClientJdbcRepository;
import org.minbox.framework.on.security.core.authorization.data.client.SecurityClientRepository;
import org.minbox.framework.on.security.core.authorization.data.session.SecuritySession;
import org.minbox.framework.on.security.core.authorization.data.session.SecuritySessionJdbcRepository;
import org.minbox.framework.on.security.core.authorization.data.session.SecuritySessionRepository;
import org.minbox.framework.on.security.core.authorization.data.session.converter.OAuth2AuthorizationToSecuritySessionConverter;
import org.minbox.framework.on.security.core.authorization.data.session.converter.SecuritySessionToOAuth2AuthorizationConverter;
import org.minbox.framework.on.security.core.authorization.data.user.SecurityUser;
import org.minbox.framework.on.security.core.authorization.data.user.SecurityUserJdbcRepository;
import org.minbox.framework.on.security.core.authorization.data.user.SecurityUserRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.util.Assert;

/**
 * On-Security提供的{@link OAuth2AuthorizationService}JDBC数据存储实现类
 *
 * @author 恒宇少年
 * @see OAuth2Authorization
 * @since 0.0.1
 */
public class JdbcOnSecurityOAuth2AuthorizationService implements OAuth2AuthorizationService {
    private Converter<OAuth2Authorization, SecuritySession> oAuth2AuthorizationToSecuritySessionConverter;
    private Converter<SecuritySession, OAuth2Authorization> securitySessionToOAuth2AuthorizationConverter;
    private SecuritySessionRepository sessionRepository;
    private SecurityClientRepository clientRepository;
    private SecurityUserRepository userRepository;

    public JdbcOnSecurityOAuth2AuthorizationService(JdbcOperations jdbcOperations) {
        this.oAuth2AuthorizationToSecuritySessionConverter = new OAuth2AuthorizationToSecuritySessionConverter();
        this.securitySessionToOAuth2AuthorizationConverter = new SecuritySessionToOAuth2AuthorizationConverter();
        this.sessionRepository = new SecuritySessionJdbcRepository(jdbcOperations);
        this.clientRepository = new SecurityClientJdbcRepository(jdbcOperations);
        this.userRepository = new SecurityUserJdbcRepository(jdbcOperations);
    }

    @Override
    public void save(OAuth2Authorization authorization) {
        // Load security client
        SecurityClient securityClient = clientRepository.findById(authorization.getRegisteredClientId());
        Assert.notNull(securityClient, "Client ID: " + authorization.getRegisteredClientId() + ", no data retrieved");

        // Load security user
        SecurityUser securityUser = userRepository.findByUsername(authorization.getPrincipalName());
        Assert.notNull(securityUser, "Username: " + authorization.getPrincipalName() + ", no data retrieved");

        SecuritySession securitySession = oAuth2AuthorizationToSecuritySessionConverter.convert(authorization);
        SecuritySession.Builder builder = SecuritySession.with(securitySession);
        // @formatter:off
        builder.regionId(securityClient.getRegionId())
                .clientId(securityClient.getId())
                .userId(securityUser.getId())
                .state(SessionState.NORMAL);
        // @formatter:on

        sessionRepository.save(builder.build());
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        SecuritySession securitySession = oAuth2AuthorizationToSecuritySessionConverter.convert(authorization);
        sessionRepository.remove(securitySession);
    }

    @Override
    public OAuth2Authorization findById(String id) {
        Assert.hasText(id, "id cannot be empty");
        SecuritySession securitySession = sessionRepository.findById(id);
        Assert.notNull(securitySession, "Session ID: " + id + ", no data retrieved");
        return securitySessionToOAuth2AuthorizationConverter.convert(securitySession);
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        Assert.hasText(token, "token cannot be empty");
        Assert.notNull(tokenType, "tokenType cannot be null");
        SecuritySession securitySession = sessionRepository.findByToken(token, tokenType);
        Assert.notNull(securitySession, "Token: " + token + ", Token Type: " + tokenType.getValue() + ", no session data retrieved");
        return securitySessionToOAuth2AuthorizationConverter.convert(securitySession);
    }
}
