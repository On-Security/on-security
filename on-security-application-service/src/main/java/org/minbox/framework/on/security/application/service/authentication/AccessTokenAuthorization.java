package org.minbox.framework.on.security.application.service.authentication;

import org.minbox.framework.on.security.core.authorization.AuthorizeMatchMethod;
import org.minbox.framework.on.security.core.authorization.ResourceType;
import org.minbox.framework.on.security.core.authorization.SessionState;
import org.minbox.framework.on.security.core.authorization.util.OnSecurityVersion;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        return user;
    }

    /**
     * 获取令牌会话信息
     *
     * @return {@link AccessTokenSession}
     */
    public AccessTokenSession getSession() {
        return session;
    }

    /**
     * 获取令牌所属用户授权的资源列表
     *
     * @return {@link AuthorizationResource}
     */
    public List<AuthorizationResource> getUserAuthorizationResource() {
        return userAuthorizationResource;
    }

    /**
     * 获取令牌所属用户授权的属性列表
     *
     * @return {@link AuthorizationAttribute}
     */
    public List<AuthorizationAttribute> getUserAuthorizationAttribute() {
        return userAuthorizationAttribute;
    }

    /**
     * 获取令牌所属用户授权的角色列表
     *
     * @return {@link AuthorizationRole}
     */
    public List<AuthorizationRole> getUserAuthorizationRole() {
        return userAuthorizationRole;
    }

    /**
     * 授权角色信息
     */
    public static class AuthorizationRole {
        private String roleId;
        private String roleName;
        private String roleCode;
        private String roleDescribe;

        public String getRoleId() {
            return roleId;
        }

        public String getRoleName() {
            return roleName;
        }

        public String getRoleCode() {
            return roleCode;
        }

        public String getRoleDescribe() {
            return roleDescribe;
        }
    }

    /**
     * 授权属性信息
     */
    public static class AuthorizationAttribute {
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
    }

    /**
     * 授权资源信息
     */
    public static class AuthorizationResource {
        private String resourceId;
        private String resourceName;
        private String resourceCode;
        private Set<String> resourceUris;
        private ResourceType resourceType;
        private AuthorizeMatchMethod matchMethod;

        public String getResourceId() {
            return resourceId;
        }

        public String getResourceName() {
            return resourceName;
        }

        public String getResourceCode() {
            return resourceCode;
        }

        public Set<String> getResourceUris() {
            return resourceUris;
        }

        public ResourceType getResourceType() {
            return resourceType;
        }

        public AuthorizeMatchMethod getMatchMethod() {
            return matchMethod;
        }
    }

    /**
     * 令牌会话信息
     */
    public static class AccessTokenSession {
        private SessionState state;
        private LocalDateTime accessTokenIssuedAt;
        private LocalDateTime accessTokenExpiresAt;
        private Set<String> accessTokenScopes;

        public SessionState getState() {
            return state;
        }

        public LocalDateTime getAccessTokenIssuedAt() {
            return accessTokenIssuedAt;
        }

        public LocalDateTime getAccessTokenExpiresAt() {
            return accessTokenExpiresAt;
        }

        public Set<String> getAccessTokenScopes() {
            return accessTokenScopes;
        }
    }
}
