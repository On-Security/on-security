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

import java.util.List;

/**
 * 资源授权属性数据存储库
 *
 * @author 恒宇少年
 * @since 0.0.4
 */
public interface SecurityResourceAuthorizeAttributeRepository {
    /**
     * 新增资源授权属性关系
     *
     * @param resourceAuthorizeAttribute {@link SecurityResourceAuthorizeAttribute}
     */
    void insert(SecurityResourceAuthorizeAttribute resourceAuthorizeAttribute);

    /**
     * 根据资源ID查询授权属性关系列表
     *
     * @param resourceId {@link SecurityResourceAuthorizeAttribute#getResourceId()}
     * @return {@link SecurityResourceAuthorizeAttribute}
     */
    List<SecurityResourceAuthorizeAttribute> findByResourceId(String resourceId);

    /**
     * 根据ID删除
     *
     * @param id {@link SecurityResourceAuthorizeAttribute#getId()}
     */
    void removeById(String id);

    /**
     * 根据资源ID删除
     *
     * @param resourceId {@link SecurityResourceAuthorizeAttribute#getResourceId()}
     */
    void removeByResourceId(String resourceId);
}
