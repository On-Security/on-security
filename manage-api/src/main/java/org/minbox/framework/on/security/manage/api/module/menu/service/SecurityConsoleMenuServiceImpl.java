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

import org.minbox.framework.on.security.core.authorization.data.console.SecurityConsoleMenu;
import org.minbox.framework.on.security.manage.api.module.menu.dao.SecurityConsoleMenuDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 控制台菜单业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 0.1.2
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class SecurityConsoleMenuServiceImpl implements SecurityConsoleMenuService {
    @Autowired
    private SecurityConsoleMenuDAO consoleMenuDAO;

    @Override
    public SecurityConsoleMenu selectById(String menuId) {
        return consoleMenuDAO.selectOne(menuId);
    }
}
