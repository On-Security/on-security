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

import org.minbox.framework.on.security.core.authorization.api.ApiException;
import org.minbox.framework.on.security.core.authorization.data.console.SecurityConsoleManager;
import org.minbox.framework.on.security.core.authorization.data.region.SecurityRegion;
import org.minbox.framework.on.security.core.authorization.jdbc.definition.OnSecurityColumnName;
import org.minbox.framework.on.security.core.authorization.jdbc.sql.Condition;
import org.minbox.framework.on.security.core.authorization.manage.context.OnSecurityManageContext;
import org.minbox.framework.on.security.manage.api.convert.RegionConvert;
import org.minbox.framework.on.security.manage.api.module.ApiErrorCodes;
import org.minbox.framework.on.security.manage.api.module.region.dao.SecurityRegionDAO;
import org.minbox.framework.on.security.manage.api.module.region.model.AddSecurityRegionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * 安全域业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 0.1.2
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class SecurityRegionServiceImpl implements SecurityRegionService {
    @Autowired
    private SecurityRegionDAO securityRegionDAO;

    @Override
    public List<SecurityRegion> selectAllAvailable() {
        // @formatter:off
        return this.securityRegionDAO.select(Condition.withColumn(OnSecurityColumnName.Enabled, true),
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
                return this.securityRegionDAO.select(Condition.withColumn(OnSecurityColumnName.Id, manager.getRegionId()),
                        Condition.withColumn(OnSecurityColumnName.Enabled, true),
                        Condition.withColumn(OnSecurityColumnName.Deleted, false));
                // @formatter:on
            }
        }
        return null;
    }

    @Override
    public SecurityRegion selectByRegionId(String regionId) {
        return this.securityRegionDAO.selectOne(Condition.withColumn(OnSecurityColumnName.RegionId, regionId));
    }

    @Override
    public void addRegion(AddSecurityRegionVO addSecurityRegionVO) throws ApiException {
        SecurityRegion storedRegion = this.selectByRegionId(addSecurityRegionVO.getRegionId());
        if (storedRegion != null) {
            throw new ApiException(ApiErrorCodes.REGION_ALREADY_EXIST, addSecurityRegionVO.getRegionId());
        }
        storedRegion = RegionConvert.INSTANCE.fromAddSecurityRegionVO(addSecurityRegionVO);
        storedRegion.setId(UUID.randomUUID().toString());
        storedRegion.setEnabled(true);
        this.securityRegionDAO.insert(storedRegion);
    }
}
