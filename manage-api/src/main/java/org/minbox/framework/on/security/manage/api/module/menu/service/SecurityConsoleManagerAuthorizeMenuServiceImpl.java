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

package org.minbox.framework.on.security.manage.api.module.menu.service;

import org.minbox.framework.on.security.core.authorization.api.ApiException;
import org.minbox.framework.on.security.core.authorization.data.console.SecurityConsoleManagerAuthorizeMenu;
import org.minbox.framework.on.security.core.authorization.data.console.SecurityConsoleMenu;
import org.minbox.framework.on.security.manage.api.module.ApiErrorCodes;
import org.minbox.framework.on.security.manage.api.module.menu.dao.SecurityConsoleManagerAuthorizeMenuDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * 控制台管理员授权菜单业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 0.1.2
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class SecurityConsoleManagerAuthorizeMenuServiceImpl implements SecurityConsoleManagerAuthorizeMenuService {
    @Autowired
    private SecurityConsoleManagerAuthorizeMenuDAO consoleManagerAuthorizeMenuDAO;
    @Autowired
    private SecurityConsoleMenuService consoleMenuService;

    @Override
    public void authorize(String regionId, String managerId, List<String> menuIds) {
        if (!ObjectUtils.isEmpty(menuIds)) {
            menuIds.forEach(menuId -> {
                SecurityConsoleMenu consoleMenu = consoleMenuService.selectById(menuId);
                if (consoleMenu == null) {
                    throw new ApiException(ApiErrorCodes.MENU_NOT_FOUND, menuId);
                }
                SecurityConsoleManagerAuthorizeMenu authorizeMenu = new SecurityConsoleManagerAuthorizeMenu();
                authorizeMenu.setId(UUID.randomUUID().toString());
                authorizeMenu.setRegionId(regionId);
                authorizeMenu.setMenuId(menuId);
                authorizeMenu.setManagerId(managerId);
                authorizeMenu.setAuthorizeTime(LocalDateTime.now());
                consoleManagerAuthorizeMenuDAO.insert(authorizeMenu);
            });
        }
    }

    @Override
    public void reauthorize(String regionId, String managerId, List<String> menuIds) {
        if (!ObjectUtils.isEmpty(menuIds)) {
            consoleManagerAuthorizeMenuDAO.deleteByManagerId(managerId);
            authorize(regionId, managerId, menuIds);
        }
    }
}
