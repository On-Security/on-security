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

import org.minbox.framework.on.security.console.data.manager.SecurityConsoleManagerAuthorizeMenuService;
import org.minbox.framework.on.security.core.authorization.data.console.SecurityConsoleManagerAuthorizeMenu;
import org.minbox.framework.on.security.core.authorization.data.console.SecurityConsoleMenu;
import org.minbox.framework.on.security.core.authorization.data.console.SecurityConsoleMenuJdbcRepository;
import org.minbox.framework.on.security.core.authorization.data.console.SecurityConsoleMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 控制台菜单业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 0.0.9
 */
@Service
public class SecurityConsoleMenuServiceImpl implements SecurityConsoleMenuService {
    private SecurityConsoleMenuRepository menuRepository;
    @Autowired
    private SecurityConsoleManagerAuthorizeMenuService managerAuthorizeMenuService;

    public SecurityConsoleMenuServiceImpl(JdbcOperations jdbcOperations) {
        this.menuRepository = new SecurityConsoleMenuJdbcRepository(jdbcOperations);
    }

    @Override
    public List<SecurityConsoleMenu> selectManagerAuthorizeMenus(String managerId) {
        List<SecurityConsoleManagerAuthorizeMenu> managerAuthorizeMenuList = this.managerAuthorizeMenuService.selectByManagerId(managerId);
        if (!ObjectUtils.isEmpty(managerAuthorizeMenuList)) {
            // @formatter:off
            return managerAuthorizeMenuList.stream()
                    .map(authorizeMenu -> this.menuRepository.selectOne(authorizeMenu.getMenuId()))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            // @formatter:on
        }
        return null;
    }
}
