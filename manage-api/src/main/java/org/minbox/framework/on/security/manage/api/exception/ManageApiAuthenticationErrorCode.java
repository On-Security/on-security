/*
 *     Copyright (C) 2022  恒宇少年
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.minbox.framework.on.security.manage.api.exception;

/**
 * 资源严重异常码定义
 *
 * @author 恒宇少年
 * @since 0.0.6
 */
public enum ManageApiAuthenticationErrorCode {
    ;
    /**
     * 错误码
     */
    private String errorCode;
    /**
     * 错误码对应的字符串格式
     * <p>
     * 该格式可以通过{@link String#format(String, Object...)}方法进行格式化
     */
    private String format;

    ManageApiAuthenticationErrorCode(String errorCode, String format) {
        this.errorCode = errorCode;
        this.format = format;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getFormat() {
        return format;
    }
}
