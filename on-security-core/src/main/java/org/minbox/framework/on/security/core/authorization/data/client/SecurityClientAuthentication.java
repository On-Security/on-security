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

package org.minbox.framework.on.security.core.authorization.data.client;

import org.minbox.framework.on.security.core.authorization.AuthorizationGrantType;
import org.minbox.framework.on.security.core.authorization.AuthorizationMethod;
import org.minbox.framework.on.security.core.authorization.SignatureAlgorithm;
import org.minbox.framework.on.security.core.authorization.util.OnSecurityVersion;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * 客户端认证配置信息
 *
 * @author 恒宇少年
 */
public class SecurityClientAuthentication implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
    private String id;
    private String clientId;
    private boolean confidential;
    private String jwksUrl;
    private boolean consentRequired;
    private SignatureAlgorithm signatureAlgorithm;
    private Set<AuthorizationMethod> authorizationMethods;
    private Set<AuthorizationGrantType> grantTypes;
    private SignatureAlgorithm idTokenSignatureAlgorithm;
    private int authorizationCodeExpirationTime;
    private int accessTokenExpirationTime;
    private int refreshTokenExpirationTime;
    private boolean reuseRefreshToken;
    private LocalDateTime createTime;

    protected SecurityClientAuthentication() {
    }

    public String getId() {
        return id;
    }

    public String getClientId() {
        return clientId;
    }

    public boolean isConfidential() {
        return confidential;
    }

    public String getJwksUrl() {
        return jwksUrl;
    }

    public boolean isConsentRequired() {
        return consentRequired;
    }

    public SignatureAlgorithm getSignatureAlgorithm() {
        return signatureAlgorithm;
    }

    public Set<AuthorizationMethod> getAuthorizationMethods() {
        return authorizationMethods;
    }

    public Set<AuthorizationGrantType> getGrantTypes() {
        return grantTypes;
    }

    public SignatureAlgorithm getIdTokenSignatureAlgorithm() {
        return idTokenSignatureAlgorithm;
    }

    public int getAuthorizationCodeExpirationTime() {
        return authorizationCodeExpirationTime;
    }

    public int getAccessTokenExpirationTime() {
        return accessTokenExpirationTime;
    }

    public int getRefreshTokenExpirationTime() {
        return refreshTokenExpirationTime;
    }

    public boolean isReuseRefreshToken() {
        return reuseRefreshToken;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public static Builder withId(String id) {
        Assert.hasText(id, "id cannot be empty.");
        return new Builder(id);
    }

    public String toString() {
        // @formatter:off
        return "SecurityClientAuthentication(id=" + this.getId() + ", clientId=" + this.getClientId() + ", confidential=" +
                this.isConfidential() + ", jwksUrl=" + this.getJwksUrl() + ", consentRequired=" + this.isConsentRequired() +
                ", signatureAlgorithm=" + this.getSignatureAlgorithm() + ", authorizationMethods=" + this.getAuthorizationMethods() +
                ", grantTypes=" + this.getGrantTypes() + ", idTokenSignatureAlgorithm=" + this.getIdTokenSignatureAlgorithm() +
                ", authorizationCodeExpirationTime=" + this.getAuthorizationCodeExpirationTime() + ", accessTokenExpirationTime=" +
                this.getAccessTokenExpirationTime() + ", refreshTokenExpirationTime=" + this.getRefreshTokenExpirationTime() +
                ", reuseRefreshToken=" + this.isReuseRefreshToken() + ", createTime=" + this.getCreateTime() + ")";
        // @formatter:on
    }

    /**
     * {@link SecurityClientAuthentication} 对象构建者
     */
    public static class Builder implements Serializable {
        private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
        private String id;
        private String clientId;
        private boolean confidential;
        private String jwksUrl;
        private boolean consentRequired;
        private SignatureAlgorithm signatureAlgorithm;
        private Set<AuthorizationMethod> authorizationMethods;
        private Set<AuthorizationGrantType> grantTypes;
        private SignatureAlgorithm idTokenSignatureAlgorithm;
        private int authorizationCodeExpirationTime;
        private int accessTokenExpirationTime;
        private int refreshTokenExpirationTime;
        private boolean reuseRefreshToken;
        private LocalDateTime createTime;

        protected Builder(String id) {
            this.id = id;
        }

        public Builder clientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder confidential(boolean confidential) {
            this.confidential = confidential;
            return this;
        }

        public Builder jwksUrl(String jwksUrl) {
            this.jwksUrl = jwksUrl;
            return this;
        }

        public Builder consentRequired(boolean consentRequired) {
            this.consentRequired = consentRequired;
            return this;
        }

        public Builder signatureAlgorithm(SignatureAlgorithm signatureAlgorithm) {
            this.signatureAlgorithm = signatureAlgorithm;
            return this;
        }

        public Builder authorizationMethods(Set<AuthorizationMethod> authorizationMethods) {
            this.authorizationMethods = authorizationMethods;
            return this;
        }

        public Builder grantTypes(Set<AuthorizationGrantType> grantTypes) {
            this.grantTypes = grantTypes;
            return this;
        }

        public Builder idTokenSignatureAlgorithm(SignatureAlgorithm idTokenSignatureAlgorithm) {
            this.idTokenSignatureAlgorithm = idTokenSignatureAlgorithm;
            return this;
        }

        public Builder authorizationCodeExpirationTime(int authorizationCodeExpirationTime) {
            this.authorizationCodeExpirationTime = authorizationCodeExpirationTime;
            return this;
        }

        public Builder accessTokenExpirationTime(int accessTokenExpirationTime) {
            this.accessTokenExpirationTime = accessTokenExpirationTime;
            return this;
        }

        public Builder refreshTokenExpirationTime(int refreshTokenExpirationTime) {
            this.refreshTokenExpirationTime = refreshTokenExpirationTime;
            return this;
        }

        public Builder reuseRefreshToken(boolean reuseRefreshToken) {
            this.reuseRefreshToken = reuseRefreshToken;
            return this;
        }

        public Builder createTime(LocalDateTime createTime) {
            this.createTime = createTime;
            return this;
        }

        public SecurityClientAuthentication build() {
            Assert.hasText(this.clientId, "clientId cannot be empty.");
            Assert.notEmpty(this.authorizationMethods, "authorizationMethods cannot be empty.");
            Assert.notEmpty(this.grantTypes, "grantTypes cannot be empty.");
            Assert.isTrue(this.authorizationCodeExpirationTime > 0, "authorizationCodeExpirationTime must be greater than 0");
            Assert.isTrue(this.accessTokenExpirationTime > 0, "accessTokenExpirationTime must be greater than 0");
            Assert.isTrue(this.refreshTokenExpirationTime > 0, "refreshTokenExpirationTime must be greater than 0");
            return this.create();
        }

        private SecurityClientAuthentication create() {
            SecurityClientAuthentication clientAuthentication = new SecurityClientAuthentication();
            clientAuthentication.id = this.id;
            clientAuthentication.clientId = this.clientId;
            clientAuthentication.confidential = this.confidential;
            clientAuthentication.jwksUrl = this.jwksUrl;
            clientAuthentication.authorizationMethods = this.authorizationMethods;
            clientAuthentication.signatureAlgorithm = this.signatureAlgorithm;
            clientAuthentication.grantTypes = this.grantTypes;
            clientAuthentication.consentRequired = this.consentRequired;
            clientAuthentication.idTokenSignatureAlgorithm = this.idTokenSignatureAlgorithm;
            clientAuthentication.authorizationCodeExpirationTime = this.authorizationCodeExpirationTime;
            clientAuthentication.accessTokenExpirationTime = this.accessTokenExpirationTime;
            clientAuthentication.refreshTokenExpirationTime = this.refreshTokenExpirationTime;
            clientAuthentication.reuseRefreshToken = this.reuseRefreshToken;
            clientAuthentication.createTime = this.createTime;
            return clientAuthentication;
        }

        public String toString() {
            // @formatter:off
            return "SecurityClientAuthentication.Builder(id=" + this.id + ", clientId=" + this.clientId + ", confidential=" +
                    this.confidential + ", jwksUrl=" + this.jwksUrl + ", consentRequired=" + this.consentRequired + ", signatureAlgorithm=" +
                    this.signatureAlgorithm + ", authorizationMethods=" + this.authorizationMethods + ", grantTypes=" + this.grantTypes +
                    ", idTokenSignatureAlgorithm=" + this.idTokenSignatureAlgorithm + ", authorizationCodeExpirationTime=" +
                    this.authorizationCodeExpirationTime + ", accessTokenExpirationTime=" + this.accessTokenExpirationTime +
                    ", refreshTokenExpirationTime=" + this.refreshTokenExpirationTime + ", reuseRefreshToken=" + this.reuseRefreshToken +
                    ", createTime=" + this.createTime + ")";
            // @formatter:on
        }
    }
}
