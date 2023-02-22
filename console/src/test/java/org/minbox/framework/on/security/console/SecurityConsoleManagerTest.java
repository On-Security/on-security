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

package org.minbox.framework.on.security.console;

import org.junit.jupiter.api.Test;
import org.minbox.framework.on.security.console.base.RunTest;
import org.minbox.framework.on.security.console.data.manager.SecurityConsoleManagerService;
import org.minbox.framework.on.security.core.authorization.data.console.SecurityConsoleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

/**
 * {@link SecurityConsoleManagerTest}单元测试类
 *
 * @author 恒宇少年
 */
@RunTest
public class SecurityConsoleManagerTest {
    @Autowired
    private SecurityConsoleManagerService consoleManagerService;

    @Test
    public void testSelect() {
        SecurityConsoleManager consoleManager = consoleManagerService.findByUsername("admin");
        System.out.println(consoleManager);
        Assert.notNull(consoleManager, "并未查询到管理员");
    }
}
