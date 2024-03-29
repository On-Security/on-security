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

package org.minbox.framework.on.security.core.authorization.data.application;

import org.minbox.framework.on.security.core.authorization.jdbc.OnSecurityBaseJdbcRepository;

import java.util.List;

/**
 * 客户端范围数据存储库接口
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public interface SecurityApplicationScopeRepository extends OnSecurityBaseJdbcRepository<SecurityApplicationScope, String> {
    /**
     * 保存客户端范围基本信息
     * <p>
     * 新增或更新客户端范围基本信息，根据{@link SecurityApplicationScope#getId()}查询如果已经存在则执行更新否则执行新增
     *
     * @param clientScope {@link SecurityApplicationScope} 客户端范围实例
     */
    void save(SecurityApplicationScope clientScope);

    /**
     * 根据{@link SecurityApplication#getId()}查询客户端范围列表
     *
     * @param applicationId 应用ID {@link SecurityApplicationScope#getApplicationId()}
     * @return 客户端绑定的范围列表 {@link SecurityApplicationScope}
     */
    List<SecurityApplicationScope> findByApplicationId(String applicationId);
}
