package org.minbox.framework.on.security.application.service.authorization;

import org.minbox.framework.on.security.core.authorization.AuthorizeMatchMethod;
import org.minbox.framework.on.security.core.authorization.ResourceType;
import org.minbox.framework.on.security.core.authorization.data.resource.SecurityResource;
import org.minbox.framework.on.security.core.authorization.data.resource.SecurityResourceUri;
import org.minbox.framework.on.security.core.authorization.util.OnSecurityVersion;

import java.io.Serializable;
import java.util.Set;

/**
 * AccessToken所属用户的授权资源信息
 *
 * @author 恒宇少年
 * @since 0.0.7
 */
public class AuthorizationResource implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
    private String resourceId;
    private String resourceName;
    private String resourceCode;
    private Set<String> resourceUris;
    private ResourceType resourceType;
    private AuthorizeMatchMethod matchMethod;

    /**
     * 获取资源的唯一ID
     *
     * @return 资源ID {@link SecurityResource#getId()}
     */
    public String getResourceId() {
        return this.resourceId;
    }

    /**
     * 获取资源的名称
     *
     * @return 资源名称 {@link SecurityResource#getName()}
     */
    public String getResourceName() {
        return this.resourceName;
    }

    /**
     * 获取资源唯一码
     *
     * @return {@link SecurityResource#getCode()}
     */
    public String getResourceCode() {
        return this.resourceCode;
    }

    /**
     * 获取资源相关的路径列表
     *
     * @return {@link SecurityResourceUri#getUri()}
     * @see SecurityResourceUri
     */
    public Set<String> getResourceUris() {
        return this.resourceUris;
    }

    /**
     * 获取资源类型
     *
     * @return {@link ResourceType}
     */
    public ResourceType getResourceType() {
        return this.resourceType;
    }

    /**
     * 用户授权资源时的匹配方式
     *
     * @return {@link AuthorizeMatchMethod}
     */
    public AuthorizeMatchMethod getMatchMethod() {
        return this.matchMethod;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    public void setResourceUris(Set<String> resourceUris) {
        this.resourceUris = resourceUris;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public void setMatchMethod(AuthorizeMatchMethod matchMethod) {
        this.matchMethod = matchMethod;
    }
}
