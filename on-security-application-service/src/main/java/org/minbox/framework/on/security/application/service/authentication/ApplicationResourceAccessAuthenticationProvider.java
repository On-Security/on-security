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

package org.minbox.framework.on.security.application.service.authentication;

import org.minbox.framework.on.security.application.service.authentication.context.OnSecurityApplicationContext;
import org.minbox.framework.on.security.application.service.web.ApplicationResourceAccessAuthenticationMatcher;
import org.minbox.framework.on.security.core.authorization.AbstractOnSecurityAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Map;

/**
 * 应用资源访问认证提供者
 *
 * @author 恒宇少年
 * @see OnSecurityApplicationContext
 * @see ApplicationResourceAccessAuthenticationMatcher
 * @since 0.0.7
 */
public final class ApplicationResourceAccessAuthenticationProvider extends AbstractOnSecurityAuthenticationProvider {
    public ApplicationResourceAccessAuthenticationProvider(Map<Class<?>, Object> sharedObjects) {
        super(sharedObjects);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        ApplicationResourceAccessAuthenticationToken resourceAccessAuthenticationToken =
                (ApplicationResourceAccessAuthenticationToken) authentication;
        // TODO 认证资源访问授权
        return resourceAccessAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ApplicationResourceAccessAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
