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

import org.minbox.framework.on.security.application.service.authentication.OnSecurityApplicationResourceAuthorizationProvider;
import org.minbox.framework.on.security.application.service.web.OnSecurityApplicationResourceAuthorizationFilter;
import org.minbox.framework.on.security.core.authorization.configurer.AbstractOnSecurityOAuth2Configurer;
import org.minbox.framework.on.security.core.authorization.util.HttpSecuritySharedObjectUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter;

/**
 * 应用服务资源权限验证配置类
 * <p>
 * 该配置类会将应用服务的资源访问相关的认证提供者 {@link OnSecurityApplicationResourceAuthorizationProvider}、
 * 认证过滤器进行注册{@link OnSecurityApplicationResourceAuthorizationFilter}
 *
 * @author 恒宇少年
 * @see OnSecurityApplicationResourceAuthorizationProvider
 * @see OnSecurityApplicationResourceAuthorizationFilter
 * @since 0.0.6
 */
public class OnSecurityApplicationResourceAuthorizationConfigurer extends AbstractOnSecurityOAuth2Configurer {
    public OnSecurityApplicationResourceAuthorizationConfigurer(ObjectPostProcessor<Object> objectPostProcessor) {
        super(objectPostProcessor);
    }

    @Override
    public void init(HttpSecurity httpSecurity) {
        // @formatter:off
        OnSecurityApplicationResourceAuthorizationProvider resourceAuthorizationProvider =
                new OnSecurityApplicationResourceAuthorizationProvider(httpSecurity.getSharedObjects());
        // @formatter:on
        httpSecurity.authenticationProvider(resourceAuthorizationProvider);
    }

    @Override
    public void configure(HttpSecurity httpSecurity) {
        AuthenticationManager authenticationManager = HttpSecuritySharedObjectUtils.getAuthenticationManager(httpSecurity);
        OnSecurityApplicationResourceAuthorizationFilter resourceAuthorizationFilter =
                new OnSecurityApplicationResourceAuthorizationFilter(authenticationManager);
        httpSecurity.addFilterAfter(resourceAuthorizationFilter, BearerTokenAuthenticationFilter.class);
    }
}
