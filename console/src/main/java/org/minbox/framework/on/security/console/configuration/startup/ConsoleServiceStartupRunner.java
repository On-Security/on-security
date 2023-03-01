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

package org.minbox.framework.on.security.console.configuration.startup;

import org.minbox.framework.on.security.console.configuration.startup.event.ConsoleServiceStartupEvent;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * 控制台服务（Console Service）启动执行器
 *
 * @author 恒宇少年
 * @since 0.1.0
 */
@Configuration
public class ConsoleServiceStartupRunner implements CommandLineRunner {
    private ApplicationContext applicationContext;

    public ConsoleServiceStartupRunner(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void run(String... args) throws Exception {
        ConsoleServiceStartupEvent startupEvent = new ConsoleServiceStartupEvent(this);
        this.applicationContext.publishEvent(startupEvent);
    }
}
