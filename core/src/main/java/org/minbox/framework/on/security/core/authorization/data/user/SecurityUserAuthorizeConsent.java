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
import java.util.Set;

/**
 * 用户授权许可关系
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class SecurityUserAuthorizeConsent implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
    private String userId;
    private String username;
    private String applicationId;
    private Set<String> authorities;
    private LocalDateTime authorizeTime;

    protected SecurityUserAuthorizeConsent() {
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public Set<String> getAuthorities() {
        return authorities;
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
        return "SecurityUserAuthorizeConsent(userId=" + this.userId + ", applicationId=" + this.applicationId + ", authorities=" +
                this.authorities + ", authorizeTime=" + this.authorizeTime + ")";
        // @formatter:on
    }

    /**
     * {@link SecurityUserAuthorizeConsent} 对象构建者
     */
    public static class Builder implements Serializable {
        private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
        private String userId;
        private String username;
        private String applicationId;
        private Set<String> authorities;
        private LocalDateTime authorizeTime;

        protected Builder(String userId) {
            this.userId = userId;
        }

        public Builder applicationId(String applicationId) {
            this.applicationId = applicationId;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder authorities(Set<String> authorities) {
            this.authorities = authorities;
            return this;
        }

        public Builder authorizeTime(LocalDateTime authorizeTime) {
            this.authorizeTime = authorizeTime;
            return this;
        }

        public SecurityUserAuthorizeConsent build() {
            Assert.hasText(this.username, "username cannot be empty");
            Assert.hasText(this.applicationId, "applicationId cannot be empty");
            Assert.notEmpty(this.authorities, "authorities cannot be empty");
            Assert.notNull(this.authorizeTime, "authorizeTime cannot be null");
            return this.create();
        }

        private SecurityUserAuthorizeConsent create() {
            SecurityUserAuthorizeConsent userAuthorizeConsent = new SecurityUserAuthorizeConsent();
            userAuthorizeConsent.userId = this.userId;
            userAuthorizeConsent.username = this.username;
            userAuthorizeConsent.applicationId = this.applicationId;
            userAuthorizeConsent.authorities = this.authorities;
            userAuthorizeConsent.authorizeTime = this.authorizeTime;
            return userAuthorizeConsent;
        }

        public String toString() {
            // @formatter:off
            return "SecurityUserAuthorizeConsent.Builder(userId=" + this.userId + "username="+this.username+", applicationId=" + this.applicationId +
                    ", authorities=" + this.authorities + ", authorizeTime=" + this.authorizeTime + ")";
            // @formatter:on
        }
    }
}
