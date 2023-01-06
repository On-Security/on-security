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

package org.minbox.framework.on.security.authorization.server.oauth2.web.converter;

import org.minbox.framework.on.security.authorization.server.oauth2.authentication.support.OnSecurityAccessAuthorizationRequestToken;
import org.minbox.framework.on.security.authorization.server.oauth2.web.resolver.BearerTokenResolver;
import org.minbox.framework.on.security.authorization.server.oauth2.web.resolver.DefaultBearerTokenResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取请求授权实体转换
 *
 * @author 恒宇少年
 * @see OnSecurityAccessAuthorizationRequestToken
 * @since 0.0.5
 */
public class OnSecurityAccessAuthorizationRequestConverter implements AuthenticationConverter {
    private BearerTokenResolver bearerTokenResolver;

    public OnSecurityAccessAuthorizationRequestConverter() {
        this.bearerTokenResolver = new DefaultBearerTokenResolver();
    }

    @Override
    public Authentication convert(HttpServletRequest request) {
        String accessToken = this.bearerTokenResolver.resolve(request);
        return new OnSecurityAccessAuthorizationRequestToken(accessToken);
    }
}
