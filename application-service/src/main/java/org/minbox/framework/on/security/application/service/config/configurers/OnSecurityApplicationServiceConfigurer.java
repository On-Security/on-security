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

package org.minbox.framework.on.security.application.service.config.configurers;

import org.minbox.framework.on.security.application.service.config.configurers.support.ApplicationResourceAccessAuthenticationConfigurer;
import org.minbox.framework.on.security.application.service.config.configurers.support.OnSecurityApplicationAccessTokenAuthorizationConfigurer;
import org.minbox.framework.on.security.core.authorization.configurer.AbstractOnSecurityOAuth2Configurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 应用服务配置类
 *
 * @author 恒宇少年
 * @since 0.0.6
 */
public final class OnSecurityApplicationServiceConfigurer extends AbstractHttpConfigurer<OnSecurityApplicationServiceConfigurer, HttpSecurity> {
    private Map<Class<? extends AbstractOnSecurityOAuth2Configurer>, AbstractOnSecurityOAuth2Configurer> configurers = createConfigurers();

    @Override
    public void init(HttpSecurity httpSecurity) throws Exception {
        this.configurers.values().forEach(configurer -> configurer.init(httpSecurity));
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        this.configurers.values().forEach(configurer -> configurer.configure(httpSecurity));
    }

    private Map<Class<? extends AbstractOnSecurityOAuth2Configurer>, AbstractOnSecurityOAuth2Configurer> createConfigurers() {
        Map<Class<? extends AbstractOnSecurityOAuth2Configurer>, AbstractOnSecurityOAuth2Configurer> configurers = new LinkedHashMap<>();
        // @formatter:off
        // Put OnSecurityApplicationResourceAuthorizationConfigurer
        configurers.put(OnSecurityApplicationAccessTokenAuthorizationConfigurer.class,
                postProcess(new OnSecurityApplicationAccessTokenAuthorizationConfigurer(this::postProcess)));
        // Put ApplicationResourceAccessAuthenticationConfigurer
        configurers.put(ApplicationResourceAccessAuthenticationConfigurer.class,
                postProcess(new ApplicationResourceAccessAuthenticationConfigurer(this::postProcess)));
        // @formatter:on
        return configurers;
    }
}
