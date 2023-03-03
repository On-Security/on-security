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

package org.minbox.framework.on.security.console.data.menu;

import org.minbox.framework.on.security.core.authorization.data.console.SecurityConsoleManagerAuthorizeMenu;
import org.minbox.framework.on.security.core.authorization.data.console.SecurityConsoleMenu;

import java.util.List;

/**
 * 控制台菜单业务逻辑接口
 *
 * @author 恒宇少年
 * @since 0.0.9
 */
public interface SecurityConsoleMenuService {
    /**
     * 查询全部有效的菜单
     *
     * @return {@link SecurityConsoleMenu}
     */
    List<SecurityConsoleMenu> selectAllMenus();

    /**
     * 查询管理员授权的菜单列表
     *
     * @param managerId 管理员ID {@link SecurityConsoleManagerAuthorizeMenu#getManagerId()}
     * @return {@link SecurityConsoleMenu}
     */
    List<SecurityConsoleMenu> selectManagerAuthorizeMenus(String managerId);
}
