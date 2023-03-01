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

package org.minbox.framework.on.security.console.data.region;

import org.minbox.framework.on.security.core.authorization.data.region.SecurityRegion;

/**
 * 安全域业务逻辑接口定义
 *
 * @author 恒宇少年
 * @since 0.0.9
 */
public interface SecurityRegionService {
    /**
     * 根据ID查询信息
     *
     * @param id ID {@link SecurityRegion#getId()}
     * @return {@link SecurityRegion}
     */
    SecurityRegion selectById(String id);

    /**
     * 根据安全域ID查询信息
     *
     * @param regionId 安全域ID {@link SecurityRegion#getRegionId()}
     * @return {@link SecurityRegion}
     */
    SecurityRegion selectByRegionId(String regionId);

    /**
     * 新增一个安全域
     *
     * @param region {@link SecurityRegion}
     */
    void insert(SecurityRegion region);
}
