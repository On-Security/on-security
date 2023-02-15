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

package org.minbox.framework.on.security.federal.identity.authentication;

import org.minbox.framework.on.security.core.authorization.AbstractOnSecurityAuthenticationProvider;
import org.minbox.framework.on.security.core.authorization.data.idp.SecurityIdentityProvider;
import org.minbox.framework.on.security.core.authorization.data.idp.SecurityIdentityProviderJdbcRepository;
import org.minbox.framework.on.security.core.authorization.data.idp.SecurityIdentityProviderRepository;
import org.minbox.framework.on.security.core.authorization.data.region.SecurityRegionIdentityProvider;
import org.minbox.framework.on.security.core.authorization.data.region.SecurityRegionIdentityProviderJdbcRepository;
import org.minbox.framework.on.security.core.authorization.data.region.SecurityRegionIdentityProviderRepository;
import org.minbox.framework.on.security.core.authorization.exception.OnSecurityError;
import org.minbox.framework.on.security.core.authorization.exception.OnSecurityErrorCodes;
import org.minbox.framework.on.security.core.authorization.exception.OnSecurityOAuth2AuthenticationException;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * IdP "Endpoint" 请求转发身份认证提供者
 * <p>
 * 在转发到目标IdP之前进行认证路径参数是否有效，认证内容包含：
 * - 安全域ID{@link SecurityRegionIdentityProvider#getRegionId()}是否正确
 * - IdP配置唯一ID{@link SecurityRegionIdentityProvider#getRegistrationId()}是否属于安全域
 * - {@link SecurityIdentityProvider}是否有效
 *
 * @author 恒宇少年
 * @since 0.0.3
 */
public class OnSecurityIdentityProviderBrokerEndpointAuthenticationProvider extends AbstractOnSecurityAuthenticationProvider {
    private SecurityIdentityProviderRepository identityProviderRepository;
    private SecurityRegionIdentityProviderRepository regionIdentityProviderRepository;

    public OnSecurityIdentityProviderBrokerEndpointAuthenticationProvider(Map<Class<?>, Object> sharedObjects) {
        super(sharedObjects);
        ApplicationContext applicationContext = (ApplicationContext) sharedObjects.get(ApplicationContext.class);
        JdbcOperations jdbcOperations = applicationContext.getBean(JdbcOperations.class);
        this.identityProviderRepository = new SecurityIdentityProviderJdbcRepository(jdbcOperations);
        this.regionIdentityProviderRepository = new SecurityRegionIdentityProviderJdbcRepository(jdbcOperations);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OnSecurityIdentityProviderBrokerEndpointRequestToken brokerEndpointRequestToken = (OnSecurityIdentityProviderBrokerEndpointRequestToken) authentication;
        SecurityRegionIdentityProvider regionIdentityProvider =
                regionIdentityProviderRepository.findByRegistrationId(brokerEndpointRequestToken.getRegistrationId());
        Assert.notNull(regionIdentityProvider, "registrationId: " + brokerEndpointRequestToken.getRegistrationId() + ", no IdP found.");
        SecurityIdentityProvider identityProvider = identityProviderRepository.selectOne(regionIdentityProvider.getIdpId());
        if (identityProvider == null || !identityProvider.getEnabled()) {
            OnSecurityError onSecurityError = new OnSecurityError(OnSecurityErrorCodes.INVALID_IDENTITY_PROVIDER.getValue());
            throw new OnSecurityOAuth2AuthenticationException(onSecurityError);
        }
        return brokerEndpointRequestToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OnSecurityIdentityProviderBrokerEndpointRequestToken.class.isAssignableFrom(authentication);
    }
}
