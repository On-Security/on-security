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

import org.minbox.framework.on.security.core.authorization.data.session.SecuritySession;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.time.ZoneId;
import java.util.Map;

/**
 * 将{@link SecuritySession}转换为{@link OAuth2Authorization}
 *
 * @author 恒宇少年
 * @see SecuritySession
 * @see OAuth2Authorization
 * @since 0.0.1
 */
public class SecuritySessionToOAuth2AuthorizationConverter implements Converter<SecuritySession, OAuth2Authorization> {
    private RegisteredClientRepository registeredClientRepository;

    public SecuritySessionToOAuth2AuthorizationConverter(RegisteredClientRepository registeredClientRepository) {
        this.registeredClientRepository = registeredClientRepository;
    }

    @Override
    public OAuth2Authorization convert(SecuritySession session) {
        RegisteredClient registeredClient = registeredClientRepository.findById(session.getClientId());
        // @formatter:off
        OAuth2Authorization.Builder builder = OAuth2Authorization
                .withRegisteredClient(registeredClient)
                .id(session.getId())
                .principalName(session.getUsername())
                .authorizationGrantType(session.getAuthorizationGrantType())
                .authorizedScopes(session.getAuthorizationScopes())
                .attributes(attibuteMap -> attibuteMap.putAll(session.getAttributes()));
        // @formatter:on

        // Convert OAuth2AuthorizationCode
        if (!ObjectUtils.isEmpty(session.getAuthorizationCodeValue())) {
            OAuth2AuthorizationCode auth2AuthorizationCode = new OAuth2AuthorizationCode(
                    session.getAuthorizationCodeValue(),
                    session.getAuthorizationCodeIssuedAt().atZone(ZoneId.systemDefault()).toInstant(),
                    session.getAuthorizationCodeExpiresAt().atZone(ZoneId.systemDefault()).toInstant());
            if (!ObjectUtils.isEmpty(session.getAuthorizationCodeMetadata())) {
                builder.token(auth2AuthorizationCode, (metadata) -> metadata.putAll(session.getAuthorizationCodeMetadata()));
            } else {
                builder.token(auth2AuthorizationCode);
            }
        }

        // Convert OAuth2AccessToken
        if (!ObjectUtils.isEmpty(session.getAccessTokenValue())) {
            // @formatter:off
            OAuth2AccessToken accessToken = new OAuth2AccessToken(
                    OAuth2AccessToken.TokenType.BEARER,
                    session.getAccessTokenValue(),
                    session.getAccessTokenIssuedAt().atZone(ZoneId.systemDefault()).toInstant(),
                    session.getAccessTokenExpiresAt().atZone(ZoneId.systemDefault()).toInstant(),
                    session.getAuthorizationScopes());
            // @formatter:on
            // Put all access token metadata
            if (!ObjectUtils.isEmpty(session.getAccessTokenMetadata())) {
                builder.token(accessToken, (metadata) -> metadata.putAll(session.getAccessTokenMetadata()));
            } else {
                builder.accessToken(accessToken);
            }
        }
        // Convert OAuth2RefreshToken
        if (!ObjectUtils.isEmpty(session.getRefreshTokenValue())) {
            // @formatter:off
            OAuth2RefreshToken refreshToken = new OAuth2RefreshToken(
                    session.getRefreshTokenValue(),
                    session.getRefreshTokenIssuedAt().atZone(ZoneId.systemDefault()).toInstant(),
                    session.getRefreshTokenExpiresAt().atZone(ZoneId.systemDefault()).toInstant());
            // @formatter:on
            // Put all refresh token metadata
            if (!ObjectUtils.isEmpty(session.getRefreshTokenMetadata())) {
                builder.token(refreshToken, (metadata) -> metadata.putAll(session.getRefreshTokenMetadata()));
            } else {
                builder.refreshToken(refreshToken);
            }
        }
        // Convert OidcIdToken
        if (!ObjectUtils.isEmpty(session.getOidcIdTokenValue())) {
            Map<String, Object> oidcIdTokenMetadata = session.getOidcIdTokenMetadata();
            // @formatter:off
            Map<String, Object> oidcIdTokenClaims =
                    oidcIdTokenMetadata.containsKey(OAuth2Authorization.Token.CLAIMS_METADATA_NAME) ?
                            (Map<String, Object>) oidcIdTokenMetadata.get(OAuth2Authorization.Token.CLAIMS_METADATA_NAME) :
                            CollectionUtils.newHashMap(0);
            OidcIdToken oidcIdToken = new OidcIdToken(
                    session.getOidcIdTokenValue(),
                    session.getOidcIdTokenIssuedAt().atZone(ZoneId.systemDefault()).toInstant(),
                    session.getOidcIdTokenExpiresAt().atZone(ZoneId.systemDefault()).toInstant(),
                    oidcIdTokenClaims);
            // @formatter:on
            // Put all oidc id token metadata
            builder.token(oidcIdToken, (metadata) -> metadata.putAll(session.getOidcIdTokenMetadata()));
        }
        return builder.build();
    }
}
