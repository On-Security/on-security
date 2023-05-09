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

import org.minbox.framework.on.security.core.authorization.data.console.SecurityConsoleManager;
import org.minbox.framework.on.security.core.authorization.data.console.SecurityConsoleMenu;
import org.minbox.framework.on.security.core.authorization.data.region.SecurityRegion;

import java.util.List;

/**
 * 控制台管理员授权菜单业务逻辑接口
 *
 * @author 恒宇少年
 * @since 0.1.2
 */
public interface SecurityConsoleManagerAuthorizeMenuService {
    /**
     * 给管理员授权菜单
     *
     * @param regionId  安全域ID {@link SecurityRegion#getId()}
     * @param managerId 管理员ID {@link SecurityConsoleManager#getId()}
     * @param menuIds   菜单ID集合 {@link SecurityConsoleMenu#getId()}
     */
    void authorize(String regionId, String managerId, List<String> menuIds);
}
