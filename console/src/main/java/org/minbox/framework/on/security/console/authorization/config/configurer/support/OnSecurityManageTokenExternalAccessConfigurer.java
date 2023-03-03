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

package org.minbox.framework.on.security.console.authorization.config.configurer.support;

import org.minbox.framework.on.security.console.authorization.authentication.OnSecurityManageTokenAccessAuthorizationProvider;
import org.minbox.framework.on.security.console.authorization.web.OnSecurityManageTokenExternalAccessAuthorizationFilter;
import org.minbox.framework.on.security.core.authorization.configurer.AbstractOnSecurityOAuth2Configurer;
import org.minbox.framework.on.security.core.authorization.endpoint.OnSecurityEndpoints;
import org.minbox.framework.on.security.core.authorization.util.HttpSecuritySharedObjectUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * 管理令牌（ManageToken）外部服务访问配置
 *
 * @author 恒宇少年
 * @since 0.0.9
 */
public class OnSecurityManageTokenExternalAccessConfigurer extends AbstractOnSecurityOAuth2Configurer {
    // @formatter:off
    private RequestMatcher MANAGE_TOKEN_ACCESS_REQUEST_MATCHER =
            new AntPathRequestMatcher(OnSecurityEndpoints.MANAGE_TOKEN_ACCESS_AUTHORIZATION_ENDPOINT, HttpMethod.POST.name());
    // @formatter:on
    public OnSecurityManageTokenExternalAccessConfigurer(ObjectPostProcessor<Object> objectPostProcessor) {
        super(objectPostProcessor);
    }

    @Override
    public void init(HttpSecurity httpSecurity) {
        // @formatter:off
        OnSecurityManageTokenAccessAuthorizationProvider manageTokenAccessAuthorizationProvider
                = new OnSecurityManageTokenAccessAuthorizationProvider(httpSecurity.getSharedObjects());
        // @formatter:on
        httpSecurity.authenticationProvider(manageTokenAccessAuthorizationProvider);
    }

    @Override
    public RequestMatcher getRequestMatcher() {
        return MANAGE_TOKEN_ACCESS_REQUEST_MATCHER;
    }

    @Override
    public void configure(HttpSecurity httpSecurity) {
        AuthenticationManager authenticationManager = HttpSecuritySharedObjectUtils.getAuthenticationManager(httpSecurity);
        // @formatter:off
        OnSecurityManageTokenExternalAccessAuthorizationFilter manageTokenExternalAccessAuthorizationFilter =
                new OnSecurityManageTokenExternalAccessAuthorizationFilter(MANAGE_TOKEN_ACCESS_REQUEST_MATCHER, authenticationManager);
        // @formatter:on
        httpSecurity.addFilterAfter(this.postProcess(manageTokenExternalAccessAuthorizationFilter), LogoutFilter.class);
    }
}
