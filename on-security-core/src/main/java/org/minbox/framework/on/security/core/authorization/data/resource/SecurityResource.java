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

package org.minbox.framework.on.security.core.authorization.data.resource;

import org.minbox.framework.on.security.core.authorization.ResourceType;
import org.minbox.framework.on.security.core.authorization.util.OnSecurityVersion;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * 资源基本信息
 *
 * @author 恒宇少年
 */
public class SecurityResource implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;

    private String id;
    private String regionId;
    private String clientId;
    private String name;
    private String code;
    private ResourceType type;
    private String describe;
    private boolean deleted;
    private LocalDateTime createTime;
    private Set<SecurityResourceUri> resourceUris;

    protected SecurityResource() {
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

    public String getCode() {
        return code;
    }

    public ResourceType getType() {
        return type;
    }

    public String getDescribe() {
        return describe;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public Set<SecurityResourceUri> getResourceUris() {
        return resourceUris;
    }

    public static Builder withId(String id) {
        Assert.hasText(id, "id cannot be empty");
        return new Builder(id);
    }

    public String toString() {
        // @formatter:off
        return "SecurityResource(id=" + this.id + ", regionId=" + this.regionId + ", clientId=" + this.clientId +
                ", name=" + this.name + ", code=" + this.code + ", type=" + this.type + ", describe=" + this.describe +
                ", deleted=" + this.deleted + ", createTime=" + this.createTime + ", resourceUris=" + this.resourceUris + ")";
        // @formatter:on
    }

    /**
     * {@link SecurityResource} 对象创建者
     */
    public static class Builder implements Serializable {
        private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
        private String id;
        private String regionId;
        private String clientId;
        private String name;
        private String code;
        private ResourceType type;
        private String describe;
        private boolean deleted;
        private LocalDateTime createTime;
        private Set<SecurityResourceUri> resourceUris;

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

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder type(ResourceType type) {
            this.type = type;
            return this;
        }

        public Builder describe(String describe) {
            this.describe = describe;
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

        public Builder resourceUris(Set<SecurityResourceUri> resourceUris) {
            this.resourceUris = resourceUris;
            return this;
        }

        public SecurityResource build() {
            Assert.hasText(this.regionId, "regionId cannot be empty");
            Assert.hasText(this.clientId, "clientId cannot be empty");
            Assert.hasText(this.name, "name cannot be empty");
            Assert.hasText(this.code, "code cannot be empty");
            Assert.notNull(this.type, "type cannot be null");
            Assert.notNull(this.createTime, "createTime cannot be null");
            return this.create();
        }

        private SecurityResource create() {
            SecurityResource resource = new SecurityResource();
            resource.id = this.id;
            resource.regionId = this.regionId;
            resource.clientId = this.clientId;
            resource.name = this.name;
            resource.code = this.code;
            resource.type = this.type;
            resource.describe = this.describe;
            resource.deleted = this.deleted;
            resource.createTime = this.createTime;
            resource.resourceUris = this.resourceUris;
            return resource;
        }

        public String toString() {
            // @formatter:off
            return "SecurityResource.Builder(id=" + this.id + ", regionId=" + this.regionId + ", clientId=" +
                    this.clientId + ", name=" + this.name + ", code=" + this.code + ", type=" +
                    this.type + ", describe=" + this.describe + ", deleted=" + this.deleted + ", createTime=" +
                    this.createTime + ", resourceUris=" + this.resourceUris + ")";
            // @formatter:on
        }
    }
}
