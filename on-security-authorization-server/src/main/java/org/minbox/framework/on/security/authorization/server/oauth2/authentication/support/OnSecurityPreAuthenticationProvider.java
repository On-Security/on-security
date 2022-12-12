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

import org.minbox.framework.on.security.authorization.server.oauth2.authentication.AbstractOnSecurityAuthenticationProvider;
import org.minbox.framework.on.security.authorization.server.utils.OnSecurityThrowErrorUtils;
import org.minbox.framework.on.security.core.authorization.adapter.OnSecurityUserDetails;
import org.minbox.framework.on.security.core.authorization.data.client.SecurityClient;
import org.minbox.framework.on.security.core.authorization.data.client.SecurityClientJdbcRepository;
import org.minbox.framework.on.security.core.authorization.data.client.SecurityClientRepository;
import org.minbox.framework.on.security.core.authorization.data.user.SecurityUserAuthorizeClient;
import org.minbox.framework.on.security.core.authorization.data.user.SecurityUserAuthorizeClientJdbcRepository;
import org.minbox.framework.on.security.core.authorization.data.user.SecurityUserAuthorizeClientRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 认证前置验证身份提供者
 * <p>
 * 用于验证请求数据有效性，如：安全域有效性、客户端是否属于安全域、用户是否绑定了认证客户端等等
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class OnSecurityPreAuthenticationProvider extends AbstractOnSecurityAuthenticationProvider {
    private SecurityClientRepository securityClientRepository;
    private SecurityUserAuthorizeClientRepository userAuthorizeClientRepository;

    public OnSecurityPreAuthenticationProvider(Map<Class<?>, Object> sharedObjects) {
        super(sharedObjects);
        ApplicationContext applicationContext = (ApplicationContext) sharedObjects.get(ApplicationContext.class);
        JdbcOperations jdbcOperations = applicationContext.getBean(JdbcOperations.class);
        this.securityClientRepository = new SecurityClientJdbcRepository(jdbcOperations);
        this.userAuthorizeClientRepository = new SecurityUserAuthorizeClientJdbcRepository(jdbcOperations);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OnSecurityPreAuthenticationToken preAuthenticationToken = (OnSecurityPreAuthenticationToken) authentication;
        OnSecurityUserDetails onSecurityUserDetails = preAuthenticationToken.getUserDetails();
        // Verification ClientId && Verification UserDetails
        if (!ObjectUtils.isEmpty(preAuthenticationToken.getClientId()) && onSecurityUserDetails != null) {
            SecurityClient securityClient = securityClientRepository.findByClientId(preAuthenticationToken.getClientId());
            if (securityClient == null) {
                OnSecurityThrowErrorUtils.throwError(OAuth2ErrorCodes.INVALID_CLIENT, OAuth2ParameterNames.CLIENT_ID);
            }
            // @formatter:off
            List<SecurityUserAuthorizeClient> userAuthorizeClientList =
                    userAuthorizeClientRepository.findByUserId(onSecurityUserDetails.getUserId());
            if(ObjectUtils.isEmpty(userAuthorizeClientList)) {
                OnSecurityThrowErrorUtils.throwError(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT, OAuth2ParameterNames.CLIENT_ID);
            }
            List<String> userAuthorizeClientIds = userAuthorizeClientList.stream()
                    .map(SecurityUserAuthorizeClient::getClientId)
                    .collect(Collectors.toList());
            // @formatter:on
            if (!userAuthorizeClientIds.contains(securityClient.getId())) {
                OnSecurityThrowErrorUtils.throwError(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT, OAuth2ParameterNames.CLIENT_ID);
            }
        }
        return preAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OnSecurityPreAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
