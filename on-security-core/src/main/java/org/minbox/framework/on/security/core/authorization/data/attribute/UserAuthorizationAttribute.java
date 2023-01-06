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

package org.minbox.framework.on.security.core.authorization.data.attribute;

import org.minbox.framework.on.security.core.authorization.util.OnSecurityVersion;

import java.io.Serializable;

/**
 * 用户授权的属性封装实体
 *
 * @author 恒宇少年
 * @since 0.0.5
 */
public final class UserAuthorizationAttribute implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
    /**
     * 属性ID
     */
    private String attributeId;
    /**
     * 属性Key
     */
    private String attributeKey;
    /**
     * 属性Value
     */
    private String attributeValue;

    public UserAuthorizationAttribute(String attributeId, String attributeKey, String attributeValue) {
        this.attributeId = attributeId;
        this.attributeKey = attributeKey;
        this.attributeValue = attributeValue;
    }

    public String getAttributeId() {
        return attributeId;
    }

    public String getAttributeKey() {
        return attributeKey;
    }

    public String getAttributeValue() {
        return attributeValue;
    }
}
