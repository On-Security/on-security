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

import org.minbox.framework.on.security.application.service.access.ResourceAccessMatcher;
import org.minbox.framework.on.security.application.service.access.ResourceRoleBasedAccessControlMatcher;
import org.minbox.framework.on.security.application.service.authentication.context.OnSecurityApplicationContext;
import org.minbox.framework.on.security.application.service.exception.OnSecurityApplicationResourceAuthenticationException;
import org.minbox.framework.on.security.application.service.exception.ResourceAuthenticationErrorCode;
import org.minbox.framework.on.security.core.authorization.AbstractOnSecurityAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Map;

/**
 * 应用资源"Role-Based Access Control（RBAC）"访问认证者
 *
 * @author 恒宇少年
 * @see OnSecurityApplicationContext
 * @see ApplicationResourceRoleBasedAccessControlAuthenticationToken
 * @since 0.0.7
 */
public final class ApplicationResourceRoleBasedAccessControlAuthenticationProvider extends AbstractOnSecurityAuthenticationProvider {
    public ApplicationResourceRoleBasedAccessControlAuthenticationProvider(Map<Class<?>, Object> sharedObjects) {
        super(sharedObjects);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // @formatter:off
        ApplicationResourceRoleBasedAccessControlAuthenticationToken authenticationToken =
                (ApplicationResourceRoleBasedAccessControlAuthenticationToken) authentication;
        ResourceAccessMatcher resourceAccessMatcher =
                new ResourceRoleBasedAccessControlMatcher(authenticationToken.getUserAuthorizationResourceList());
        if (!resourceAccessMatcher.match(authenticationToken.getRequest())) {
            throw new OnSecurityApplicationResourceAuthenticationException("access denied",
                    ResourceAuthenticationErrorCode.UNAUTHORIZED_ACCESS,
                    authenticationToken.getRequest().getRequestURI());
        }
        // @formatter:on
        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ApplicationResourceRoleBasedAccessControlAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
