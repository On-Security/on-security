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

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

/**
 * OAuth2认证异常定义
 *
 * @author 恒宇少年
 * @see OAuth2AuthenticationException
 * @since 0.0.1
 */
public class OnSecurityOAuth2AuthenticationException extends AuthenticationException {
    private OnSecurityError onSecurityError;

    public OnSecurityOAuth2AuthenticationException(OnSecurityError onSecurityError, String msg, Throwable cause) {
        super(msg, cause);
        this.onSecurityError = onSecurityError;
    }

    public OnSecurityOAuth2AuthenticationException(OnSecurityError onSecurityError) {
        super(onSecurityError.getDescription());
        this.onSecurityError = onSecurityError;
    }

    public OnSecurityError getOnSecurityError() {
        return onSecurityError;
    }
}
