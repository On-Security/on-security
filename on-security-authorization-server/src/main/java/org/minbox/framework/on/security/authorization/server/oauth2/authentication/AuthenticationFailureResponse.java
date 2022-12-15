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

import org.minbox.framework.on.security.core.authorization.util.OnSecurityVersion;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * 认证失败响应实体定义
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public final class AuthenticationFailureResponse implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
    /**
     * 定义错误码
     * 部分SAS（Spring Authorization Server）内部定义的异常码参考 {@link OAuth2ErrorCodes}
     */
    private String errorCode;
    /**
     * 定义错误描述
     */
    private String description;
    /**
     * 定义错误帮助文档路径
     */
    private String helpUri;

    private AuthenticationFailureResponse() {
        //...
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getDescription() {
        return description;
    }

    public String getHelpUri() {
        return helpUri;
    }

    public static Builder withErrorCode(String errorCode) {
        Assert.hasText(errorCode, "errorCode cannot be empty.");
        return new Builder(errorCode);
    }

    public static Builder withFailureResponse(AuthenticationFailureResponse failureResponse) {
        Assert.notNull(failureResponse, "failureResponse cannot be null.");
        return new Builder(failureResponse);
    }

    public static class Builder implements Serializable {
        private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
        private String errorCode;
        private String description;
        private String helpUri;

        private Builder(String errorCode) {
            this.errorCode = errorCode;
        }

        private Builder(AuthenticationFailureResponse failureResponse) {
            this.errorCode = failureResponse.errorCode;
            this.description = failureResponse.description;
            this.helpUri = failureResponse.helpUri;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder helpUri(String helpUri) {
            this.helpUri = helpUri;
            return this;
        }

        public AuthenticationFailureResponse build() {
            AuthenticationFailureResponse failureResponse = new AuthenticationFailureResponse();
            failureResponse.errorCode = this.errorCode;
            failureResponse.description = this.description;
            failureResponse.helpUri = this.helpUri;
            return failureResponse;
        }
    }
}
