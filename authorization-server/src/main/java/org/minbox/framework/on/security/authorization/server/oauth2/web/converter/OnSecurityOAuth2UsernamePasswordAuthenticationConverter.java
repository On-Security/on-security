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

import org.minbox.framework.on.security.core.authorization.exception.OnSecurityErrorCodes;
import org.minbox.framework.on.security.authorization.server.oauth2.authentication.support.OnSecurityOAuth2UsernamePasswordAuthenticationToken;
import org.minbox.framework.on.security.core.authorization.util.OnSecurityThrowErrorUtils;
import org.minbox.framework.on.security.core.authorization.util.RequestParameterUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户名密码认证数据实体转换验证类
 * <p>
 * 数据转换时需要验证请求是否携带了必要的参数："username"、"password"、"grant_type=password"，
 * 参数缺失或者数据不匹配都需要抛出认证异常
 *
 * @author 恒宇少年
 * @see org.springframework.security.authentication.UsernamePasswordAuthenticationToken
 * @since 0.0.1
 */
public class OnSecurityOAuth2UsernamePasswordAuthenticationConverter implements AuthenticationConverter {
    @Override
    public Authentication convert(HttpServletRequest request) {
        MultiValueMap<String, String> parameters = RequestParameterUtils.getParameters(request);

        // verify username
        String username = parameters.getFirst(OAuth2ParameterNames.USERNAME);
        if (!StringUtils.hasText(username)) {
            OnSecurityThrowErrorUtils.throwError(OnSecurityErrorCodes.INVALID_USERNAME, OAuth2ParameterNames.USERNAME);
        }
        // verify password
        String password = parameters.getFirst(OAuth2ParameterNames.PASSWORD);
        if (!StringUtils.hasText(password)) {
            OnSecurityThrowErrorUtils.throwError(OnSecurityErrorCodes.INVALID_PASSWORD, OAuth2ParameterNames.PASSWORD);
        }
        SecurityContext securityContext = SecurityContextHolder.getContext();
        OAuth2ClientAuthenticationToken clientAuthenticationToken = (OAuth2ClientAuthenticationToken) securityContext.getAuthentication();
        // @formatter:off
        return new OnSecurityOAuth2UsernamePasswordAuthenticationToken(
                username,
                password,
                clientAuthenticationToken.getRegisteredClient(),
                clientAuthenticationToken.isAuthenticated()
        );
        // @formatter:on
    }
}
