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
import org.minbox.framework.on.security.core.authorization.exception.OnSecurityErrorCodes;
import org.minbox.framework.on.security.core.authorization.util.OnSecurityThrowErrorUtils;
import org.minbox.framework.on.security.core.authorization.adapter.OnSecurityUserDetails;
import org.minbox.framework.on.security.core.authorization.data.application.SecurityApplication;
import org.minbox.framework.on.security.core.authorization.data.application.SecurityApplicationJdbcRepository;
import org.minbox.framework.on.security.core.authorization.data.application.SecurityApplicationRepository;
import org.minbox.framework.on.security.core.authorization.data.region.SecurityRegion;
import org.minbox.framework.on.security.core.authorization.data.region.SecurityRegionJdbcRepository;
import org.minbox.framework.on.security.core.authorization.data.region.SecurityRegionRepository;
import org.minbox.framework.on.security.core.authorization.data.user.SecurityUserAuthorizeApplication;
import org.minbox.framework.on.security.core.authorization.data.user.SecurityUserAuthorizeApplicationJdbcRepository;
import org.minbox.framework.on.security.core.authorization.data.user.SecurityUserAuthorizeApplicationRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 授权码方式前置认证身份提供者
 * <p>
 * 用于验证请求数据有效性，如：安全域有效性、客户端是否属于安全域、用户是否绑定了认证客户端等等
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class OnSecurityPreAuthorizationCodeAuthenticationProvider extends AbstractOnSecurityAuthenticationProvider {
    private SecurityApplicationRepository securityApplicationRepository;
    private SecurityUserAuthorizeApplicationRepository userAuthorizeClientRepository;
    private SecurityRegionRepository regionRepository;

    public OnSecurityPreAuthorizationCodeAuthenticationProvider(Map<Class<?>, Object> sharedObjects) {
        super(sharedObjects);
        ApplicationContext applicationContext = (ApplicationContext) sharedObjects.get(ApplicationContext.class);
        JdbcOperations jdbcOperations = applicationContext.getBean(JdbcOperations.class);
        this.securityApplicationRepository = new SecurityApplicationJdbcRepository(jdbcOperations);
        this.userAuthorizeClientRepository = new SecurityUserAuthorizeApplicationJdbcRepository(jdbcOperations);
        this.regionRepository = new SecurityRegionJdbcRepository(jdbcOperations);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OnSecurityPreAuthorizationCodeAuthenticationToken preAuthenticationToken = (OnSecurityPreAuthorizationCodeAuthenticationToken) authentication;
        OnSecurityUserDetails onSecurityUserDetails = preAuthenticationToken.getUserDetails();
        // Verification ClientId
        SecurityApplication securityApplication = null;
        if (!ObjectUtils.isEmpty(preAuthenticationToken.getApplicationId())) {
            securityApplication = securityApplicationRepository.findByApplicationId(preAuthenticationToken.getApplicationId());
            if (securityApplication == null || !securityApplication.isEnabled() || securityApplication.isDeleted()) {
                //@formatter:off
                OnSecurityThrowErrorUtils.throwError(OnSecurityErrorCodes.INVALID_APPLICATION,
                        OAuth2ParameterNames.CLIENT_ID,
                        "Invalid Application，ID：" + preAuthenticationToken.getApplicationId() + "，Please check data validity.");
                // @formatter:on
            }
            SecurityRegion securityRegion = regionRepository.selectOne(securityApplication.getRegionId());
            if (securityRegion == null || !securityRegion.isEnabled() || securityRegion.isDeleted()) {
                //@formatter:off
                OnSecurityThrowErrorUtils.throwError(OnSecurityErrorCodes.INVALID_REGION,
                        null,
                        "Invalid Region：" + (securityRegion == null ? securityApplication.getRegionId() : securityRegion.getRegionId()) +
                                "，Please check data validity.");
                // @formatter:on
            }
        }
        // Verification UserDetails
        if (onSecurityUserDetails != null) {
            // @formatter:off
            List<SecurityUserAuthorizeApplication> userAuthorizeClientList =
                    userAuthorizeClientRepository.findByUserId(onSecurityUserDetails.getUserId());
            if(ObjectUtils.isEmpty(userAuthorizeClientList)) {
                OnSecurityThrowErrorUtils.throwError(OnSecurityErrorCodes.UNAUTHORIZED_APPLICATION,
                        OAuth2ParameterNames.CLIENT_ID,
                        "User: " + onSecurityUserDetails.getUsername() + ", not authorized to bind application: " + preAuthenticationToken.getApplicationId());
            }
            List<String> userAuthorizeClientIds = userAuthorizeClientList.stream()
                    .map(SecurityUserAuthorizeApplication::getApplicationId)
                    .collect(Collectors.toList());
            // @formatter:on
            if (!userAuthorizeClientIds.contains(securityApplication.getId())) {
                // @formatter:off
                OnSecurityThrowErrorUtils.throwError(OnSecurityErrorCodes.UNAUTHORIZED_APPLICATION,
                        OAuth2ParameterNames.CLIENT_ID,
                        "User: " + onSecurityUserDetails.getUsername() + ", not authorized to bind application: " + preAuthenticationToken.getApplicationId());
                // @formatter:on
            }
        }
        return preAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OnSecurityPreAuthorizationCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
