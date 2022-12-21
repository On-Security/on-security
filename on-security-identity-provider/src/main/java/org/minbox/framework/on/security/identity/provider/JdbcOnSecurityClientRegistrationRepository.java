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

package org.minbox.framework.on.security.identity.provider;

import org.minbox.framework.on.security.core.authorization.data.idp.SecurityIdentityProvider;
import org.minbox.framework.on.security.core.authorization.data.idp.SecurityIdentityProviderJdbcRepository;
import org.minbox.framework.on.security.core.authorization.data.idp.SecurityIdentityProviderRepository;
import org.minbox.framework.on.security.core.authorization.data.region.SecurityRegionIdentityProvider;
import org.minbox.framework.on.security.core.authorization.data.region.SecurityRegionIdentityProviderJdbcRepository;
import org.minbox.framework.on.security.core.authorization.data.region.SecurityRegionIdentityProviderRepository;
import org.minbox.framework.on.security.identity.provider.convert.ClientRegistrationAdapter;
import org.minbox.framework.on.security.identity.provider.convert.ClientRegistrationConvert;
import org.springframework.core.convert.converter.Converter;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.util.Assert;

/**
 * On-Security提供的{@link ClientRegistrationRepository}JDBC方式实现类
 * <p>
 * 用于从数据库中读取{@link ClientRegistration}相关配置信息
 *
 * @author 恒宇少年
 * @since 0.0.2
 */
public final class JdbcOnSecurityClientRegistrationRepository implements ClientRegistrationRepository {
    /**
     * The name of bean in IOC
     */
    public static final String BEAN_NAME = "jdbcOnSecurityClientRegistrationRepository";
    private SecurityIdentityProviderRepository identityProviderRepository;
    private SecurityRegionIdentityProviderRepository regionIdentityProviderRepository;
    private Converter<ClientRegistrationAdapter, ClientRegistration> converter;

    public JdbcOnSecurityClientRegistrationRepository(JdbcOperations jdbcOperations) {
        Assert.notNull(jdbcOperations, "jdbcOperations cannot be null");
        this.identityProviderRepository = new SecurityIdentityProviderJdbcRepository(jdbcOperations);
        this.regionIdentityProviderRepository = new SecurityRegionIdentityProviderJdbcRepository(jdbcOperations);
        this.converter = new ClientRegistrationConvert();
    }

    /**
     * 根据IdP唯一注册ID查询详细信息
     *
     * @param registrationId the registration identifier {@link SecurityRegionIdentityProvider#getRegistrationId()}
     * @return {@link ClientRegistration}
     */
    @Override
    public ClientRegistration findByRegistrationId(String registrationId) {
        SecurityRegionIdentityProvider regionIdentityProvider = regionIdentityProviderRepository.findByRegistrationId(registrationId);
        SecurityIdentityProvider identityProvider = identityProviderRepository.findById(regionIdentityProvider.getIdpId());
        return converter.convert(new ClientRegistrationAdapter(regionIdentityProvider, identityProvider));
    }
}
