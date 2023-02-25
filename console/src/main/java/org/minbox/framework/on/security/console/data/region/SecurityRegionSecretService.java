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

import org.minbox.framework.on.security.core.authorization.data.region.SecurityRegionSecret;

/**
 * 安全域密钥业务逻辑接口定义
 *
 * @author 恒宇少年
 * @since 0.0.9
 */
public interface SecurityRegionSecretService {
    /**
     * 根据安全域ID以及密钥查询
     *
     * @param regionId 安全域ID {@link SecurityRegionSecret#getRegionId()}
     * @param secret   安全域密钥 {@link SecurityRegionSecret#getRegionSecret()}
     * @return {@link SecurityRegionSecret}
     */
    SecurityRegionSecret selectBySecret(String regionId, String secret);

    /**
     * 根据ID查询安全域密钥
     *
     * @param id {@link SecurityRegionSecret#getId()}
     * @return {@link SecurityRegionSecret}
     */
    SecurityRegionSecret selectById(String id);
}
