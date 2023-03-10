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

package org.minbox.framework.on.security.core.authorization.data.application;

import org.minbox.framework.on.security.core.authorization.data.resource.ApplicationResource;
import org.minbox.framework.on.security.core.authorization.util.OnSecurityVersion;

import java.io.Serializable;
import java.util.List;

/**
 * 用户授权应用映射实体
 *
 * @author 恒宇少年
 * @since 0.1.1
 */
public final class UserAuthorizationApplication implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
    private SecurityApplication application;
    private List<ApplicationResource> resourceList;

    /**
     * 获取应用基本信息
     *
     * @return {@link SecurityApplication}
     */
    public SecurityApplication getApplication() {
        return application;
    }

    /**
     * 获取应用资源列表
     *
     * @return {@link ApplicationResource}
     */
    public List<ApplicationResource> getResourceList() {
        return resourceList;
    }

    public static Builder withApplication(SecurityApplication application) {
        return new Builder(application);
    }

    /**
     * The {@link UserAuthorizationApplication} Builder
     */
    public static class Builder {
        private SecurityApplication application;
        private List<ApplicationResource> resourceList;

        public Builder(SecurityApplication application) {
            this.application = application;
        }

        public Builder application(SecurityApplication application) {
            this.application = application;
            return this;
        }

        public Builder resourceList(List<ApplicationResource> resourceList) {
            this.resourceList = resourceList;
            return this;
        }

        public UserAuthorizationApplication build() {
            UserAuthorizationApplication userAuthorizationApplication = new UserAuthorizationApplication();
            userAuthorizationApplication.application = this.application;
            userAuthorizationApplication.resourceList = this.resourceList;
            return userAuthorizationApplication;
        }
    }
}
