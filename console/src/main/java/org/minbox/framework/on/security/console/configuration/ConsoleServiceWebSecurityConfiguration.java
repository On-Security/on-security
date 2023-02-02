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

package org.minbox.framework.on.security.console.configuration;

import org.minbox.framework.on.security.authorization.server.oauth2.config.configuration.OnSecurityWebConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 控制台服务Web安全配置类
 *
 * @author 恒宇少年
 * @since 0.0.7
 */
@Configuration
public class ConsoleServiceWebSecurityConfiguration extends OnSecurityWebConfiguration {
    @Override
    public SecurityFilterChain onSecurityWebSecurityFilterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http.authorizeHttpRequests().anyRequest().authenticated()
                .and()
                .formLogin(Customizer.withDefaults())
                .oauth2ResourceServer()
                .jwt();
        // @formatter:on
        return http.build();
    }
}