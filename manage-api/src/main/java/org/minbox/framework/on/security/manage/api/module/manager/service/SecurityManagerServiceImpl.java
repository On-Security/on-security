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

package org.minbox.framework.on.security.manage.api.module.manager.service;

import org.minbox.framework.on.security.core.authorization.api.ApiException;
import org.minbox.framework.on.security.core.authorization.data.console.SecurityConsoleManager;
import org.minbox.framework.on.security.core.authorization.jdbc.definition.OnSecurityColumnName;
import org.minbox.framework.on.security.core.authorization.jdbc.sql.ColumnValue;
import org.minbox.framework.on.security.core.authorization.jdbc.sql.Condition;
import org.minbox.framework.on.security.manage.api.convert.ConsoleManagerConvert;
import org.minbox.framework.on.security.manage.api.module.ApiErrorCodes;
import org.minbox.framework.on.security.manage.api.module.manager.dao.SecurityManagerDAO;
import org.minbox.framework.on.security.manage.api.module.manager.model.AddManagerVO;
import org.minbox.framework.on.security.manage.api.module.manager.model.DeleteManagerVO;
import org.minbox.framework.on.security.manage.api.module.manager.model.UpdateManagerVO;
import org.minbox.framework.on.security.manage.api.module.menu.service.SecurityConsoleManagerAuthorizeMenuService;
import org.minbox.framework.on.security.manage.api.module.session.service.SecurityConsoleManagerSessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.UUID;

/**
 * 管理员业务逻辑实现层
 *
 * @author 恒宇少年
 * @since 0.1.2
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class SecurityManagerServiceImpl implements SecurityManagerService {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(SecurityManagerServiceImpl.class);

    @Autowired
    private SecurityManagerDAO managerDAO;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SecurityConsoleManagerAuthorizeMenuService managerAuthorizeMenuService;
    @Autowired
    private SecurityConsoleManagerSessionService managerSessionService;

    @Override
    public SecurityConsoleManager selectById(String managerId) {
        return managerDAO.selectOne(managerId);
    }

    @Override
    public SecurityConsoleManager selectByUsername(String username) {
        return managerDAO.selectOne(Condition.withColumn(OnSecurityColumnName.Username, username));
    }

    @Override
    public void addManager(AddManagerVO addManagerVO) throws ApiException {
        SecurityConsoleManager storedManager = selectByUsername(addManagerVO.getUsername());
        if (storedManager != null) {
            throw new ApiException(ApiErrorCodes.MANAGER_ALREADY_EXIST, addManagerVO.getUsername());
        }
        storedManager = ConsoleManagerConvert.INSTANCE.fromAddManagerVO(addManagerVO);
        storedManager.setId(UUID.randomUUID().toString());
        storedManager.setPassword(passwordEncoder.encode(addManagerVO.getPassword()));
        this.managerDAO.insert(storedManager);
        managerAuthorizeMenuService.authorize(storedManager.getRegionId(), storedManager.getId(), addManagerVO.getAuthorizeMenuIds());
    }

    @Override
    public void updateManager(UpdateManagerVO updateManagerVO) throws ApiException {
        SecurityConsoleManager storedManager = selectById(updateManagerVO.getManagerId());
        if (storedManager == null) {
            throw new ApiException(ApiErrorCodes.MANAGER_NOT_FOUND, updateManagerVO.getManagerId());
        }
        ConsoleManagerConvert.INSTANCE.fromUpdateManagerVO(updateManagerVO, storedManager);
        this.managerDAO.update(storedManager);
        managerAuthorizeMenuService.reauthorize(storedManager.getRegionId(), storedManager.getId(), updateManagerVO.getAuthorizeMenuIds());
    }

    @Override
    public void deleteManager(DeleteManagerVO deleteManagerVO) throws ApiException {
        // @formatter:off
        this.managerDAO.update(
                Arrays.asList(
                        ColumnValue.with(OnSecurityColumnName.Enabled, false),
                        ColumnValue.with(OnSecurityColumnName.Deleted, true)
                ),
                Condition.withColumn(OnSecurityColumnName.Id, deleteManagerVO.getManagerId()));
        // @formatter:on
        managerSessionService.setAllExpiration(deleteManagerVO.getManagerId());
    }
}
