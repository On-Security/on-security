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

package org.minbox.framework.on.security.core.authorization.api;

/**
 * 接口错误码实体定义类
 *
 * @author 恒宇少年
 * @since 0.1.2
 */
public class ApiErrorCode {
    /**
     * 错误码
     */
    private final String code;
    /**
     * 原始未格式化的错误描述信息
     */
    private final String format;

    public ApiErrorCode(String code, String format) {
        this.code = code;
        this.format = format;
    }

    /**
     * 格式化错误描述
     *
     * @param args 格式化时所需要的参数列表
     * @return 格式化后的错误描述
     */
    public String formatErrorDescription(Object... args) {
        return String.format(format, args);
    }

    public String getCode() {
        return code;
    }

    public String getFormat() {
        return format;
    }
}
