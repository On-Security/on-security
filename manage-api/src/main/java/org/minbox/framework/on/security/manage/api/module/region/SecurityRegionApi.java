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

package org.minbox.framework.on.security.manage.api.module.region;

import org.minbox.framework.on.security.core.authorization.api.ApiResponse;
import org.minbox.framework.on.security.core.authorization.data.region.SecurityRegion;
import org.minbox.framework.on.security.core.authorization.manage.context.OnSecurityManageContext;
import org.minbox.framework.on.security.core.authorization.manage.context.OnSecurityManageContextHolder;
import org.minbox.framework.on.security.manage.api.module.region.service.SecurityRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 安全域相关接口
 *
 * @author 恒宇少年
 * @since 0.1.0
 */
@RestController
@RequestMapping(value = "/region")
public class SecurityRegionApi {
    @Autowired
    private SecurityRegionService regionService;

    /**
     * 获取授权的安全域列表
     *
     * @return {@link SecurityRegion}
     */
    @GetMapping(value = "/authorization")
    public ApiResponse getAuthorizationRegions() {
        OnSecurityManageContext manageContext = OnSecurityManageContextHolder.getContext();
        List<SecurityRegion> securityRegionList = this.regionService.getManagerAuthorization(manageContext);
        return ApiResponse.success(securityRegionList);
    }
}
