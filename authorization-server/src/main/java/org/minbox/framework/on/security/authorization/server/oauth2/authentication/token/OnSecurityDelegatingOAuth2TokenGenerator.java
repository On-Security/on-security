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

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.minbox.framework.on.security.authorization.server.oauth2.authentication.token.customizer.OnSecuritySortTokenCustomizer;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.token.*;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;

/**
 * OnSecurity提供的代理令牌生成器
 *
 * @author 恒宇少年
 * @see OnSecurityJwtGenerator
 * @see OAuth2AccessTokenGenerator
 * @see OAuth2RefreshTokenGenerator
 * @see OAuth2TokenCustomizer
 * @see JwtEncodingContext
 * @see OAuth2TokenClaimsContext
 * @since 0.0.4
 */
public final class OnSecurityDelegatingOAuth2TokenGenerator implements OAuth2TokenGenerator<OAuth2Token> {
    private List<OAuth2TokenGenerator<? extends OAuth2Token>> tokenGenerators;

    private OnSecurityDelegatingOAuth2TokenGenerator(List<OAuth2TokenGenerator<? extends OAuth2Token>> tokenGenerators) {
        this.tokenGenerators = tokenGenerators;
    }

    public static Builder withJwtEncoder(JwtEncoder jwtEncoder) {
        Assert.notNull(jwtEncoder, "jwtEncoder cannot be null.");
        return new Builder(jwtEncoder);
    }

    public static Builder withJWKSource(JWKSource<SecurityContext> jwkSource) {
        Assert.notNull(jwkSource, "jwkSource cannot be null.");
        // Default use NimbusJwtEncoder
        JwtEncoder jwtEncoder = new NimbusJwtEncoder(jwkSource);
        return new Builder(jwtEncoder);
    }

    public static class Builder {
        private JwtEncoder jwtEncoder;
        private List<OnSecuritySortTokenCustomizer<JwtEncodingContext>> jwtCustomizers;
        private OAuth2TokenCustomizer<OAuth2TokenClaimsContext> accessTokenCustomizer;

        public Builder(JwtEncoder jwtEncoder) {
            this.jwtEncoder = jwtEncoder;
        }

        public Builder setJwtCustomizers(List<OnSecuritySortTokenCustomizer<JwtEncodingContext>> jwtCustomizers) {
            Assert.notEmpty(jwtCustomizers, "jwtCustomizers cannot be empty");
            this.jwtCustomizers = jwtCustomizers;
            return this;
        }

        public Builder setAccessTokenCustomizer(OAuth2TokenCustomizer<OAuth2TokenClaimsContext> accessTokenCustomizer) {
            Assert.notNull(accessTokenCustomizer, "accessTokenCustomizer cannot be null");
            this.accessTokenCustomizer = accessTokenCustomizer;
            return this;
        }

        private List<OAuth2TokenGenerator<? extends OAuth2Token>> getTokenGenerators() {
            OnSecurityJwtGenerator jwtGenerator = new OnSecurityJwtGenerator(this.jwtEncoder);
            if (!ObjectUtils.isEmpty(this.jwtCustomizers)) {
                jwtGenerator.setJwtCustomizers(this.jwtCustomizers);
            }
            OAuth2AccessTokenGenerator accessTokenGenerator = new OAuth2AccessTokenGenerator();
            if (this.accessTokenCustomizer != null) {
                accessTokenGenerator.setAccessTokenCustomizer(this.accessTokenCustomizer);
            }
            OAuth2RefreshTokenGenerator refreshTokenGenerator = new OAuth2RefreshTokenGenerator();
            return Arrays.asList(jwtGenerator, accessTokenGenerator, refreshTokenGenerator);
        }

        /**
         * Create new {@link OnSecurityDelegatingOAuth2TokenGenerator}
         *
         * @return The {@link OnSecurityDelegatingOAuth2TokenGenerator} new instance
         */
        public OnSecurityDelegatingOAuth2TokenGenerator build() {
            // @formatter:off
            List<OAuth2TokenGenerator<? extends OAuth2Token>> tokenGeneratorList = this.getTokenGenerators();
            OnSecurityDelegatingOAuth2TokenGenerator delegatingOAuth2TokenGenerator =
                    new OnSecurityDelegatingOAuth2TokenGenerator(tokenGeneratorList);
            // @formatter:on
            return delegatingOAuth2TokenGenerator;
        }
    }

    @Override
    public OAuth2Token generate(OAuth2TokenContext context) {
        for (OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator : this.tokenGenerators) {
            OAuth2Token token = tokenGenerator.generate(context);
            if (token != null) {
                return token;
            }
        }
        return null;
    }
}
