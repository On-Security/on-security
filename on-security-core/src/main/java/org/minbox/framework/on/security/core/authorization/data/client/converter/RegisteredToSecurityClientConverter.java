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

import org.minbox.framework.on.security.core.authorization.ClientRedirectUriType;
import org.minbox.framework.on.security.core.authorization.SignatureAlgorithm;
import org.minbox.framework.on.security.core.authorization.data.client.*;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 客户端数据转换器
 * <p>
 * 提供{@link SecurityClient}与{@link RegisteredClient}客户端数据相互转换的方法
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public final class RegisteredToSecurityClientConverter implements Converter<RegisteredClient, SecurityClient> {
    /**
     * 将{@link RegisteredClient}转换为{@link SecurityClient}
     *
     * @param registeredClient SpringSecurity认证服务器提供的客户端实例
     * @return On-Security所维护管理的客户端实例
     */
    @Override
    public SecurityClient convert(RegisteredClient registeredClient) {
        // @formatter:off
        String id = registeredClient.getId();
        SecurityClient.Builder builder = SecurityClient.withId(id);
        builder.clientId(registeredClient.getClientId())
                .enabled(true)
                .displayName(registeredClient.getClientName())
                .createTime(LocalDateTime.ofInstant(registeredClient.getClientIdIssuedAt(), ZoneId.systemDefault()));
        SecurityClientAuthentication.Builder authenticationBuilder = SecurityClientAuthentication.withId(UUID.randomUUID().toString());
        authenticationBuilder
                .clientId(id)
                .createTime(LocalDateTime.now())
                .confidential(registeredClient.getClientSettings().isRequireProofKey())
                .consentRequired(registeredClient.getClientSettings().isRequireAuthorizationConsent())
                .jwksUrl(registeredClient.getClientSettings().getJwkSetUrl())
                .authorizationMethods(registeredClient.getClientAuthenticationMethods())
                .grantTypes(registeredClient.getAuthorizationGrantTypes());
        // AccessTokenFormat
        if(!ObjectUtils.isEmpty(registeredClient.getTokenSettings().getAccessTokenFormat())) {
           authenticationBuilder.accessTokenFormat(registeredClient.getTokenSettings().getAccessTokenFormat());
        }
        // TokenEndpointAuthenticationSigningAlgorithm
        if(!ObjectUtils.isEmpty(registeredClient.getClientSettings().getTokenEndpointAuthenticationSigningAlgorithm())) {
            SignatureAlgorithm signatureAlgorithm =
                    new SignatureAlgorithm(registeredClient.getClientSettings().getTokenEndpointAuthenticationSigningAlgorithm().getName());
            authenticationBuilder.signatureAlgorithm(signatureAlgorithm);
        }
        // IdTokenSignatureAlgorithm
        if(!ObjectUtils.isEmpty(registeredClient.getTokenSettings().getIdTokenSignatureAlgorithm())) {
            SignatureAlgorithm idTokenSignatureAlgorithm =
                    new SignatureAlgorithm(registeredClient.getTokenSettings().getIdTokenSignatureAlgorithm().getName());
            authenticationBuilder.idTokenSignatureAlgorithm(idTokenSignatureAlgorithm);
        }
        if(!ObjectUtils.isEmpty(registeredClient.getTokenSettings().getAuthorizationCodeTimeToLive())) {
        authenticationBuilder
                .authorizationCodeExpirationTime((int)registeredClient.getTokenSettings().getAuthorizationCodeTimeToLive().getSeconds());
        }
        if(!ObjectUtils.isEmpty(registeredClient.getTokenSettings().getAccessTokenTimeToLive())) {
            authenticationBuilder.accessTokenExpirationTime((int)registeredClient.getTokenSettings().getAccessTokenTimeToLive().getSeconds());
        }
        if(!ObjectUtils.isEmpty(registeredClient.getTokenSettings().getRefreshTokenTimeToLive())) {
            authenticationBuilder.refreshTokenExpirationTime((int)registeredClient.getTokenSettings().getRefreshTokenTimeToLive().getSeconds());
        }
        authenticationBuilder.reuseRefreshToken(registeredClient.getTokenSettings().isReuseRefreshTokens());
        builder.authentication(authenticationBuilder.build());

        // scopes
        if(!ObjectUtils.isEmpty(registeredClient.getScopes())) {
            List<SecurityClientScope> clientScopeList = registeredClient.getScopes().stream().map(scope ->
                            SecurityClientScope.withId(UUID.randomUUID().toString())
                                    .clientId(id)
                                    .scopeName(scope)
                                    .scopeCode(scope)
                                    .createTime(LocalDateTime.now())
                                    .build())
                    .collect(Collectors.toList());
            builder.scopes(clientScopeList);
        }

        // redirectUris
        if(!ObjectUtils.isEmpty(registeredClient.getRedirectUris())) {
            List<SecurityClientRedirectUri> redirectUriList = registeredClient.getRedirectUris().stream().map(uri ->
                            SecurityClientRedirectUri.withId(UUID.randomUUID().toString())
                                    .clientId(id)
                                    .redirectType(ClientRedirectUriType.LOGIN)
                                    .redirectUri(uri)
                                    .createTime(LocalDateTime.now())
                                    .build())
                    .collect(Collectors.toList());
            builder.redirectUris(redirectUriList);
        }

        // secrets
        if(!ObjectUtils.isEmpty(registeredClient.getClientSecret())) {
            SecurityClientSecret clientSecret = SecurityClientSecret.withId(UUID.randomUUID().toString())
                    .clientId(id)
                    .clientSecret(registeredClient.getClientSecret())
                    .secretExpiresAt(LocalDateTime.ofInstant(registeredClient.getClientSecretExpiresAt(), ZoneId.systemDefault()))
                    .createTime(LocalDateTime.now())
                    .build();
            builder.secrets(Arrays.asList(clientSecret));
        }

        // @formatter:on
        return builder.build();
    }
}
