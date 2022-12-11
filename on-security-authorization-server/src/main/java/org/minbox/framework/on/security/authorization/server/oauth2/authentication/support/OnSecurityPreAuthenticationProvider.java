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

package org.minbox.framework.on.security.authorization.server.oauth2.authentication.support;

import org.minbox.framework.on.security.authorization.server.oauth2.authentication.AbstractOnSecurityAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Map;

/**
 * 认证前置验证身份提供者
 * <p>
 * 用于验证请求数据有效性，如：安全域有效性、客户端是否属于安全域、用户是否绑定了认证客户端等等
 *
 * @author 恒宇少年
 */
public class OnSecurityPreAuthenticationProvider extends AbstractOnSecurityAuthenticationProvider {
    public OnSecurityPreAuthenticationProvider(Map<Class<?>, Object> sharedObjects) {
        super(sharedObjects);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // TODO 业务认证
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OnSecurityPreAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
