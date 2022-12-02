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

/**
 * 安全组授权角色关系
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class SecurityGroupAuthorizeRole implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;

    private String groupId;
    private String roleId;
    private LocalDateTime authorizeTime;

    protected SecurityGroupAuthorizeRole() {
    }

    public String getGroupId() {
        return groupId;
    }

    public String getRoleId() {
        return roleId;
    }

    public LocalDateTime getAuthorizeTime() {
        return authorizeTime;
    }

    public static Builder withGroupId(String groupId) {
        Assert.hasText(groupId, "groupId cannot be empty");
        return new Builder(groupId);
    }

    public String toString() {
        return "SecurityGroupAuthorizeRole(groupId=" + this.groupId + ", roleId=" + this.roleId + ", authorizeTime=" + this.authorizeTime + ")";
    }

    public static class Builder implements Serializable {
        private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
        private String groupId;
        private String roleId;
        private LocalDateTime authorizeTime;

        protected Builder(String groupId) {
            this.groupId = groupId;
        }

        public Builder roleId(String roleId) {
            this.roleId = roleId;
            return this;
        }

        public Builder authorizeTime(LocalDateTime authorizeTime) {
            this.authorizeTime = authorizeTime;
            return this;
        }

        public SecurityGroupAuthorizeRole build() {
            Assert.hasText(this.roleId, "roleId cannot be empty");
            Assert.notNull(this.authorizeTime, "authorizeTime cannot be null");
            return this.create();
        }

        private SecurityGroupAuthorizeRole create() {
            SecurityGroupAuthorizeRole groupAuthorizeRole = new SecurityGroupAuthorizeRole();
            groupAuthorizeRole.groupId = this.groupId;
            groupAuthorizeRole.roleId = this.roleId;
            groupAuthorizeRole.authorizeTime = this.authorizeTime;
            return groupAuthorizeRole;
        }

        public String toString() {
            return "SecurityGroupAuthorizeRole.Builder(groupId=" + this.groupId + ", roleId=" + this.roleId + ", authorizeTime=" + this.authorizeTime + ")";
        }
    }
}
