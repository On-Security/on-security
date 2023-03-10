/*
 *   Copyright (C) 2022  恒宇少年
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.minbox.framework.on.security.core.authorization.data.resource;

import org.minbox.framework.on.security.core.authorization.data.attribute.ResourceAuthorizeAttribute;

import java.util.List;
import java.util.Set;

/**
 * 应用资源实体
 *
 * @author 恒宇少年
 * @since 0.1.1
 */
public class ApplicationResource {
    private SecurityResource resource;
    private Set<SecurityResourceUri> resourceUriList;
    private List<ResourceAuthorizeAttribute> resourceAuthorizeAttributeList;

    /**
     * 获取资源信息
     *
     * @return {@link SecurityResource}
     */
    public SecurityResource getResource() {
        return resource;
    }

    /**
     * 获取资源授权的路径列表
     *
     * @return {@link SecurityResourceUri}
     */
    public Set<SecurityResourceUri> getResourceUriList() {
        return resourceUriList;
    }

    /**
     * 获取资源授权的属性列表
     *
     * @return
     */
    public List<ResourceAuthorizeAttribute> getResourceAuthorizeAttributeList() {
        return resourceAuthorizeAttributeList;
    }

    public static Builder withResource(SecurityResource resource) {
        return new Builder(resource);
    }

    /**
     * The {@link ApplicationResource} Builder
     */
    public static class Builder {
        private SecurityResource resource;
        private Set<SecurityResourceUri> resourceUriList;
        private List<ResourceAuthorizeAttribute> resourceAuthorizeAttributeList;

        public Builder(SecurityResource resource) {
            this.resource = resource;
        }

        public Builder resourceUriList(Set<SecurityResourceUri> resourceUriList) {
            this.resourceUriList = resourceUriList;
            return this;
        }

        public Builder resourceAuthorizeAttributeList(List<ResourceAuthorizeAttribute> resourceAuthorizeAttributeList) {
            this.resourceAuthorizeAttributeList = resourceAuthorizeAttributeList;
            return this;
        }

        public ApplicationResource build() {
            ApplicationResource applicationResource = new ApplicationResource();
            applicationResource.resource = this.resource;
            applicationResource.resourceUriList = this.resourceUriList;
            applicationResource.resourceAuthorizeAttributeList = this.resourceAuthorizeAttributeList;
            return applicationResource;
        }
    }
}
