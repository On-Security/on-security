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

import org.minbox.framework.on.security.console.data.manager.SecurityConsoleManagerService;
import org.minbox.framework.on.security.console.data.manager.SecurityConsoleManagerSessionService;
import org.minbox.framework.on.security.console.data.menu.SecurityConsoleMenuService;
import org.minbox.framework.on.security.console.data.region.SecurityRegionSecretService;
import org.minbox.framework.on.security.console.data.region.SecurityRegionService;
import org.minbox.framework.on.security.core.authorization.AbstractOnSecurityAuthenticationProvider;
import org.minbox.framework.on.security.core.authorization.data.console.SecurityConsoleManager;
import org.minbox.framework.on.security.core.authorization.data.console.SecurityConsoleManagerSession;
import org.minbox.framework.on.security.core.authorization.data.console.SecurityConsoleMenu;
import org.minbox.framework.on.security.core.authorization.data.region.SecurityRegion;
import org.minbox.framework.on.security.core.authorization.data.region.SecurityRegionSecret;
import org.minbox.framework.on.security.core.authorization.exception.OnSecurityError;
import org.minbox.framework.on.security.core.authorization.exception.OnSecurityErrorCodes;
import org.minbox.framework.on.security.core.authorization.exception.OnSecurityOAuth2AuthenticationException;
import org.minbox.framework.on.security.core.authorization.manage.ManageTokenAccessAuthorization;
import org.minbox.framework.on.security.core.authorization.manage.ManageTokenAuthorizationCache;
import org.minbox.framework.on.security.core.authorization.manage.context.OnSecurityManageContext;
import org.minbox.framework.on.security.core.authorization.manage.context.OnSecurityManageContextHolder;
import org.minbox.framework.on.security.core.authorization.manage.context.OnSecurityManageContextImpl;
import org.minbox.framework.on.security.core.authorization.util.OnSecurityThrowErrorUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 管理令牌资源请求认证提供者
 * <p>
 * 根据请求资源所携带的"on-security-manage-token"头信息来验证令牌的有效性，提取令牌所属的安全域以及令牌的授权信息等
 *
 * @author 恒宇少年
 * @since 0.0.9
 */
public class OnSecurityManageTokenAccessAuthorizationProvider extends AbstractOnSecurityAuthenticationProvider {
    private SecurityConsoleManagerSessionService managerSessionService;
    private SecurityConsoleManagerService managerService;
    private SecurityConsoleMenuService menuService;
    private SecurityRegionService regionService;
    private SecurityRegionSecretService regionSecretService;

    public OnSecurityManageTokenAccessAuthorizationProvider(Map<Class<?>, Object> sharedObjects) {
        super(sharedObjects);
        ApplicationContext applicationContext = (ApplicationContext) sharedObjects.get(ApplicationContext.class);
        this.managerSessionService = applicationContext.getBean(SecurityConsoleManagerSessionService.class);
        this.managerService = applicationContext.getBean(SecurityConsoleManagerService.class);
        this.menuService = applicationContext.getBean(SecurityConsoleMenuService.class);
        this.regionService = applicationContext.getBean(SecurityRegionService.class);
        this.regionSecretService = applicationContext.getBean(SecurityRegionSecretService.class);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // @formatter:off
        OnSecurityManageTokenAccessRequestAuthorizationToken requestAuthorizationToken =
                (OnSecurityManageTokenAccessRequestAuthorizationToken) authentication;
        String manageToken = requestAuthorizationToken.getManageToken();
        // load from cache
        ManageTokenAccessAuthorization accessAuthorization = ManageTokenAuthorizationCache.getAccessAuthorization(manageToken);
        // cache miss
        if (accessAuthorization == null) {
            SecurityConsoleManagerSession managerSession = this.managerSessionService.selectByToken(manageToken);
            if (managerSession == null || LocalDateTime.now().isAfter(managerSession.getManageTokenExpiresAt())) {
                OnSecurityError onSecurityError = new OnSecurityError(OnSecurityErrorCodes.INVALID_MANAGE_TOKEN.getValue(),
                        null,
                        "Invalid manage token，check if the manage token is expired.",
                        OnSecurityThrowErrorUtils.DEFAULT_HELP_URI);
                throw new OnSecurityOAuth2AuthenticationException(onSecurityError);
            }
            SecurityRegion region = this.regionService.selectById(managerSession.getRegionId());
            if (region == null || region.getDeleted() || !region.getEnabled()) {
                OnSecurityError onSecurityError = new OnSecurityError(OnSecurityErrorCodes.INVALID_REGION.getValue(),
                        null,
                        "Invalid Region：" + (region == null ? region.getId() : managerSession.getRegionId()) +
                                "，Please check data validity.",
                        OnSecurityThrowErrorUtils.DEFAULT_HELP_URI);
                throw new OnSecurityOAuth2AuthenticationException(onSecurityError);
            }
            accessAuthorization = this.buildAccessAuthorization(managerSession, region);
            ManageTokenAuthorizationCache.setAccessAuthorization(manageToken, accessAuthorization);
        }
        OnSecurityManageContextImpl.Builder manageContextBuilder = OnSecurityManageContextImpl
                .withManageToken(manageToken)
                .authorization(accessAuthorization);
        OnSecurityManageContext manageContext = manageContextBuilder.build();
        OnSecurityManageContextHolder.setContext(manageContext);
        // @formatter:on
        return requestAuthorizationToken;
    }

    private ManageTokenAccessAuthorization buildAccessAuthorization(SecurityConsoleManagerSession managerSession, SecurityRegion region) {
        // @formatter:off
        ManageTokenAccessAuthorization.Builder builder =
                ManageTokenAccessAuthorization
                        .withManageSession(managerSession)
                        .region(region);
        // @formatter:on
        ManageTokenAuthenticateType authenticateType = ManageTokenAuthenticateType.valueOf(managerSession.getAuthenticateType());
        if (ManageTokenAuthenticateType.username_password == authenticateType) {
            SecurityConsoleManager manager = this.managerService.selectById(managerSession.getManagerId());
            if (manager == null || manager.getDeleted() || !manager.getEnabled()) {
                // @formatter:off
                OnSecurityError onSecurityError = new OnSecurityError(OnSecurityErrorCodes.INVALID_MANAGER.getValue(),
                        null,
                        "Invalid manager：" + (manager == null ? manager.getId() : managerSession.getManagerId()) +
                                "，Please check data validity.",
                        OnSecurityThrowErrorUtils.DEFAULT_HELP_URI);
                // @formatter:on
                throw new OnSecurityOAuth2AuthenticationException(onSecurityError);
            }
            builder.manager(manager);
            List<SecurityConsoleMenu> authorizeMenus = this.menuService.selectManagerAuthorizeMenus(manager.getId());
            if (!ObjectUtils.isEmpty(authorizeMenus)) {
                builder.managerAuthorizeMenus(authorizeMenus.stream().collect(Collectors.toSet()));
            }
        } else if (ManageTokenAuthenticateType.id_secret == authenticateType) {
            SecurityRegionSecret regionSecret = this.regionSecretService.selectById(managerSession.getRegionSecretId());
            if (regionSecret == null || regionSecret.getDeleted() || LocalDateTime.now().isAfter(regionSecret.getSecretExpiresAt())) {
                // @formatter:off
                OnSecurityError onSecurityError = new OnSecurityError(OnSecurityErrorCodes.INVALID_REGION_SECRET.getValue(),
                        null,
                        "Invalid region secret：" + (regionSecret == null ? regionSecret.getId() : managerSession.getRegionSecretId()) +
                                "，Please check data validity.",
                        OnSecurityThrowErrorUtils.DEFAULT_HELP_URI);
                // @formatter:on
                throw new OnSecurityOAuth2AuthenticationException(onSecurityError);
            }
            builder.regionSecret(regionSecret);
        }
        return builder.build();
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OnSecurityManageTokenAccessRequestAuthorizationToken.class.isAssignableFrom(authentication);
    }
}
