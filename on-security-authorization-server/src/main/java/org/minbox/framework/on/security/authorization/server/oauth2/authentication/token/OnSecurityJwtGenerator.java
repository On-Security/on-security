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

package org.minbox.framework.on.security.authorization.server.oauth2.authentication.token;

import org.minbox.framework.on.security.authorization.server.oauth2.authentication.token.customizer.OnSecuritySortTokenCustomizer;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithm;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.token.*;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 使用Jwt方式生成令牌，代替{@link JwtGenerator}使用
 * 支持设置多个{@link OAuth2TokenCustomizer}来自定义令牌的{@link JwtClaimsSet}
 *
 * @author 恒宇少年
 * @see Jwt
 * @see JwtGenerator
 * @see JwtClaimsSet
 * @see JwtEncoder
 * @see OAuth2TokenContext
 * @see OAuth2AccessToken
 * @see OidcIdToken
 * @see OAuth2TokenCustomizer
 * @since 0.0.4
 */
public final class OnSecurityJwtGenerator implements OAuth2TokenGenerator<Jwt> {
    private final JwtEncoder jwtEncoder;
    private List<OnSecuritySortTokenCustomizer<JwtEncodingContext>> jwtCustomizers;

    public OnSecurityJwtGenerator(JwtEncoder jwtEncoder) {
        Assert.notNull(jwtEncoder, "jwtEncoder cannot be null");
        this.jwtEncoder = jwtEncoder;
    }

    @Override
    public Jwt generate(OAuth2TokenContext context) {
        if (context.getTokenType() == null ||
                (!OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType()) &&
                        !OidcParameterNames.ID_TOKEN.equals(context.getTokenType().getValue()))) {
            return null;
        }
        if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType()) &&
                !OAuth2TokenFormat.SELF_CONTAINED.equals(context.getRegisteredClient().getTokenSettings().getAccessTokenFormat())) {
            return null;
        }

        String issuer = null;
        if (context.getAuthorizationServerContext() != null) {
            issuer = context.getAuthorizationServerContext().getIssuer();
        }
        RegisteredClient registeredClient = context.getRegisteredClient();

        Instant issuedAt = Instant.now();
        Instant expiresAt;
        JwsAlgorithm jwsAlgorithm = SignatureAlgorithm.RS256;
        if (OidcParameterNames.ID_TOKEN.equals(context.getTokenType().getValue())) {
            expiresAt = issuedAt.plus(30, ChronoUnit.MINUTES);
            if (registeredClient.getTokenSettings().getIdTokenSignatureAlgorithm() != null) {
                jwsAlgorithm = registeredClient.getTokenSettings().getIdTokenSignatureAlgorithm();
            }
        } else {
            expiresAt = issuedAt.plus(registeredClient.getTokenSettings().getAccessTokenTimeToLive());
        }

        // @formatter:off
        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder();
        if (StringUtils.hasText(issuer)) {
            claimsBuilder.issuer(issuer);
        }
        claimsBuilder
                .subject(context.getPrincipal().getName())
                .audience(Collections.singletonList(registeredClient.getClientId()))
                .issuedAt(issuedAt)
                .expiresAt(expiresAt);
        if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
            claimsBuilder.notBefore(issuedAt);
            if (!CollectionUtils.isEmpty(context.getAuthorizedScopes())) {
                claimsBuilder.claim(OAuth2ParameterNames.SCOPE, context.getAuthorizedScopes());
            }
        } else if (OidcParameterNames.ID_TOKEN.equals(context.getTokenType().getValue())) {
            claimsBuilder.claim(IdTokenClaimNames.AZP, registeredClient.getClientId());
            if (AuthorizationGrantType.AUTHORIZATION_CODE.equals(context.getAuthorizationGrantType())) {
                OAuth2AuthorizationRequest authorizationRequest = context.getAuthorization().getAttribute(
                        OAuth2AuthorizationRequest.class.getName());
                String nonce = (String) authorizationRequest.getAdditionalParameters().get(OidcParameterNames.NONCE);
                if (StringUtils.hasText(nonce)) {
                    claimsBuilder.claim(IdTokenClaimNames.NONCE, nonce);
                }
            }
        }
        // @formatter:on

        JwsHeader.Builder jwsHeaderBuilder = JwsHeader.with(jwsAlgorithm);

        if (this.jwtCustomizers != null) {
            // @formatter:off
            JwtEncodingContext.Builder jwtContextBuilder = JwtEncodingContext.with(jwsHeaderBuilder, claimsBuilder)
                    .registeredClient(context.getRegisteredClient())
                    .principal(context.getPrincipal())
                    .authorizationServerContext(context.getAuthorizationServerContext())
                    .authorizedScopes(context.getAuthorizedScopes())
                    .tokenType(context.getTokenType())
                    .authorizationGrantType(context.getAuthorizationGrantType());
            if (context.getAuthorization() != null) {
                jwtContextBuilder.authorization(context.getAuthorization());
            }
            if (context.getAuthorizationGrant() != null) {
                jwtContextBuilder.authorizationGrant(context.getAuthorizationGrant());
            }
            // @formatter:on

            JwtEncodingContext jwtContext = jwtContextBuilder.build();
            this.jwtCustomizers.stream().forEach(jwtCustomizer -> jwtCustomizer.customize(jwtContext));
        }

        JwsHeader jwsHeader = jwsHeaderBuilder.build();
        JwtClaimsSet claims = claimsBuilder.build();

        Jwt jwt = this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims));

        return jwt;
    }

    public void setJwtCustomizers(List<OnSecuritySortTokenCustomizer<JwtEncodingContext>> jwtCustomizers) {
        Assert.notEmpty(jwtCustomizers, "jwtCustomizers cannot be empty");
        jwtCustomizers.sort(Comparator.comparingInt((OnSecuritySortTokenCustomizer o) -> o.getOrder()));
        this.jwtCustomizers = jwtCustomizers;
    }
}
