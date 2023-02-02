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

package org.minbox.framework.on.security.core.authorization.data.idp;

import org.minbox.framework.on.security.core.authorization.util.OnSecurityVersion;
import org.springframework.security.oauth2.core.AuthenticationMethod;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 身份供应商（Identity Provider，IdP）实体类
 *
 * @author 恒宇少年
 * @since 0.0.2
 */
public class SecurityIdentityProvider implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
    private String id;
    private String regionId;
    private String displayName;
    private String protocolId;
    private String describe;
    private String issuerUri;
    private String authorizationUri;
    private String tokenUri;
    private String userInfoUri;
    private AuthenticationMethod userInfoAuthenticationMethod;
    private String userNameAttribute;
    private String endSessionUri;
    private String jwkSetUri;
    private ClientAuthenticationMethod clientAuthenticationMethod;
    private AuthorizationGrantType authorizationGrantType;
    private boolean enabled;
    private LocalDateTime createTime;

    protected SecurityIdentityProvider() {
    }

    public String getId() {
        return id;
    }

    public String getRegionId() {
        return regionId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getProtocolId() {
        return protocolId;
    }

    public String getDescribe() {
        return describe;
    }

    public String getIssuerUri() {
        return issuerUri;
    }

    public String getAuthorizationUri() {
        return authorizationUri;
    }

    public String getTokenUri() {
        return tokenUri;
    }

    public String getUserInfoUri() {
        return userInfoUri;
    }

    public AuthenticationMethod getUserInfoAuthenticationMethod() {
        return userInfoAuthenticationMethod;
    }

    public String getUserNameAttribute() {
        return userNameAttribute;
    }

    public String getEndSessionUri() {
        return endSessionUri;
    }

    public String getJwkSetUri() {
        return jwkSetUri;
    }

    public ClientAuthenticationMethod getClientAuthenticationMethod() {
        return clientAuthenticationMethod;
    }

    public AuthorizationGrantType getAuthorizationGrantType() {
        return authorizationGrantType;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public String toString() {
        // @formatter:off
        return "SecurityIdentityProvider(id=" + this.getId() + ", regionId=" + this.getRegionId() + ", displayName=" +
                this.getDisplayName() + ", protocolId=" + this.getProtocolId() + ", describe=" +
                this.getDescribe() + ", issuerUri=" + this.getIssuerUri() + ", authorizationUri=" + this.getAuthorizationUri() +
                ", tokenUri=" + this.getTokenUri() + ", userInfoUri=" + this.getUserInfoUri() + ", userInfoAuthenticationMethod=" +
                this.getUserInfoAuthenticationMethod() + ", userNameAttribute=" + this.getUserNameAttribute() + ", endSessionUri=" +
                this.getEndSessionUri() + ", jwkSetUri=" + this.getJwkSetUri() + ", clientAuthenticationMethod=" +
                this.getClientAuthenticationMethod() + ", authorizationGrantType=" + this.getAuthorizationGrantType() +
                ", enabled=" + this.isEnabled() + ", createTime=" + this.getCreateTime() + ")";
        // @formatter:on
    }

    public static Builder withId(String id) {
        Assert.hasText(id, "Id cannot be empty.");
        return new Builder(id);
    }

    /**
     * {@link SecurityIdentityProvider} 对象构建者
     */
    public static class Builder implements Serializable {
        private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
        private String id;
        private String regionId;
        private String displayName;
        private String protocolId;
        private String describe;
        private String issuerUri;
        private String authorizationUri;
        private String tokenUri;
        private String userInfoUri;
        private AuthenticationMethod userInfoAuthenticationMethod;
        private String userNameAttribute;
        private String endSessionUri;
        private String jwkSetUri;
        private ClientAuthenticationMethod clientAuthenticationMethod;
        private AuthorizationGrantType authorizationGrantType;
        private boolean enabled;
        private LocalDateTime createTime;

        public Builder(String id) {
            this.id = id;
        }

        public Builder regionId(String regionId) {
            this.regionId = regionId;
            return this;
        }

        public Builder displayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder protocolId(String protocolId) {
            this.protocolId = protocolId;
            return this;
        }

        public Builder describe(String describe) {
            this.describe = describe;
            return this;
        }

        public Builder issuerUri(String issuerUri) {
            this.issuerUri = issuerUri;
            return this;
        }

        public Builder authorizationUri(String authorizationUri) {
            this.authorizationUri = authorizationUri;
            return this;
        }

        public Builder tokenUri(String tokenUri) {
            this.tokenUri = tokenUri;
            return this;
        }

        public Builder userInfoUri(String userInfoUri) {
            this.userInfoUri = userInfoUri;
            return this;
        }

        public Builder userInfoAuthenticationMethod(AuthenticationMethod userInfoAuthenticationMethod) {
            this.userInfoAuthenticationMethod = userInfoAuthenticationMethod;
            return this;
        }

        public Builder userNameAttribute(String userNameAttribute) {
            this.userNameAttribute = userNameAttribute;
            return this;
        }

        public Builder endSessionUri(String endSessionUri) {
            this.endSessionUri = endSessionUri;
            return this;
        }

        public Builder jwkSetUri(String jwkSetUri) {
            this.jwkSetUri = jwkSetUri;
            return this;
        }

        public Builder clientAuthenticationMethod(ClientAuthenticationMethod clientAuthenticationMethod) {
            this.clientAuthenticationMethod = clientAuthenticationMethod;
            return this;
        }

        public Builder authorizationGrantType(AuthorizationGrantType authorizationGrantType) {
            this.authorizationGrantType = authorizationGrantType;
            return this;
        }

        public Builder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public Builder createTime(LocalDateTime createTime) {
            this.createTime = createTime;
            return this;
        }

        public SecurityIdentityProvider build() {
            Assert.hasText(this.displayName, "displayName cannot be empty");
            Assert.hasText(this.protocolId, "protocolId cannot be empty");
            Assert.hasText(this.authorizationUri, "authorizationUri cannot be empty");
            Assert.hasText(this.tokenUri, "tokenUri cannot be empty");
            Assert.notNull(this.userInfoAuthenticationMethod, "userInfoAuthenticationMethod cannot be null");
            Assert.hasText(this.userNameAttribute, "userNameAttribute cannot be empty");
            Assert.notNull(this.clientAuthenticationMethod, "clientAuthenticationMethod cannot be null");
            this.createTime = this.createTime == null ? LocalDateTime.now() : this.createTime;
            return this.create();
        }

        private SecurityIdentityProvider create() {
            SecurityIdentityProvider identityProvider = new SecurityIdentityProvider();
            identityProvider.id = this.id;
            identityProvider.regionId = this.regionId;
            identityProvider.displayName = this.displayName;
            identityProvider.protocolId = this.protocolId;
            identityProvider.describe = this.describe;
            identityProvider.issuerUri = this.issuerUri;
            identityProvider.authorizationUri = this.authorizationUri;
            identityProvider.tokenUri = this.tokenUri;
            identityProvider.userInfoUri = this.userInfoUri;
            identityProvider.userInfoAuthenticationMethod = this.userInfoAuthenticationMethod;
            identityProvider.userNameAttribute = this.userNameAttribute;
            identityProvider.endSessionUri = this.endSessionUri;
            identityProvider.jwkSetUri = this.jwkSetUri;
            identityProvider.clientAuthenticationMethod = this.clientAuthenticationMethod;
            identityProvider.authorizationGrantType = this.authorizationGrantType;
            identityProvider.enabled = this.enabled;
            identityProvider.createTime = this.createTime;
            return identityProvider;
        }

        public String toString() {
            // @formatter:off
            return "SecurityIdentityProvider.Builder(id=" + this.id + ", regionId=" + this.regionId + ", displayName=" +
                    this.displayName + ", protocolId=" + this.protocolId + ", describe=" +
                    this.describe + ", issuerUri=" + this.issuerUri + ", authorizationUri=" + this.authorizationUri +
                    ", tokenUri=" + this.tokenUri + ", userInfoUri=" + this.userInfoUri + ", userInfoAuthenticationMethod=" +
                    this.userInfoAuthenticationMethod + ", userNameAttribute=" + this.userNameAttribute + ", endSessionUri=" +
                    this.endSessionUri + ", jwkSetUri=" + this.jwkSetUri + ", clientAuthenticationMethod=" +
                    this.clientAuthenticationMethod + ", authorizationGrantType=" + this.authorizationGrantType +
                    ", enabled=" + this.enabled + ", createTime=" + this.createTime + ")";
            // @formatter:on
        }
    }
}
