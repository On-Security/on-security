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

package org.minbox.framework.on.security.authorization.server;

import org.minbox.framework.on.security.core.authorization.ClientProtocol;
import org.minbox.framework.on.security.core.authorization.data.client.*;
import org.minbox.framework.on.security.core.authorization.data.client.converter.RegisteredToSecurityClientConverter;
import org.minbox.framework.on.security.core.authorization.data.client.converter.SecurityToRegisteredClientConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.List;

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
public final class JdbcOnSecurityRegisteredClientRepository implements RegisteredClientRepository {
    /**
     * The name of bean in IOC
     */
    public static final String BEAN_NAME = "jdbcOnSecurityRegisteredClientRepository";
    private static final String DEFAULT_SECURITY_REGION_ID = "default";
    private DataSourceTransactionManager dataSourceTransactionManager;
    private Converter<RegisteredClient, SecurityClient> registeredToSecurityClientConverter;
    private Converter<SecurityClient, RegisteredClient> securityToRegisteredClientConverter;
    private SecurityClientRepository clientRepository;
    private SecurityClientScopeRepository clientScopeRepository;
    private SecurityClientSecretRepository clientSecretRepository;
    private SecurityClientRedirectUriRepository clientRedirectUriRepository;
    private SecurityClientAuthenticationRepository clientAuthenticationRepository;

    public JdbcOnSecurityRegisteredClientRepository(JdbcOperations jdbcOperations, DataSourceTransactionManager dataSourceTransactionManager) {
        Assert.notNull(jdbcOperations, "jdbcOperations cannot be null");
        Assert.notNull(dataSourceTransactionManager, "dataSourceTransactionManager cannot be null");
        this.dataSourceTransactionManager = dataSourceTransactionManager;
        this.registeredToSecurityClientConverter = new RegisteredToSecurityClientConverter();
        this.securityToRegisteredClientConverter = new SecurityToRegisteredClientConverter();
        this.clientRepository = new SecurityClientJdbcRepository(jdbcOperations);
        this.clientScopeRepository = new SecurityClientScopeJdbcRepository(jdbcOperations);
        this.clientSecretRepository = new SecurityClientSecretJdbcRepository(jdbcOperations);
        this.clientRedirectUriRepository = new SecurityClientRedirectUriJdbcRepository(jdbcOperations);
        this.clientAuthenticationRepository = new SecurityClientAuthenticationJdbcRepository(jdbcOperations);
    }

    @Override
    public void save(RegisteredClient registeredClient) {
        SecurityClient securityClient = registeredToSecurityClientConverter.convert(registeredClient);
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
        Assert.hasText(id, "id cannot be empty");
        // Load client
        SecurityClient securityClient = clientRepository.findById(id);
        if (securityClient == null) {
            return null;
        }
        // Build client
        securityClient = this.buildSecurityClient(securityClient);
        return securityToRegisteredClientConverter.convert(securityClient);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        Assert.hasText(clientId, "clientId cannot be empty");
        // Load client
        SecurityClient securityClient = clientRepository.findByClientId(clientId);
        if (securityClient == null) {
            return null;
        }
        // Build client
        securityClient = this.buildSecurityClient(securityClient);
        return securityToRegisteredClientConverter.convert(securityClient);
    }

    /**
     * 根据客户端基本信息构建相关数据
     *
     * @param securityClient {@link SecurityClient}
     * @return 构建填充数据后的客户端对象实例 {@link SecurityClient}
     */
    private SecurityClient buildSecurityClient(SecurityClient securityClient) {
        // Load client authentication
        SecurityClient.Builder builder = SecurityClient.with(securityClient);
        SecurityClientAuthentication clientAuthentication = clientAuthenticationRepository.findByClientId(securityClient.getId());
        Assert.notNull(clientAuthentication, "No client authentication information was retrieved based on client ID: " + securityClient.getId());
        builder.authentication(clientAuthentication);

        // Load client scopes
        List<SecurityClientScope> clientScopeList = clientScopeRepository.findByClientId(securityClient.getId());
        if (!ObjectUtils.isEmpty(clientScopeList)) {
            builder.scopes(clientScopeList);
        }

        // Load client redirect uris
        List<SecurityClientRedirectUri> redirectUris = clientRedirectUriRepository.findByClientId(securityClient.getId());
        if (!ObjectUtils.isEmpty(redirectUris)) {
            builder.redirectUris(redirectUris);
        }

        // Load client secrets
        List<SecurityClientSecret> secrets = clientSecretRepository.findByClientId(securityClient.getId());
        if (!ObjectUtils.isEmpty(secrets)) {
            builder.secrets(secrets);
        }
        return builder.build();
    }
}
