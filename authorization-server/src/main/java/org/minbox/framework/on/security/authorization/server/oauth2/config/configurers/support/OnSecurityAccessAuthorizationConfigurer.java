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

import org.minbox.framework.on.security.authorization.server.oauth2.authentication.support.OnSecurityAccessAuthorizationAuthenticationProvider;
import org.minbox.framework.on.security.authorization.server.oauth2.web.OnSecurityAccessAuthorizationEndpointFilter;
import org.minbox.framework.on.security.core.authorization.configurer.AbstractOnSecurityOAuth2Configurer;
import org.minbox.framework.on.security.core.authorization.util.HttpSecuritySharedObjectUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * @author 恒宇少年
 * @since 0.0.5
 */
public final class OnSecurityAccessAuthorizationConfigurer extends AbstractOnSecurityOAuth2Configurer {
    private static final String ACCESS_AUTHORIZATION_ENDPOINT_URI = "/access/authorization";
    private RequestMatcher requestMatcher;

    public OnSecurityAccessAuthorizationConfigurer(ObjectPostProcessor<Object> objectPostProcessor) {
        super(objectPostProcessor);
        this.requestMatcher = new AntPathRequestMatcher(ACCESS_AUTHORIZATION_ENDPOINT_URI, HttpMethod.GET.name());
    }

    @Override
    public void init(HttpSecurity httpSecurity) {
        // @formatter:off
        OnSecurityAccessAuthorizationAuthenticationProvider accessAuthorizationAuthenticationProvider =
                new OnSecurityAccessAuthorizationAuthenticationProvider(httpSecurity.getSharedObjects());
        // @formatter:on
        httpSecurity.authenticationProvider(accessAuthorizationAuthenticationProvider);
    }

    @Override
    public void configure(HttpSecurity httpSecurity) {
        // @formatter:off
        AuthenticationManager authenticationManager = HttpSecuritySharedObjectUtils.getAuthenticationManager(httpSecurity);
        OnSecurityAccessAuthorizationEndpointFilter accessAuthorizationEndpointFilter =
                new OnSecurityAccessAuthorizationEndpointFilter(this.requestMatcher,authenticationManager);
        // @formatter:on
        httpSecurity.addFilterAfter(postProcess(accessAuthorizationEndpointFilter), LogoutFilter.class);
    }

    @Override
    public RequestMatcher getRequestMatcher() {
        return this.requestMatcher;
    }
}
