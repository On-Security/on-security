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
 * 用户授权客户端关系
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class SecurityUserAuthorizeApplication implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
    private String userId;
    private String applicationId;
    private LocalDateTime authorizeTime;

    public String getUserId() {
        return userId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public LocalDateTime getAuthorizeTime() {
        return authorizeTime;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public void setAuthorizeTime(LocalDateTime authorizeTime) {
        this.authorizeTime = authorizeTime;
    }

    public static Builder withUserId(String userId) {
        Assert.hasText(userId, "userId cannot be empty.");
        return new Builder(userId);
    }

    public String toString() {
        // @formatter:off
        return "SecurityUserAuthorizeClient(userId=" + this.userId + ", applicationId=" + this.applicationId +
                ", authorizeTime=" + this.authorizeTime + ")";
        // @formatter:on
    }

    /**
     * {@link SecurityUserAuthorizeApplication} 对象构建者
     */
    public static class Builder implements Serializable {
        private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
        private String userId;
        private String applicationId;
        private LocalDateTime authorizeTime;

        protected Builder(String userId) {
            this.userId = userId;
        }

        public Builder applicationId(String applicationId) {
            this.applicationId = applicationId;
            return this;
        }

        public Builder authorizeTime(LocalDateTime authorizeTime) {
            this.authorizeTime = authorizeTime;
            return this;
        }

        public SecurityUserAuthorizeApplication build() {
            Assert.hasText(this.applicationId, "applicationId cannot be empty.");
            Assert.notNull(this.authorizeTime, "authorizeTime cannot be null.");
            return this.create();
        }

        private SecurityUserAuthorizeApplication create() {
            SecurityUserAuthorizeApplication userAuthorizeClient = new SecurityUserAuthorizeApplication();
            userAuthorizeClient.userId = this.userId;
            userAuthorizeClient.applicationId = this.applicationId;
            userAuthorizeClient.authorizeTime = this.authorizeTime;
            return userAuthorizeClient;
        }

        public String toString() {
            return "SecurityUserAuthorizeClient.Builder(userId=" + this.userId + ", applicationId=" + this.applicationId + ", authorizeTime=" + this.authorizeTime + ")";
        }
    }
}
