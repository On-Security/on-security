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

package org.minbox.framework.on.security.core.authorization.util;

import com.google.common.base.CaseFormat;

/**
 * @author 恒宇少年
 * @since 0.0.8
 */
public class StringUtils {
    /**
     * 将字符串转换为驼峰格式首字母大写
     *
     * @param value 原始字符串
     * @return 转换后的字符串
     */
    public static String toUpperCamelName(String value) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, value);
    }

    /**
     * 将字符串转换为驼峰格式首字母小写
     *
     * @param value 原始字符串
     * @return 转换后的字符串
     */
    public static String toLowerCamelName(String value) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, value);
    }
}
