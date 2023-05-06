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

package org.minbox.framework.on.security.manage.api.module.manager.service;

import org.minbox.framework.on.security.core.authorization.data.console.SecurityConsoleManager;
import org.minbox.framework.on.security.core.authorization.data.console.SecurityConsoleManagerJdbcRepository;
import org.minbox.framework.on.security.core.authorization.data.console.SecurityConsoleManagerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;

/**
 * 管理员业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 0.1.2
 */
@Service
public class SecurityManagerServiceImpl implements SecurityManagerService {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(SecurityManagerServiceImpl.class);
    private SecurityConsoleManagerRepository repository;

    public SecurityManagerServiceImpl(JdbcOperations jdbcOperations) {
        this.repository = new SecurityConsoleManagerJdbcRepository(jdbcOperations);
    }

    @Override
    public SecurityConsoleManager selectById(String managerId) {
        return repository.selectOne(managerId);
    }
}
