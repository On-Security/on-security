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
 * 认证方式
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public final class AuthorizationMethod implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;

    public static final AuthorizationMethod CLIENT_SECRET_BASIC = new AuthorizationMethod("client_secret_basic");
    public static final AuthorizationMethod CLIENT_SECRET_POST = new AuthorizationMethod("client_secret_post");
    public static final AuthorizationMethod CLIENT_SECRET_JWT = new AuthorizationMethod("client_secret_jwt");
    public static final AuthorizationMethod PRIVATE_KEY_JWT = new AuthorizationMethod("private_key_jwt");
    public static final AuthorizationMethod NONE = new AuthorizationMethod("none");
    private final String value;

    public AuthorizationMethod(String value) {
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
        AuthorizationMethod that = (AuthorizationMethod) obj;
        return getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        return getValue().hashCode();
    }
}
