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
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationToken;

import java.util.Map;

/**
 * On-Security 授权码请求认证身份验证提供者
 * <p>
 * 该身份验证提供者会在{@link OAuth2AuthorizationCodeRequestAuthenticationProvider}之前执行，而且{@link #authenticate}方法必须返回 "null"
 * 因为{@link ProviderManager#authenticate}在遍历全部身份验证提供者时会根据{@link #authenticate}方法返回值判定是否执行身份验证完成
 *
 * @author 恒宇少年
 * @see AuthenticationProvider
 * @since 0.0.1
 */
public class OnSecurityOAuth2AuthorizationCodeRequestAuthenticationProvider extends AbstractOnSecurityAuthenticationProvider {
    public OnSecurityOAuth2AuthorizationCodeRequestAuthenticationProvider(Map<Class<?>, Object> sharedObjects) {
        super(sharedObjects);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // TODO 实现业务
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OAuth2AuthorizationCodeRequestAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
