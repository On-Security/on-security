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

package org.minbox.framework.on.security.core.authorization.data.user;

import org.minbox.framework.on.security.core.authorization.jdbc.OnSecurityBaseJdbcRepository;

import java.util.List;

/**
 * 用户属性授权关系数据存储库
 *
 * @author 恒宇少年
 */
public interface SecurityUserAuthorizeAttributeRepository extends OnSecurityBaseJdbcRepository<SecurityUserAuthorizeAttribute, String> {

    /**
     * 查询用户授权的属性ID列表
     *
     * @param userId 用户ID {@link SecurityUser#getId()}
     * @return 用户授权的属性关系实例列表 {@link SecurityUserAuthorizeAttribute}
     */
    List<SecurityUserAuthorizeAttribute> findByUserId(String userId);
}
