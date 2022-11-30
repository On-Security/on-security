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

import org.minbox.framework.on.security.core.authorization.util.OnSecurityVersion;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 权限授权关系
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class SecurityPermissionAuthorize implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;

    private String id;
    private String permissionId;
    private String roleId;
    private String resourceId;
    private LocalDateTime authorizeTime;

    protected SecurityPermissionAuthorize() {

    }

    public String getId() {
        return id;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public String getRoleId() {
        return roleId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public LocalDateTime getAuthorizeTime() {
        return authorizeTime;
    }

    public static Builder withId(String id) {
        Assert.hasText(id, "id cannot be empty");
        return new Builder(id);
    }

    public String toString() {
        // @formatter:off
        return "SecurityPermissionAuthorize(id=" + this.id + ", permissionId=" + this.permissionId + ", roleId=" +
                this.roleId + ", resourceId=" + this.resourceId + ", authorizeTime=" + this.authorizeTime + ")";
        // @formatter:on
    }

    /**
     * {@link SecurityPermissionAuthorize} 对象构建者
     */
    public static class Builder implements Serializable {
        private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
        private String id;
        private String permissionId;
        private String roleId;
        private String resourceId;
        private LocalDateTime authorizeTime;

        protected Builder(String id) {
            this.id = id;
        }

        public Builder permissionId(String permissionId) {
            this.permissionId = permissionId;
            return this;
        }

        public Builder roleId(String roleId) {
            this.roleId = roleId;
            return this;
        }

        public Builder resourceId(String resourceId) {
            this.resourceId = resourceId;
            return this;
        }

        public Builder authorizeTime(LocalDateTime authorizeTime) {
            this.authorizeTime = authorizeTime;
            return this;
        }

        public SecurityPermissionAuthorize build() {
            Assert.hasText(this.permissionId, "permissionId cannot be empty");
            Assert.hasText(this.roleId, "roleId cannot be empty");
            Assert.hasText(this.resourceId, "resourceId cannot be empty");
            Assert.notNull(this.authorizeTime, "authorizeTime cannot be null");

            return this.create();
        }

        private SecurityPermissionAuthorize create() {
            SecurityPermissionAuthorize permissionAuthorize = new SecurityPermissionAuthorize();
            permissionAuthorize.id = this.id;
            permissionAuthorize.permissionId = this.permissionId;
            permissionAuthorize.roleId = this.roleId;
            permissionAuthorize.resourceId = this.resourceId;
            permissionAuthorize.authorizeTime = this.authorizeTime;
            return permissionAuthorize;
        }

        public String toString() {
            // @formatter:off
            return "SecurityPermissionAuthorize.Builder(id=" + this.id + ", permissionId=" + this.permissionId +
                    ", roleId=" + this.roleId + ", resourceId=" + this.resourceId + ", authorizeTime=" + this.authorizeTime + ")";
            // @formatter:on
        }
    }
}
