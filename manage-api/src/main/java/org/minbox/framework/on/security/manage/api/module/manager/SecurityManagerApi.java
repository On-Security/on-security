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

package org.minbox.framework.on.security.manage.api.module.manager;

import org.minbox.framework.on.security.core.authorization.api.ApiException;
import org.minbox.framework.on.security.core.authorization.api.ApiResponse;
import org.minbox.framework.on.security.core.authorization.data.console.SecurityConsoleManager;
import org.minbox.framework.on.security.core.authorization.manage.context.OnSecurityManageContext;
import org.minbox.framework.on.security.core.authorization.manage.context.OnSecurityManageContextHolder;
import org.minbox.framework.on.security.manage.api.module.ApiErrorCodes;
import org.minbox.framework.on.security.manage.api.module.manager.service.SecurityManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理员基本信息维护接口
 *
 * @author 恒宇少年
 * @since 0.1.2
 */
@RestController
@RequestMapping(value = "/manager")
public class SecurityManagerApi {
    @Autowired
    private SecurityManagerService managerService;

    /**
     * 获取管理员基本信息
     *
     * @return {@link SecurityConsoleManager}
     */
    @GetMapping(value = "/info")
    public ApiResponse getManagerInfo() throws ApiException {
        OnSecurityManageContext manageContext = OnSecurityManageContextHolder.getContext();
        SecurityConsoleManager manager = managerService.selectById(manageContext.getManagerId());
        if (!manager.getEnabled()) {
            throw new ApiException(ApiErrorCodes.MANAGER_DISABLED);
        }
        if (manager.getDeleted()) {
            throw new ApiException(ApiErrorCodes.MANAGER_DELETED);
        }
        return ApiResponse.success(manager);
    }
}
