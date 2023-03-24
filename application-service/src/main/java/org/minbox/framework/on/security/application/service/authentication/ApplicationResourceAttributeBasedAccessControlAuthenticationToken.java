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

package org.minbox.framework.on.security.application.service.authentication;

import org.minbox.framework.on.security.core.authorization.data.application.SecurityApplication;
import org.minbox.framework.on.security.core.authorization.data.application.UserAuthorizationApplication;
import org.minbox.framework.on.security.core.authorization.data.attribute.UserAuthorizationAttribute;
import org.minbox.framework.on.security.core.authorization.data.resource.ApplicationResource;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

/**
 * 应用资源"Attribute-Based Access Control（ABAC）"访问认证请求实体
 *
 * @author 恒宇少年
 * @see HttpServletRequest
 * @see UserAuthorizationAttribute
 * @see UserAuthorizationApplication
 * @since 0.1.1
 */
public class ApplicationResourceAttributeBasedAccessControlAuthenticationToken extends AbstractAuthenticationToken {
    private HttpServletRequest request;
    private List<UserAuthorizationAttribute> userAuthorizationAttributeList;
    private UserAuthorizationApplication userAuthorizationApplication;

    public ApplicationResourceAttributeBasedAccessControlAuthenticationToken(HttpServletRequest request,
                                                                             List<UserAuthorizationAttribute> userAuthorizationAttributeList,
                                                                             UserAuthorizationApplication userAuthorizationApplication) {
        super(Collections.emptyList());
        this.request = request;
        this.userAuthorizationAttributeList = userAuthorizationAttributeList;
        this.userAuthorizationApplication = userAuthorizationApplication;
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
     * 获取用户授权的属性列表
     *
     * @return {@link UserAuthorizationAttribute}
     */
    public List<UserAuthorizationAttribute> getUserAuthorizationAttributeList() {
        return userAuthorizationAttributeList;
    }

    /**
     * 获取用户授权的当前应用实例
     *
     * @return {@link UserAuthorizationApplication}
     * @see SecurityApplication
     * @see ApplicationResource
     */
    public UserAuthorizationApplication getUserAuthorizationApplication() {
        return userAuthorizationApplication;
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
