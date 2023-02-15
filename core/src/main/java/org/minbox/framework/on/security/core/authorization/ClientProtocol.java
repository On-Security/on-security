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
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * 客户端协议
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public final class ClientProtocol implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
    public static final ClientProtocol OAuth2_0 = new ClientProtocol("OAuth2.0");
    public static final ClientProtocol OpenID_Connect_1_0 = new ClientProtocol("OpenID Connect1.0");
    public static final ClientProtocol SAML_2_0 = new ClientProtocol("SAML2.0");
    private final String name;

    /**
     * Constructs an {@code GlobalDataClientProtocol} using the protocol.
     *
     * @param name the name of the protocol
     */
    public ClientProtocol(String name) {
        Assert.hasText(name, "value cannot be empty");
        this.name = name;
    }

    /**
     * Returns the name of the protocol.
     *
     * @return the name of the protocol
     */
    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        ClientProtocol that = (ClientProtocol) obj;
        return getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    @Override
    public String toString() {
        return "ClientProtocol{" +
                "name='" + name + '\'' +
                '}';
    }
}
