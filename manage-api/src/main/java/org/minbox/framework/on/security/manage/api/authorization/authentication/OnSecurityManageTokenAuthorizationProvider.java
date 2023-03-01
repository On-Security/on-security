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

package org.minbox.framework.on.security.manage.api.authorization.authentication;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.minbox.framework.on.security.core.authorization.AbstractOnSecurityAuthenticationProvider;
import org.minbox.framework.on.security.core.authorization.exception.OnSecurityError;
import org.minbox.framework.on.security.core.authorization.exception.OnSecurityErrorCodes;
import org.minbox.framework.on.security.core.authorization.exception.OnSecurityOAuth2AuthenticationException;
import org.minbox.framework.on.security.core.authorization.jackson2.OnSecurityJsonMapper;
import org.minbox.framework.on.security.core.authorization.manage.ManageTokenAccessAuthorization;
import org.minbox.framework.on.security.core.authorization.manage.ManageTokenAuthorizationCache;
import org.minbox.framework.on.security.core.authorization.manage.context.OnSecurityManageContext;
import org.minbox.framework.on.security.core.authorization.manage.context.OnSecurityManageContextHolder;
import org.minbox.framework.on.security.core.authorization.manage.context.OnSecurityManageContextImpl;
import org.minbox.framework.on.security.core.authorization.util.OnSecurityThrowErrorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.client.RestTemplate;

import java.net.ConnectException;
import java.util.Map;

/**
 * 获取管理令牌（Manage Token）授权信息认证提供者
 * <p>
 * 本类将会向控制台服务（Console Service）发起认证管理令牌授权信息的请求，将控制台服务返回的授权信息通过
 * {@link OnSecurityManageContextHolder}存储到{@link OnSecurityManageContext}
 *
 * @author 恒宇少年
 * @see OnSecurityManageContext
 * @see OnSecurityManageContextHolder
 * @see RestTemplate
 * @since 0.1.0
 */
public class OnSecurityManageTokenAuthorizationProvider extends AbstractOnSecurityAuthenticationProvider {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(OnSecurityManageTokenAuthorizationProvider.class);
    private static final String MANAGE_ACCESS_AUTHORIZATION_URI = "/on-security/manage/access/authorization";
    private static final String ERROR_CODE_RESPONSE_PARAM = "errorCode";
    private String consoleServerAddress;
    private RestTemplate restTemplate;

    public OnSecurityManageTokenAuthorizationProvider(String consoleServerAddress, Map<Class<?>, Object> sharedObjects) {
        super(sharedObjects);
        this.consoleServerAddress = consoleServerAddress;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OnSecurityManageTokenAuthorizationRequestToken authorizationRequestToken = (OnSecurityManageTokenAuthorizationRequestToken) authentication;
        ManageTokenAccessAuthorization accessAuthorization = ManageTokenAuthorizationCache.getAccessAuthorization(authorizationRequestToken.getManageToken());
        // cache miss
        if (accessAuthorization == null) {
            accessAuthorization = this.getAccessAuthorizationFromIssuer(authorizationRequestToken.getManageToken());
            ManageTokenAuthorizationCache.setAccessAuthorization(authorizationRequestToken.getManageToken(), accessAuthorization);
        }
        // @formatter:off
        OnSecurityManageContextImpl.Builder builder =
                OnSecurityManageContextImpl
                        .withManageToken(authorizationRequestToken.getManageToken())
                        .authorization(accessAuthorization);
        // @formatter:on
        OnSecurityManageContext manageContext = builder.build();
        OnSecurityManageContextHolder.setContext(manageContext);
        return authorizationRequestToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OnSecurityManageTokenAuthorizationRequestToken.class.isAssignableFrom(authentication);
    }

    /**
     * 获取令牌授权实体
     *
     * @param manageToken 访问令牌
     * @return 令牌授权的访问信息 {@link ManageTokenAccessAuthorization}
     */
    private ManageTokenAccessAuthorization getAccessAuthorizationFromIssuer(String manageToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.AUTHORIZATION, manageToken);
            // @formatter:off
            HttpEntity<String> response = restTemplate
                    .exchange(this.consoleServerAddress + MANAGE_ACCESS_AUTHORIZATION_URI,
                            HttpMethod.POST,
                            new HttpEntity<>(null, headers),
                            String.class);
            // @formatter:on
            OnSecurityJsonMapper jsonMapper = new OnSecurityJsonMapper();
            boolean ifHaveError = this.checkIfHaveError(response.getBody(), jsonMapper);
            if (ifHaveError) {
                // @formatter:off
                OnSecurityError onSecurityError = new OnSecurityError(OnSecurityErrorCodes.INVALID_MANAGE_TOKEN.getValue(),
                        null,
                        "Failed to obtain manage token authorization，please check the validity of the manage token.",
                        OnSecurityThrowErrorUtils.DEFAULT_HELP_URI);
                // @formatter:on
                throw new OnSecurityOAuth2AuthenticationException(onSecurityError);
            }
            ManageTokenAccessAuthorization accessAuthorization = jsonMapper.readValue(response.getBody(), ManageTokenAccessAuthorization.class);
            return accessAuthorization;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            // @formatter:off
            OnSecurityError onSecurityError = new OnSecurityError(OnSecurityErrorCodes.UNKNOWN_EXCEPTION.getValue(),
                    null,
                    "Unknown exception encountered while obtaining manage token authorization information.",
                    OnSecurityThrowErrorUtils.DEFAULT_HELP_URI);
            // @formatter:on
            if (e.getCause() instanceof ConnectException) {
                // @formatter:off
                onSecurityError = new OnSecurityError(OnSecurityErrorCodes.CONSOLE_SERVICE_CONNECTION_REFUSED.getValue(),
                        null,
                        "The connection to the console service failed and the management token authorization information could not be obtained.",
                        OnSecurityThrowErrorUtils.DEFAULT_HELP_URI);
                // @formatter:on
            }
            throw new OnSecurityOAuth2AuthenticationException(onSecurityError);
        }
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
}
