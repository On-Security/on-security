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

import org.minbox.framework.on.security.console.authorization.config.configurer.OnSecurityConsoleManageConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * 控制台授权管理配置
 *
 * @author 恒宇少年
 * @see OnSecurityConsoleManageConfigurer
 * @since 0.0.9
 */
@Configuration
public class ConsoleManageAuthorizationConfiguration {
    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE + 1)
    public SecurityFilterChain consoleManageSecurityFilterChain(HttpSecurity http) throws Exception {
        // Apply OnSecurityConsoleManageConfigurer
        OnSecurityConsoleManageConfigurer consoleManageConfigurer = new OnSecurityConsoleManageConfigurer();
        RequestMatcher requestMatcher = consoleManageConfigurer.getRequestMatcher();
        // @formatter:off
        http
                .requestMatcher(requestMatcher)
                .authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
                .csrf(csrf -> csrf.ignoringRequestMatchers(requestMatcher))
                .apply(consoleManageConfigurer);
        // @formatter:on
        return http.build();
    }
}
