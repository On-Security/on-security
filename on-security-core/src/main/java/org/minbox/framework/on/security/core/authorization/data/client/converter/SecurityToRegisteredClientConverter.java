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

package org.minbox.framework.on.security.core.authorization.data.client.converter;

import org.minbox.framework.on.security.core.authorization.data.client.*;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.util.ObjectUtils;

import java.time.Duration;
import java.time.ZoneId;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 客户端数据转换器
 * <p>
 * 提供{@link RegisteredClient}与{@link SecurityClient}客户端数据相互转换的方法
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class SecurityToRegisteredClientConverter implements Converter<SecurityClient, RegisteredClient> {
    @Override
    public RegisteredClient convert(SecurityClient securityClient) {
        RegisteredClient.Builder builder = RegisteredClient.withId(securityClient.getId());
        // @formatter:off
        builder.clientId(securityClient.getClientId())
                .clientIdIssuedAt(securityClient.getCreateTime().atZone(ZoneId.systemDefault()).toInstant())
                .clientName(securityClient.getDisplayName());
        // @formatter:on
        // secret
        if (!ObjectUtils.isEmpty(securityClient.getSecrets())) {
            Optional<SecurityClientSecret> securityClientSecretOptional =
                    securityClient.getSecrets().stream().filter(s -> !s.isDeleted()).findFirst();
            if (securityClientSecretOptional.isPresent()) {
                SecurityClientSecret clientSecret = securityClientSecretOptional.get();
                // @formatter:off
                builder.clientSecret(clientSecret.getClientSecret())
                        .clientSecretExpiresAt(clientSecret.getSecretExpiresAt().atZone(ZoneId.systemDefault()).toInstant());
                // @formatter:on
            }
        }

        // scopes
        if (!ObjectUtils.isEmpty(securityClient.getScopes())) {
            // @formatter:off
            Set<String> scopeSet = securityClient.getScopes().stream()
                    .map(SecurityClientScope::getScopeCode)
                    .collect(Collectors.toSet());
            builder.scopes((scopes) -> scopes.addAll(scopeSet));
            // @formatter:on
        }

        // redirect uris
        if (!ObjectUtils.isEmpty(securityClient.getRedirectUris())) {
            // @formatter:off
            Set<String> redirectUriSet = securityClient.getRedirectUris().stream()
                    .map(SecurityClientRedirectUri::getRedirectUri)
                    .collect(Collectors.toSet());
            builder.redirectUris((redirectUris) -> redirectUris.addAll(redirectUriSet));
            // @formatter:on
        }

        // authentication
        if (!ObjectUtils.isEmpty(securityClient.getAuthentication())) {
            SecurityClientAuthentication clientAuthentication = securityClient.getAuthentication();
            // authentication methods
            if (!ObjectUtils.isEmpty(clientAuthentication.getAuthorizationMethods())) {
                // @formatter:off
                Set<ClientAuthenticationMethod> authenticationMethodSet =
                        clientAuthentication.getAuthorizationMethods().stream()
                                .map(am -> new ClientAuthenticationMethod(am.getValue()))
                                .collect(Collectors.toSet());
                builder.clientAuthenticationMethods((authenticationMethods) -> authenticationMethods.addAll(authenticationMethodSet));
                // @formatter:on
            }
            // authentication grant types
            if (!ObjectUtils.isEmpty(clientAuthentication.getGrantTypes())) {
                // @formatter:off
                Set<AuthorizationGrantType> authorizationGrantTypeSet =
                        clientAuthentication.getGrantTypes().stream()
                                .map(am -> new AuthorizationGrantType(am.getValue()))
                                .collect(Collectors.toSet());
                builder.authorizationGrantTypes((authorizationGrantTypes) -> authorizationGrantTypes.addAll(authorizationGrantTypeSet));
                // @formatter:on
            }
            // @formatter:off
            ClientSettings.Builder clientSettingsBuilder =
                    ClientSettings.builder()
                            .requireProofKey(clientAuthentication.isConfidential())
                            .requireAuthorizationConsent(clientAuthentication.isConsentRequired());
            if (!ObjectUtils.isEmpty(clientAuthentication.getJwksUrl())) {
                clientSettingsBuilder.jwkSetUrl(clientAuthentication.getJwksUrl());
            }
            if (!ObjectUtils.isEmpty(clientAuthentication.getSignatureAlgorithm())) {
                clientSettingsBuilder.tokenEndpointAuthenticationSigningAlgorithm(() ->
                        clientAuthentication.getSignatureAlgorithm().getValue());
            }
            // @formatter:on
            builder.clientSettings(clientSettingsBuilder.build());

            // @formatter:off
            TokenSettings tokenSettings =
                    TokenSettings.builder()
                            .authorizationCodeTimeToLive(Duration.ofSeconds(clientAuthentication.getAuthorizationCodeExpirationTime()))
                            .accessTokenTimeToLive(Duration.ofSeconds(clientAuthentication.getAccessTokenExpirationTime()))
                            .reuseRefreshTokens(clientAuthentication.isReuseRefreshToken())
                            .refreshTokenTimeToLive(Duration.ofSeconds(clientAuthentication.getRefreshTokenExpirationTime()))
                            .idTokenSignatureAlgorithm(SignatureAlgorithm.from(clientAuthentication.getIdTokenSignatureAlgorithm().getValue()))
                            .build();
            // @formatter:on
            builder.tokenSettings(tokenSettings);
        }
        return builder.build();
    }
}
