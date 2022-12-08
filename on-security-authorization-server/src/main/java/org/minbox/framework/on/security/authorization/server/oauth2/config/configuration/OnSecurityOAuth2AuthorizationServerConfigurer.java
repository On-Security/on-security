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

package org.minbox.framework.on.security.authorization.server.oauth2.config.configuration;

import org.minbox.framework.on.security.authorization.server.oauth2.config.configurers.AbstractOnSecurityOAuth2Configurer;
import org.minbox.framework.on.security.authorization.server.oauth2.config.configurers.OnSecurityOAuth2AuthorizationCodeRequestAuthenticationConfigurer;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * On-Security授权服务器{@link AbstractHttpConfigurer}实现配置
 * <p>
 * 将继承自{@link AbstractOnSecurityOAuth2Configurer}的全部子类全部进行初始化以及配置，
 * 并且将On-Security提供的身份认证器{@link AuthenticationProvider}进行注册
 *
 * @author 恒宇少年
 * @see HttpSecurity#authenticationProvider
 */
public class OnSecurityOAuth2AuthorizationServerConfigurer
        extends AbstractHttpConfigurer<OnSecurityOAuth2AuthorizationServerConfigurer, HttpSecurity> {
    /**
     * 继承自{@link AbstractOnSecurityOAuth2Configurer}的认证配置集合，每一个配置类对应注册一种身份验证提供者
     *
     * @see AuthenticationProvider
     */
    private final Map<Class<? extends AbstractOnSecurityOAuth2Configurer>, AbstractOnSecurityOAuth2Configurer> configurers = this.createConfigurers();

    @Override
    public void init(HttpSecurity httpSecurity) throws Exception {
        this.configurers.values().forEach(configurer -> configurer.init(httpSecurity));
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        this.configurers.values().forEach(configurer -> configurer.configure(httpSecurity));
    }

    private Map<Class<? extends AbstractOnSecurityOAuth2Configurer>, AbstractOnSecurityOAuth2Configurer> createConfigurers() {
        Map<Class<? extends AbstractOnSecurityOAuth2Configurer>, AbstractOnSecurityOAuth2Configurer> configurerMap = new LinkedHashMap<>();
        configurerMap.put(OnSecurityOAuth2AuthorizationCodeRequestAuthenticationConfigurer.class,
                new OnSecurityOAuth2AuthorizationCodeRequestAuthenticationConfigurer(this::postProcess));
        return configurerMap;
    }
}
