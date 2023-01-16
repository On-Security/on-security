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
public class ResourceAuthorizationAttribute implements Serializable {
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

        public ResourceAuthorizationAttribute build() {
            ResourceAuthorizationAttribute authorizationAttribute = new ResourceAuthorizationAttribute();
            authorizationAttribute.attributeId = this.attributeId;
            authorizationAttribute.attributeKey = this.attributeKey;
            authorizationAttribute.attributeValue = this.attributeValue;
            authorizationAttribute.matchMethod = this.matchMethod;
            return authorizationAttribute;
        }
    }
}
