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
 * 接口异常类
 *
 * @author 恒宇少年
 * @since 0.1.2
 */
public class ApiException extends RuntimeException {
    private final ApiErrorCode errorCode;
    private final Object[] args;

    public ApiException(ApiErrorCode errorCode, Object... args) {
        super(errorCode.formatErrorDescription(args));
        this.errorCode = errorCode;
        this.args = args;
    }

    public ApiErrorCode getErrorCode() {
        return errorCode;
    }

    public Object[] getArgs() {
        return args;
    }
}
