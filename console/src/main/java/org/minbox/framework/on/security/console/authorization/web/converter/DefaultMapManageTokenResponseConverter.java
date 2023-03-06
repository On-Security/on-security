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

import org.minbox.framework.on.security.console.authorization.authentication.ManageTokenAuthenticateType;
import org.minbox.framework.on.security.console.authorization.token.ConsoleManageToken;
import org.minbox.framework.on.security.console.authorization.web.ConsoleManageTokenResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.ObjectUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

/**
 * {@link Map}与{@link ConsoleManageTokenResponse}转换器
 *
 * @author 恒宇少年
 * @since 0.0.9
 */
public class DefaultMapManageTokenResponseConverter implements Converter<Map<String, Object>, ConsoleManageTokenResponse> {
    @Override
    public ConsoleManageTokenResponse convert(Map<String, Object> source) {
        // @formatter:off
        ConsoleManageTokenResponse tokenResponse = ConsoleManageTokenResponse
                .withConsoleManageToken(this.getManageToken(source))
                .authenticateType(this.getAuthenticateType(source))
                .build();
        // @formatter:on
        return tokenResponse;
    }

    private ConsoleManageToken getManageToken(Map<String, Object> source) {
        String tokenValue = getParameterValue(source, ManageTokenParameterNames.MANAGE_TOKEN);
        String expiresInValue = getParameterValue(source, ManageTokenParameterNames.EXPIRES_IN);
        Instant issuedAt = Instant.now();
        Instant expiresAt = Instant.now().plus(Duration.ofSeconds(Long.valueOf(expiresInValue)));
        return new ConsoleManageToken(tokenValue, null, issuedAt, expiresAt);
    }

    private ManageTokenAuthenticateType getAuthenticateType(Map<String, Object> source) {
        String parameterValue = getParameterValue(source, ManageTokenParameterNames.AUTHENTICATE_TYPE);
        return !ObjectUtils.isEmpty(parameterValue) ? ManageTokenAuthenticateType.valueOf(parameterValue) :
                ManageTokenAuthenticateType.username_password;
    }

    private static String getParameterValue(Map<String, Object> tokenResponseParameters, String parameterName) {
        Object obj = tokenResponseParameters.get(parameterName);
        return (obj != null) ? obj.toString() : null;
    }
}
