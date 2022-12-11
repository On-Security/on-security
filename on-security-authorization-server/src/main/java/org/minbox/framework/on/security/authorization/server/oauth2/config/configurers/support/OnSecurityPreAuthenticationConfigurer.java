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

import org.minbox.framework.on.security.authorization.server.oauth2.config.configurers.AbstractOnSecurityOAuth2Configurer;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * @author 恒宇少年
 */
public class OnSecurityPreAuthenticationConfigurer extends AbstractOnSecurityOAuth2Configurer {
    private RequestMatcher allEndpointMatcher;

    public OnSecurityPreAuthenticationConfigurer(ObjectPostProcessor<Object> objectPostProcessor, RequestMatcher allEndpointMatcher) {
        super(objectPostProcessor);
        this.allEndpointMatcher = allEndpointMatcher;
    }

    @Override
    protected void init(HttpSecurity httpSecurity) {
        // TODO 初始化requestMatcher
        // TODO 注册OnSecurityAuthenticationPreProvider
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) {
        // TODO 注册OnSecurityAuthenticationPreFilter
    }

    @Override
    public RequestMatcher getRequestMatcher() {
        return this.allEndpointMatcher;
    }
}
