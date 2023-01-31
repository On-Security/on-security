package org.minbox.framework.on.security.application.service.web;

import org.minbox.framework.on.security.application.service.authentication.OnSecurityAccessTokenAuthorizationProvider;
import org.minbox.framework.on.security.application.service.authentication.OnSecurityAccessTokenAuthorizationToken;
import org.minbox.framework.on.security.application.service.authentication.context.InheritableThreadLocalOnSecurityApplicationContextHolderStrategy;
import org.minbox.framework.on.security.application.service.authentication.context.OnSecurityApplicationContext;
import org.minbox.framework.on.security.application.service.authentication.context.OnSecurityApplicationContextHolder;
import org.minbox.framework.on.security.application.service.exception.OnSecurityApplicationResourceAuthenticationException;
import org.minbox.framework.on.security.application.service.exception.ResourceAuthenticationErrorCode;
import org.minbox.framework.on.security.application.service.web.convert.OnSecurityAccessTokenAuthorizationConvert;
import org.minbox.framework.on.security.core.authorization.endpoint.resolver.BearerTokenResolver;
import org.minbox.framework.on.security.core.authorization.endpoint.resolver.DefaultBearerTokenResolver;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * AccessToken授权信息加载过滤器
 * <p>
 * 该过滤器会拦截全部资源访问地址，提取访问时携带的"AccessToken"去授权服务器获取所属用户的授权信息
 * 认证成功后会将本次访问的上下文{@link OnSecurityApplicationContext}数据写入线程副本{@link ThreadLocal}中以便于业务程序使用
 *
 * @author 恒宇少年
 * @see OnSecurityApplicationContext
 * @see OnSecurityApplicationContextHolder
 * @see InheritableThreadLocalOnSecurityApplicationContextHolderStrategy
 * @see OnSecurityAccessTokenAuthorizationProvider
 * @see OnSecurityAccessTokenAuthorizationToken
 * @see OnSecurityApplicationResourceAuthorizationFailureHandler
 * @since 0.0.6
 */
public final class OnSecurityAccessTokenAuthorizationFilter extends OncePerRequestFilter {
    private AuthenticationManager authenticationManager;
    private BearerTokenResolver bearerTokenResolver;
    private AuthenticationConverter authenticationConverter;
    private AuthenticationFailureHandler authenticationFailureHandler;

    public OnSecurityAccessTokenAuthorizationFilter(AuthenticationManager authenticationManager) {
        // @formatter:off
        this(authenticationManager,
                new DefaultBearerTokenResolver(),
                new OnSecurityApplicationResourceAuthorizationFailureHandler());
        // @formatter:on
    }

    public OnSecurityAccessTokenAuthorizationFilter(AuthenticationManager authenticationManager,
                                                    BearerTokenResolver tokenResolver,
                                                    AuthenticationFailureHandler authenticationFailureHandler) {
        Assert.notNull(authenticationManager, "authenticationManager cannot be null");
        Assert.notNull(tokenResolver, "tokenResolver cannot be null");
        Assert.notNull(authenticationFailureHandler, "authenticationFailureHandler cannot be null");
        this.authenticationManager = authenticationManager;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.bearerTokenResolver = tokenResolver;
        this.authenticationConverter = new OnSecurityAccessTokenAuthorizationConvert(this.bearerTokenResolver);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            OnSecurityAccessTokenAuthorizationToken resourceAuthorizationToken =
                    (OnSecurityAccessTokenAuthorizationToken) this.authenticationConverter.convert(request);
            if (ObjectUtils.isEmpty(resourceAuthorizationToken.getAccessToken())) {
                throw new OnSecurityApplicationResourceAuthenticationException("No valid access_token was extracted.",
                        ResourceAuthenticationErrorCode.NO_ACCESS_TOKEN);
            }
            authenticationManager.authenticate(resourceAuthorizationToken);
            filterChain.doFilter(request, response);
        } catch (AuthenticationException e) {
            SecurityContextHolder.clearContext();
            OnSecurityApplicationContextHolder.clearContext();
            this.authenticationFailureHandler.onAuthenticationFailure(request, response, e);
        }
    }
}
