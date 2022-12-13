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

package org.minbox.framework.on.security.authorization.server.oauth2.authentication;

/**
 * 定义OnSecurity所使用的异常码
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public enum OnSecurityErrorCodes {
    /**
     * 无效的客户端
     * 未查询到客户端或客户端状态不正常时使用的错误码
     */
    INVALID_CLIENT("invalid_client"),
    /**
     * 未授权客户端
     * 用户在请求授权时，判定用户是否授权了发起请求的客户端
     */
    UNAUTHORIZED_CLIENT("unauthorized_client"),
    /**
     * 未知的异常
     */
    UNKNOWN_EXCEPTION("unknown_exception");
    private String value;

    OnSecurityErrorCodes(java.lang.String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
