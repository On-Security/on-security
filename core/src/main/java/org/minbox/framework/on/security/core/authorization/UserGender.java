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
 * 用户性别
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class UserGender implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
    private final String value;
    public static final UserGender MAN = new UserGender("man");
    public static final UserGender WOMAN = new UserGender("woman");
    public static final UserGender OTHER = new UserGender("other");

    public UserGender(String value) {
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
        UserGender that = (UserGender) obj;
        return getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        return getValue().hashCode();
    }

    @Override
    public String toString() {
        return "UserGender{" +
                "value='" + value + '\'' +
                '}';
    }
}
