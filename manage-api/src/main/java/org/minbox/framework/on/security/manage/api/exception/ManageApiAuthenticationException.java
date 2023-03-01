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

import org.springframework.security.core.AuthenticationException;

/**
 * 管理接口验证异常
 *
 * @author 恒宇少年
 * @see ManageApiAuthenticationErrorCode
 * @since 0.0.6
 */
public class ManageApiAuthenticationException extends AuthenticationException {
    private ManageApiAuthenticationErrorCode authenticationErrorCode;
    private String[] formatParams;

    public ManageApiAuthenticationException(String msg, ManageApiAuthenticationErrorCode authenticationErrorCode, String... formatParams) {
        super(msg);
        this.authenticationErrorCode = authenticationErrorCode;
        this.formatParams = formatParams;
    }

    /**
     * 获取资源访问认证失败的错误码定义
     *
     * @return {@link ManageApiAuthenticationErrorCode} 错误码定义
     */
    public ManageApiAuthenticationErrorCode getAuthenticationErrorCode() {
        return this.authenticationErrorCode;
    }

    /**
     * 获取格式化错误码所需要的参数列表
     * <p>
     * 使用{@link String#format(String, Object...)}进行格式化
     *
     * @return 格式化 {@link ManageApiAuthenticationErrorCode#getFormat()}所需要的参数列表，注意顺序
     */
    public String[] getFormatParams() {
        return this.formatParams;
    }
}
