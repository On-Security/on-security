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

package org.minbox.framework.on.security.authorization.server;

import org.minbox.framework.on.security.core.authorization.data.client.*;
import org.minbox.framework.on.security.core.authorization.data.session.SecuritySession;
import org.minbox.framework.on.security.core.authorization.data.session.SecuritySessionJdbcRepository;
import org.minbox.framework.on.security.core.authorization.data.session.SecuritySessionRepository;
import org.minbox.framework.on.security.core.authorization.data.session.converter.OAuth2AuthorizationToSecuritySessionConverter;
import org.minbox.framework.on.security.core.authorization.data.session.converter.SecuritySessionToOAuth2AuthorizationConverter;
import org.minbox.framework.on.security.core.authorization.data.user.SecurityUserJdbcRepository;
import org.minbox.framework.on.security.core.authorization.data.user.SecurityUserRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

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
    private SecurityClientAuthenticationRepository clientAuthenticationRepository;
    private SecurityUserRepository userRepository;

    public JdbcOnSecurityOAuth2AuthorizationService(JdbcOperations jdbcOperations, RegisteredClientRepository registeredClientRepository) {
        this.sessionRepository = new SecuritySessionJdbcRepository(jdbcOperations);
        this.clientRepository = new SecurityClientJdbcRepository(jdbcOperations);
        this.clientAuthenticationRepository = new SecurityClientAuthenticationJdbcRepository(jdbcOperations);
        this.userRepository = new SecurityUserJdbcRepository(jdbcOperations);
        this.securitySessionToOAuth2AuthorizationConverter =
                new SecuritySessionToOAuth2AuthorizationConverter(registeredClientRepository);
        this.oAuth2AuthorizationToSecuritySessionConverter =
                new OAuth2AuthorizationToSecuritySessionConverter(clientRepository, userRepository);
    }

    @Override
    public void save(OAuth2Authorization authorization) {
        SecuritySession securitySession = oAuth2AuthorizationToSecuritySessionConverter.convert(authorization);
        SecuritySession.Builder builder = SecuritySession.with(securitySession);
        SecurityClientAuthentication clientAuthentication = clientAuthenticationRepository.findByClientId(securitySession.getClientId());
        LocalDateTime issuedAt = LocalDateTime.now();
        // set access token expire time
        // authorization_code || password || client_credentials
        if (AuthorizationGrantType.AUTHORIZATION_CODE == authorization.getAuthorizationGrantType() ||
                AuthorizationGrantType.PASSWORD == authorization.getAuthorizationGrantType() ||
                AuthorizationGrantType.CLIENT_CREDENTIALS == authorization.getAuthorizationGrantType()) {
            builder.accessTokenIssuedAt(issuedAt)
                    .accessTokenExpiresAt(issuedAt.plusSeconds(clientAuthentication.getAccessTokenExpirationTime()));
        }
        // set oidc id token expire time
        builder.oidcIdTokenIssuedAt(issuedAt)
                .oidcIdTokenExpiresAt(issuedAt.plusSeconds(clientAuthentication.getAccessTokenExpirationTime()));

        // authorization_code
        if (AuthorizationGrantType.AUTHORIZATION_CODE == authorization.getAuthorizationGrantType()) {
            builder.authorizationCodeIssuedAt(issuedAt)
                    .authorizationCodeExpiresAt(issuedAt.plusSeconds(clientAuthentication.getAuthorizationCodeExpirationTime()));
        }
        // refresh_token
        else if (AuthorizationGrantType.REFRESH_TOKEN == authorization.getAuthorizationGrantType()) {
            builder.refreshTokenIssuedAt(issuedAt)
                    .refreshTokenExpiresAt(issuedAt.plusSeconds(clientAuthentication.getRefreshTokenExpirationTime()));
        }
        SecuritySession session = builder.build();
        sessionRepository.save(session);
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        sessionRepository.removeById(authorization.getId());
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
