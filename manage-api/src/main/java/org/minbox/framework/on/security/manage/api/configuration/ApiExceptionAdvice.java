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
import org.minbox.framework.on.security.manage.api.module.ApiErrorCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

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
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ApiExceptionAdvice.class);
    @Autowired
    private MessageSource messageSource;

    /**
     * 统一处理{@link ApiException}异常
     *
     * @param exception 系统中遇到的{@link ApiException}异常实例
     * @return 统一响应实体实例 {@link ApiResponse}
     */
    @ExceptionHandler(ApiException.class)
    public ApiResponse apiException(ApiException exception) {
        ApiErrorCode errorCode = exception.getErrorCode();
        String formattedMsg = errorCode.formatErrorDescription(exception.getArgs());
        logger.error("[" + errorCode.getCode() + "]" + formattedMsg, exception);
        return ApiResponse.error(errorCode.getCode(), formattedMsg);
    }

    /**
     * 统一处理{@link Exception}、{@link RuntimeException}异常
     *
     * @return 统一响应实体实例 {@link ApiResponse}
     */
    @ExceptionHandler({RuntimeException.class, Exception.class})
    public ApiResponse runtimeException(Exception exception) {
        ApiErrorCode errorCode = ApiErrorCodes.SYSTEM_EXCEPTION;
        logger.error("[" + errorCode.getCode() + "]" + errorCode.getFormat(), exception);
        return ApiResponse.error(errorCode.getCode(), errorCode.getFormat());
    }

    /**
     * 统一参数验证异常处理
     * <p>
     * "@Valid" 注解会验证属性，验证不通过时会先交给BindingResult，
     * 如果没有这个参数则会抛出异常BindException
     *
     * @param e The {@link BindException} instance
     * @return 统一响应实体实例 {@link ApiResponse}
     */
    @ExceptionHandler(BindException.class)
    public ApiResponse illegalParamsExceptionHandler(BindException e) {
        String allFieldErrorMessage = this.getErrorFieldMessage(e.getBindingResult());
        ApiErrorCode errorCode = ApiErrorCodes.PARAMETER_VERIFICATION_FAILED;
        String formattedMsg = errorCode.formatErrorDescription(allFieldErrorMessage);
        return ApiResponse.error(errorCode.getCode(), formattedMsg);
    }

    /**
     * 获取全部验证不通过字段格式化后的错误信息
     *
     * @param bindingResult The {@link BindingResult} instance
     * @return 格式化后全部验证不通过的字段异常内容
     */
    private String getErrorFieldMessage(BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        StringBuilder errorMsg = new StringBuilder();
        for (int i = 0; i < fieldErrors.size(); i++) {
            FieldError fieldError = fieldErrors.get(i);
            Locale currentLocale = LocaleContextHolder.getLocale();
            String fieldErrorMsg = messageSource.getMessage(fieldError, currentLocale);
            errorMsg.append(fieldErrorMsg).append(i == fieldErrors.size() - 1 ? "" : " ; ");
        }
        return errorMsg.toString();
    }
}
