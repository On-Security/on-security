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

package org.minbox.framework.on.security.manage.api.module.region.service;

import org.minbox.framework.on.security.core.authorization.data.console.SecurityConsoleManager;
import org.minbox.framework.on.security.core.authorization.data.region.SecurityRegion;
import org.minbox.framework.on.security.core.authorization.data.region.SecurityRegionJdbcRepository;
import org.minbox.framework.on.security.core.authorization.data.region.SecurityRegionRepository;
import org.minbox.framework.on.security.core.authorization.jdbc.definition.OnSecurityColumnName;
import org.minbox.framework.on.security.core.authorization.jdbc.sql.Condition;
import org.minbox.framework.on.security.core.authorization.manage.context.OnSecurityManageContext;
import org.minbox.framework.on.security.manage.api.module.manager.service.SecurityManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 安全域业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 0.1.2
 */
@Service
public class SecurityRegionServiceImpl implements SecurityRegionService {
    private SecurityRegionRepository repository;
    @Autowired
    private SecurityManagerService managerService;

    public SecurityRegionServiceImpl(JdbcOperations jdbcOperations) {
        this.repository = new SecurityRegionJdbcRepository(jdbcOperations);
    }

    @Override
    public List<SecurityRegion> selectAllAvailable() {
        // @formatter:off
        return this.repository.select(Condition.withColumn(OnSecurityColumnName.Enabled, true),
                Condition.withColumn(OnSecurityColumnName.Deleted, false));
        // @formatter:on
    }

    @Override
    public List<SecurityRegion> getManagerAuthorization(OnSecurityManageContext manageContext) {
        SecurityConsoleManager manager = manageContext.getManager();
        if (manager != null) {
            if (manager.getInternal()) {
                return this.selectAllAvailable();
            } else {
                // @formatter:off
                return this.repository.select(Condition.withColumn(OnSecurityColumnName.Id, manager.getRegionId()),
                        Condition.withColumn(OnSecurityColumnName.Enabled, true),
                        Condition.withColumn(OnSecurityColumnName.Deleted, false));
                // @formatter:on
            }
        }
        return null;
    }
}
