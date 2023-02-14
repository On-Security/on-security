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

package org.minbox.framework.on.security.core.authorization.data.console;

import org.minbox.framework.on.security.core.authorization.jdbc.OnSecurityBaseJdbcRepositorySupport;
import org.minbox.framework.on.security.core.authorization.jdbc.definition.OnSecurityTables;
import org.springframework.jdbc.core.JdbcOperations;

/**
 * 控制台管理员数据操作类
 *
 * @author 恒宇少年
 * @since 0.0.8
 */
public class SecurityConsoleManagerJdbcRepository extends OnSecurityBaseJdbcRepositorySupport<SecurityConsoleManager, String>
        implements SecurityConsoleManagerRepository {
    public SecurityConsoleManagerJdbcRepository(JdbcOperations jdbcOperations) {
        super(OnSecurityTables.SecurityConsoleManager, jdbcOperations);
    }

}
