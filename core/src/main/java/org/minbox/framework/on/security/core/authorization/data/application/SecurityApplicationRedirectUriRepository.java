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
 * 客户端跳转地址数据存储库接口
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public interface SecurityApplicationRedirectUriRepository extends OnSecurityBaseJdbcRepository<SecurityApplicationRedirectUri, String> {
    /**
     * 存储客户端跳转地址
     * <p>
     * 根据{@link SecurityApplicationRedirectUri#getId()}判断如果已经存在执行更新，否则执行新增
     *
     * @param clientRedirectUri {@link SecurityApplicationRedirectUri} 客户端跳转地址对象实例
     */
    void save(SecurityApplicationRedirectUri clientRedirectUri);

    /**
     * 根据{@link SecurityApplication#getId()}查询客户端跳转地址列表
     *
     * @param applicationId 应用ID {@link SecurityApplicationRedirectUri#getApplicationId()}
     * @return 客户端跳转地址对象列表 {@link SecurityApplicationRedirectUri}
     */
    List<SecurityApplicationRedirectUri> findByApplicationId(String applicationId);
}
