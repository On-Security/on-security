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

import org.minbox.framework.on.security.console.authorization.token.ConsoleManageToken;
import org.minbox.framework.on.security.console.authorization.web.ConsoleManageTokenResponse;
import org.springframework.core.convert.converter.Converter;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * {@link ConsoleManageTokenResponse}与{@link Map}转换器
 *
 * @author 恒宇少年
 * @since 0.0.9
 */
public class DefaultConsoleManageTokenResponseMapConverter implements Converter<ConsoleManageTokenResponse, Map<String, Object>> {
    @Override
    public Map<String, Object> convert(ConsoleManageTokenResponse tokenResponse) {
        ConsoleManageToken manageToken = tokenResponse.getManageToken();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(ManageTokenParameterNames.MANAGE_TOKEN, manageToken.getTokenValue());
        parameters.put(ManageTokenParameterNames.EXPIRES_IN, getExpiresIn(manageToken));
        parameters.put(ManageTokenParameterNames.AUTHENTICATE_TYPE, tokenResponse.getAuthenticateType().toString());
        return this.sort(parameters);
    }

    private static long getExpiresIn(ConsoleManageToken manageToken) {
        if (manageToken.getExpiresAt() != null) {
            return ChronoUnit.SECONDS.between(Instant.now(), manageToken.getExpiresAt());
        }
        return -1;
    }

    private Map<String, Object> sort(Map<String, Object> map) {
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }
}
