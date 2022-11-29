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

package org.minbox.framework.on.security.core.authorization.data.client;

import org.minbox.framework.on.security.core.authorization.util.OnSecurityVersion;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 客户端认证密钥
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class SecurityClientSecret implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
    private String id;
    private String clientId;
    private String clientSecret;
    private LocalDateTime secretExpiresAt;
    private LocalDateTime createTime;
    private boolean deleted;
    private LocalDateTime deleteTime;

    protected SecurityClientSecret() {
    }

    public String getId() {
        return id;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public LocalDateTime getSecretExpiresAt() {
        return secretExpiresAt;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public LocalDateTime getDeleteTime() {
        return deleteTime;
    }

    public static Builder withId(String id) {
        Assert.hasText(id, "id cannot be empty");
        return new Builder(id);
    }

    public String toString() {
        // @formatter:off
        return "SecurityClientSecret(id=" + this.getId() + ", clientId=" + this.getClientId() + ", clientSecret=" +
                this.getClientSecret() + ", secretExpiresAt=" + this.getSecretExpiresAt() + ", createTime=" + this.getCreateTime() +
                ", deleted=" + this.isDeleted() + ", deleteTime=" + this.getDeleteTime() + ")";
        // @formatter:on
    }

    /**
     * {@link SecurityClientSecret} 对象构建者
     */
    public static class Builder implements Serializable {
        private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
        private String id;
        private String clientId;
        private String clientSecret;
        private LocalDateTime secretExpiresAt;
        private LocalDateTime createTime;
        private boolean deleted;
        private LocalDateTime deleteTime;

        protected Builder(String id) {
            this.id = id;
        }

        public Builder clientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder clientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
            return this;
        }

        public Builder secretExpiresAt(LocalDateTime secretExpiresAt) {
            this.secretExpiresAt = secretExpiresAt;
            return this;
        }

        public Builder createTime(LocalDateTime createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder deleted(boolean deleted) {
            this.deleted = deleted;
            return this;
        }

        public Builder deleteTime(LocalDateTime deleteTime) {
            this.deleteTime = deleteTime;
            return this;
        }

        public SecurityClientSecret build() {
            Assert.hasText(this.clientId, "clientId cannot be empty");
            Assert.hasText(this.clientSecret, "clientSecret cannot be empty");
            Assert.notNull(this.secretExpiresAt, "secretExpiresAt cannot be null");
            return this.create();
        }

        private SecurityClientSecret create() {
            SecurityClientSecret securityClientSecret = new SecurityClientSecret();
            securityClientSecret.id = this.id;
            securityClientSecret.clientId = this.clientId;
            securityClientSecret.clientSecret = this.clientSecret;
            securityClientSecret.secretExpiresAt = this.secretExpiresAt;
            securityClientSecret.createTime = this.createTime;
            securityClientSecret.deleted = this.deleted;
            securityClientSecret.deleteTime = this.deleteTime;
            return securityClientSecret;
        }

        public String toString() {
            //@formatter:off
            return "SecurityClientSecret.SecurityClientSecretBuilder(id=" + this.id + ", clientId=" +
                    this.clientId + ", clientSecret=" + this.clientSecret + ", secretExpiresAt=" + this.secretExpiresAt
                    + ", createTime=" + this.createTime + ", deleted=" + this.deleted + ", deleteTime=" + this.deleteTime + ")";
            //@formatter:on
        }
    }
}
