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
 * 客户端认证数据存储库接口
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public interface SecurityApplicationAuthenticationRepository extends OnSecurityBaseJdbcRepository<SecurityApplicationAuthentication, String> {
    /**
     * 存储客户端认证信息
     * <p>
     * 根据{@link SecurityApplicationAuthentication#getId()}判定是否存在，如果已经存在执行更新，否则执行新增
     *
     * @param clientAuthentication {@link SecurityApplicationAuthentication} 客户端安全认证对象实例
     */
    void save(SecurityApplicationAuthentication clientAuthentication);

    /**
     * 根据{@link SecurityApplication#getId()}查询客户端认证信息
     *
     * @param applicationId 应用ID {@link SecurityApplicationAuthentication#getApplicationId()}
     * @return 客户端认证信息对象实例 {@link SecurityApplicationAuthentication}
     */
    SecurityApplicationAuthentication findByApplicationId(String applicationId);
}
