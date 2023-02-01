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

package org.minbox.framework.on.security.open.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 开放接口服务应用启动类
 *
 * @author 恒宇少年
 * @since 0.0.7
 */
@SpringBootApplication
public class OpenApiServiceApplicationStartup {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(OpenApiServiceApplicationStartup.class);

    public static void main(String[] args) {
        SpringApplication.run(OpenApiServiceApplicationStartup.class, args);
        logger.info("On-Security OpenApi Service Startup Successfully.");
    }
}
