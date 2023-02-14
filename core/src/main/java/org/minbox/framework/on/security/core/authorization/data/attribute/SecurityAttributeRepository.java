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

package org.minbox.framework.on.security.core.authorization.data.attribute;

import org.minbox.framework.on.security.core.authorization.jdbc.OnSecurityBaseJdbcRepository;

import java.util.List;

/**
 * 安全属性存储库
 *
 * @author 恒宇少年
 * @since 0.0.4
 */
public interface SecurityAttributeRepository extends OnSecurityBaseJdbcRepository<SecurityAttribute, String> {
    /**
     * 保存属性
     *
     * @param attribute {@link SecurityAttribute}
     */
    void save(SecurityAttribute attribute);

    /**
     * 根据安全域ID查询属性列表
     *
     * @param regionId 安全域ID {@link SecurityAttribute#getRegionId()}
     * @return 安全域下创建的全部属性
     */
    List<SecurityAttribute> findByRegionId(String regionId);

    /**
     * 根据ID列表查询属性列表
     *
     * @param ids 属性ID列表 {@link SecurityAttribute#getId()}
     * @return 属性列表 {@link SecurityAttribute}
     */
    List<SecurityAttribute> findByIds(List<String> ids);
}
