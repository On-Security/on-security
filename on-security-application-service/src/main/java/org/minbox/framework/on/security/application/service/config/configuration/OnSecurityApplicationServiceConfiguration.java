/*
 *     Copyright (C) 2022  恒宇少年
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.minbox.framework.on.security.application.service.config.configuration;

import org.minbox.framework.on.security.application.service.config.configurers.OnSecurityApplicationServiceConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 应用服务配置类
 *
 * @author 恒宇少年
 * @since 0.0.6
 */
@EnableWebSecurity
@Configuration
public class OnSecurityApplicationServiceConfiguration {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain onSecurityApplicationServiceSecurityFilterChain(HttpSecurity http) throws Exception {
        this.defaultApplicationService(http);
        // Apply OnSecurityApplicationServiceConfigurer
        OnSecurityApplicationServiceConfigurer applicationServiceConfigurer = new OnSecurityApplicationServiceConfigurer();
        http.apply(applicationServiceConfigurer);
        return http.build();
    }

    protected void defaultApplicationService(HttpSecurity http) throws Exception {
        // @formatter:off
        http.authorizeHttpRequests().anyRequest().authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt();
        // @formatter:on
    }
}
