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

package org.minbox.framework.on.security.application.service.authentication;

import org.minbox.framework.on.security.application.service.authentication.context.OnSecurityApplicationContext;
import org.minbox.framework.on.security.core.authorization.data.resource.UserAuthorizationResource;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

/**
 * 应用资源"Role-Based Access Control（RBAC）"访问认证请求实体
 *
 * @author 恒宇少年
 * @see UserAuthorizationResource
 * @since 0.0.7
 */
public class ApplicationResourceRoleBasedAccessControlAuthenticationToken extends AbstractAuthenticationToken {
    private HttpServletRequest request;
    private List<UserAuthorizationResource> userAuthorizationResourceList;

    public ApplicationResourceRoleBasedAccessControlAuthenticationToken(HttpServletRequest request,
                                                                        List<UserAuthorizationResource> userAuthorizationResourceList) {
        super(Collections.emptyList());
        this.request = request;
        this.userAuthorizationResourceList = userAuthorizationResourceList;
    }

    /**
     * 获取本次请求实例
     *
     * @return {@link HttpServletRequest}
     */
    public HttpServletRequest getRequest() {
        return request;
    }

    /**
     * 获取请求令牌"AccessToken"所授权的资源列表
     *
     * @return {@link OnSecurityApplicationContext#getUserAuthorizationResource()}
     */
    public List<UserAuthorizationResource> getUserAuthorizationResourceList() {
        return userAuthorizationResourceList;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
