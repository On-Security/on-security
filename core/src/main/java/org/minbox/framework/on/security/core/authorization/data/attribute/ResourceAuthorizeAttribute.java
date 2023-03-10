/*
 *   Copyright (C) 2022  恒宇少年
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.minbox.framework.on.security.core.authorization.data.attribute;

import org.minbox.framework.on.security.core.authorization.AuthorizeMatchMethod;
import org.minbox.framework.on.security.core.authorization.util.OnSecurityVersion;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * 资源授权属性信息定义
 *
 * @author 恒宇少年
 * @since 0.0.7
 */
public class ResourceAuthorizeAttribute implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
    private String attributeId;
    private String attributeKey;
    private String attributeValue;
    private AuthorizeMatchMethod matchMethod;

    /**
     * 获取资源授权的属性ID
     *
     * @return {@link SecurityAttribute#getId()}
     */
    public String getAttributeId() {
        return attributeId;
    }

    /**
     * 获取资源授权的属性Key
     *
     * @return {@link SecurityAttribute#getKey()}
     */
    public String getAttributeKey() {
        return attributeKey;
    }

    /**
     * 获取资源授权的属性Value
     *
     * @return {@link SecurityAttribute#getValue()}
     */
    public String getAttributeValue() {
        return attributeValue;
    }

    /**
     * 获取资源授权的属性匹配方式
     *
     * @return {@link AuthorizeMatchMethod}
     */
    public AuthorizeMatchMethod getMatchMethod() {
        return matchMethod;
    }

    public static Builder withAttributeId(String attributeId) {
        Assert.hasText(attributeId, "attributeId cannot be empty");
        return new Builder(attributeId);
    }

    public static class Builder implements Serializable {
        private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
        private String attributeId;
        private String attributeKey;
        private String attributeValue;
        private AuthorizeMatchMethod matchMethod;

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

        public Builder matchMethod(AuthorizeMatchMethod matchMethod) {
            this.matchMethod = matchMethod;
            return this;
        }

        public ResourceAuthorizeAttribute build() {
            ResourceAuthorizeAttribute authorizationAttribute = new ResourceAuthorizeAttribute();
            authorizationAttribute.attributeId = this.attributeId;
            authorizationAttribute.attributeKey = this.attributeKey;
            authorizationAttribute.attributeValue = this.attributeValue;
            authorizationAttribute.matchMethod = this.matchMethod;
            return authorizationAttribute;
        }
    }
}
