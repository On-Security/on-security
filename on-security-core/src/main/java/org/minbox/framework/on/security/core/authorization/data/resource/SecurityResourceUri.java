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

package org.minbox.framework.on.security.core.authorization.data.resource;

import org.minbox.framework.on.security.core.authorization.util.OnSecurityVersion;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 资源路径基本信息
 *
 * @author 恒宇少年
 */
public class SecurityResourceUri implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;

    private String id;
    private String resourceId;
    private String uri;
    private LocalDateTime createTime;

    protected SecurityResourceUri() {
    }

    public String getId() {
        return id;
    }

    public String getResourceId() {
        return resourceId;
    }

    public String getUri() {
        return uri;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public static Builder withId(String id) {
        Assert.hasText(id, "id cannot be empty");
        return new Builder(id);
    }

    public String toString() {
        return "SecurityResourceUri(id=" + this.id + ", resourceId=" + this.resourceId + ", uri=" + this.uri + ", createTime=" + this.createTime + ")";
    }

    /**
     * {@link SecurityResourceUri} 对象构建者
     */
    public static class Builder implements Serializable {
        private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
        private String id;
        private String resourceId;
        private String uri;
        private LocalDateTime createTime;

        protected Builder(String id) {
            this.id = id;
        }

        public Builder resourceId(String resourceId) {
            this.resourceId = resourceId;
            return this;
        }

        public Builder uri(String uri) {
            this.uri = uri;
            return this;
        }

        public Builder createTime(LocalDateTime createTime) {
            this.createTime = createTime;
            return this;
        }

        public SecurityResourceUri build() {
            Assert.hasText(this.resourceId, "resourceId cannot be empty");
            Assert.hasText(this.uri, "uri cannot be empty");
            Assert.notNull(this.createTime, "createTime cannot be null");
            return this.create();
        }

        private SecurityResourceUri create() {
            SecurityResourceUri resourceUri = new SecurityResourceUri();
            resourceUri.id = this.id;
            resourceUri.resourceId = this.resourceId;
            resourceUri.uri = this.uri;
            resourceUri.createTime = this.createTime;
            return resourceUri;
        }

        public String toString() {
            return "SecurityResourceUri.Builder(id=" + this.id + ", resourceId=" + this.resourceId + ", uri=" + this.uri + ", createTime=" + this.createTime + ")";
        }
    }
}
