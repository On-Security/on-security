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

package org.minbox.framework.on.security.console.data.manager;

import org.minbox.framework.on.security.core.authorization.data.console.SecurityConsoleManagerSession;

/**
 * 控制台管理会话业务逻辑接口定义
 *
 * @author 恒宇少年
 * @since 0.0.9
 */
public interface SecurityConsoleManagerSessionService {
    /**
     * 新增管理会话
     *
     * @param managerSession {@link SecurityConsoleManagerSession}
     */
    void insert(SecurityConsoleManagerSession managerSession);

    /**
     * 根据管理令牌查询会话信息
     *
     * @param manageToken {@link SecurityConsoleManagerSession#getManageTokenValue()}
     * @return 控制台管理员会话实例 {@link SecurityConsoleManagerSession}
     */
    SecurityConsoleManagerSession selectByToken(String manageToken);
}
