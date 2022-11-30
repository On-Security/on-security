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
 * 安全组授权客户端关系
 *
 * @author 恒宇少年
 */
public class SecurityGroupAuthorizeClient implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;

    private String groupId;
    private String clientId;
    private LocalDateTime authorizeTime;

    protected SecurityGroupAuthorizeClient() {
    }

    public String getGroupId() {
        return groupId;
    }

    public String getClientId() {
        return clientId;
    }

    public LocalDateTime getAuthorizeTime() {
        return authorizeTime;
    }

    public static Builder withGroupId(String groupId) {
        Assert.hasText(groupId, "groupId cannot be empty");
        return new Builder(groupId);
    }

    public String toString() {
        return "SecurityGroupAuthorizeClient(groupId=" + this.groupId + ", clientId=" + this.clientId + ", authorizeTime=" + this.authorizeTime + ")";
    }

    /**
     * {@link SecurityGroupAuthorizeClient} 对象创建者
     */
    public static class Builder implements Serializable {
        private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
        private String groupId;
        private String clientId;
        private LocalDateTime authorizeTime;

        protected Builder(String groupId) {
            this.groupId = groupId;
        }

        public Builder clientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder authorizeTime(LocalDateTime authorizeTime) {
            this.authorizeTime = authorizeTime;
            return this;
        }

        public SecurityGroupAuthorizeClient build() {
            Assert.hasText(clientId, "clientId cannot be empty.");
            Assert.notNull(authorizeTime, "authorizeTime cannot be null");
            return this.create();
        }

        private SecurityGroupAuthorizeClient create() {
            SecurityGroupAuthorizeClient groupAuthorizeClient = new SecurityGroupAuthorizeClient();
            groupAuthorizeClient.groupId = this.groupId;
            groupAuthorizeClient.clientId = this.clientId;
            groupAuthorizeClient.authorizeTime = this.authorizeTime;
            return groupAuthorizeClient;
        }

        public String toString() {
            return "SecurityGroupAuthorizeClient.Builder(groupId=" + this.groupId + ", clientId=" + this.clientId + ", authorizeTime=" + this.authorizeTime + ")";
        }
    }
}
