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

package org.minbox.framework.on.security.manage.api.module.menu;

import org.minbox.framework.on.security.core.authorization.api.ApiResponse;
import org.minbox.framework.on.security.core.authorization.data.console.SecurityConsoleMenu;
import org.minbox.framework.on.security.core.authorization.manage.context.OnSecurityManageContext;
import org.minbox.framework.on.security.core.authorization.manage.context.OnSecurityManageContextHolder;
import org.minbox.framework.on.security.manage.api.module.menu.service.SecurityConsoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * 控制台菜单接口
 *
 * @author 恒宇少年
 * @since 0.1.2
 */
@RestController
@RequestMapping(value = "/menu")
public class SecurityConsoleMenuApi {
    @Autowired
    private SecurityConsoleMenuService consoleMenuService;

    /**
     * 获取管理员授权的菜单列表
     *
     * @return {@link SecurityConsoleMenu}
     */
    @GetMapping(value = "/authorized")
    public ApiResponse selectAuthorized() {
        OnSecurityManageContext manageContext = OnSecurityManageContextHolder.getContext();
        Set<SecurityConsoleMenu> consoleMenuList = manageContext.getManagerAuthorizeMenu();
        return ApiResponse.success(consoleMenuList);
    }
}
