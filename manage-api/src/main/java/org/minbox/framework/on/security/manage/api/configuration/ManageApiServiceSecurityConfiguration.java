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

package org.minbox.framework.on.security.manage.api.configuration;

import org.minbox.framework.on.security.manage.api.authorization.config.configurer.OnSecurityManageApiAccessConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 开放接口服务安全配置
 *
 * @author 恒宇少年
 * @since 0.0.7
 */
@Configuration
public class ManageApiServiceSecurityConfiguration {
    @Bean
    public SecurityFilterChain onSecurityWebSecurityFilterChain(HttpSecurity http) throws Exception {
        OnSecurityManageApiAccessConfigurer accessConfigurer = new OnSecurityManageApiAccessConfigurer();
        http.apply(accessConfigurer);
        // @formatter:off
        return http
                .csrf().disable()
                .build();
        // @formatter:on
    }
}
