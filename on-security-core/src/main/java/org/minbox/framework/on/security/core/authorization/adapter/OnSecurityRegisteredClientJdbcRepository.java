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

package org.minbox.framework.on.security.core.authorization.adapter;

import org.minbox.framework.on.security.core.authorization.ClientProtocol;
import org.minbox.framework.on.security.core.authorization.data.client.*;
import org.minbox.framework.on.security.core.authorization.data.client.converter.SecurityRegisteredClientConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

/**
 * On-Security提供的{@link RegisteredClientRepository}JDBC数据存储方式实现类
 * <p>
 * 根据SpringSecurity授权服务器所维护的{@link RegisteredClient}客户端信息转换并
 * 维护On-Security授权服务器所维护的{@link SecurityClient}客户端数据
 *
 * @author 恒宇少年
 * @see RegisteredClientRepository
 * @see RegisteredClient
 * @see SecurityClient
 * @see JdbcOperations
 * @since 0.0.1
 */
public final class OnSecurityRegisteredClientJdbcRepository implements RegisteredClientRepository {
    private static final String DEFAULT_SECURITY_REGION_ID = "default";
    private JdbcOperations jdbcOperations;
    private DataSourceTransactionManager dataSourceTransactionManager;
    private Converter<RegisteredClient, SecurityClient> converter;
    private SecurityClientRepository clientRepository;
    private SecurityClientScopeRepository clientScopeRepository;
    private SecurityClientSecretRepository clientSecretRepository;
    private SecurityClientRedirectUriRepository clientRedirectUriRepository;
    private SecurityClientAuthenticationRepository clientAuthenticationRepository;

    public OnSecurityRegisteredClientJdbcRepository(JdbcOperations jdbcOperations, DataSourceTransactionManager dataSourceTransactionManager) {
        Assert.notNull(jdbcOperations, "jdbcOperations cannot be null");
        Assert.notNull(dataSourceTransactionManager, "dataSourceTransactionManager cannot be null");
        this.jdbcOperations = jdbcOperations;
        this.dataSourceTransactionManager = dataSourceTransactionManager;
        this.converter = new SecurityRegisteredClientConverter();
        this.clientRepository = new SecurityClientJdbcRepository(jdbcOperations);
        this.clientScopeRepository = new SecurityClientScopeJdbcRepository(jdbcOperations);
        this.clientSecretRepository = new SecurityClientSecretJdbcRepository(jdbcOperations);
        this.clientRedirectUriRepository = new SecurityClientRedirectUriJdbcRepository(jdbcOperations);
        this.clientAuthenticationRepository = new SecurityClientAuthenticationJdbcRepository(jdbcOperations);
    }

    @Override
    public void save(RegisteredClient registeredClient) {
        SecurityClient securityClient = converter.convert(registeredClient);
        securityClient = SecurityClient.with(securityClient)
                .regionId(DEFAULT_SECURITY_REGION_ID) // use default regionId
                .protocol(ClientProtocol.OIDC)
                .build();
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            // Save client
            clientRepository.save(securityClient);

            // Save client authentication
            clientAuthenticationRepository.save(securityClient.getAuthentication());

            // Save client scopes
            if (!ObjectUtils.isEmpty(securityClient.getScopes())) {
                for (SecurityClientScope scope : securityClient.getScopes()) {
                    clientScopeRepository.save(scope);
                }
            }
            // Save client secret
            if (!ObjectUtils.isEmpty(securityClient.getSecrets())) {
                clientSecretRepository.save(securityClient.getSecrets().get(0));
            }
            // Save redirect uris
            if (!ObjectUtils.isEmpty(securityClient.getRedirectUris())) {
                for (SecurityClientRedirectUri redirectUri : securityClient.getRedirectUris()) {
                    clientRedirectUriRepository.save(redirectUri);
                }
            }
            this.dataSourceTransactionManager.commit(transactionStatus);
        } catch (Exception e) {
            this.dataSourceTransactionManager.rollback(transactionStatus);
            throw e;
        }
    }

    @Override
    public RegisteredClient findById(String id) {
        // TODO 将SecurityClient转换为RegisteredClient
        return null;
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        // TODO 将SecurityClient转换为RegisteredClient
        return null;
    }
}
