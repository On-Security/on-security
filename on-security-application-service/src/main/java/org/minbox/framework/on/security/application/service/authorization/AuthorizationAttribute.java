package org.minbox.framework.on.security.application.service.authorization;

import org.minbox.framework.on.security.core.authorization.data.attribute.SecurityAttribute;
import org.minbox.framework.on.security.core.authorization.util.OnSecurityVersion;

import java.io.Serializable;

/**
 * AccessToken所属用户的授权属性信息
 *
 * @author 恒宇少年
 * @since 0.0.7
 */
public class AuthorizationAttribute implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
    private String attributeId;
    private String attributeKey;
    private String attributeValue;

    /**
     * 获取属性ID
     *
     * @return {@link SecurityAttribute#getId()}
     */
    public String getAttributeId() {
        return this.attributeId;
    }

    /**
     * 获取属性Key
     *
     * @return {@link SecurityAttribute#getKey()}
     */
    public String getAttributeKey() {
        return this.attributeKey;
    }

    /**
     * 获取属性Value
     *
     * @return {@link SecurityAttribute#getValue()}
     */
    public String getAttributeValue() {
        return this.attributeValue;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public void setAttributeKey(String attributeKey) {
        this.attributeKey = attributeKey;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }
}
