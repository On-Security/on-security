package org.minbox.framework.on.security.application.service.authentication;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

/**
 * 缓存"AccessToken"从授权服务器获取的授权信息
 * <p>
 * 缓存采用{@link Caffeine}内存方式进行临时缓存数据
 * "AccessToken"从授权服务器获取的授权信息数据写入{@link Caffeine}缓存后默认60分钟自动过期
 *
 * @author 恒宇少年
 * @see AccessTokenAuthorization
 * @see Caffeine
 * @since 0.0.6
 */
public final class AccessTokenAuthorizationCache {
    private static final long DEFAULT_EXPIRE_SECONDS = 60 * 60;
    /**
     * 授权信息缓存
     * <p>
     * 最大存储1024个"AccessToken"对应的授权信息
     * Key存储"AccessToken"，Value存储{@link AccessTokenAuthorization}
     */
    private static final Cache<Object, Object> ACCESS_AUTHORIZATION_CACHE =
            Caffeine.newBuilder()
                    .expireAfterWrite(DEFAULT_EXPIRE_SECONDS, TimeUnit.SECONDS)
                    .maximumSize(1024)
                    .build();

    private AccessTokenAuthorizationCache() {
    }

    static AccessTokenAuthorization getAccessAuthorization(String accessToken) {
        Assert.hasText(accessToken, "Failed to fetch token authorization info cache, accessToken cannot be empty");
        Object cacheData = ACCESS_AUTHORIZATION_CACHE.getIfPresent(accessToken);
        return cacheData != null ? (AccessTokenAuthorization) cacheData : null;
    }

    static void setAccessAuthorization(String accessToken, AccessTokenAuthorization accessAuthorization) {
        // @formatter:off
        Assert.hasText(accessToken,
                "Failed to put token authorization information cache, accessToken cannot be empty");
        Assert.notNull(accessAuthorization,
                "Failed to put token authorization information cache, accessAuthorization cannot be null");
        // @formatter:on
        ACCESS_AUTHORIZATION_CACHE.put(accessToken, accessAuthorization);
    }

    static void removeAccessAuthorization(String accessToken) {
        // @formatter:off
        Assert.hasText(accessToken,
                "Failed to delete token authorization information cache, accessToken cannot be empty");
        // @formatter:on
        ACCESS_AUTHORIZATION_CACHE.invalidate(accessToken);
    }
}
