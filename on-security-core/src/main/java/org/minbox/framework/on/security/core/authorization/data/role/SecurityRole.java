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

package org.minbox.framework.on.security.core.authorization.data.role;

import org.minbox.framework.on.security.core.authorization.util.OnSecurityVersion;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 角色基本信息
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class SecurityRole implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;

    private String id;
    private String regionId;
    private String clientId;
    private String name;
    private String code;
    private String describe;
    private boolean deleted;
    private LocalDateTime createTime;

    protected SecurityRole() {
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

    public String getDescribe() {
        return describe;
    }

    public boolean isDeleted() {
        return deleted;
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
        return "SecurityRole(id=" + this.id + ", regionId=" + this.regionId + ", clientId=" + this.clientId + ", name=" +
                this.name + ", code=" + this.code + ", describe=" + this.describe + ", deleted=" + this.deleted +
                ", createTime=" + this.createTime + ")";
        // @formatter:on
    }

    /**
     * {@link SecurityRole} 对象创建者
     */
    public static class Builder implements Serializable {
        private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
        private String id;
        private String regionId;
        private String clientId;
        private String name;
        private String code;
        private String describe;
        private boolean deleted;
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

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder code(String code) {
            this.code = code;
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

        public SecurityRole build() {
            Assert.hasText(this.regionId, "regionId cannot be empty");
            Assert.hasText(this.clientId, "clientId cannot be empty");
            Assert.hasText(this.name, "name cannot be empty");
            Assert.hasText(this.code, "code cannot be empty");
            Assert.notNull(this.createTime, "createTime cannot be null");
            return this.create();
        }

        private SecurityRole create() {
            SecurityRole securityRole = new SecurityRole();
            securityRole.id = this.id;
            securityRole.regionId = this.regionId;
            securityRole.clientId = this.clientId;
            securityRole.name = this.name;
            securityRole.code = this.code;
            securityRole.describe = this.describe;
            securityRole.deleted = this.deleted;
            securityRole.createTime = this.createTime;
            return securityRole;
        }

        public String toString() {
            // @formatter:off
            return "SecurityRole.Builder(id=" + this.id + ", regionId=" + this.regionId + ", clientId=" + this.clientId +
                    ", name=" + this.name + ", code=" + this.code + ", describe=" + this.describe + ", deleted=" +
                    this.deleted + ", createTime=" + this.createTime + ")";
            // @formatter:on
        }
    }
}
