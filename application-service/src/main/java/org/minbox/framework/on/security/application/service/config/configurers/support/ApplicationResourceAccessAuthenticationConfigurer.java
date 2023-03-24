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

import org.minbox.framework.on.security.application.service.authentication.ApplicationResourceAttributeBasedAccessControlAuthenticationProvider;
import org.minbox.framework.on.security.application.service.authentication.ApplicationResourceRoleBasedAccessControlAuthenticationProvider;
import org.minbox.framework.on.security.application.service.web.ApplicationResourceAttributeBasedAccessControlFilter;
import org.minbox.framework.on.security.application.service.web.ApplicationResourceRoleBasedAccessControlFilter;
import org.minbox.framework.on.security.application.service.web.OnSecurityAccessTokenAuthorizationFilter;
import org.minbox.framework.on.security.core.authorization.configurer.AbstractOnSecurityOAuth2Configurer;
import org.minbox.framework.on.security.core.authorization.util.HttpSecuritySharedObjectUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * 应用资源访问认证配置类
 * <p>
 * 该配置类会注册资源访问认证所需要的过滤器{@link ApplicationResourceRoleBasedAccessControlFilter}
 * 以及认证业务逻辑处理器{@link ApplicationResourceRoleBasedAccessControlAuthenticationProvider}
 *
 * @author 恒宇少年
 * @see ApplicationResourceRoleBasedAccessControlAuthenticationProvider
 * @see ApplicationResourceRoleBasedAccessControlFilter
 * @since 0.0.7
 */
public final class ApplicationResourceAccessAuthenticationConfigurer extends AbstractOnSecurityOAuth2Configurer {
    public ApplicationResourceAccessAuthenticationConfigurer(ObjectPostProcessor<Object> objectPostProcessor) {
        super(objectPostProcessor);
    }

    @Override
    public void init(HttpSecurity httpSecurity) {
        // Register ApplicationResourceRoleBasedAccessControlAuthenticationProvider
        ApplicationResourceRoleBasedAccessControlAuthenticationProvider roleBasedAccessControlAuthenticationProvider =
                new ApplicationResourceRoleBasedAccessControlAuthenticationProvider(httpSecurity.getSharedObjects());
        httpSecurity.authenticationProvider(roleBasedAccessControlAuthenticationProvider);
        
        // Register ApplicationResourceAttributeBasedAccessControlAuthenticationProvider
        ApplicationResourceAttributeBasedAccessControlAuthenticationProvider attributeBasedAccessControlAuthenticationProvider =
                new ApplicationResourceAttributeBasedAccessControlAuthenticationProvider(httpSecurity.getSharedObjects());
        httpSecurity.authenticationProvider(attributeBasedAccessControlAuthenticationProvider);
    }

    @Override
    public void configure(HttpSecurity httpSecurity) {
        AuthenticationManager authenticationManager = HttpSecuritySharedObjectUtils.getAuthenticationManager(httpSecurity);
        // Register ApplicationResourceRoleBasedAccessControlFilter
        ApplicationResourceRoleBasedAccessControlFilter roleBasedAccessControlFilter =
                new ApplicationResourceRoleBasedAccessControlFilter(authenticationManager);
        httpSecurity.addFilterAfter(roleBasedAccessControlFilter, OnSecurityAccessTokenAuthorizationFilter.class);

        // Register ApplicationResourceRoleBasedAccessControlFilter
        ApplicationResourceAttributeBasedAccessControlFilter attributeBasedAccessControlFilter =
                new ApplicationResourceAttributeBasedAccessControlFilter(authenticationManager);
        httpSecurity.addFilterAfter(attributeBasedAccessControlFilter, ApplicationResourceRoleBasedAccessControlFilter.class);
    }
}
