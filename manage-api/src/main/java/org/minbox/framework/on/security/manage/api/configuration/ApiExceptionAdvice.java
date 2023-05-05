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

package org.minbox.framework.on.security.manage.api.configuration;

import org.minbox.framework.on.security.core.authorization.api.ApiErrorCode;
import org.minbox.framework.on.security.core.authorization.api.ApiException;
import org.minbox.framework.on.security.core.authorization.api.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * 接口异常{@link ApiException}统一捕获通知类
 *
 * @author 恒宇少年
 * @since 0.1.2
 */
@ControllerAdvice(annotations = RestController.class)
@ResponseBody
@ResponseStatus(HttpStatus.OK)
public class ApiExceptionAdvice {
    /**
     * 统一处理{@link ApiException}异常
     *
     * @param exception 系统中遇到的{@link ApiException}异常实例
     * @return 统一响应实体实例 {@link ApiResponse}
     */
    @ExceptionHandler(ApiException.class)
    public ApiResponse apiException(ApiException exception) {
        exception.printStackTrace();
        ApiErrorCode errorCode = exception.getErrorCode();
        return ApiResponse.error(errorCode.getCode(), errorCode.formatErrorDescription(exception.getArgs()));
    }
}
