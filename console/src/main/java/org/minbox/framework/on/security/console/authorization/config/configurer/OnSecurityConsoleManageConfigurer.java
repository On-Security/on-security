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

import org.minbox.framework.on.security.console.authorization.config.configurer.support.OnSecurityConsoleManageTokenConfigurer;
import org.minbox.framework.on.security.console.authorization.config.configurer.support.OnSecurityManageTokenExternalAccessConfigurer;
import org.minbox.framework.on.security.core.authorization.configurer.AbstractOnSecurityOAuth2Configurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 控制台安全管理{@link HttpSecurity}配置
 *
 * @author 恒宇少年
 * @see RequestMatcher
 * @see AbstractOnSecurityOAuth2Configurer
 * @see OnSecurityConsoleManageTokenConfigurer
 * @since 0.0.9
 */
public class OnSecurityConsoleManageConfigurer extends AbstractHttpConfigurer<OnSecurityConsoleManageConfigurer, HttpSecurity> {
    private RequestMatcher requestMatcher;
    private Map<Class<? extends AbstractOnSecurityOAuth2Configurer>, AbstractOnSecurityOAuth2Configurer> configurers = createConfigurers();

    @Override
    public void init(HttpSecurity httpSecurity) throws Exception {
        List<RequestMatcher> requestMatchers = new ArrayList<>();
        this.configurers.values().forEach(configurer -> {
            configurer.init(httpSecurity);
            RequestMatcher requestMatcher = configurer.getRequestMatcher();
            if (requestMatcher != null) {
                requestMatchers.add(requestMatcher);
            }
        });
        this.requestMatcher = new OrRequestMatcher(requestMatchers);
    }

    @Override
    public void configure(HttpSecurity httpSecurity) {
        this.configurers.values().forEach(configurer -> configurer.configure(httpSecurity));
    }

    public RequestMatcher getRequestMatcher() {
        // Return a deferred RequestMatcher
        // since endpointsMatcher is constructed in init(HttpSecurity).
        return (request) -> this.requestMatcher.matches(request);
    }

    private Map<Class<? extends AbstractOnSecurityOAuth2Configurer>, AbstractOnSecurityOAuth2Configurer> createConfigurers() {
        Map<Class<? extends AbstractOnSecurityOAuth2Configurer>, AbstractOnSecurityOAuth2Configurer> configurers = new LinkedHashMap<>();
        // @formatter:off
        // Put OnSecurityIdentityProviderBrokerEndpointConfigurer
        configurers.put(OnSecurityConsoleManageTokenConfigurer.class,
                postProcess(new OnSecurityConsoleManageTokenConfigurer(this::postProcess)));
        // Put OnSecurityManageTokenExternalAccessConfigurer
        configurers.put(OnSecurityManageTokenExternalAccessConfigurer.class,
                postProcess(new OnSecurityManageTokenExternalAccessConfigurer(this::postProcess)));
        // @formatter:on
        return configurers;
    }
}
