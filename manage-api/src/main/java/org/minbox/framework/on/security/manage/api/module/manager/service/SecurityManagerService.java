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
import org.minbox.framework.on.security.manage.api.module.manager.model.AddManagerVO;
import org.minbox.framework.on.security.manage.api.module.manager.model.DeleteManagerVO;
import org.minbox.framework.on.security.manage.api.module.manager.model.UpdateManagerVO;

/**
 * 管理员业务逻辑接口定义
 *
 * @author 恒宇少年
 * @since 0.1.2
 */
public interface SecurityManagerService {
    /**
     * 根据ID查询管理员信息
     *
     * @param managerId {@link SecurityConsoleManager#getId()}
     * @return {@link SecurityConsoleManager}
     */
    SecurityConsoleManager selectById(String managerId);

    /**
     * 根据用户名查询管理员信息
     *
     * @param username {@link SecurityConsoleManager#getUsername()}
     * @return {@link SecurityConsoleManager}
     */
    SecurityConsoleManager selectByUsername(String username);

    /**
     * 添加管理员
     *
     * @param addManagerVO 添加管理员数据实体 {@link AddManagerVO}
     */
    void addManager(AddManagerVO addManagerVO) throws ApiException;

    /**
     * 更新管理员
     *
     * @param updateManagerVO 更新管理员数据实体 {@link UpdateManagerVO}
     */
    void updateManager(UpdateManagerVO updateManagerVO) throws ApiException;

    /**
     * 删除管理员
     *
     * @param deleteManagerVO 删除管理员数据实体 {@link DeleteManagerVO}
     */
    void deleteManager(DeleteManagerVO deleteManagerVO) throws ApiException;
}
