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
import org.minbox.framework.on.security.core.authorization.endpoint.AccessTokenSession;

import java.util.List;
import java.util.Map;

/**
 * 应用上下文接口定义
 *
 * @author 恒宇少年
 * @see AccessTokenSession
 * @see org.minbox.framework.on.security.core.authorization.data.resource.UserAuthorizationResource
 * @see org.minbox.framework.on.security.core.authorization.data.attribute.UserAuthorizationAttribute
 * @see org.minbox.framework.on.security.core.authorization.data.role.UserAuthorizationRole
 * @since 0.0.6
 */
public interface OnSecurityApplicationContext {
    /**
     * 获取本次访问资源携带的"AccessToken"
     *
     * @return 从请求Header中提取的"AccessToken"
     * @see org.minbox.framework.on.security.core.authorization.endpoint.resolver.BearerTokenResolver
     */
    String getAccessToken();

    /**
     * 获取"AccessToken"所属用户的元数据列表
     *
     * @return 用户元数据集合
     */
    Map<String, Object> getUserMetadata();

    /**
     * 获取令牌会话基本信息，包含状态、发布时间、过期时间、授权Scopes等
     *
     * @return {@link AccessTokenSession}
     */
    AccessTokenSession getSession();

    /**
     * 获取用户授权的资源列表
     *
     * @return 用户授权资源 {@link org.minbox.framework.on.security.core.authorization.data.resource.UserAuthorizationResource}
     */
    List<UserAuthorizationResource> getUserAuthorizationResource();

    /**
     * 获取用户授权的属性列表
     *
     * @return 用户授权属性 {@link org.minbox.framework.on.security.core.authorization.data.attribute.UserAuthorizationAttribute}
     */
    List<UserAuthorizationAttribute> getUserAuthorizationAttribute();

    /**
     * 获取用户授权的角色列表
     *
     * @return 用户授权角色 {@link org.minbox.framework.on.security.core.authorization.data.role.UserAuthorizationRole}
     */
    List<UserAuthorizationRole> getUserAuthorizationRole();
}
