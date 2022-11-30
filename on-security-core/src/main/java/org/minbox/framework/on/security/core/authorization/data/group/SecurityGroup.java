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

package org.minbox.framework.on.security.core.authorization.data.group;

import org.minbox.framework.on.security.core.authorization.util.OnSecurityVersion;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * 安全组基本信息
 *
 * @author 恒宇少年
 */
public class SecurityGroup implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;

    private String id;
    private String regionId;
    private String name;
    private String describe;
    private LocalDateTime createTime;

    private Set<SecurityGroupAuthorizeClient> authorizeClients;
    private Set<SecurityGroupAuthorizeRole> authorizeRoles;

    protected SecurityGroup() {
    }

    public String getId() {
        return id;
    }

    public String getRegionId() {
        return regionId;
    }

    public String getName() {
        return name;
    }

    public String getDescribe() {
        return describe;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public Set<SecurityGroupAuthorizeClient> getAuthorizeClients() {
        return authorizeClients;
    }

    public Set<SecurityGroupAuthorizeRole> getAuthorizeRoles() {
        return authorizeRoles;
    }

    public static Builder withId(String id) {
        Assert.hasText(id, "id cannot be empty");
        return new Builder(id);
    }

    public String toString() {
        // @formatter:off
        return "SecurityGroup(id=" + this.id + ", regionId=" + this.regionId + ", name=" + this.name + ", describe=" +
                this.describe + ", createTime=" + this.createTime + ", authorizeClients=" + this.authorizeClients +
                ", authorizeRoles=" + this.authorizeRoles + ")";
        // @formatter:on
    }

    /**
     * {@link SecurityGroup} 对象创建者
     */
    public static class Builder implements Serializable {
        private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
        private String id;
        private String regionId;
        private String name;
        private String describe;
        private LocalDateTime createTime;
        private Set<SecurityGroupAuthorizeClient> authorizeClients;
        private Set<SecurityGroupAuthorizeRole> authorizeRoles;

        protected Builder(String id) {
            this.id = id;
        }

        public Builder regionId(String regionId) {
            this.regionId = regionId;
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

        public Builder createTime(LocalDateTime createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder authorizeClients(Set<SecurityGroupAuthorizeClient> authorizeClients) {
            this.authorizeClients = authorizeClients;
            return this;
        }

        public Builder authorizeRoles(Set<SecurityGroupAuthorizeRole> authorizeRoles) {
            this.authorizeRoles = authorizeRoles;
            return this;
        }

        public SecurityGroup build() {
            Assert.hasText(this.regionId, "regionId cannot be empty");
            Assert.hasText(this.name, "name cannot be empty");
            Assert.notNull(this.createTime, "createTime cannot be null");
            return this.create();
        }

        private SecurityGroup create() {
            SecurityGroup group = new SecurityGroup();
            group.id = this.id;
            group.regionId = this.regionId;
            group.name = this.name;
            group.describe = this.describe;
            group.createTime = this.createTime;
            group.authorizeClients = this.authorizeClients;
            group.authorizeRoles = this.authorizeRoles;
            return group;
        }

        public String toString() {
            // @formatter:off
            return "SecurityGroup.Builder(id=" + this.id + ", regionId=" + this.regionId + ", name=" + this.name +
                    ", describe=" + this.describe + ", createTime=" + this.createTime + ", authorizeClients=" +
                    this.authorizeClients + ", authorizeRoles=" + this.authorizeRoles + ")";
            // @formatter:on
        }
    }
}
