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

import org.minbox.framework.on.security.identity.provider.config.configurers.OnSecurityIdentityProviderBrokerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * OnSecurity Web安全配置类
 * <p>
 * 该类配置{@link EnableWebSecurity}注解，默认启用Web Security
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
@EnableWebSecurity
@Configuration
@Import(OnSecurityWebRegistrar.class)
public class OnSecurityWebConfiguration {
    @Bean
    public SecurityFilterChain onSecurityWebSecurityFilterChain(HttpSecurity http) throws Exception {
        this.defaultOnSecurityAuthorizationServer(http);
        // Enable Identity Provider
        OnSecurityIdentityProviderBrokerConfigurer identityProviderBrokerConfigurer = new OnSecurityIdentityProviderBrokerConfigurer();
        http.apply(identityProviderBrokerConfigurer);
        return http.build();
    }

    /**
     * 默认安全配置
     * <p>
     * 如果需要自定义，则可以重写该方法
     *
     * @param http {@link HttpSecurity}
     * @throws Exception
     */
    protected void defaultOnSecurityAuthorizationServer(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
                .formLogin(Customizer.withDefaults());
        // @formatter:on
    }

    /**
     * 配置密码加密方式
     * <p>
     * 默认使用{@link BCryptPasswordEncoder}，如果需要自定义可以重写该方法
     *
     * @return {@link PasswordEncoder}
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
