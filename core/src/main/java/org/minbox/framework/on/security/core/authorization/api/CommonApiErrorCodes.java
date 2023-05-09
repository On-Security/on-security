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
 * 通用的接口异常码定义
 *
 * @author 恒宇少年
 * @since 0.1.2
 */
public interface CommonApiErrorCodes {
    ApiErrorCode SYSTEM_EXCEPTION = new ApiErrorCode("SYSTEM_EXCEPTION", "系统异常，请联系管理员");
    ApiErrorCode PARAMETER_VERIFICATION_FAILED = new ApiErrorCode("PARAMETER_VERIFICATION_FAILED", "参数校验失败，原因：%s");
}
