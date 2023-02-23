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
import org.minbox.framework.on.security.core.authorization.data.console.SecurityConsoleManagerSessionJdbcRepository;
import org.minbox.framework.on.security.core.authorization.data.console.SecurityConsoleManagerSessionRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;

/**
 * 控制台管理员会话业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 0.0.9
 */
@Service
public class SecurityConsoleManagerSessionServiceImpl implements SecurityConsoleManagerSessionService {
    private SecurityConsoleManagerSessionRepository managerSessionRepository;

    public SecurityConsoleManagerSessionServiceImpl(JdbcOperations jdbcOperations) {
        this.managerSessionRepository = new SecurityConsoleManagerSessionJdbcRepository(jdbcOperations);
    }

    @Override
    public void insert(SecurityConsoleManagerSession managerSession) {
        managerSessionRepository.insert(managerSession);
    }
}
