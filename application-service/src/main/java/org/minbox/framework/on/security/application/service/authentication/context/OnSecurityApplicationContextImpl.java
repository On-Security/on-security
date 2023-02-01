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

package org.minbox.framework.on.security.application.service.authentication.context;

import org.minbox.framework.on.security.core.authorization.data.attribute.UserAuthorizationAttribute;
import org.minbox.framework.on.security.core.authorization.data.resource.UserAuthorizationResource;
import org.minbox.framework.on.security.core.authorization.data.role.UserAuthorizationRole;
import org.minbox.framework.on.security.core.authorization.endpoint.AccessTokenAuthorization;
import org.minbox.framework.on.security.core.authorization.endpoint.AccessTokenSession;

import java.util.List;
import java.util.Map;

/**
 * 应用上下文{@link OnSecurityApplicationContext}实现类
 *
 * @author 恒宇少年
 * @see AccessTokenAuthorization
 * @see org.minbox.framework.on.security.core.authorization.data.resource.UserAuthorizationResource
 * @see org.minbox.framework.on.security.core.authorization.data.attribute.UserAuthorizationAttribute
 * @see org.minbox.framework.on.security.core.authorization.data.role.UserAuthorizationRole
 * @see AccessTokenSession
 * @since 0.0.6
 */
public class OnSecurityApplicationContextImpl implements OnSecurityApplicationContext {
    private String accessToken;
    private AccessTokenAuthorization accessTokenAuthorization;

    private OnSecurityApplicationContextImpl() {
    }

    public static OnSecurityApplicationContextImpl createEmptyContext() {
        return new OnSecurityApplicationContextImpl();
    }

    public static OnSecurityApplicationContextImpl.Builder withAccessToken(String accessToken) {
        return new OnSecurityApplicationContextImpl.Builder(accessToken);
    }

    @Override
    public String getAccessToken() {
        return this.accessToken;
    }

    @Override
    public Map<String, Object> getUserMetadata() {
        return this.accessTokenAuthorization.getUser();
    }

    @Override
    public AccessTokenSession getSession() {
        return this.accessTokenAuthorization.getSession();
    }

    @Override
    public List<UserAuthorizationResource> getUserAuthorizationResource() {
        return this.accessTokenAuthorization.getUserAuthorizationResource();
    }

    @Override
    public List<UserAuthorizationAttribute> getUserAuthorizationAttribute() {
        return this.accessTokenAuthorization.getUserAuthorizationAttribute();
    }

    @Override
    public List<UserAuthorizationRole> getUserAuthorizationRole() {
        return this.accessTokenAuthorization.getUserAuthorizationRole();
    }

    /**
     * The {@link OnSecurityApplicationContextImpl} Builder
     */
    public static class Builder {
        private String accessToken;
        private AccessTokenAuthorization accessTokenAuthorization;

        public Builder(String accessToken) {
            this.accessToken = accessToken;
        }

        public Builder accessTokenAuthorization(AccessTokenAuthorization accessTokenAuthorization) {
            this.accessTokenAuthorization = accessTokenAuthorization;
            return this;
        }


        public OnSecurityApplicationContextImpl build() {
            OnSecurityApplicationContextImpl applicationContext = new OnSecurityApplicationContextImpl();
            applicationContext.accessToken = this.accessToken;
            applicationContext.accessTokenAuthorization = this.accessTokenAuthorization;
            return applicationContext;
        }
    }
}
