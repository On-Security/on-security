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

/**
 * 客户端数据存储库接口
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public interface SecurityApplicationRepository extends OnSecurityBaseJdbcRepository<SecurityApplication, String> {
    /**
     * 存储客户端
     * <p>
     * 根据{@link SecurityApplication#getId()}判定是新增还是更新数据，如果已经存在则执行更新，否则执行新增
     *
     * @param client {@link SecurityApplication} 客户端对象实例
     */
    void save(SecurityApplication client);

    /**
     * 根据{@link SecurityApplication#getApplicationId()}查询客户端信息
     *
     * @param applicationId {@link SecurityApplication#getApplicationId()}
     * @return {@link SecurityApplication}
     */
    SecurityApplication findByApplicationId(String applicationId);
}
