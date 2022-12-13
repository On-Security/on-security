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

package org.minbox.framework.on.security.authorization.server.oauth2.authentication;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import java.util.Map;

/**
 * 身份认证验证提供者
 *
 * @author 恒宇少年
 * @see AuthenticationProvider
 * @since 0.0.1
 */
public abstract class AbstractOnSecurityAuthenticationProvider implements AuthenticationProvider {
    /**
     * SpringSecurity共享对象集合
     *
     * @see org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
     * @see org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService
     * @see org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService
     * @see org.springframework.context.ApplicationContext
     * @see org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings
     * @see org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator
     * @see org.springframework.security.authentication.AuthenticationManager
     * @see HttpSecurity#getSharedObjects()
     */
    private Map<Class<?>, Object> sharedObjects;

    public AbstractOnSecurityAuthenticationProvider(Map<Class<?>, Object> sharedObjects) {
        this.sharedObjects = sharedObjects;
    }

    protected  <C> C getSharedObject(Class<C> sharedType) {
        return (C) this.sharedObjects.get(sharedType);
    }
}
