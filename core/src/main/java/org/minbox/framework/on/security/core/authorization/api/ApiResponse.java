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
 * 接口响应基类
 *
 * @author 恒宇少年
 * @since 0.1.2
 */
public class ApiResponse {
    /**
     * 错误码
     */
    private final String errorCode;
    /**
     * 描述信息
     */
    private final String description;
    /**
     * 接口响应数据
     */
    private final Object data;
    /**
     * 接口是否执行成功
     */
    private final boolean success;
    /**
     * 接口响应时间戳
     */
    private final long timestamp;

    private ApiResponse(String errorCode, String description, Object data, boolean success) {
        this.errorCode = errorCode;
        this.description = description;
        this.data = data;
        this.success = success;
        this.timestamp = System.currentTimeMillis();
    }

    public static ApiResponse success(Object data) {
        return new ApiResponse(null, null, data, true);
    }

    public static ApiResponse error(String errorCode, String description) {
        return new ApiResponse(errorCode, description, null, false);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getDescription() {
        return description;
    }

    public Object getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
