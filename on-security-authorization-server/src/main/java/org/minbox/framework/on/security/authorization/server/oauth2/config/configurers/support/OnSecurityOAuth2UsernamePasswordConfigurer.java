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

package org.minbox.framework.on.security.authorization.server.oauth2.config.configurers.support;

import org.minbox.framework.on.security.authorization.server.oauth2.authentication.support.OnSecurityOAuth2UsernamePasswordAuthenticationProvider;
import org.minbox.framework.on.security.authorization.server.oauth2.config.configurers.AbstractOnSecurityOAuth2Configurer;
import org.minbox.framework.on.security.authorization.server.oauth2.web.OnSecurityOAuth2UsernamePasswordAuthenticationFilter;
import org.minbox.framework.on.security.authorization.server.utils.HttpSecuritySharedObjectUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * OnSecurity用户密码方式认证配置类
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class OnSecurityOAuth2UsernamePasswordConfigurer extends AbstractOnSecurityOAuth2Configurer {
    private RequestMatcher requestMatcher;

    public OnSecurityOAuth2UsernamePasswordConfigurer(ObjectPostProcessor<Object> objectPostProcessor) {
        super(objectPostProcessor);
    }

    @Override
    protected void init(HttpSecurity httpSecurity) {
        AuthorizationServerSettings authorizationServerSettings = HttpSecuritySharedObjectUtils.getAuthorizationServerSettings(httpSecurity);
        this.requestMatcher = new AntPathRequestMatcher(authorizationServerSettings.getTokenEndpoint(), HttpMethod.POST.name());
        OnSecurityOAuth2UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider =
                new OnSecurityOAuth2UsernamePasswordAuthenticationProvider(httpSecurity.getSharedObjects());
        PasswordEncoder passwordEncoder = HttpSecuritySharedObjectUtils.getOptionalBean(httpSecurity, PasswordEncoder.class);
        if (passwordEncoder != null) {
            usernamePasswordAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        }
        httpSecurity.authenticationProvider(usernamePasswordAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) {
        AuthenticationManager authenticationManager = HttpSecuritySharedObjectUtils.getAuthenticationManager(httpSecurity);
        OnSecurityOAuth2UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter =
                new OnSecurityOAuth2UsernamePasswordAuthenticationFilter(this.requestMatcher, authenticationManager);
        httpSecurity.addFilterAfter(postProcess(usernamePasswordAuthenticationFilter), FilterSecurityInterceptor.class);
    }

    @Override
    public RequestMatcher getRequestMatcher() {
        return this.requestMatcher;
    }
}
