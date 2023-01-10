package org.minbox.framework.on.security.application.service.authentication;

import org.minbox.framework.on.security.core.authorization.AbstractOnSecurityAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Map;

/**
 * 应用服务资源认证提供者
 *
 * @author 恒宇少年
 * @since 0.0.6
 */
public final class OnSecurityApplicationResourceAuthorizationProvider extends AbstractOnSecurityAuthenticationProvider {
    public OnSecurityApplicationResourceAuthorizationProvider(Map<Class<?>, Object> sharedObjects) {
        super(sharedObjects);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OnSecurityApplicationResourceAuthorizationToken resourceAuthorizationToken = (OnSecurityApplicationResourceAuthorizationToken) authentication;
        // TODO 1. 如果之前并未缓存过授权信息：
        // TODO 根据access_token去授权服务器获取授权信息"/access/authorization"
        // TODO 根据接口响应：验证令牌是否有效
        // TODO 根据接口响应：验证令牌是否过期
        // TODO 验证通过：缓存令牌所对应的授权信息
        // TODO 保存OnSecurityApplicationContextHolder#setContext()

        // TODO 2. 如果之前缓存过授权信息：
        // TODO 保存OnSecurityApplicationContextHolder#setContext()

        // TODO 验证资源是否授权允许访问，允许,拒绝，最好是根据策略来
        return resourceAuthorizationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OnSecurityApplicationResourceAuthorizationToken.class.isAssignableFrom(authentication);
    }
}
