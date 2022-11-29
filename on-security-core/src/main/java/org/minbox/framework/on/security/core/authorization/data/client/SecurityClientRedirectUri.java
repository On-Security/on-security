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

import org.minbox.framework.on.security.core.authorization.ClientRedirectUriType;
import org.minbox.framework.on.security.core.authorization.util.OnSecurityVersion;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 客户端跳转地址
 * <p>
 * 根据{@link ClientRedirectUriType}配置，支持多种跳转地址配置
 *
 * @author 恒宇少年
 */
public class SecurityClientRedirectUri implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
    private String id;
    private String clientId;
    private ClientRedirectUriType redirectType;
    private String redirectUri;
    private LocalDateTime createTime;

    protected SecurityClientRedirectUri() {
    }

    public String getId() {
        return id;
    }

    public String getClientId() {
        return clientId;
    }

    public ClientRedirectUriType getRedirectType() {
        return redirectType;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public static Builder withId(String id) {
        Assert.hasText(id, "id cannot be empty");
        return new Builder(id);
    }

    public String toString() {
        // @formatter:off
        return "SecurityClientRedirectUri(id=" + this.getId() + ", clientId=" + this.getClientId() + ", redirectType=" +
                this.getRedirectType() + ", redirectUri=" + this.getRedirectUri() + ", createTime=" + this.getCreateTime() + ")";
        // @formatter:on
    }

    /**
     * {@link SecurityClientRedirectUri} 对象构建者
     */
    public static class Builder implements Serializable {
        private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
        private String id;
        private String clientId;
        private ClientRedirectUriType redirectType;
        private String redirectUri;
        private LocalDateTime createTime;

        protected Builder(String id) {
            this.id = id;
        }

        public Builder clientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder redirectType(ClientRedirectUriType redirectType) {
            this.redirectType = redirectType;
            return this;
        }

        public Builder redirectUri(String redirectUri) {
            this.redirectUri = redirectUri;
            return this;
        }

        public Builder createTime(LocalDateTime createTime) {
            this.createTime = createTime;
            return this;
        }

        public SecurityClientRedirectUri build() {
            Assert.hasText(this.clientId, "clientId cannot be empty");
            Assert.hasText(this.redirectUri, "redirectUri cannot be empty");
            Assert.notNull(this.redirectType, "redirectType cannot be null");
            return this.create();
        }

        private SecurityClientRedirectUri create() {
            SecurityClientRedirectUri clientRedirectUri = new SecurityClientRedirectUri();
            clientRedirectUri.id = this.id;
            clientRedirectUri.clientId = this.clientId;
            clientRedirectUri.redirectType = this.redirectType;
            clientRedirectUri.redirectUri = this.redirectUri;
            clientRedirectUri.createTime = this.createTime;
            return clientRedirectUri;
        }

        public String toString() {
            // @formatter:off
            return "SecurityClientRedirectUri.Builder(id=" + this.id + ", clientId=" + this.clientId +
                    ", redirectType=" + this.redirectType + ", redirectUri=" + this.redirectUri + ", createTime=" + this.createTime + ")";
            // @formatter:on
        }
    }
}
