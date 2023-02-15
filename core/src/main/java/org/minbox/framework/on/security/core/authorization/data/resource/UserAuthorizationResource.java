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

import org.minbox.framework.on.security.core.authorization.AuthorizeMatchMethod;
import org.minbox.framework.on.security.core.authorization.ResourceType;
import org.minbox.framework.on.security.core.authorization.util.OnSecurityVersion;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Set;

/**
 * 用户授权的资源封装实体定义
 *
 * @author 恒宇少年
 * @since 0.0.5
 */
public final class UserAuthorizationResource implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
    private String resourceId;
    private String resourceName;
    private String resourceCode;
    private Set<String> resourceUris;
    private ResourceType resourceType;
    private AuthorizeMatchMethod matchMethod;

    public String getResourceId() {
        return resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getResourceCode() {
        return resourceCode;
    }

    public Set<String> getResourceUris() {
        return resourceUris;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public AuthorizeMatchMethod getMatchMethod() {
        return matchMethod;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    public void setResourceUris(Set<String> resourceUris) {
        this.resourceUris = resourceUris;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public void setMatchMethod(AuthorizeMatchMethod matchMethod) {
        this.matchMethod = matchMethod;
    }

    public static Builder withResourceId(String resourceId) {
        Assert.hasText(resourceId, "resourceId cannot be empty");
        return new Builder(resourceId);
    }

    /**
     * The {@link UserAuthorizationResource} Builder
     */
    public static class Builder implements Serializable {
        private String resourceId;
        private String resourceName;
        private String resourceCode;
        private Set<String> resourceUris;
        private ResourceType resourceType;
        private AuthorizeMatchMethod matchMethod;

        public Builder(String resourceId) {
            this.resourceId = resourceId;
        }

        public Builder resourceName(String resourceName) {
            this.resourceName = resourceName;
            return this;
        }

        public Builder resourceCode(String resourceCode) {
            this.resourceCode = resourceCode;
            return this;
        }

        public Builder resourceUris(Set<String> resourceUris) {
            this.resourceUris = resourceUris;
            return this;
        }

        public Builder resourceType(ResourceType resourceType) {
            this.resourceType = resourceType;
            return this;
        }

        public Builder matchMethod(AuthorizeMatchMethod matchMethod) {
            this.matchMethod = matchMethod;
            return this;
        }

        public UserAuthorizationResource build() {
            UserAuthorizationResource authorizationResource = new UserAuthorizationResource();
            authorizationResource.resourceId = this.resourceId;
            authorizationResource.resourceName = this.resourceName;
            authorizationResource.resourceCode = this.resourceCode;
            authorizationResource.resourceUris = this.resourceUris;
            authorizationResource.resourceType = this.resourceType;
            authorizationResource.matchMethod = this.matchMethod;
            return authorizationResource;
        }
    }
}
