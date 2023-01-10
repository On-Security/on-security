package org.minbox.framework.on.security.application.service.authentication;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.minbox.framework.on.security.core.authorization.endpoint.AccessAuthorizationEndpointResponse;

/**
 * 缓存"AccessToken"从授权服务器获取的授权信息
 * <p>
 * 缓存采用{@link Caffeine}内存方式进行临时缓存数据，会根据每一个"AccessToken"的过期时间设置缓存失效的时间，
 * 这代表着一旦缓存失效，则无需再维护"AccessToken"请求令牌所授权的信息
 *
 * @author 恒宇少年
 * @see AccessAuthorizationEndpointResponse
 * @see Caffeine
 * @since 0.0.6
 */
public final class AccessTokenAuthorizationCache {

}
