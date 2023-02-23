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

package org.minbox.framework.on.security.core.authorization.exception;

import org.springframework.security.oauth2.core.AuthorizationGrantType;

/**
 * 定义OnSecurity所使用的异常码
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public enum OnSecurityErrorCodes {
    /**
     * 无效的应用
     * <p>
     * 未查询到应用或应用状态不正常或已被删除时使用的错误码
     */
    INVALID_APPLICATION("invalid_application"),
    /**
     * 无效的用户
     * <p>
     * 未查询到客户或者客户状态不正常时使用的错误码
     */
    INVALID_USER("invalid_user"),
    /**
     * 无效的安全域密钥
     */
    INVALID_REGION_SECRET("invalid_region_secret"),
    /**
     * 无效的授权方式
     */
    INVALID_GRANT_TYPE("invalid_grant_type"),
    /**
     * 无效的用户名
     * <p>
     * 未传递"username"参数时使用
     */
    INVALID_USERNAME("invalid_username"),
    /**
     * 无效的密码
     * <p>
     * 未传递"password"参数时使用
     */
    INVALID_PASSWORD("invalid_password"),
    /**
     * 无效的身份供应商
     */
    INVALID_IDENTITY_PROVIDER("invalid_identity_provider"),
    /**
     * 身份验证失败
     * <p>
     * 用户名或密码错误时使用
     */
    AUTHENTICATION_FAILED("authentication_failed"),
    /**
     * 无效的安全域
     * <p>
     * 未查询到安全域或安全域状态不正常或已被删除时使用的错误码
     */
    INVALID_REGION("invalid_region"),
    /**
     * 未授权应用
     * <p>
     * 用户在请求授权时，判定用户是否授权了发起请求的应用
     */
    UNAUTHORIZED_APPLICATION("unauthorized_application"),
    /**
     * 不支持的授权类型
     * <p>
     * 未传递"grant_type"参数或者不存在于{@link AuthorizationGrantType}定义时使用的错误码
     */
    UNSUPPORTED_GRANT_TYPE("unsupported_grant_type"),
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
