package org.minbox.framework.on.security.application.service.authentication.context;

import org.minbox.framework.on.security.core.authorization.data.attribute.UserAuthorizationAttribute;
import org.minbox.framework.on.security.core.authorization.data.resource.UserAuthorizationResource;
import org.minbox.framework.on.security.core.authorization.data.role.UserAuthorizationRole;
import org.minbox.framework.on.security.core.authorization.endpoint.AccessTokenSession;

import java.util.List;
import java.util.Map;

/**
 * 应用上下文接口定义
 *
 * @author 恒宇少年
 * @see AccessTokenSession
 * @see org.minbox.framework.on.security.core.authorization.data.resource.UserAuthorizationResource
 * @see org.minbox.framework.on.security.core.authorization.data.attribute.UserAuthorizationAttribute
 * @see org.minbox.framework.on.security.core.authorization.data.role.UserAuthorizationRole
 * @since 0.0.6
 */
public interface OnSecurityApplicationContext {
    /**
     * 获取本次访问资源携带的"AccessToken"
     *
     * @return 从请求Header中提取的"AccessToken"
     * @see org.minbox.framework.on.security.core.authorization.endpoint.resolver.BearerTokenResolver
     */
    String getAccessToken();

    /**
     * 获取"AccessToken"所属用户的元数据列表
     *
     * @return 用户元数据集合
     */
    Map<String, Object> getUserMetadata();

    /**
     * 获取令牌会话基本信息，包含状态、发布时间、过期时间、授权Scopes等
     *
     * @return {@link AccessTokenSession}
     */
    AccessTokenSession getSession();

    /**
     * 获取用户授权的资源列表
     *
     * @return 用户授权资源 {@link org.minbox.framework.on.security.core.authorization.data.resource.UserAuthorizationResource}
     */
    List<UserAuthorizationResource> getUserAuthorizationResource();

    /**
     * 获取用户授权的属性列表
     *
     * @return 用户授权属性 {@link org.minbox.framework.on.security.core.authorization.data.attribute.UserAuthorizationAttribute}
     */
    List<UserAuthorizationAttribute> getUserAuthorizationAttribute();

    /**
     * 获取用户授权的角色列表
     *
     * @return 用户授权角色 {@link org.minbox.framework.on.security.core.authorization.data.role.UserAuthorizationRole}
     */
    List<UserAuthorizationRole> getUserAuthorizationRole();
}
