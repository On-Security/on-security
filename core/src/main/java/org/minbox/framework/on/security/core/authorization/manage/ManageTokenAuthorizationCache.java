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

package org.minbox.framework.on.security.core.authorization.manage;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

/**
 * 缓存"ManageToken"从授权服务器获取的授权信息
 * <p>
 * 缓存采用{@link Caffeine}内存方式进行临时缓存数据
 * "ManageToken"从授权服务器获取的授权信息数据写入{@link Caffeine}缓存后默认60分钟自动过期
 *
 * @author 恒宇少年
 * @see ManageTokenAccessAuthorization
 * @see Caffeine
 * @since 0.0.6
 */
public final class ManageTokenAuthorizationCache {
    /**
     * 缓存默认过期时间，单位：秒
     */
    private static final long DEFAULT_EXPIRE_SECONDS = 60 * 5;
    /**
     * 授权信息缓存
     * <p>
     * 最大存储1024个"ManageToken"对应的授权信息
     * Key存储"ManageToken"，Value存储{@link ManageTokenAccessAuthorization}
     */
    private static final Cache<Object, Object> ACCESS_AUTHORIZATION_CACHE =
            Caffeine.newBuilder()
                    .expireAfterWrite(DEFAULT_EXPIRE_SECONDS, TimeUnit.SECONDS)
                    .maximumSize(1024)
                    .build();

    private ManageTokenAuthorizationCache() {
    }

    public static ManageTokenAccessAuthorization getAccessAuthorization(String manageToken) {
        Assert.hasText(manageToken, "Failed to fetch token authorization info cache, manageToken cannot be empty");
        Object cacheData = ACCESS_AUTHORIZATION_CACHE.getIfPresent(manageToken);
        return cacheData != null ? (ManageTokenAccessAuthorization) cacheData : null;
    }

    public static void setAccessAuthorization(String manageToken, ManageTokenAccessAuthorization accessAuthorization) {
        // @formatter:off
        Assert.hasText(manageToken,
                "Failed to put token authorization information cache, manageToken cannot be empty");
        Assert.notNull(accessAuthorization,
                "Failed to put token authorization information cache, accessAuthorization cannot be null");
        // @formatter:on
        ACCESS_AUTHORIZATION_CACHE.put(manageToken, accessAuthorization);
    }

    public static void removeAccessAuthorization(String manageToken) {
        // @formatter:off
        Assert.hasText(manageToken,
                "Failed to delete token authorization information cache, manageToken cannot be empty");
        // @formatter:on
        ACCESS_AUTHORIZATION_CACHE.invalidate(manageToken);
    }
}
