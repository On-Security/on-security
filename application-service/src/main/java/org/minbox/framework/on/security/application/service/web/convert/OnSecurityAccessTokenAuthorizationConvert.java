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

package org.minbox.framework.on.security.application.service.web.convert;

import org.minbox.framework.on.security.application.service.authentication.OnSecurityAccessTokenAuthorizationToken;
import org.minbox.framework.on.security.core.authorization.endpoint.resolver.BearerTokenResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;

/**
 * 转换应用资源认证请求实体
 *
 * @author 恒宇少年
 * @see OnSecurityAccessTokenAuthorizationToken
 * @since 0.0.6
 */
public class OnSecurityAccessTokenAuthorizationConvert implements AuthenticationConverter {
    private BearerTokenResolver bearerTokenResolver;

    public OnSecurityAccessTokenAuthorizationConvert(BearerTokenResolver bearerTokenResolver) {
        Assert.notNull("bearerTokenResolver cannot be null");
        this.bearerTokenResolver = bearerTokenResolver;
    }

    @Override
    public Authentication convert(HttpServletRequest request) {
        String accessToken = bearerTokenResolver.resolve(request);
        // @formatter:off
        OnSecurityAccessTokenAuthorizationToken applicationResourceAuthorizationToken =
                new OnSecurityAccessTokenAuthorizationToken(
                        accessToken,
                        request.getRequestURI(),
                        request.getRequestedSessionId());
        // @formatter:on
        return applicationResourceAuthorizationToken;
    }
}
