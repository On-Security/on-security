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
 * 用户安全组关系
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class SecurityUserGroup implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
    private String userId;
    private String groupId;
    private LocalDateTime bindTime;

    protected SecurityUserGroup() {
    }

    public String getUserId() {
        return userId;
    }

    public String getGroupId() {
        return groupId;
    }

    public LocalDateTime getBindTime() {
        return bindTime;
    }

    public static Builder withUserId(String userId) {
        Assert.hasText(userId, "userId cannot be empty");
        return new Builder(userId);
    }

    public String toString() {
        return "SecurityUserGroup(userId=" + this.userId + ", groupId=" + this.groupId + ", bindTime=" + this.bindTime + ")";
    }

    /**
     * {@link SecurityUserGroup} 对象创建者
     */
    public static class Builder implements Serializable {
        private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
        private String userId;
        private String groupId;
        private LocalDateTime bindTime;

        protected Builder(String userId) {
            this.userId = userId;
        }

        public Builder groupId(String groupId) {
            this.groupId = groupId;
            return this;
        }

        public Builder bindTime(LocalDateTime bindTime) {
            this.bindTime = bindTime;
            return this;
        }

        public SecurityUserGroup build() {
            Assert.hasText(this.groupId, "groupId cannot be empty");
            Assert.notNull(this.bindTime, "bindTime cannot be null");
            return this.create();
        }

        private SecurityUserGroup create() {
            SecurityUserGroup userGroup = new SecurityUserGroup();
            userGroup.userId = this.userId;
            userGroup.groupId = this.groupId;
            userGroup.bindTime = this.bindTime;
            return userGroup;
        }

        public String toString() {
            return "SecurityUserGroup.Builder(userId=" + this.userId + ", groupId=" + this.groupId + ", bindTime=" + this.bindTime + ")";
        }
    }
}
