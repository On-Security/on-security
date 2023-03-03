/*
 *     Copyright (C) 2022  恒宇少年
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.minbox.framework.on.security.authorization.server.oauth2.authentication.support;

import org.minbox.framework.on.security.core.authorization.AbstractOnSecurityAuthenticationProvider;
import org.minbox.framework.on.security.core.authorization.data.application.SecurityApplication;
import org.minbox.framework.on.security.core.authorization.data.attribute.SecurityAttributeService;
import org.minbox.framework.on.security.core.authorization.data.attribute.UserAuthorizationAttribute;
import org.minbox.framework.on.security.core.authorization.data.resource.SecurityResourceService;
import org.minbox.framework.on.security.core.authorization.data.resource.UserAuthorizationResource;
import org.minbox.framework.on.security.core.authorization.data.role.SecurityRoleService;
import org.minbox.framework.on.security.core.authorization.data.role.UserAuthorizationRole;
import org.minbox.framework.on.security.core.authorization.data.session.SecuritySession;
import org.minbox.framework.on.security.core.authorization.data.session.SecuritySessionJdbcRepository;
import org.minbox.framework.on.security.core.authorization.data.session.SecuritySessionRepository;
import org.minbox.framework.on.security.core.authorization.data.user.SecurityUser;
import org.minbox.framework.on.security.core.authorization.data.user.SecurityUserJdbcRepository;
import org.minbox.framework.on.security.core.authorization.data.user.SecurityUserRepository;
import org.minbox.framework.on.security.core.authorization.exception.OnSecurityError;
import org.minbox.framework.on.security.core.authorization.exception.OnSecurityErrorCodes;
import org.minbox.framework.on.security.core.authorization.exception.OnSecurityOAuth2AuthenticationException;
import org.minbox.framework.on.security.core.authorization.util.OnSecurityThrowErrorUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 访问授权信息认证提供者
 * <p>
 * 确认访问所携带的"AccessToken"令牌是否有效，根据令牌的所属用户{@link SecurityUser}以及所属应用{@link SecurityApplication}
 * 获取授权的资源以及属性列表等
 *
 * @author 恒宇少年
 * @since 0.0.5
 */
public final class OnSecurityAccessAuthorizationAuthenticationProvider extends AbstractOnSecurityAuthenticationProvider {
    private SecuritySessionRepository sessionRepository;
    private SecurityUserRepository userRepository;
    private SecurityResourceService resourceService;
    private SecurityAttributeService attributeService;
    private SecurityRoleService roleService;

    public OnSecurityAccessAuthorizationAuthenticationProvider(Map<Class<?>, Object> sharedObjects) {
        super(sharedObjects);
        ApplicationContext applicationContext = (ApplicationContext) sharedObjects.get(ApplicationContext.class);
        JdbcOperations jdbcOperations = applicationContext.getBean(JdbcOperations.class);
        this.sessionRepository = new SecuritySessionJdbcRepository(jdbcOperations);
        this.userRepository = new SecurityUserJdbcRepository(jdbcOperations);
        this.resourceService = new SecurityResourceService(jdbcOperations);
        this.attributeService = new SecurityAttributeService(jdbcOperations);
        this.roleService = new SecurityRoleService(jdbcOperations);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OnSecurityAccessAuthorizationRequestToken accessAuthorizationRequestToken = (OnSecurityAccessAuthorizationRequestToken) authentication;
        if (ObjectUtils.isEmpty(accessAuthorizationRequestToken.getAccessTokenValue())) {
            // @formatter:off
            OnSecurityError onSecurityError = new OnSecurityError(OAuth2ErrorCodes.INVALID_TOKEN,
                    null,
                    "No access token passed.",
                    OnSecurityThrowErrorUtils.DEFAULT_HELP_URI);
            // @formatter:on
            throw new OnSecurityOAuth2AuthenticationException(onSecurityError);
        }
        SecuritySession session =
                sessionRepository.findByToken(accessAuthorizationRequestToken.getAccessTokenValue(), OAuth2TokenType.ACCESS_TOKEN);
        if (session == null) {
            // @formatter:off
            OnSecurityError onSecurityError = new OnSecurityError(OAuth2ErrorCodes.INVALID_TOKEN,
                    null,
                    "Invalid access token.",
                    OnSecurityThrowErrorUtils.DEFAULT_HELP_URI);
            // @formatter:on
            throw new OnSecurityOAuth2AuthenticationException(onSecurityError);
        }
        if (LocalDateTime.now().isAfter(session.getAccessTokenExpiresAt())) {
            // @formatter:off
            OnSecurityError onSecurityError = new OnSecurityError(OAuth2ErrorCodes.INVALID_TOKEN,
                    null,
                    "access token has expired.",
                    OnSecurityThrowErrorUtils.DEFAULT_HELP_URI);
            // @formatter:on
            throw new OnSecurityOAuth2AuthenticationException(onSecurityError);
        }
        SecurityUser user = this.userRepository.selectOne(session.getUserId());
        if (user == null || user.isDeleted() || !user.isEnabled()) {
            // @formatter:off
            OnSecurityError onSecurityError = new OnSecurityError(OnSecurityErrorCodes.INVALID_USER.getValue(),
                    null,
                    "The user to which the access token belongs is invalid.",
                    OnSecurityThrowErrorUtils.DEFAULT_HELP_URI);
            // @formatter:on
            throw new OnSecurityOAuth2AuthenticationException(onSecurityError);
        }
        OnSecurityAccessAuthorizationAuthenticationToken.Builder builder =
                OnSecurityAccessAuthorizationAuthenticationToken.withUserAndSession(user, session);
        // Authorization Resource
        List<UserAuthorizationResource> authorizeResourceList = resourceService.findByUserId(session.getUserId());
        if (!ObjectUtils.isEmpty(authorizeResourceList)) {
            builder.userAuthorizationResourceList(authorizeResourceList);
        }
        // Authorization Attribute
        List<UserAuthorizationAttribute> authorizeAttributeList = attributeService.findByUserId(user.getId());
        if (!ObjectUtils.isEmpty(authorizeAttributeList)) {
            builder.userAuthorizationAttributeList(authorizeAttributeList);
        }
        // Authorization Role
        List<UserAuthorizationRole> authorizationRoleList = roleService.findByUserId(user.getId());
        if (!ObjectUtils.isEmpty(authorizationRoleList)) {
            builder.userAuthorizationRoleList(authorizationRoleList);
        }
        return builder.build();
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OnSecurityAccessAuthorizationRequestToken.class.isAssignableFrom(authentication);
    }
}
