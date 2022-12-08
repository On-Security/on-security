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

package org.minbox.framework.on.security.authorization.server.oauth2.config.configurers;

import org.minbox.framework.on.security.authorization.server.oauth2.authentication.OnSecurityOAuth2AuthorizationCodeRequestAuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * 授权码请求授权身份认证配置
 * <p>
 * 初始化注册请求授权身份提供者{@link OnSecurityOAuth2AuthorizationCodeRequestAuthenticationProvider}
 *
 * @author 恒宇少年
 * @see OnSecurityOAuth2AuthorizationCodeRequestAuthenticationProvider
 * @since 0.0.1
 */
public class OnSecurityOAuth2AuthorizationCodeRequestAuthenticationConfigurer extends AbstractOnSecurityOAuth2Configurer {
    public OnSecurityOAuth2AuthorizationCodeRequestAuthenticationConfigurer(ObjectPostProcessor<Object> objectPostProcessor) {
        super(objectPostProcessor);
    }

    @Override
    public void init(HttpSecurity httpSecurity) {
        httpSecurity.authenticationProvider(postProcess(new OnSecurityOAuth2AuthorizationCodeRequestAuthenticationProvider(httpSecurity.getSharedObjects())));
    }
}
