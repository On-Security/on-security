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

import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.JsonbHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.ClassUtils;

/**
 * {@link org.springframework.http.converter.HttpMessageConverter}具体实例使用定义
 *
 * @author 恒宇少年
 * @since 0.0.9
 */
public class HttpMessageConverters {
    private static final boolean jackson2Present;

    private static final boolean gsonPresent;

    private static final boolean jsonbPresent;

    static {
        ClassLoader classLoader = HttpMessageConverters.class.getClassLoader();
        jackson2Present = ClassUtils.isPresent("com.fasterxml.jackson.databind.ObjectMapper", classLoader)
                && ClassUtils.isPresent("com.fasterxml.jackson.core.JsonGenerator", classLoader);
        gsonPresent = ClassUtils.isPresent("com.google.gson.Gson", classLoader);
        jsonbPresent = ClassUtils.isPresent("javax.json.bind.Jsonb", classLoader);
    }

    private HttpMessageConverters() {
    }

    static GenericHttpMessageConverter<Object> getJsonMessageConverter() {
        if (jackson2Present) {
            return new MappingJackson2HttpMessageConverter();
        }
        if (gsonPresent) {
            return new GsonHttpMessageConverter();
        }
        if (jsonbPresent) {
            return new JsonbHttpMessageConverter();
        }
        return null;
    }

}
