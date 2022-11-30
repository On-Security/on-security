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

package org.minbox.framework.on.security.core.authorization.data.session;

import org.minbox.framework.on.security.core.authorization.AuthorizationGrantType;
import org.minbox.framework.on.security.core.authorization.SessionState;
import org.minbox.framework.on.security.core.authorization.data.client.SecurityClientScope;
import org.minbox.framework.on.security.core.authorization.util.OnSecurityVersion;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * 认证会话
 *
 * @author 恒宇少年
 */
public class SecuritySession implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;

    private String id;
    private String regionId;
    private String clientId;
    private String userId;
    private SessionState state;
    private String attributes;
    private Set<AuthorizationGrantType> grantTypes;
    private Set<SecurityClientScope> authorizationScopes;
    private String authorizationCodeValue;
    private LocalDateTime authorizationCodeIssuedAt;
    private LocalDateTime authorizationCodeExpiresAt;
    private String authorizationCodeMetadata;
    private String accessTokenValue;
    private LocalDateTime accessTokenIssuedAt;
    private LocalDateTime accessTokenExpiresAt;
    private String accessTokenMetadata;
    private String accessTokenType;
    private Set<SecurityClientScope> accessTokenScopes;
    private String oidcIdTokenValue;
    private LocalDateTime oidcIdTokenIssuedAt;
    private LocalDateTime oidcIdTokenExpiresAt;
    private String oidcIdTokenMetadata;
    private String refreshTokenValue;
    private LocalDateTime refreshTokenIssuedAt;
    private LocalDateTime refreshTokenExpiresAt;
    private String refreshTokenMetadata;
    private LocalDateTime createTime;

    protected SecuritySession() {
    }

    public String getId() {
        return id;
    }

    public String getRegionId() {
        return regionId;
    }

    public String getClientId() {
        return clientId;
    }

    public String getUserId() {
        return userId;
    }

    public SessionState getState() {
        return state;
    }

    public String getAttributes() {
        return attributes;
    }

    public Set<AuthorizationGrantType> getGrantTypes() {
        return grantTypes;
    }

    public Set<SecurityClientScope> getAuthorizationScopes() {
        return authorizationScopes;
    }

    public String getAuthorizationCodeValue() {
        return authorizationCodeValue;
    }

    public LocalDateTime getAuthorizationCodeIssuedAt() {
        return authorizationCodeIssuedAt;
    }

    public LocalDateTime getAuthorizationCodeExpiresAt() {
        return authorizationCodeExpiresAt;
    }

    public String getAuthorizationCodeMetadata() {
        return authorizationCodeMetadata;
    }

    public String getAccessTokenValue() {
        return accessTokenValue;
    }

    public LocalDateTime getAccessTokenIssuedAt() {
        return accessTokenIssuedAt;
    }

    public LocalDateTime getAccessTokenExpiresAt() {
        return accessTokenExpiresAt;
    }

    public String getAccessTokenMetadata() {
        return accessTokenMetadata;
    }

    public String getAccessTokenType() {
        return accessTokenType;
    }

    public Set<SecurityClientScope> getAccessTokenScopes() {
        return accessTokenScopes;
    }

    public String getOidcIdTokenValue() {
        return oidcIdTokenValue;
    }

    public LocalDateTime getOidcIdTokenIssuedAt() {
        return oidcIdTokenIssuedAt;
    }

    public LocalDateTime getOidcIdTokenExpiresAt() {
        return oidcIdTokenExpiresAt;
    }

    public String getOidcIdTokenMetadata() {
        return oidcIdTokenMetadata;
    }

    public String getRefreshTokenValue() {
        return refreshTokenValue;
    }

    public LocalDateTime getRefreshTokenIssuedAt() {
        return refreshTokenIssuedAt;
    }

    public LocalDateTime getRefreshTokenExpiresAt() {
        return refreshTokenExpiresAt;
    }

    public String getRefreshTokenMetadata() {
        return refreshTokenMetadata;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public static Builder withId(String id) {
        Assert.hasText(id, "id cannot be empty");
        return new Builder(id);
    }

    public String toString() {
        // @formatter:off
        return "SecuritySession(id=" + this.id + ", regionId=" + this.regionId + ", clientId=" + this.clientId +
                ", userId=" + this.userId + ", state=" + this.state + ", attributes=" + this.attributes +
                ", grantTypes=" + this.grantTypes + ", authorizationScopes=" + this.authorizationScopes +
                ", authorizationCodeValue=" + this.authorizationCodeValue + ", authorizationCodeIssuedAt=" +
                this.authorizationCodeIssuedAt + ", authorizationCodeExpiresAt=" + this.authorizationCodeExpiresAt +
                ", authorizationCodeMetadata=" + this.authorizationCodeMetadata + ", accessTokenValue=" + this.accessTokenValue +
                ", accessTokenIssuedAt=" + this.accessTokenIssuedAt + ", accessTokenExpiresAt=" + this.accessTokenExpiresAt +
                ", accessTokenMetadata=" + this.accessTokenMetadata + ", accessTokenType=" + this.accessTokenType +
                ", accessTokenScopes=" + this.accessTokenScopes + ", oidcIdTokenValue=" + this.oidcIdTokenValue +
                ", oidcIdTokenIssuedAt=" + this.oidcIdTokenIssuedAt + ", oidcIdTokenExpiresAt=" + this.oidcIdTokenExpiresAt +
                ", oidcIdTokenMetadata=" + this.oidcIdTokenMetadata + ", refreshTokenValue=" + this.refreshTokenValue +
                ", refreshTokenIssuedAt=" + this.refreshTokenIssuedAt + ", refreshTokenExpiresAt=" + this.refreshTokenExpiresAt +
                ", refreshTokenMetadata=" + this.refreshTokenMetadata + ", createTime=" + this.createTime + ")";
        // @formatter:on
    }

    /**
     * {@link SecuritySession} 对象创建者
     */
    public static class Builder implements Serializable {
        private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
        private String id;
        private String regionId;
        private String clientId;
        private String userId;
        private SessionState state;
        private String attributes;
        private Set<AuthorizationGrantType> grantTypes;
        private Set<SecurityClientScope> authorizationScopes;
        private String authorizationCodeValue;
        private LocalDateTime authorizationCodeIssuedAt;
        private LocalDateTime authorizationCodeExpiresAt;
        private String authorizationCodeMetadata;
        private String accessTokenValue;
        private LocalDateTime accessTokenIssuedAt;
        private LocalDateTime accessTokenExpiresAt;
        private String accessTokenMetadata;
        private String accessTokenType;
        private Set<SecurityClientScope> accessTokenScopes;
        private String oidcIdTokenValue;
        private LocalDateTime oidcIdTokenIssuedAt;
        private LocalDateTime oidcIdTokenExpiresAt;
        private String oidcIdTokenMetadata;
        private String refreshTokenValue;
        private LocalDateTime refreshTokenIssuedAt;
        private LocalDateTime refreshTokenExpiresAt;
        private String refreshTokenMetadata;
        private LocalDateTime createTime;

        protected Builder(String id) {
            this.id = id;
        }

        public Builder regionId(String regionId) {
            this.regionId = regionId;
            return this;
        }

        public Builder clientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder state(SessionState state) {
            this.state = state;
            return this;
        }

        public Builder attributes(String attributes) {
            this.attributes = attributes;
            return this;
        }

        public Builder grantTypes(Set<AuthorizationGrantType> grantTypes) {
            this.grantTypes = grantTypes;
            return this;
        }

        public Builder authorizationScopes(Set<SecurityClientScope> authorizationScopes) {
            this.authorizationScopes = authorizationScopes;
            return this;
        }

        public Builder authorizationCodeValue(String authorizationCodeValue) {
            this.authorizationCodeValue = authorizationCodeValue;
            return this;
        }

        public Builder authorizationCodeIssuedAt(LocalDateTime authorizationCodeIssuedAt) {
            this.authorizationCodeIssuedAt = authorizationCodeIssuedAt;
            return this;
        }

        public Builder authorizationCodeExpiresAt(LocalDateTime authorizationCodeExpiresAt) {
            this.authorizationCodeExpiresAt = authorizationCodeExpiresAt;
            return this;
        }

        public Builder authorizationCodeMetadata(String authorizationCodeMetadata) {
            this.authorizationCodeMetadata = authorizationCodeMetadata;
            return this;
        }

        public Builder accessTokenValue(String accessTokenValue) {
            this.accessTokenValue = accessTokenValue;
            return this;
        }

        public Builder accessTokenIssuedAt(LocalDateTime accessTokenIssuedAt) {
            this.accessTokenIssuedAt = accessTokenIssuedAt;
            return this;
        }

        public Builder accessTokenExpiresAt(LocalDateTime accessTokenExpiresAt) {
            this.accessTokenExpiresAt = accessTokenExpiresAt;
            return this;
        }

        public Builder accessTokenMetadata(String accessTokenMetadata) {
            this.accessTokenMetadata = accessTokenMetadata;
            return this;
        }

        public Builder accessTokenType(String accessTokenType) {
            this.accessTokenType = accessTokenType;
            return this;
        }

        public Builder accessTokenScopes(Set<SecurityClientScope> accessTokenScopes) {
            this.accessTokenScopes = accessTokenScopes;
            return this;
        }

        public Builder oidcIdTokenValue(String oidcIdTokenValue) {
            this.oidcIdTokenValue = oidcIdTokenValue;
            return this;
        }

        public Builder oidcIdTokenIssuedAt(LocalDateTime oidcIdTokenIssuedAt) {
            this.oidcIdTokenIssuedAt = oidcIdTokenIssuedAt;
            return this;
        }

        public Builder oidcIdTokenExpiresAt(LocalDateTime oidcIdTokenExpiresAt) {
            this.oidcIdTokenExpiresAt = oidcIdTokenExpiresAt;
            return this;
        }

        public Builder oidcIdTokenMetadata(String oidcIdTokenMetadata) {
            this.oidcIdTokenMetadata = oidcIdTokenMetadata;
            return this;
        }

        public Builder refreshTokenValue(String refreshTokenValue) {
            this.refreshTokenValue = refreshTokenValue;
            return this;
        }

        public Builder refreshTokenIssuedAt(LocalDateTime refreshTokenIssuedAt) {
            this.refreshTokenIssuedAt = refreshTokenIssuedAt;
            return this;
        }

        public Builder refreshTokenExpiresAt(LocalDateTime refreshTokenExpiresAt) {
            this.refreshTokenExpiresAt = refreshTokenExpiresAt;
            return this;
        }

        public Builder refreshTokenMetadata(String refreshTokenMetadata) {
            this.refreshTokenMetadata = refreshTokenMetadata;
            return this;
        }

        public Builder createTime(LocalDateTime createTime) {
            this.createTime = createTime;
            return this;
        }

        public SecuritySession build() {
            return this.create();
        }

        private SecuritySession create() {
            SecuritySession session = new SecuritySession();
            session.id = this.id;
            session.regionId = this.regionId;
            session.clientId = this.clientId;
            session.userId = this.userId;
            session.state = this.state;
            session.attributes = this.attributes;
            session.grantTypes = this.grantTypes;
            session.authorizationScopes = this.authorizationScopes;
            session.authorizationCodeValue = this.authorizationCodeValue;
            session.authorizationCodeIssuedAt = this.authorizationCodeIssuedAt;
            session.authorizationCodeExpiresAt = this.authorizationCodeExpiresAt;
            session.authorizationCodeMetadata = this.authorizationCodeMetadata;
            session.accessTokenValue = this.accessTokenValue;
            session.accessTokenIssuedAt = this.accessTokenIssuedAt;
            session.accessTokenExpiresAt = this.accessTokenExpiresAt;
            session.accessTokenMetadata = this.accessTokenMetadata;
            session.accessTokenType = this.accessTokenType;
            session.accessTokenScopes = this.accessTokenScopes;
            session.oidcIdTokenValue = this.oidcIdTokenValue;
            session.oidcIdTokenIssuedAt = this.oidcIdTokenIssuedAt;
            session.oidcIdTokenExpiresAt = this.oidcIdTokenExpiresAt;
            session.oidcIdTokenMetadata = this.oidcIdTokenMetadata;
            session.refreshTokenValue = this.refreshTokenValue;
            session.refreshTokenIssuedAt = this.refreshTokenIssuedAt;
            session.refreshTokenExpiresAt = this.refreshTokenExpiresAt;
            session.refreshTokenMetadata = this.refreshTokenMetadata;
            session.createTime = this.createTime;
            return session;
        }

        public String toString() {
            // @formatter:off
            return "SecuritySession.Builder(id=" + this.id + ", regionId=" + this.regionId + ", clientId=" + this.clientId +
                    ", userId=" + this.userId + ", state=" + this.state + ", attributes=" + this.attributes + ", grantTypes=" +
                    this.grantTypes + ", authorizationScopes=" + this.authorizationScopes + ", authorizationCodeValue=" +
                    this.authorizationCodeValue + ", authorizationCodeIssuedAt=" + this.authorizationCodeIssuedAt +
                    ", authorizationCodeExpiresAt=" + this.authorizationCodeExpiresAt + ", authorizationCodeMetadata=" +
                    this.authorizationCodeMetadata + ", accessTokenValue=" + this.accessTokenValue + ", accessTokenIssuedAt=" +
                    this.accessTokenIssuedAt + ", accessTokenExpiresAt=" + this.accessTokenExpiresAt + ", accessTokenMetadata=" +
                    this.accessTokenMetadata + ", accessTokenType=" + this.accessTokenType + ", accessTokenScopes=" +
                    this.accessTokenScopes + ", oidcIdTokenValue=" + this.oidcIdTokenValue + ", oidcIdTokenIssuedAt=" +
                    this.oidcIdTokenIssuedAt + ", oidcIdTokenExpiresAt=" + this.oidcIdTokenExpiresAt + ", oidcIdTokenMetadata=" +
                    this.oidcIdTokenMetadata + ", refreshTokenValue=" + this.refreshTokenValue + ", refreshTokenIssuedAt=" +
                    this.refreshTokenIssuedAt + ", refreshTokenExpiresAt=" + this.refreshTokenExpiresAt + ", refreshTokenMetadata=" +
                    this.refreshTokenMetadata + ", createTime=" + this.createTime + ")";
            // @formatter:on
        }
    }
}
