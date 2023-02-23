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
import org.minbox.framework.on.security.core.authorization.data.region.SecurityRegionSecretJdbcRepository;
import org.minbox.framework.on.security.core.authorization.data.region.SecurityRegionSecretRepository;
import org.minbox.framework.on.security.core.authorization.jdbc.definition.OnSecurityColumnName;
import org.minbox.framework.on.security.core.authorization.jdbc.sql.Condition;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;

/**
 * 安全域密钥业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 0.0.9
 */
@Service
public class SecurityRegionSecretServiceImpl implements SecurityRegionSecretService {
    private SecurityRegionSecretRepository regionSecretRepository;

    public SecurityRegionSecretServiceImpl(JdbcOperations jdbcOperations) {
        this.regionSecretRepository = new SecurityRegionSecretJdbcRepository(jdbcOperations);
    }

    @Override
    public SecurityRegionSecret selectBySecret(String regionId, String secret) {
        return regionSecretRepository.selectOne(Condition.withColumn(OnSecurityColumnName.RegionId, regionId),
                Condition.withColumn(OnSecurityColumnName.RegionSecret, secret));
    }
}
