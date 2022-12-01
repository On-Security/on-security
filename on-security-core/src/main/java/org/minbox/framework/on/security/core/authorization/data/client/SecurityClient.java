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

import org.minbox.framework.on.security.core.authorization.ClientProtocol;
import org.minbox.framework.on.security.core.authorization.ClientRedirectUriType;
import org.minbox.framework.on.security.core.authorization.util.OnSecurityVersion;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 客户端基本信息
 * <p>
 * 用于向On-Security授权服务器注册的客户端基本信息
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class SecurityClient implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
    private String id;
    private String clientId;
    private String regionId;
    private ClientProtocol protocol;
    private String displayName;
    private String describe;
    private boolean enabled;
    private boolean deleted;
    private LocalDateTime createTime;

    private SecurityClientAuthentication authentication;
    private List<SecurityClientScope> scopes;
    private List<SecurityClientRedirectUri> redirectUris;
    private List<SecurityClientSecret> secrets;

    protected SecurityClient() {
    }

    public String getId() {
        return id;
    }

    public String getClientId() {
        return clientId;
    }

    public String getRegionId() {
        return regionId;
    }

    public ClientProtocol getProtocol() {
        return protocol;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescribe() {
        return describe;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public SecurityClientAuthentication getAuthentication() {
        return authentication;
    }

    public List<SecurityClientScope> getScopes() {
        return scopes;
    }

    public List<SecurityClientRedirectUri> getRedirectUris() {
        return redirectUris;
    }

    public List<SecurityClientSecret> getSecrets() {
        return secrets;
    }

    public static Builder withId(String id) {
        Assert.hasText(id, "id cannot be empty.");
        return new Builder(id);
    }

    public String toString() {
        // @formatter:off
        return "SecurityClient(id=" + this.getId() + ", clientId=" + this.getClientId() + ", regionId=" + this.getRegionId() +
                ", protocol=" + this.getProtocol() + ", displayName=" + this.getDisplayName() + ", describe=" + this.getDescribe() +
                ", enabled=" + this.isEnabled() + ", deleted=" + this.isDeleted() + ", createTime=" + this.getCreateTime() +
                ", authentication=" + this.getAuthentication() + ", scopes=" + this.getScopes() + ", redirectUris=" +
                this.getRedirectUris() + ", secrets=" + this.getSecrets() + ")";
        // @formatter:on
    }

    /**
     * {@link SecurityClient} 对象构建者
     */
    public static class Builder implements Serializable {
        private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
        private String id;
        private String clientId;
        private String regionId;
        private ClientProtocol protocol;
        private String displayName;
        private String describe;
        private boolean enabled;
        private boolean deleted;
        private LocalDateTime createTime;
        private SecurityClientAuthentication authentication;
        private List<SecurityClientScope> scopes;
        private List<SecurityClientRedirectUri> redirectUris;
        private List<SecurityClientSecret> secrets;

        protected Builder(String id) {
            this.id = id;
        }

        public Builder clientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder regionId(String regionId) {
            this.regionId = regionId;
            return this;
        }

        public Builder protocol(ClientProtocol protocol) {
            this.protocol = protocol;
            return this;
        }

        public Builder displayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder describe(String describe) {
            this.describe = describe;
            return this;
        }

        public Builder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public Builder deleted(boolean deleted) {
            this.deleted = deleted;
            return this;
        }

        public Builder createTime(LocalDateTime createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder authentication(SecurityClientAuthentication authentication) {
            this.authentication = authentication;
            return this;
        }

        public Builder scopes(List<SecurityClientScope> scopes) {
            this.scopes = scopes;
            return this;
        }

        public Builder redirectUris(List<SecurityClientRedirectUri> redirectUris) {
            this.redirectUris = redirectUris;
            return this;
        }

        public Builder secrets(List<SecurityClientSecret> secrets) {
            this.secrets = secrets;
            return this;
        }

        public SecurityClient build() {
            Assert.hasText(this.clientId, "clientId cannot be empty.");
            Assert.hasText(this.regionId, "regionId cannot be empty.");
            Assert.notNull(this.protocol, "protocol cannot be empty.");
            Assert.hasText(this.describe, "describe cannot be empty.");
            Assert.notNull(this.authentication, "authentication cannot be null.");
            if (authentication.isConfidential()) {
                Assert.notEmpty(this.secrets, "confidential client must configure secret.");
                long notDeletedCount = this.secrets.stream().filter(s -> !s.isDeleted()).count();
                Assert.isTrue(notDeletedCount > 0, "confidential client must be configured with at least 1 valid secret");
            }
            if (authentication.getGrantTypes().contains(AuthorizationGrantType.AUTHORIZATION_CODE)) {
                Assert.notEmpty(this.redirectUris, "redirectUris cannot be empty.");
                long loginRedirectUriCount = this.redirectUris.stream().filter(ru -> ClientRedirectUriType.LOGIN.equals(ru.getRedirectType())).count();
                Assert.isTrue(loginRedirectUriCount > 0, "The client has enabled the authorization_code grant type, " +
                        "must configure a valid login redirect uri.");
            }
            return this.create();
        }

        private SecurityClient create() {
            SecurityClient client = new SecurityClient();
            client.id = this.id;
            client.clientId = this.clientId;
            client.regionId = this.regionId;
            client.protocol = this.protocol;
            client.displayName = this.displayName;
            client.describe = this.describe;
            client.enabled = this.enabled;
            client.deleted = this.deleted;
            client.createTime = this.createTime;
            client.authentication = this.authentication;
            client.scopes = this.scopes;
            client.redirectUris = this.redirectUris;
            client.secrets = this.secrets;
            return client;
        }

        public String toString() {
            // @formatter:off
            return "SecurityClient.Builder(id=" + this.id + ", clientId=" + this.clientId + ", regionId=" +
                    this.regionId + ", protocol=" + this.protocol + ", displayName=" + this.displayName + ", describe=" +
                    this.describe + ", enabled=" + this.enabled + ", deleted=" + this.deleted + ", createTime=" +
                    this.createTime + ", authentication=" + this.authentication + ", scopes=" + this.scopes + ", redirectUris=" +
                    this.redirectUris + ", secrets=" + this.secrets + ")";
            // @formatter:on
        }
    }
}
