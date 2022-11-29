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

import org.minbox.framework.on.security.core.authorization.ClientScopeType;
import org.minbox.framework.on.security.core.authorization.util.OnSecurityVersion;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 客户端授权范围
 * <p>
 * On-Security授权服务器客户端授权的"scope"
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class SecurityClientScope implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
    private String id;
    private String clientId;
    private String scopeName;
    private String scopeCode;
    private ClientScopeType type;
    private LocalDateTime createTime;

    protected SecurityClientScope() {
    }

    public String getId() {
        return id;
    }

    public String getClientId() {
        return clientId;
    }

    public String getScopeName() {
        return scopeName;
    }

    public String getScopeCode() {
        return scopeCode;
    }

    public ClientScopeType getType() {
        return type;
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
        return "SecurityClientScope(id=" + this.getId() + ", clientId=" + this.getClientId() + ", scopeName=" +
                this.getScopeName() + ", scopeCode=" + this.getScopeCode() + ", type=" + this.getType() + ", createTime=" + this.getCreateTime() + ")";
        // @formatter:on
    }

    /**
     * {@link SecurityClientScope} 对象构建者
     */
    public static class Builder extends SecurityClientScope implements Serializable {
        private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
        private String id;
        private String clientId;
        private String scopeName;
        private String scopeCode;
        private ClientScopeType type;
        private LocalDateTime createTime;

        protected Builder(String id) {
            this.id = id;
        }

        public Builder clientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder scopeName(String scopeName) {
            this.scopeName = scopeName;
            return this;
        }

        public Builder scopeCode(String scopeCode) {
            this.scopeCode = scopeCode;
            return this;
        }

        public Builder type(ClientScopeType type) {
            this.type = type;
            return this;
        }

        public Builder createTime(LocalDateTime createTime) {
            this.createTime = createTime;
            return this;
        }

        public SecurityClientScope build() {
            Assert.hasText(this.clientId, "clientId cannot be empty");
            Assert.hasText(this.scopeName, "scopeName cannot be empty");
            Assert.hasText(this.scopeCode, "scopeCode cannot be empty");
            this.type = ObjectUtils.isEmpty(this.type) ? ClientScopeType.DEFAULT : this.type;
            return this.create();
        }

        private SecurityClientScope create() {
            SecurityClientScope clientScope = new SecurityClientScope();
            clientScope.id = this.id;
            clientScope.clientId = this.clientId;
            clientScope.scopeName = this.scopeName;
            clientScope.scopeCode = this.scopeCode;
            clientScope.type = this.type;
            clientScope.createTime = this.createTime;
            return clientScope;
        }
    }
}
