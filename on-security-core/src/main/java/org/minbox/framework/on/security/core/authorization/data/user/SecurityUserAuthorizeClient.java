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
 */
public class SecurityUserAuthorizeClient implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
    private String userId;
    private String clientId;
    private LocalDateTime authorizeTime;

    protected SecurityUserAuthorizeClient() {
    }

    public String getUserId() {
        return userId;
    }

    public String getClientId() {
        return clientId;
    }

    public LocalDateTime getAuthorizeTime() {
        return authorizeTime;
    }

    public static Builder withUserId(String userId) {
        Assert.hasText(userId, "userId cannot be empty.");
        return new Builder(userId);
    }

    public String toString() {
        // @formatter:off
        return "SecurityUserAuthorizeClient(userId=" + this.userId + ", clientId=" + this.clientId +
                ", authorizeTime=" + this.authorizeTime + ")";
        // @formatter:on
    }

    /**
     * {@link SecurityUserAuthorizeClient} 对象构建者
     */
    public static class Builder implements Serializable {
        private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
        private String userId;
        private String clientId;
        private LocalDateTime authorizeTime;

        protected Builder(String userId) {
            this.userId = userId;
        }

        public Builder clientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder authorizeTime(LocalDateTime authorizeTime) {
            this.authorizeTime = authorizeTime;
            return this;
        }

        public SecurityUserAuthorizeClient build() {
            Assert.hasText(this.clientId, "clientId cannot be empty.");
            Assert.notNull(this.authorizeTime, "authorizeTime cannot be null.");
            return this.create();
        }

        private SecurityUserAuthorizeClient create() {
            SecurityUserAuthorizeClient userAuthorizeClient = new SecurityUserAuthorizeClient();
            userAuthorizeClient.userId = this.userId;
            userAuthorizeClient.clientId = this.clientId;
            userAuthorizeClient.authorizeTime = this.authorizeTime;
            return userAuthorizeClient;
        }

        public String toString() {
            return "SecurityUserAuthorizeClient.Builder(userId=" + this.userId + ", clientId=" + this.clientId + ", authorizeTime=" + this.authorizeTime + ")";
        }
    }
}
