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

import org.minbox.framework.on.security.authorization.server.oauth2.authentication.support.OnSecurityPreAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;

import javax.servlet.http.HttpServletRequest;

/**
 * 转换前置身份认证器请求Token
 *
 * @author 恒宇少年
 * @see OnSecurityPreAuthenticationToken
 */
public class OnSecurityPreAuthenticationConverter implements AuthenticationConverter {
    @Override
    public Authentication convert(HttpServletRequest request) {
        OnSecurityPreAuthenticationToken preAuthenticationToken = new OnSecurityPreAuthenticationToken(null);
        // TODO 从Request中获取相关字段
        return preAuthenticationToken;
    }
}
