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

import org.minbox.framework.on.security.console.authorization.web.ConsoleManageTokenResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 格式化控制台管理令牌响应内容
 *
 * @author 恒宇少年
 * @since 0.0.9
 */
public class ConsoleManageTokenResponseHttpMessageConverter extends AbstractHttpMessageConverter<ConsoleManageTokenResponse> {
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private static final ParameterizedTypeReference<Map<String, Object>> STRING_OBJECT_MAP = new ParameterizedTypeReference<Map<String, Object>>() {
    };
    private GenericHttpMessageConverter<Object> jsonMessageConverter = HttpMessageConverters.getJsonMessageConverter();
    private Converter<Map<String, Object>, ConsoleManageTokenResponse> manageTokenResponseConverter = new DefaultMapManageTokenResponseConverter();
    private Converter<ConsoleManageTokenResponse, Map<String, Object>> manageTokenResponseParametersConverter = new DefaultConsoleManageTokenResponseMapConverter();

    public ConsoleManageTokenResponseHttpMessageConverter() {
        super(DEFAULT_CHARSET, MediaType.APPLICATION_JSON, new MediaType("application", "*+json"));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return ConsoleManageTokenResponse.class.isAssignableFrom(clazz);
    }

    @Override
    protected ConsoleManageTokenResponse readInternal(Class<? extends ConsoleManageTokenResponse> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        try {
            Map<String, Object> tokenResponseParameters = (Map<String, Object>) this.jsonMessageConverter
                    .read(STRING_OBJECT_MAP.getType(), null, inputMessage);
            return this.manageTokenResponseConverter.convert(tokenResponseParameters);
        }
        catch (Exception ex) {
            throw new HttpMessageNotReadableException(
                    "An error occurred reading the Console Manage Token Response: " + ex.getMessage(), ex,
                    inputMessage);
        }
    }

    @Override
    protected void writeInternal(ConsoleManageTokenResponse tokenResponse, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        try {
            Map<String, Object> tokenResponseParameters = this.manageTokenResponseParametersConverter.convert(tokenResponse);
            this.jsonMessageConverter.write(tokenResponseParameters, STRING_OBJECT_MAP.getType(),
                    MediaType.APPLICATION_JSON, outputMessage);
        } catch (Exception ex) {
            throw new HttpMessageNotWritableException(
                    "An error occurred writing the Console Manage Token Response: " + ex.getMessage(), ex);
        }
    }
}
