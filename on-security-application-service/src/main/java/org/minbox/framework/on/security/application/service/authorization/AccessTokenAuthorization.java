package org.minbox.framework.on.security.application.service.authorization;

import org.minbox.framework.on.security.core.authorization.util.OnSecurityVersion;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * AccessToken授权信息定义
 * <p>
 * 响应内容参考授权服务器接口"/access/authorization"
 *
 * @author 恒宇少年
 * @since 0.0.6
 */
public class AccessTokenAuthorization implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
    private Map<String, Object> user;
    private AccessTokenSession session;
    private List<AuthorizationResource> userAuthorizationResource;
    private List<AuthorizationAttribute> userAuthorizationAttribute;
    private List<AuthorizationRole> userAuthorizationRole;

    /**
     * 获取用户元数据信息列表
     *
     * @return 信息集合
     * @see org.minbox.framework.on.security.core.authorization.data.user.SecurityUser
     */
    public Map<String, Object> getUser() {
        return this.user;
    }

    /**
     * 获取令牌会话信息
     *
     * @return {@link AccessTokenSession}
     */
    public AccessTokenSession getSession() {
        return this.session;
    }

    /**
     * 获取令牌所属用户授权的资源列表
     *
     * @return {@link AuthorizationResource}
     */
    public List<AuthorizationResource> getUserAuthorizationResource() {
        return this.userAuthorizationResource;
    }

    /**
     * 获取令牌所属用户授权的属性列表
     *
     * @return {@link AuthorizationAttribute}
     */
    public List<AuthorizationAttribute> getUserAuthorizationAttribute() {
        return this.userAuthorizationAttribute;
    }

    /**
     * 获取令牌所属用户授权的角色列表
     *
     * @return {@link AuthorizationRole}
     */
    public List<AuthorizationRole> getUserAuthorizationRole() {
        return this.userAuthorizationRole;
    }

    public void setUser(Map<String, Object> user) {
        this.user = user;
    }

    public void setSession(AccessTokenSession session) {
        this.session = session;
    }

    public void setUserAuthorizationResource(List<AuthorizationResource> userAuthorizationResource) {
        this.userAuthorizationResource = userAuthorizationResource;
    }

    public void setUserAuthorizationAttribute(List<AuthorizationAttribute> userAuthorizationAttribute) {
        this.userAuthorizationAttribute = userAuthorizationAttribute;
    }

    public void setUserAuthorizationRole(List<AuthorizationRole> userAuthorizationRole) {
        this.userAuthorizationRole = userAuthorizationRole;
    }
}
