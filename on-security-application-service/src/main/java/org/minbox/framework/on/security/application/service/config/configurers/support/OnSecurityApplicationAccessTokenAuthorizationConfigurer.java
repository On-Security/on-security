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

package org.minbox.framework.on.security.application.service.config.configurers.support;

import org.minbox.framework.on.security.application.service.authentication.OnSecurityAccessTokenAuthorizationProvider;
import org.minbox.framework.on.security.application.service.web.OnSecurityAccessTokenAuthorizationFilter;
import org.minbox.framework.on.security.core.authorization.configurer.AbstractOnSecurityOAuth2Configurer;
import org.minbox.framework.on.security.core.authorization.util.HttpSecuritySharedObjectUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter;

/**
 * 应用服务资源权限验证配置类
 * <p>
 * 该配置类会将应用服务的资源访问相关的认证提供者 {@link OnSecurityAccessTokenAuthorizationProvider}、
 * 认证过滤器进行注册{@link OnSecurityAccessTokenAuthorizationFilter}
 *
 * @author 恒宇少年
 * @see OnSecurityAccessTokenAuthorizationProvider
 * @see OnSecurityAccessTokenAuthorizationFilter
 * @since 0.0.6
 */
public class OnSecurityApplicationAccessTokenAuthorizationConfigurer extends AbstractOnSecurityOAuth2Configurer {
    public OnSecurityApplicationAccessTokenAuthorizationConfigurer(ObjectPostProcessor<Object> objectPostProcessor) {
        super(objectPostProcessor);
    }

    @Override
    public void init(HttpSecurity httpSecurity) {
        // @formatter:off
        OnSecurityAccessTokenAuthorizationProvider resourceAuthorizationProvider =
                new OnSecurityAccessTokenAuthorizationProvider(httpSecurity.getSharedObjects());
        // @formatter:on
        httpSecurity.authenticationProvider(resourceAuthorizationProvider);
    }

    @Override
    public void configure(HttpSecurity httpSecurity) {
        AuthenticationManager authenticationManager = HttpSecuritySharedObjectUtils.getAuthenticationManager(httpSecurity);
        OnSecurityAccessTokenAuthorizationFilter resourceAuthorizationFilter =
                new OnSecurityAccessTokenAuthorizationFilter(authenticationManager);
        httpSecurity.addFilterAfter(resourceAuthorizationFilter, BearerTokenAuthenticationFilter.class);
    }
}
