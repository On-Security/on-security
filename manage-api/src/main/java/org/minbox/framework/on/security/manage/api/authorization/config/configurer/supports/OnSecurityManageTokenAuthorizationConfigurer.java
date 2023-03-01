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

package org.minbox.framework.on.security.manage.api.authorization.config.configurer.supports;

import org.minbox.framework.on.security.core.authorization.configurer.AbstractOnSecurityOAuth2Configurer;
import org.minbox.framework.on.security.core.authorization.util.HttpSecuritySharedObjectUtils;
import org.minbox.framework.on.security.manage.api.authorization.authentication.OnSecurityManageTokenAuthorizationProvider;
import org.minbox.framework.on.security.manage.api.authorization.web.OnSecurityManageTokenAuthorizationFilter;
import org.minbox.framework.on.security.manage.api.configuration.ConsoleConfigProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.logout.LogoutFilter;

/**
 * 管理令牌（Manage Token）授权认证配置
 *
 * @author 恒宇少年
 * @see OnSecurityManageTokenAuthorizationProvider
 * @see OnSecurityManageTokenAuthorizationFilter
 * @see AuthenticationManager
 * @since 0.1.0
 */
public class OnSecurityManageTokenAuthorizationConfigurer extends AbstractOnSecurityOAuth2Configurer {
    public OnSecurityManageTokenAuthorizationConfigurer(ObjectPostProcessor<Object> objectPostProcessor) {
        super(objectPostProcessor);
    }

    @Override
    public void init(HttpSecurity httpSecurity) {
        // @formatter:off
        // Get ConsoleConfigProperties Bean
        ApplicationContext applicationContext = HttpSecuritySharedObjectUtils.getApplicationContext(httpSecurity);
        ConsoleConfigProperties consoleConfigProperties = applicationContext.getBean(ConsoleConfigProperties.class);
        OnSecurityManageTokenAuthorizationProvider manageTokenAuthorizationProvider =
                new OnSecurityManageTokenAuthorizationProvider(consoleConfigProperties.getServerAddress(), httpSecurity.getSharedObjects());
        httpSecurity.authenticationProvider(manageTokenAuthorizationProvider);
        // @formatter:on
    }

    @Override
    public void configure(HttpSecurity httpSecurity) {
        // @formatter:off
        AuthenticationManager authenticationManager = HttpSecuritySharedObjectUtils.getAuthenticationManager(httpSecurity);
        OnSecurityManageTokenAuthorizationFilter manageTokenAuthorizationFilter =
                new OnSecurityManageTokenAuthorizationFilter(authenticationManager);
        httpSecurity.addFilterAfter(manageTokenAuthorizationFilter, LogoutFilter.class);
        // @formatter:on
    }
}
