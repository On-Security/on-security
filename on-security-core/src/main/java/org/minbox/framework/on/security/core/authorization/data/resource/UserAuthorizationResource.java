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

    public UserAuthorizationResource(String resourceId, String resourceName, String resourceCode, Set<String> resourceUris, ResourceType resourceType, AuthorizeMatchMethod matchMethod) {
        this.resourceId = resourceId;
        this.resourceName = resourceName;
        this.resourceCode = resourceCode;
        this.resourceUris = resourceUris;
        this.resourceType = resourceType;
        this.matchMethod = matchMethod;
    }

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
}
