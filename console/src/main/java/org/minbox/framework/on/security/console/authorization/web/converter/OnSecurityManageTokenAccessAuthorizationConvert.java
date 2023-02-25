/*
 *   Copyright (C) 2022  恒宇少年
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.minbox.framework.on.security.console.authorization.web.converter;

import org.minbox.framework.on.security.console.authorization.authentication.OnSecurityManageTokenAccessRequestAuthorizationToken;
import org.minbox.framework.on.security.core.authorization.exception.OnSecurityError;
import org.minbox.framework.on.security.core.authorization.exception.OnSecurityErrorCodes;
import org.minbox.framework.on.security.core.authorization.exception.OnSecurityOAuth2AuthenticationException;
import org.minbox.framework.on.security.core.authorization.util.OnSecurityThrowErrorUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 管理令牌访问资源认证请求实体转换器
 * <p>
 * 从{@link HttpServletRequest}请求中提取 {@link HttpHeaders#AUTHORIZATION}头信息
 *
 * @author 恒宇少年
 * @since 0.0.9
 */
public class OnSecurityManageTokenAccessAuthorizationConvert implements AuthenticationConverter {
    @Override
    public Authentication convert(HttpServletRequest request) {
        String manageToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (ObjectUtils.isEmpty(manageToken)) {
            // @formatter:off
            OnSecurityError onSecurityError = new OnSecurityError(OnSecurityErrorCodes.INVALID_MANAGE_TOKEN.getValue(),
                    null,
                    "Invalid manage token，please check the validity of the manage token.",
                    OnSecurityThrowErrorUtils.DEFAULT_HELP_URI);
            // @formatter:on
            throw new OnSecurityOAuth2AuthenticationException(onSecurityError);
        }
        return new OnSecurityManageTokenAccessRequestAuthorizationToken(manageToken);
    }
}
