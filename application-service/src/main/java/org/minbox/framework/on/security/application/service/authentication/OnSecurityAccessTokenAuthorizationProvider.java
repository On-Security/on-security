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

package org.minbox.framework.on.security.application.service.authentication;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.minbox.framework.on.security.application.service.authentication.context.OnSecurityApplicationContext;
import org.minbox.framework.on.security.application.service.authentication.context.OnSecurityApplicationContextHolder;
import org.minbox.framework.on.security.application.service.authentication.context.OnSecurityApplicationContextImpl;
import org.minbox.framework.on.security.application.service.exception.OnSecurityApplicationResourceAuthenticationException;
import org.minbox.framework.on.security.application.service.exception.ResourceAuthenticationErrorCode;
import org.minbox.framework.on.security.core.authorization.AbstractOnSecurityAuthenticationProvider;
import org.minbox.framework.on.security.core.authorization.endpoint.AccessTokenAuthorization;
import org.minbox.framework.on.security.core.authorization.jackson2.OnSecurityJsonMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * AccessToken授权信息加载提供者
 * <p>
 * 根据JWT令牌解析出来的{@link IdTokenClaimNames#ISS}令牌颁发者的地址来获取令牌的授权信息，授权信息获取地址为："/access/authorization"
 *
 * @author 恒宇少年
 * @see RestTemplate
 * @see AccessTokenAuthorization
 * @see OnSecurityAccessTokenAuthorizationToken
 * @see OnSecurityApplicationContext
 * @see SecurityContext
 * @since 0.0.6
 */
public final class OnSecurityAccessTokenAuthorizationProvider extends AbstractOnSecurityAuthenticationProvider {
    private static final String BEARER_TOKEN_VALUE_FORMAT = "Bearer %s";
    private static final String ACCESS_AUTHORIZATION_URI = "/access/authorization";
    private static final String ERROR_CODE_RESPONSE_PARAM = "errorCode";
    private RestTemplate restTemplate;

    public OnSecurityAccessTokenAuthorizationProvider(Map<Class<?>, Object> sharedObjects) {
        super(sharedObjects);
        this.restTemplate = new RestTemplate();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OnSecurityAccessTokenAuthorizationToken resourceAuthorizationToken = (OnSecurityAccessTokenAuthorizationToken) authentication;
        String accessToken = resourceAuthorizationToken.getAccessToken();
        AccessTokenAuthorization accessAuthorization = AccessTokenAuthorizationCache.getAccessAuthorization(accessToken);
        // hit cache
        if (accessAuthorization == null) {
            accessAuthorization = this.getAccessAuthorizationFromIssuer(accessToken);
            if (accessAuthorization == null) {
                throw new OnSecurityApplicationResourceAuthenticationException("Failed to get token authorization resource.",
                        ResourceAuthenticationErrorCode.UNAUTHORIZED_ACCESS);
            }
            AccessTokenAuthorizationCache.setAccessAuthorization(accessToken, accessAuthorization);
        }
        // @formatter:off
        OnSecurityApplicationContextImpl.Builder builder =
                OnSecurityApplicationContextImpl
                        .withAccessToken(accessToken)
                        .accessTokenAuthorization(accessAuthorization);
        // @formatter:on
        OnSecurityApplicationContext applicationContext = builder.build();
        OnSecurityApplicationContextHolder.setContext(applicationContext);
        return resourceAuthorizationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OnSecurityAccessTokenAuthorizationToken.class.isAssignableFrom(authentication);
    }

    /**
     * 获取令牌授权实体
     *
     * @param accessToken 访问令牌
     * @return 令牌授权的访问信息 {@link AccessTokenAuthorization}
     */
    private AccessTokenAuthorization getAccessAuthorizationFromIssuer(String accessToken) throws OnSecurityApplicationResourceAuthenticationException {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.AUTHORIZATION, String.format(BEARER_TOKEN_VALUE_FORMAT, accessToken));
            // @formatter:off
            HttpEntity<String> response = restTemplate
                    .exchange(this.getIssuer() + ACCESS_AUTHORIZATION_URI,
                            HttpMethod.GET,
                            new HttpEntity<>(null, headers),
                            String.class);
            // @formatter:on
            OnSecurityJsonMapper jsonMapper = new OnSecurityJsonMapper();
            boolean ifHaveError = this.checkIfHaveError(response.getBody(), jsonMapper);
            if (ifHaveError) {
                throw new OnSecurityApplicationResourceAuthenticationException("Invalid access token",
                        ResourceAuthenticationErrorCode.INVALID_ACCESS_TOKEN);
            }
            AccessTokenAuthorization accessTokenAuthorization = jsonMapper.readValue(response.getBody(), AccessTokenAuthorization.class);
            return accessTokenAuthorization;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 检查获取授权信息时是否遇到了异常
     *
     * @param responseJson 响应Json字符串
     * @param jsonMapper   {@link OnSecurityJsonMapper}
     * @return 遇到异常返回true，否者返回false
     * @throws JsonProcessingException
     */
    private boolean checkIfHaveError(String responseJson, OnSecurityJsonMapper jsonMapper) throws JsonProcessingException {
        Map<String, Object> resoponseMap = jsonMapper.readValue(responseJson, Map.class);
        return resoponseMap.containsKey(ERROR_CODE_RESPONSE_PARAM);
    }

    /**
     * 获取令牌颁发者（授权服务器）地址
     *
     * @return {@link IdTokenClaimNames#ISS}
     */
    private String getIssuer() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) securityContext.getAuthentication();
        Map<String, Object> tokenAttributes = authenticationToken.getTokenAttributes();
        return (String) tokenAttributes.get(IdTokenClaimNames.ISS);
    }
}
