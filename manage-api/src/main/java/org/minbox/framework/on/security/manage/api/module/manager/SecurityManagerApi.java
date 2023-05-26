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
import org.minbox.framework.on.security.manage.api.module.manager.model.AddManagerVO;
import org.minbox.framework.on.security.manage.api.module.manager.model.DeleteManagerVO;
import org.minbox.framework.on.security.manage.api.module.manager.model.UpdateManagerVO;
import org.minbox.framework.on.security.manage.api.module.manager.service.SecurityManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    /**
     * 添加管理员
     *
     * @param addManagerVO 添加管理员的请求实体 {@link AddManagerVO}
     * @return {@link ApiResponse}
     */
    @PostMapping(value = "/add")
    public ApiResponse addManager(@RequestBody @Valid AddManagerVO addManagerVO) throws ApiException {
        OnSecurityManageContext manageContext = OnSecurityManageContextHolder.getContext();
        addManagerVO.setRegionId(manageContext.getRegion().getId());
        managerService.addManager(addManagerVO);
        return ApiResponse.success();
    }

    /**
     * 更新管理员
     *
     * @param updateManagerVO 更新管理员的请求实体 {@link UpdateManagerVO}
     * @return {@link ApiResponse}
     */
    @PostMapping(value = "/update")
    public ApiResponse updateManager(@RequestBody @Valid UpdateManagerVO updateManagerVO) {
        managerService.updateManager(updateManagerVO);
        return ApiResponse.success();
    }

    /**
     * 删除管理员
     *
     * @param deleteManagerVO 删除管理员的请求实体 {@link DeleteManagerVO}
     * @return {@link ApiResponse}
     */
    @PostMapping(value = "/delete")
    public ApiResponse deleteManager(@RequestBody @Valid DeleteManagerVO deleteManagerVO) {
        managerService.deleteManager(deleteManagerVO);
        return ApiResponse.success();
    }
}
