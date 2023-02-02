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

import org.minbox.framework.on.security.core.authorization.util.OnSecurityVersion;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;

import java.io.Serializable;

/**
 * 定义OnSecurity异常封装类
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class OnSecurityError implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
    /**
     * 定义错误码
     * 部分SAS（Spring Authorization Server）内部定义的异常码参考 {@link OAuth2ErrorCodes}
     */
    private String errorCode;
    /**
     * 定义错误参数名称
     * 部分SAS（Spring Authorization Server）内部定义的异常码参考  {@link OAuth2ParameterNames}
     */
    private String parameter;
    /**
     * 定义错误描述
     */
    private String description;
    /**
     * 定义错误帮助文档路径
     */
    private String helpUri;

    public OnSecurityError(String errorCode) {
        this(errorCode, null, null, null);
    }

    public OnSecurityError(String errorCode, String parameter) {
        this(errorCode, parameter, null, null);
    }

    public OnSecurityError(String errorCode, String parameter, String description, String helpUri) {
        this.errorCode = errorCode;
        this.parameter = parameter;
        this.description = description;
        this.helpUri = helpUri;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getParameter() {
        return parameter;
    }

    public String getDescription() {
        return description;
    }

    public String getHelpUri() {
        return helpUri;
    }
}
