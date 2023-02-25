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

package org.minbox.framework.on.security.console.authorization.config.configurer;

import org.minbox.framework.on.security.console.authorization.authentication.OnSecurityManageTokenAccessAuthorizationProvider;
import org.minbox.framework.on.security.console.authorization.web.OnSecurityManageTokenAccessAuthorizationFilter;
import org.minbox.framework.on.security.core.authorization.util.HttpSecuritySharedObjectUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.logout.LogoutFilter;

/**
 * 管理令牌访问认证配置
 *
 * @author 恒宇少年
 * @see OnSecurityManageTokenAccessAuthorizationProvider
 * @see OnSecurityManageTokenAccessAuthorizationFilter
 * @see AuthenticationManager
 * @since 0.0.9
 */
public class OnSecurityManageTokenAccessConfigurer extends AbstractHttpConfigurer<OnSecurityManageTokenAccessConfigurer, HttpSecurity> {
    @Override
    public void init(HttpSecurity httpSecurity) throws Exception {
        // @formatter:off
        OnSecurityManageTokenAccessAuthorizationProvider manageTokenAccessAuthorizationProvider
                = new OnSecurityManageTokenAccessAuthorizationProvider(httpSecurity.getSharedObjects());
        // @formatter:on
        httpSecurity.authenticationProvider(manageTokenAccessAuthorizationProvider);
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManager authenticationManager = HttpSecuritySharedObjectUtils.getAuthenticationManager(httpSecurity);
        // @formatter:off
        OnSecurityManageTokenAccessAuthorizationFilter manageTokenAccessAuthorizationFilter =
                new OnSecurityManageTokenAccessAuthorizationFilter(authenticationManager);
        // @formatter:on
        httpSecurity.addFilterAfter(this.postProcess(manageTokenAccessAuthorizationFilter), LogoutFilter.class);
    }
}
