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
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * 身份供应商支持的
 *
 * @author 恒宇少年
 * @since 0.0.2
 */
public class SecurityIdentityProviderScope implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
    private String id;
    private String idpId;
    private String pid;
    private String name;
    private String describe;
    private boolean enabled;
    private boolean requiredAuthorization;

    protected SecurityIdentityProviderScope() {
    }

    public String getId() {
        return id;
    }

    public String getIdpId() {
        return idpId;
    }

    public String getPid() {
        return pid;
    }

    public String getName() {
        return name;
    }

    public String getDescribe() {
        return describe;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isRequiredAuthorization() {
        return requiredAuthorization;
    }

    public String toString() {
        // @formatter:off
        return "SecurityIdentityProviderScope(id=" + this.id + ", idpId=" + this.idpId + ", pid=" + this.pid + ", name=" +
                this.name + ", describe=" + this.describe + ", enabled=" + this.enabled + ", requiredAuthorization=" +
                this.requiredAuthorization + ")";
        // @formatter:on
    }

    public static Builder withId(String id) {
        Assert.hasText(id, "id cannot be empty");
        return new Builder(id);
    }

    /**
     * {@link SecurityIdentityProviderScope} 对象构建者
     */
    public static class Builder implements Serializable {
        private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
        private String id;
        private String idpId;
        private String pid;
        private String name;
        private String describe;
        private boolean enabled;
        private boolean requiredAuthorization;

        public Builder(String id) {
            this.id = id;
        }

        public Builder idpId(String idpId) {
            this.idpId = idpId;
            return this;
        }

        public Builder pid(String pid) {
            this.pid = pid;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
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

        public Builder requiredAuthorization(boolean requiredAuthorization) {
            this.requiredAuthorization = requiredAuthorization;
            return this;
        }

        public SecurityIdentityProviderScope build() {
            Assert.hasText(idpId, "idpId cannot be empty");
            return this.create();
        }

        private SecurityIdentityProviderScope create() {
            SecurityIdentityProviderScope identityProviderScope = new SecurityIdentityProviderScope();
            identityProviderScope.id = this.id;
            identityProviderScope.idpId = this.idpId;
            identityProviderScope.pid = this.pid;
            identityProviderScope.name = this.name;
            identityProviderScope.describe = this.describe;
            identityProviderScope.enabled = this.enabled;
            identityProviderScope.requiredAuthorization = this.requiredAuthorization;
            return identityProviderScope;
        }

        public String toString() {
            // @formatter:off
            return "SecurityIdentityProviderScope.Builder(id=" + this.id + ", idpId=" + this.idpId + ", pid=" +
                    this.pid + ", name=" + this.name + ", describe=" + this.describe + ", enabled=" + this.enabled +
                    ", requiredAuthorization=" + this.requiredAuthorization + ")";
            // @formatter:on
        }
    }
}
