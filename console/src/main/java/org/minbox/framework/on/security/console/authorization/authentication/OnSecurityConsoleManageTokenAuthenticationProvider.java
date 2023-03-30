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

package org.minbox.framework.on.security.console.authorization.authentication;

import org.minbox.framework.on.security.console.authorization.token.Base64StringConsoleManageTokenGenerator;
import org.minbox.framework.on.security.console.authorization.token.ConsoleManageToken;
import org.minbox.framework.on.security.console.authorization.token.ConsoleManageTokenContext;
import org.minbox.framework.on.security.console.authorization.token.ConsoleManageTokenGenerator;
import org.minbox.framework.on.security.console.configuration.OnSecurityConsoleServiceJwkSource;
import org.minbox.framework.on.security.console.data.manager.SecurityConsoleManagerService;
import org.minbox.framework.on.security.console.data.manager.SecurityConsoleManagerSessionService;
import org.minbox.framework.on.security.console.data.region.SecurityRegionSecretService;
import org.minbox.framework.on.security.console.data.region.SecurityRegionService;
import org.minbox.framework.on.security.core.authorization.AbstractOnSecurityAuthenticationProvider;
import org.minbox.framework.on.security.core.authorization.data.console.SecurityConsoleManager;
import org.minbox.framework.on.security.core.authorization.data.console.SecurityConsoleManagerSession;
import org.minbox.framework.on.security.core.authorization.data.region.SecurityRegion;
import org.minbox.framework.on.security.core.authorization.data.region.SecurityRegionSecret;
import org.minbox.framework.on.security.core.authorization.exception.OnSecurityError;
import org.minbox.framework.on.security.core.authorization.exception.OnSecurityErrorCodes;
import org.minbox.framework.on.security.core.authorization.exception.OnSecurityOAuth2AuthenticationException;
import org.minbox.framework.on.security.core.authorization.util.BeanUtils;
import org.minbox.framework.on.security.core.authorization.util.OnSecurityThrowErrorUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.UUID;

import static org.minbox.framework.on.security.console.authorization.web.OnSecurityConsoleManageTokenFilter.*;

/**
 * 获取控制台管理令牌（ManageToken）提供者
 * <p>
 * 该提供者支持根据安全域ID（RegionID）、安全域密钥（Region Secret）以及管理员的用户名、密码获取管理令牌（ManageToken）
 *
 * @author 恒宇少年
 * @since 0.0.9
 */
public class OnSecurityConsoleManageTokenAuthenticationProvider extends AbstractOnSecurityAuthenticationProvider {
    private SecurityRegionService regionService;
    private SecurityConsoleManagerService consoleManagerService;
    private SecurityRegionSecretService regionSecretService;
    private SecurityConsoleManagerSessionService consoleManagerSessionService;
    private ConsoleManageTokenGenerator tokenGenerator;
    private PasswordEncoder passwordEncoder;

    public OnSecurityConsoleManageTokenAuthenticationProvider(Map<Class<?>, Object> sharedObjects) {
        super(sharedObjects);
        ApplicationContext applicationContext = (ApplicationContext) sharedObjects.get(ApplicationContext.class);
        this.regionService = applicationContext.getBean(SecurityRegionService.class);
        this.consoleManagerService = applicationContext.getBean(SecurityConsoleManagerService.class);
        this.regionSecretService = applicationContext.getBean(SecurityRegionSecretService.class);
        this.consoleManagerSessionService = applicationContext.getBean(SecurityConsoleManagerSessionService.class);
        OnSecurityConsoleServiceJwkSource consoleServiceJwkSource = applicationContext.getBean(OnSecurityConsoleServiceJwkSource.class);
        ConsoleManageTokenGenerator consoleManageTokenGenerator = BeanUtils.getOptionalBean(applicationContext, ConsoleManageTokenGenerator.class);
        this.tokenGenerator = consoleManageTokenGenerator != null ? consoleManageTokenGenerator : new Base64StringConsoleManageTokenGenerator(consoleServiceJwkSource);
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OnSecurityConsoleManageTokenRequestToken manageTokenRequestToken = (OnSecurityConsoleManageTokenRequestToken) authentication;
        ManageTokenAuthenticateType authenticateType = manageTokenRequestToken.getAuthenticateType();
        MultiValueMap<String, String> parameters = manageTokenRequestToken.getParameters();
        String username = parameters.getFirst(USERNAME);
        String password = parameters.getFirst(PASSWORD);
        String regionId = parameters.getFirst(REGION_ID);
        String regionSecret = parameters.getFirst(REGION_SECRET);
        OnSecurityConsoleManageTokenAuthenticationToken manageTokenAuthenticationToken = null;
        // username_password authenticate type
        if (ManageTokenAuthenticateType.username_password == authenticateType) {
            if ((ObjectUtils.isEmpty(username) || ObjectUtils.isEmpty(password))) {
                // @formatter:off
                OnSecurityError onSecurityError = new OnSecurityError(OAuth2ErrorCodes.INVALID_REQUEST,
                null,
                "Bad request, username or password cannot be empty.",
                OnSecurityThrowErrorUtils.DEFAULT_HELP_URI);
                // @formatter:on
                throw new OnSecurityOAuth2AuthenticationException(onSecurityError);
            }
            manageTokenAuthenticationToken = this.usernamePasswordAuthenticate(username, password);
        }
        // id_secret authenticate type
        else if (ManageTokenAuthenticateType.id_secret == authenticateType) {
            if ((ObjectUtils.isEmpty(regionId) || ObjectUtils.isEmpty(regionSecret))) {
                // @formatter:off
                OnSecurityError onSecurityError = new OnSecurityError(OAuth2ErrorCodes.INVALID_REQUEST,
                    null,
                    "Bad request, region_id or region_secret cannot be empty.",
                    OnSecurityThrowErrorUtils.DEFAULT_HELP_URI);
                // @formatter:on
                throw new OnSecurityOAuth2AuthenticationException(onSecurityError);
            }
            manageTokenAuthenticationToken = this.idSecretAuthenticate(regionId, regionSecret);
        }
        // Save Manage Token
        if (manageTokenAuthenticationToken != null) {
            ConsoleManageTokenContext manageTokenContext = manageTokenAuthenticationToken.getTokenContext();
            ConsoleManageToken manageToken = manageTokenAuthenticationToken.getToken();
            SecurityConsoleManagerSession managerSession = new SecurityConsoleManagerSession();
            managerSession.setId(UUID.randomUUID().toString());
            managerSession.setRegionId(manageTokenContext.getRegionId());
            managerSession.setManagerId(manageTokenContext.getManagerId());
            managerSession.setRegionSecretId(manageTokenContext.getSecretId());
            managerSession.setAuthenticateType(manageTokenContext.getAuthenticateType().toString());
            managerSession.setManageTokenValue(manageToken.getOriginal());
            managerSession.setManageTokenIssuedAt(LocalDateTime.ofInstant(manageToken.getIssuedAt(), ZoneId.systemDefault()));
            managerSession.setManageTokenExpiresAt(LocalDateTime.ofInstant(manageToken.getExpiresAt(), ZoneId.systemDefault()));
            consoleManagerSessionService.insert(managerSession);
        }
        return manageTokenAuthenticationToken != null ? manageTokenAuthenticationToken : authentication;
    }

    /**
     * {@link ManageTokenAuthenticateType#username_password}认证方式获取ManageToken
     *
     * @param username 控制台管理员用户名
     * @param password 控制台管理员密码
     * @return {@link OnSecurityConsoleManageTokenAuthenticationToken}
     */
    private OnSecurityConsoleManageTokenAuthenticationToken usernamePasswordAuthenticate(String username, String password) {
        SecurityConsoleManager consoleManager = this.consoleManagerService.findByUsername(username);
        if (consoleManager == null) {
            // @formatter:off
            OnSecurityError onSecurityError = new OnSecurityError(OnSecurityErrorCodes.INVALID_USER.getValue(),
                    null,
                    "Invalid user, no user named: " + username + " was found.",
                    OnSecurityThrowErrorUtils.DEFAULT_HELP_URI);
            // @formatter:on
            throw new OnSecurityOAuth2AuthenticationException(onSecurityError);
        }
        if (!this.passwordEncoder.matches(password, consoleManager.getPassword())) {
            // @formatter:off
            OnSecurityError onSecurityError = new OnSecurityError(OnSecurityErrorCodes.INVALID_PASSWORD.getValue(),
                    null,
                    "Password verification failed.",
                    OnSecurityThrowErrorUtils.DEFAULT_HELP_URI);
            // @formatter:on
            throw new OnSecurityOAuth2AuthenticationException(onSecurityError);
        }
        ConsoleManageTokenContext consoleManageTokenContext = ConsoleManageTokenContext.withUsernamePassword(consoleManager);
        ConsoleManageToken consoleManageToken = this.tokenGenerator.generator(consoleManageTokenContext);
        return new OnSecurityConsoleManageTokenAuthenticationToken(consoleManageTokenContext, consoleManageToken);
    }

    /**
     * {@link ManageTokenAuthenticateType#id_secret}认证方式获取ManageToken
     *
     * @param regionId     安全域ID
     * @param regionSecret 安全域密钥
     * @return {@link OnSecurityConsoleManageTokenAuthenticationToken}
     */
    private OnSecurityConsoleManageTokenAuthenticationToken idSecretAuthenticate(String regionId, String regionSecret) {
        SecurityRegion region = regionService.selectByRegionId(regionId);
        if (region == null) {
            // @formatter:off
            OnSecurityError onSecurityError = new OnSecurityError(OnSecurityErrorCodes.INVALID_REGION.getValue(),
                    null,
                    "Invalid region, no region id : " + regionId + " was found.",
                    OnSecurityThrowErrorUtils.DEFAULT_HELP_URI);
            // @formatter:on
            throw new OnSecurityOAuth2AuthenticationException(onSecurityError);
        }
        SecurityRegionSecret securityRegionSecret = this.regionSecretService.selectBySecret(region.getId(), regionSecret);
        if (securityRegionSecret == null) {
            // @formatter:off
            OnSecurityError onSecurityError = new OnSecurityError(OnSecurityErrorCodes.INVALID_REGION_SECRET.getValue(),
                    null,
                    "Invalid region secret, no secret value is : " + regionSecret + " was found.",
                    OnSecurityThrowErrorUtils.DEFAULT_HELP_URI);
            // @formatter:on
            throw new OnSecurityOAuth2AuthenticationException(onSecurityError);
        }
        ConsoleManageTokenContext consoleManageTokenContext = ConsoleManageTokenContext.withIdSecret(securityRegionSecret);
        ConsoleManageToken consoleManageToken = this.tokenGenerator.generator(consoleManageTokenContext);
        return new OnSecurityConsoleManageTokenAuthenticationToken(consoleManageTokenContext, consoleManageToken);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OnSecurityConsoleManageTokenRequestToken.class.isAssignableFrom(authentication);
    }
}
