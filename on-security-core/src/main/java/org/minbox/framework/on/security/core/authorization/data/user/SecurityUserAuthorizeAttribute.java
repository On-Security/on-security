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
 * 用户授权属性关系
 *
 * @author 恒宇少年
 * @since 0.0.4
 */
public class SecurityUserAuthorizeAttribute implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
    private String userId;
    private String attributeId;
    private LocalDateTime authorizeTime;

    private SecurityUserAuthorizeAttribute() {
    }

    public String getUserId() {
        return userId;
    }

    public String getAttributeId() {
        return attributeId;
    }

    public LocalDateTime getAuthorizeTime() {
        return authorizeTime;
    }

    public String toString() {
        // @formatter:off
        return "SecurityUserAuthorizeAttribute(userId=" + this.userId + ", attributeId=" + this.attributeId +
                ", authorizeTime=" + this.authorizeTime + ")";
        // @formatter:on
    }

    public static Builder withUserId(String userId) {
        Assert.hasText(userId, "userId cannot be empty");
        return new Builder(userId);
    }

    /**
     * The {@link SecurityUserAuthorizeAttribute} Builder
     */
    public static class Builder implements Serializable {
        private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
        private String userId;
        private String attributeId;
        private LocalDateTime authorizeTime;

        Builder(String userId) {
            this.userId = userId;
        }

        public Builder attributeId(String attributeId) {
            this.attributeId = attributeId;
            return this;
        }

        public Builder authorizeTime(LocalDateTime authorizeTime) {
            this.authorizeTime = authorizeTime;
            return this;
        }

        public SecurityUserAuthorizeAttribute build() {
            Assert.hasText(this.attributeId, "attributeId cannot be empty");
            this.authorizeTime = this.authorizeTime == null ? LocalDateTime.now() : this.authorizeTime;
            return this.create();
        }

        private SecurityUserAuthorizeAttribute create() {
            SecurityUserAuthorizeAttribute userAuthorizeAttribute = new SecurityUserAuthorizeAttribute();
            userAuthorizeAttribute.userId = this.userId;
            userAuthorizeAttribute.attributeId = this.attributeId;
            userAuthorizeAttribute.authorizeTime = this.authorizeTime;
            return userAuthorizeAttribute;
        }

        public String toString() {
            // @formatter:off
            return "SecurityUserAuthorizeAttribute.SecurityUserAuthorizeAttributeBuilder(userId=" + this.userId +
                    ", attributeId=" + this.attributeId + ", authorizeTime=" + this.authorizeTime + ")";
            // @formatter:on
        }
    }
}
