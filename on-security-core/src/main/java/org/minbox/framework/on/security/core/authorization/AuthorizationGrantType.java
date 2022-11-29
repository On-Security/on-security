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

package org.minbox.framework.on.security.core.authorization;

import org.minbox.framework.on.security.core.authorization.util.OnSecurityVersion;

import java.io.Serializable;

/**
 * 认证授权类型
 *
 * @author 恒宇少年
 */
public final class AuthorizationGrantType implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
    public static final AuthorizationGrantType AUTHORIZATION_CODE = new AuthorizationGrantType("authorization_code");
    public static final AuthorizationGrantType PASSWORD = new AuthorizationGrantType("password");
    public static final AuthorizationGrantType REFRESH_TOKEN = new AuthorizationGrantType("refresh_token");
    public static final AuthorizationGrantType CLIENT_CREDENTIALS = new AuthorizationGrantType("client_credentials");
    public static final AuthorizationGrantType IMPLICIT = new AuthorizationGrantType("implicit");
    private final String value;

    public AuthorizationGrantType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        AuthorizationGrantType that = (AuthorizationGrantType) obj;
        return getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        return getValue().hashCode();
    }
}
