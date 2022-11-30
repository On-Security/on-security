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
 * 用户授权许可关系
 *
 * @author 恒宇少年
 */
public class SecurityUserAuthorizeConsent implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
    private String userId;
    private String clientId;
    private String scopeId;
    private LocalDateTime authorizeTime;

    protected SecurityUserAuthorizeConsent() {
    }

    public String getUserId() {
        return userId;
    }

    public String getClientId() {
        return clientId;
    }

    public String getScopeId() {
        return scopeId;
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
        return "SecurityUserAuthorizeConsent(userId=" + this.userId + ", clientId=" + this.clientId + ", scopeId=" +
                this.scopeId + ", authorizeTime=" + this.authorizeTime + ")";
        // @formatter:on
    }

    /**
     * {@link SecurityUserAuthorizeConsent} 对象构建者
     */
    public static class Builder implements Serializable {
        private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
        private String userId;
        private String clientId;
        private String scopeId;
        private LocalDateTime authorizeTime;

        protected Builder(String userId) {
            this.userId = userId;
        }

        public Builder clientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder scopeId(String scopeId) {
            this.scopeId = scopeId;
            return this;
        }

        public Builder authorizeTime(LocalDateTime authorizeTime) {
            this.authorizeTime = authorizeTime;
            return this;
        }

        public SecurityUserAuthorizeConsent build() {
            Assert.hasText(this.clientId, "clientId cannot be empty");
            Assert.hasText(this.scopeId, "scopeId cannot be empty");
            Assert.notNull(this.authorizeTime, "authorizeTime cannot be null");
            return this.create();
        }

        private SecurityUserAuthorizeConsent create() {
            SecurityUserAuthorizeConsent userAuthorizeConsent = new SecurityUserAuthorizeConsent();
            userAuthorizeConsent.userId = this.userId;
            userAuthorizeConsent.clientId = this.clientId;
            userAuthorizeConsent.scopeId = this.scopeId;
            userAuthorizeConsent.authorizeTime = this.authorizeTime;
            return userAuthorizeConsent;
        }

        public String toString() {
            // @formatter:off
            return "SecurityUserAuthorizeConsent.Builder(userId=" + this.userId + ", clientId=" + this.clientId +
                    ", scopeId=" + this.scopeId + ", authorizeTime=" + this.authorizeTime + ")";
            // @formatter:on
        }
    }
}