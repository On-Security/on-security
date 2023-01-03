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

package org.minbox.framework.on.security.core.authorization.data.session.converter;

import org.minbox.framework.on.security.core.authorization.AccessTokenType;
import org.minbox.framework.on.security.core.authorization.SessionState;
import org.minbox.framework.on.security.core.authorization.data.application.SecurityApplication;
import org.minbox.framework.on.security.core.authorization.data.application.SecurityApplicationRepository;
import org.minbox.framework.on.security.core.authorization.data.session.SecuritySession;
import org.minbox.framework.on.security.core.authorization.data.user.SecurityUser;
import org.minbox.framework.on.security.core.authorization.data.user.SecurityUserRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * 将{@link OAuth2Authorization}转换为{@link SecuritySession}
 *
 * @author 恒宇少年
 * @see OAuth2Authorization
 * @see SecuritySession
 * @since 0.0.1
 */
public final class OAuth2AuthorizationToSecuritySessionConverter implements Converter<OAuth2Authorization, SecuritySession> {
    private SecurityApplicationRepository clientRepository;
    private SecurityUserRepository userRepository;

    public OAuth2AuthorizationToSecuritySessionConverter(SecurityApplicationRepository clientRepository, SecurityUserRepository userRepository) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
    }

    @Override
    public SecuritySession convert(OAuth2Authorization authorization) {
        // Load security client
        SecurityApplication securityApplication = clientRepository.findById(authorization.getRegisteredClientId());
        Assert.notNull(securityApplication, "Client ID: " + authorization.getRegisteredClientId() + ", no data retrieved");

        String authorizationId = authorization.getId();
        SecuritySession.Builder builder = SecuritySession.withId(authorizationId);

        if (AuthorizationGrantType.CLIENT_CREDENTIALS != authorization.getAuthorizationGrantType()) {
            // Load security user
            SecurityUser securityUser = userRepository.findByUsername(authorization.getPrincipalName());
            Assert.notNull(securityUser, "Username: " + authorization.getPrincipalName() + ", no data retrieved");
            builder.userId(securityUser.getId());
        }

        // @formatter:off
        builder
                .regionId(securityApplication.getRegionId())
                .applicationId(securityApplication.getId())
                .sessionState(SessionState.NORMAL)
                .username(authorization.getPrincipalName())
                .attributes(authorization.getAttributes())
                .authorizationGrantType(authorization.getAuthorizationGrantType())
                .authorizationScopes(authorization.getAuthorizedScopes());
        // @formatter:on

        String authorizationState = authorization.getAttribute(OAuth2ParameterNames.STATE);
        if (StringUtils.hasText(authorizationState)) {
            builder.state(authorizationState);
        }

        // Convert OAuth2AuthorizationCode
        OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCodeToken = authorization.getToken(OAuth2AuthorizationCode.class);
        if (!ObjectUtils.isEmpty(authorizationCodeToken)) {
            OAuth2AuthorizationCode oAuth2AuthorizationCode = authorizationCodeToken.getToken();
            // @formatter:off
            builder.authorizationCodeValue(oAuth2AuthorizationCode.getTokenValue())
                    .authorizationCodeIssuedAt(LocalDateTime.ofInstant(oAuth2AuthorizationCode.getIssuedAt(), ZoneId.systemDefault()))
                    .authorizationCodeExpiresAt(LocalDateTime.ofInstant(oAuth2AuthorizationCode.getExpiresAt(),ZoneId.systemDefault()))
                    .authorizationCodeMetadata(authorizationCodeToken.getMetadata());
            // @formatter:on
        }
        // Convert OAuth2AccessToken
        OAuth2Authorization.Token<OAuth2AccessToken> authorizationAccessToken = authorization.getToken(OAuth2AccessToken.class);
        if (!ObjectUtils.isEmpty(authorizationAccessToken)) {
            OAuth2AccessToken accessToken = authorizationAccessToken.getToken();
            // @formatter:off
            builder.accessTokenValue(accessToken.getTokenValue())
                    .accessTokenType(new AccessTokenType(accessToken.getTokenType().getValue()))
                    .accessTokenScopes(accessToken.getScopes())
                    .accessTokenIssuedAt(LocalDateTime.ofInstant(accessToken.getIssuedAt(), ZoneId.systemDefault()))
                    .accessTokenExpiresAt(LocalDateTime.ofInstant(accessToken.getExpiresAt(), ZoneId.systemDefault()))
                    .accessTokenMetadata(authorizationAccessToken.getMetadata());
            // @formatter:on
        }
        // Convert OidcIdToken
        OAuth2Authorization.Token<OidcIdToken> authorizationOidcIdToken = authorization.getToken(OidcIdToken.class);
        if (!ObjectUtils.isEmpty(authorizationOidcIdToken)) {
            OidcIdToken oidcIdToken = authorizationOidcIdToken.getToken();
            // @formatter:off
            builder.oidcIdTokenValue(oidcIdToken.getTokenValue())
                    .oidcIdTokenIssuedAt(LocalDateTime.ofInstant(oidcIdToken.getIssuedAt(), ZoneId.systemDefault()))
                    .oidcIdTokenExpiresAt(LocalDateTime.ofInstant(oidcIdToken.getExpiresAt(), ZoneId.systemDefault()))
                    .oidcIdTokenMetadata(authorizationOidcIdToken.getMetadata());
            // @formatter:on
        }

        // Convert OAuth2RefreshToken
        OAuth2Authorization.Token<OAuth2RefreshToken> authorizationRefreshToken = authorization.getRefreshToken();
        if (!ObjectUtils.isEmpty(authorizationRefreshToken)) {
            OAuth2RefreshToken refreshToken = authorizationRefreshToken.getToken();
            // @formatter:off
            builder.refreshTokenValue(refreshToken.getTokenValue())
                    .refreshTokenIssuedAt(LocalDateTime.ofInstant(refreshToken.getIssuedAt(), ZoneId.systemDefault()))
                    .refreshTokenExpiresAt(LocalDateTime.ofInstant(refreshToken.getExpiresAt(), ZoneId.systemDefault()))
                    .refreshTokenMetadata(authorizationRefreshToken.getMetadata());
            // @formatter:on
        }
        return builder.build();
    }
}
