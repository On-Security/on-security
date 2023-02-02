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
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * 用户授权的属性封装实体
 *
 * @author 恒宇少年
 * @since 0.0.5
 */
public final class UserAuthorizationAttribute implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;

    private String attributeId;
    private String attributeKey;
    private String attributeValue;

    public String getAttributeId() {
        return attributeId;
    }

    public String getAttributeKey() {
        return attributeKey;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public static Builder withAttributeId(String attributeId) {
        Assert.hasText(attributeId, "attributeId cannot be empty");
        return new Builder(attributeId);
    }

    /**
     * The {@link UserAuthorizationAttribute} Builder
     */
    public static class Builder implements Serializable {
        private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
        private String attributeId;
        private String attributeKey;
        private String attributeValue;

        public Builder(String attributeId) {
            this.attributeId = attributeId;
        }

        public Builder attributeKey(String attributeKey) {
            this.attributeKey = attributeKey;
            return this;
        }

        public Builder attributeValue(String attributeValue) {
            this.attributeValue = attributeValue;
            return this;
        }

        public UserAuthorizationAttribute build() {
            UserAuthorizationAttribute authorizationAttribute = new UserAuthorizationAttribute();
            authorizationAttribute.attributeId = this.attributeId;
            authorizationAttribute.attributeKey = this.attributeKey;
            authorizationAttribute.attributeValue = this.attributeValue;
            return authorizationAttribute;
        }
    }
}
