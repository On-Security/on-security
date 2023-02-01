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

package org.minbox.framework.on.security.core.authorization.data.role;

import java.util.List;

/**
 * 角色授权资源数据存储库
 *
 * @author 恒宇少年
 * @since 0.0.4
 */
public interface SecurityRoleAuthorizeResourceRepository {
    /**
     * 新增角色授权资源关系
     *
     * @param roleAuthorizeResource {@link SecurityRoleAuthorizeResource}
     */
    void insert(SecurityRoleAuthorizeResource roleAuthorizeResource);

    /**
     * 查询角色授权的资源关系列表
     *
     * @param roleId 角色ID {@link SecurityRole#getId()}
     * @return {@link SecurityRoleAuthorizeResource}
     */
    List<SecurityRoleAuthorizeResource> findByRoleId(String roleId);

    /**
     * 查询多个角色授权的全部资源关系列表
     *
     * @param roleIds 角色ID集合 {@link SecurityRole#getId()}
     * @return {@link SecurityRoleAuthorizeResource}
     */
    List<SecurityRoleAuthorizeResource> findByRoleIds(List<String> roleIds);

    /**
     * 根据ID删除角色资源的授权关系
     *
     * @param id {@link SecurityRoleAuthorizeResource#getId()}
     */
    void removeById(String id);

    /**
     * 根据角色ID删除全部授权的资源关系
     *
     * @param roleId 角色ID {@link SecurityRoleAuthorizeResource#getRoleId()}
     */
    void removeByRoleId(String roleId);
}
