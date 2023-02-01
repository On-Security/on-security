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
 * 签名算法
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public final class SignatureAlgorithm implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;

    public static final SignatureAlgorithm ES256 = new SignatureAlgorithm("ES256");
    public static final SignatureAlgorithm ES256K = new SignatureAlgorithm("ES256K");
    public static final SignatureAlgorithm ES512 = new SignatureAlgorithm("ES512");
    public static final SignatureAlgorithm HS256 = new SignatureAlgorithm("HS256");
    public static final SignatureAlgorithm HS384 = new SignatureAlgorithm("HS384");
    public static final SignatureAlgorithm HS512 = new SignatureAlgorithm("HS512");
    public static final SignatureAlgorithm PS256 = new SignatureAlgorithm("PS256");
    public static final SignatureAlgorithm PS384 = new SignatureAlgorithm("PS384");
    public static final SignatureAlgorithm PS512 = new SignatureAlgorithm("PS512");
    public static final SignatureAlgorithm RS256 = new SignatureAlgorithm("RS256");
    public static final SignatureAlgorithm RS384 = new SignatureAlgorithm("RS384");
    public static final SignatureAlgorithm RS512 = new SignatureAlgorithm("RS512");
    private final String value;

    public SignatureAlgorithm(String value) {
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
        SignatureAlgorithm that = (SignatureAlgorithm) obj;
        return getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        return getValue().hashCode();
    }
}
