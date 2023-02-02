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

package org.minbox.framework.on.security.core.authorization.data.region;

import org.minbox.framework.on.security.core.authorization.util.OnSecurityVersion;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

/**
 * 安全域下身份供应商配置类
 *
 * @author 恒宇少年
 * @since 0.0.2
 */
public class SecurityRegionIdentityProvider implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
    private String id;
    private String regionId;
    private String idpId;
    private String uniqueIdentifier;
    private String registrationId;
    private String applicationId;
    private String clientSecret;
    private String callbackUrl;
    private Set<String> authorizationScopes;
    private Map<String, Object> expandMetadata;
    private String describe;
    private LocalDateTime createTime;

    protected SecurityRegionIdentityProvider() {
    }

    public String getId() {
        return id;
    }

    public String getRegionId() {
        return regionId;
    }

    public String getIdpId() {
        return idpId;
    }

    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getApplicationSecret() {
        return clientSecret;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public Set<String> getAuthorizationScopes() {
        return authorizationScopes;
    }

    public String getDescribe() {
        return describe;
    }

    public Map<String, Object> getExpandMetadata() {
        return expandMetadata;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public String toString() {
        // @formatter:off
        return "SecurityRegionIdentityProvider(id=" + this.id + ", regionId=" + this.regionId + ", idpId=" + this.idpId +
                ", uniqueIdentifier=" + this.uniqueIdentifier + ", registrationId=" + this.registrationId +
                ", applicationId=" + this.applicationId + ", clientSecret=" + this.clientSecret + ", callbackUrl=" + this.callbackUrl +
                ", authorizationScopes=" + this.authorizationScopes + ", describe=" + this.describe +
                ", expandMetadata=" + this.expandMetadata + ", createTime=" + this.createTime + ")";
        // @formatter:on
    }

    public static Builder withId(String id) {
        Assert.hasText(id, "id cannot be empty");
        return new Builder(id);
    }

    /**
     * {@link SecurityRegionIdentityProvider} 对象构建者
     */
    public static class Builder implements Serializable {
        private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;

        private String id;
        private String regionId;
        private String idpId;
        private String uniqueIdentifier;
        private String registrationId;
        private String applicationId;
        private String clientSecret;
        private String callbackUrl;
        private Set<String> authorizationScopes;
        private String describe;
        private Map<String, Object> expandMetadata;
        private LocalDateTime createTime;

        public Builder(String id) {
            this.id = id;
        }

        public Builder regionId(String regionId) {
            this.regionId = regionId;
            return this;
        }

        public Builder idpId(String idpId) {
            this.idpId = idpId;
            return this;
        }

        public Builder uniqueIdentifier(String uniqueIdentifier) {
            this.uniqueIdentifier = uniqueIdentifier;
            return this;
        }

        public Builder registrationId(String registrationId) {
            this.registrationId = registrationId;
            return this;
        }

        public Builder applicationId(String applicationId) {
            this.applicationId = applicationId;
            return this;
        }

        public Builder clientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
            return this;
        }

        public Builder callbackUrl(String callbackUrl) {
            this.callbackUrl = callbackUrl;
            return this;
        }

        public Builder authorizationScopes(Set<String> authorizationScopes) {
            this.authorizationScopes = authorizationScopes;
            return this;
        }

        public Builder describe(String describe) {
            this.describe = describe;
            return this;
        }

        public Builder expandMetadata(Map<String, Object> expandMetadata) {
            this.expandMetadata = expandMetadata;
            return this;
        }

        public Builder createTime(LocalDateTime createTime) {
            this.createTime = createTime;
            return this;
        }

        public SecurityRegionIdentityProvider build() {
            Assert.hasText(regionId, "regionId cannot be empty");
            Assert.hasText(idpId, "idpId cannot be empty");
            Assert.hasText(uniqueIdentifier, "uniqueIdentifier cannot be empty");
            Assert.hasText(registrationId, "registrationId cannot be empty");
            Assert.hasText(applicationId, "applicationId cannot be empty");
            Assert.hasText(clientSecret, "clientSecret cannot be empty");
            Assert.hasText(callbackUrl, "callbackUrl cannot be empty");
            this.createTime = this.createTime == null ? LocalDateTime.now() : this.createTime;
            return this.create();
        }

        private SecurityRegionIdentityProvider create() {
            SecurityRegionIdentityProvider identityProviderConfig = new SecurityRegionIdentityProvider();
            identityProviderConfig.id = this.id;
            identityProviderConfig.regionId = this.regionId;
            identityProviderConfig.idpId = this.idpId;
            identityProviderConfig.uniqueIdentifier = this.uniqueIdentifier;
            identityProviderConfig.registrationId = this.registrationId;
            identityProviderConfig.applicationId = this.applicationId;
            identityProviderConfig.clientSecret = this.clientSecret;
            identityProviderConfig.callbackUrl = this.callbackUrl;
            identityProviderConfig.authorizationScopes = this.authorizationScopes;
            identityProviderConfig.describe = this.describe;
            identityProviderConfig.expandMetadata = this.expandMetadata;
            identityProviderConfig.createTime = this.createTime;
            return identityProviderConfig;
        }

        public String toString() {
            // @formatter:off
            return "SecurityRegionIdentityProvider.Builder(id=" + this.id + ", regionId=" + this.regionId + ", idpId=" + this.idpId +
                    ", uniqueIdentifier=" + this.uniqueIdentifier + ", registrationId=" + this.registrationId +
                    ", applicationId=" + this.applicationId + ", clientSecret=" + this.clientSecret + ", callbackUrl=" + this.callbackUrl +
                    ", authorizationScopes=" + this.authorizationScopes + ", describe=" + this.describe +
                    ", expandMetadata=" + this.expandMetadata + ", createTime=" + this.createTime + ")";
            // @formatter:on
        }
    }
}
