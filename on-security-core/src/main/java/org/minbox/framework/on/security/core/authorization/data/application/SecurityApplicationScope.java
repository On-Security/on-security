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

package org.minbox.framework.on.security.core.authorization.data.application;

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
public class SecurityApplicationScope implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
    private String id;
    private String applicationId;
    private String scopeName;
    private String scopeCode;
    private ClientScopeType type;
    private LocalDateTime createTime;

    protected SecurityApplicationScope() {
    }

    public String getId() {
        return id;
    }

    public String getApplicationId() {
        return applicationId;
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
        return "SecurityClientScope(id=" + this.getId() + ", applicationId=" + this.getApplicationId() + ", scopeName=" +
                this.getScopeName() + ", scopeCode=" + this.getScopeCode() + ", type=" + this.getType() + ", createTime=" + this.getCreateTime() + ")";
        // @formatter:on
    }

    /**
     * {@link SecurityApplicationScope} 对象构建者
     */
    public static class Builder extends SecurityApplicationScope implements Serializable {
        private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
        private String id;
        private String applicationId;
        private String scopeName;
        private String scopeCode;
        private ClientScopeType type;
        private LocalDateTime createTime;

        protected Builder(String id) {
            this.id = id;
        }

        public Builder applicationId(String applicationId) {
            this.applicationId = applicationId;
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

        public SecurityApplicationScope build() {
            Assert.hasText(this.applicationId, "applicationId cannot be empty");
            Assert.hasText(this.scopeName, "scopeName cannot be empty");
            Assert.hasText(this.scopeCode, "scopeCode cannot be empty");
            Assert.notNull(this.createTime, "createTime cannot be null");
            this.type = ObjectUtils.isEmpty(this.type) ? ClientScopeType.DEFAULT : this.type;
            return this.create();
        }

        private SecurityApplicationScope create() {
            SecurityApplicationScope clientScope = new SecurityApplicationScope();
            clientScope.id = this.id;
            clientScope.applicationId = this.applicationId;
            clientScope.scopeName = this.scopeName;
            clientScope.scopeCode = this.scopeCode;
            clientScope.type = this.type;
            clientScope.createTime = this.createTime;
            return clientScope;
        }
    }
}
