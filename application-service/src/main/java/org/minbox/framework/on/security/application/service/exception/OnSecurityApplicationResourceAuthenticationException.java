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

package org.minbox.framework.on.security.application.service.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 应用资源验证异常
 *
 * @author 恒宇少年
 * @see ResourceAuthenticationErrorCode
 * @since 0.0.6
 */
public class OnSecurityApplicationResourceAuthenticationException extends AuthenticationException {
    private ResourceAuthenticationErrorCode authenticationErrorCode;
    private String[] formatParams;

    public OnSecurityApplicationResourceAuthenticationException(String msg, ResourceAuthenticationErrorCode authenticationErrorCode, String... formatParams) {
        super(msg);
        this.authenticationErrorCode = authenticationErrorCode;
        this.formatParams = formatParams;
    }

    /**
     * 获取资源访问认证失败的错误码定义
     *
     * @return {@link ResourceAuthenticationErrorCode} 错误码定义
     */
    public ResourceAuthenticationErrorCode getAuthenticationErrorCode() {
        return this.authenticationErrorCode;
    }

    /**
     * 获取格式化错误码所需要的参数列表
     * <p>
     * 使用{@link String#format(String, Object...)}进行格式化
     *
     * @return 格式化 {@link ResourceAuthenticationErrorCode#getFormat()}所需要的参数列表，注意顺序
     */
    public String[] getFormatParams() {
        return this.formatParams;
    }
}
