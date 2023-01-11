package org.minbox.framework.on.security.application.service.authentication;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.minbox.framework.on.security.application.service.authentication.context.OnSecurityApplicationContext;
import org.minbox.framework.on.security.application.service.authentication.context.OnSecurityApplicationContextHolder;
import org.minbox.framework.on.security.application.service.authentication.context.OnSecurityApplicationContextImpl;
import org.minbox.framework.on.security.application.service.exception.OnSecurityApplicationResourceAuthenticationException;
import org.minbox.framework.on.security.application.service.exception.ResourceAuthenticationErrorCode;
import org.minbox.framework.on.security.core.authorization.AbstractOnSecurityAuthenticationProvider;
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
 * 应用服务资源认证提供者
 *
 * @author 恒宇少年
 * @since 0.0.6
 */
public final class OnSecurityApplicationResourceAuthorizationProvider extends AbstractOnSecurityAuthenticationProvider {
    private static final String BEARER_TOKEN_VALUE_FORMAT = "Bearer %s";
    private static final String ACCESS_AUTHORIZATION_URI = "/access/authorization";
    private static final String ERROR_CODE_RESPONSE_PARAM = "errorCode";
    private RestTemplate restTemplate;

    public OnSecurityApplicationResourceAuthorizationProvider(Map<Class<?>, Object> sharedObjects) {
        super(sharedObjects);
        this.restTemplate = new RestTemplate();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OnSecurityApplicationResourceAuthorizationToken resourceAuthorizationToken = (OnSecurityApplicationResourceAuthorizationToken) authentication;
        String accessToken = resourceAuthorizationToken.getAccessToken();
        AccessTokenAuthorization accessAuthorization = AccessTokenAuthorizationCache.getAccessAuthorization(accessToken);
        // hit cache
        if (accessAuthorization == null) {
            accessAuthorization = this.getAccessAuthorization(accessToken);
            AccessTokenAuthorizationCache.setAccessAuthorization(accessToken, accessAuthorization);
        }
        // @formatter:off
        OnSecurityApplicationContextImpl.Builder builder =
                OnSecurityApplicationContextImpl
                        .withAccessTokenAuthorization(accessAuthorization)
                        .accessToken(accessToken);
        // @formatter:on
        OnSecurityApplicationContext applicationContext = builder.build();
        OnSecurityApplicationContextHolder.setContext(applicationContext);
        // TODO 验证资源是否授权允许访问，允许,拒绝，最好是根据策略来
        return resourceAuthorizationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OnSecurityApplicationResourceAuthorizationToken.class.isAssignableFrom(authentication);
    }

    /**
     * 获取令牌授权实体
     *
     * @param accessToken 访问令牌
     * @return 令牌授权的访问信息 {@link AccessTokenAuthorization}
     */
    private AccessTokenAuthorization getAccessAuthorization(String accessToken) throws OnSecurityApplicationResourceAuthenticationException {
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
