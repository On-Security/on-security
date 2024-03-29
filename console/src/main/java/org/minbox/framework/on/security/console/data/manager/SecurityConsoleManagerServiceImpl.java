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

import org.minbox.framework.on.security.core.authorization.data.console.SecurityConsoleManager;
import org.minbox.framework.on.security.core.authorization.data.console.SecurityConsoleManagerJdbcRepository;
import org.minbox.framework.on.security.core.authorization.jdbc.definition.OnSecurityColumnName;
import org.minbox.framework.on.security.core.authorization.jdbc.sql.Condition;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;

/**
 * @author 恒宇少年
 * @since 0.0.9
 */
@Service
public class SecurityConsoleManagerServiceImpl implements SecurityConsoleManagerService {
    private SecurityConsoleManagerJdbcRepository consoleManagerJdbcRepository;

    public SecurityConsoleManagerServiceImpl(JdbcOperations jdbcOperations) {
        this.consoleManagerJdbcRepository = new SecurityConsoleManagerJdbcRepository(jdbcOperations);
    }

    @Override
    public SecurityConsoleManager findByUsername(String username) {
        Condition condition = Condition.withColumn(OnSecurityColumnName.Username, username);
        return consoleManagerJdbcRepository.selectOne(condition);
    }

    @Override
    public SecurityConsoleManager selectById(String id) {
        return this.consoleManagerJdbcRepository.selectOne(id);
    }

    @Override
    public void insert(SecurityConsoleManager manager) {
        this.consoleManagerJdbcRepository.insert(manager);
    }
}
