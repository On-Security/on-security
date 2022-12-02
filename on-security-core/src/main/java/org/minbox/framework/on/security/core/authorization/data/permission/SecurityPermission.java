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

package org.minbox.framework.on.security.core.authorization.data.permission;

import org.minbox.framework.on.security.core.authorization.PermissionMatchMethod;
import org.minbox.framework.on.security.core.authorization.util.OnSecurityVersion;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * 权限基本信息
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class SecurityPermission implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;

    private String id;
    private String regionId;
    private String clientId;
    private String name;
    private PermissionMatchMethod matchMethod;
    private String describe;
    private LocalDateTime createTime;
    private boolean deleted;
    private Set<SecurityPermissionAuthorize> authorizes;

    protected SecurityPermission() {
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

    public String getName() {
        return name;
    }

    public PermissionMatchMethod getMatchMethod() {
        return matchMethod;
    }

    public String getDescribe() {
        return describe;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public Set<SecurityPermissionAuthorize> getAuthorizes() {
        return authorizes;
    }

    public static Builder withId(String id) {
        Assert.hasText(id, "id cannot be empty");
        return new Builder(id);
    }

    public String toString() {
        // @formatter:off
        return "SecurityPermission(id=" + this.id + ", regionId=" + this.regionId + ", clientId=" + this.clientId +
                ", name=" + this.name + ", matchMethod=" + this.matchMethod + ", describe=" + this.describe +
                ", createTime=" + this.createTime + ", deleted=" + this.deleted + ", authorizes=" + this.authorizes + ")";
        // @formatter:on
    }

    /**
     * {@link SecurityPermission} 对象创建者
     */
    public static class Builder implements Serializable {
        private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
        private String id;
        private String regionId;
        private String clientId;
        private String name;
        private PermissionMatchMethod matchMethod;
        private String describe;
        private LocalDateTime createTime;
        private boolean deleted;
        private Set<SecurityPermissionAuthorize> authorizes;

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

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder matchMethod(PermissionMatchMethod matchMethod) {
            this.matchMethod = matchMethod;
            return this;
        }

        public Builder describe(String describe) {
            this.describe = describe;
            return this;
        }

        public Builder createTime(LocalDateTime createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder deleted(boolean deleted) {
            this.deleted = deleted;
            return this;
        }

        public Builder authorizes(Set<SecurityPermissionAuthorize> authorizes) {
            this.authorizes = authorizes;
            return this;
        }

        public SecurityPermission build() {
            Assert.hasText(this.regionId, "regionId cannot be empty");
            Assert.hasText(this.clientId, "clientId cannot be empty");
            Assert.hasText(this.name, "name cannot be empty");
            Assert.notNull(this.matchMethod, "matchMethod cannot be null");
            Assert.notNull(this.createTime, "createTime cannot be null");
            return this.create();
        }

        private SecurityPermission create() {
            SecurityPermission permission = new SecurityPermission();
            permission.id = this.id;
            permission.regionId = this.regionId;
            permission.clientId = this.clientId;
            permission.name = this.name;
            permission.matchMethod = this.matchMethod;
            permission.describe = this.describe;
            permission.createTime = this.createTime;
            permission.deleted = this.deleted;
            permission.authorizes = this.authorizes;
            return permission;
        }

        public String toString() {
            // @formatter:off
            return "SecurityPermission.Builder(id=" + this.id + ", regionId=" + this.regionId + ", clientId=" +
                    this.clientId + ", name=" + this.name + ", matchMethod=" + this.matchMethod + ", describe=" +
                    this.describe + ", createTime=" + this.createTime + ", deleted=" + this.deleted + ", authorizes=" + this.authorizes + ")";
            // @formatter:on
        }
    }
}
