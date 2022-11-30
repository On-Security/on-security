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

package org.minbox.framework.on.security.core.authorization.data.user;

import org.minbox.framework.on.security.core.authorization.util.OnSecurityVersion;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户授权角色关系
 *
 * @author 恒宇少年
 */
public class SecurityUserAuthorizeRole implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
    private String userId;
    private String roleId;
    private LocalDateTime authorizeTime;

    protected SecurityUserAuthorizeRole() {
    }

    public String getUserId() {
        return userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public LocalDateTime getAuthorizeTime() {
        return authorizeTime;
    }

    public String toString() {
        return "SecurityUserAuthorizeRole(userId=" + this.userId + ", roleId=" + this.roleId + ", authorizeTime=" + this.authorizeTime + ")";
    }

    /**
     * {@link SecurityUserAuthorizeRole} 对象创建者
     */
    public static class Builder implements Serializable {
        private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
        private String userId;
        private String roleId;
        private LocalDateTime authorizeTime;

        protected Builder(String userId) {
            this.userId = userId;
        }

        public Builder roleId(String roleId) {
            this.roleId = roleId;
            return this;
        }

        public Builder authorizeTime(LocalDateTime authorizeTime) {
            this.authorizeTime = authorizeTime;
            return this;
        }

        public SecurityUserAuthorizeRole build() {
            Assert.hasText(this.roleId, "roleId cannot be empty");
            Assert.notNull(this.authorizeTime, "authorizeTime cannot be null");
            return this.create();
        }

        private SecurityUserAuthorizeRole create() {
            SecurityUserAuthorizeRole userAuthorizeRole = new SecurityUserAuthorizeRole();
            userAuthorizeRole.userId = this.userId;
            userAuthorizeRole.roleId = this.roleId;
            userAuthorizeRole.authorizeTime = this.authorizeTime;
            return userAuthorizeRole;
        }

        public String toString() {
            return "SecurityUserAuthorizeRole.Builder(userId=" + this.userId + ", roleId=" + this.roleId + ", authorizeTime=" + this.authorizeTime + ")";
        }
    }
}
