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

package org.minbox.framework.on.security.core.authorization.data.application;

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
public class SecurityApplication implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
    private String id;
    private String applicationId;
    private String regionId;
    private ClientProtocol protocol;
    private String displayName;
    private String describe;
    private boolean enabled;
    private boolean deleted;
    private LocalDateTime createTime;

    private SecurityApplicationAuthentication authentication;
    private List<SecurityApplicationScope> scopes;
    private List<SecurityApplicationRedirectUri> redirectUris;
    private List<SecurityApplicationSecret> secrets;

    protected SecurityApplication() {
    }

    public String getId() {
        return id;
    }

    public String getApplicationId() {
        return applicationId;
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

    public SecurityApplicationAuthentication getAuthentication() {
        return authentication;
    }

    public List<SecurityApplicationScope> getScopes() {
        return scopes;
    }

    public List<SecurityApplicationRedirectUri> getRedirectUris() {
        return redirectUris;
    }

    public List<SecurityApplicationSecret> getSecrets() {
        return secrets;
    }

    public static Builder withId(String id) {
        Assert.hasText(id, "id cannot be empty.");
        return new Builder(id);
    }

    public static Builder with(SecurityApplication client) {
        Assert.notNull(client, "client cannot be empty");
        return new Builder(client);
    }

    public String toString() {
        // @formatter:off
        return "SecurityClient(id=" + this.getId() + ", applicationId=" + this.getApplicationId() + ", regionId=" + this.getRegionId() +
                ", protocol=" + this.getProtocol() + ", displayName=" + this.getDisplayName() + ", describe=" + this.getDescribe() +
                ", enabled=" + this.isEnabled() + ", deleted=" + this.isDeleted() + ", createTime=" + this.getCreateTime() +
                ", authentication=" + this.getAuthentication() + ", scopes=" + this.getScopes() + ", redirectUris=" +
                this.getRedirectUris() + ", secrets=" + this.getSecrets() + ")";
        // @formatter:on
    }

    /**
     * {@link SecurityApplication} 对象构建者
     */
    public static class Builder implements Serializable {
        private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
        private String id;
        private String applicationId;
        private String regionId;
        private ClientProtocol protocol;
        private String displayName;
        private String describe;
        private boolean enabled;
        private boolean deleted;
        private LocalDateTime createTime;
        private SecurityApplicationAuthentication authentication;
        private List<SecurityApplicationScope> scopes;
        private List<SecurityApplicationRedirectUri> redirectUris;
        private List<SecurityApplicationSecret> secrets;

        protected Builder(String id) {
            this.id = id;
        }

        protected Builder(SecurityApplication client) {
            this.id = client.id;
            this.applicationId = client.applicationId;
            this.regionId = client.regionId;
            this.protocol = client.protocol;
            this.displayName = client.displayName;
            this.describe = client.describe;
            this.enabled = client.enabled;
            this.deleted = client.deleted;
            this.createTime = client.createTime;
            this.authentication = client.authentication;
            this.scopes = client.scopes;
            this.redirectUris = client.redirectUris;
            this.secrets = client.secrets;
        }

        public Builder applicationId(String applicationId) {
            this.applicationId = applicationId;
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

        public Builder authentication(SecurityApplicationAuthentication authentication) {
            this.authentication = authentication;
            return this;
        }

        public Builder scopes(List<SecurityApplicationScope> scopes) {
            this.scopes = scopes;
            return this;
        }

        public Builder redirectUris(List<SecurityApplicationRedirectUri> redirectUris) {
            this.redirectUris = redirectUris;
            return this;
        }

        public Builder secrets(List<SecurityApplicationSecret> secrets) {
            this.secrets = secrets;
            return this;
        }

        public SecurityApplication build() {
            Assert.hasText(this.applicationId, "applicationId cannot be empty.");
            if (this.authentication != null) {
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
            }
            return this.create();
        }

        private SecurityApplication create() {
            SecurityApplication client = new SecurityApplication();
            client.id = this.id;
            client.applicationId = this.applicationId;
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
            return "SecurityClient.Builder(id=" + this.id + ", applicationId=" + this.applicationId + ", regionId=" +
                    this.regionId + ", protocol=" + this.protocol + ", displayName=" + this.displayName + ", describe=" +
                    this.describe + ", enabled=" + this.enabled + ", deleted=" + this.deleted + ", createTime=" +
                    this.createTime + ", authentication=" + this.authentication + ", scopes=" + this.scopes + ", redirectUris=" +
                    this.redirectUris + ", secrets=" + this.secrets + ")";
            // @formatter:on
        }
    }
}
