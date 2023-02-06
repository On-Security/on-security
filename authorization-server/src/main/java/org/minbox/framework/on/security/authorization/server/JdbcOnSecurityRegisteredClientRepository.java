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
import org.minbox.framework.on.security.core.authorization.data.application.*;
import org.minbox.framework.on.security.core.authorization.data.application.converter.RegisteredToSecurityApplicationConverter;
import org.minbox.framework.on.security.core.authorization.data.application.converter.SecurityApplicationToRegisteredClientConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * On-Security提供的{@link RegisteredClientRepository}JDBC数据存储方式实现类
 * <p>
 * 根据SpringSecurity授权服务器所维护的{@link RegisteredClient}客户端信息转换并
 * 维护On-Security授权服务器所维护的{@link SecurityApplication}客户端数据
 *
 * @author 恒宇少年
 * @see RegisteredClientRepository
 * @see RegisteredClient
 * @see SecurityApplication
 * @see JdbcOperations
 * @since 0.0.1
 */
public final class JdbcOnSecurityRegisteredClientRepository implements RegisteredClientRepository {
    /**
     * The name of bean in IOC
     */
    public static final String BEAN_NAME = "jdbcOnSecurityRegisteredClientRepository";
    private static final String DEFAULT_SECURITY_REGION_ID = "default";
    private PlatformTransactionManager platformTransactionManager;
    private Converter<RegisteredClient, SecurityApplication> registeredToSecurityClientConverter;
    private Converter<SecurityApplication, RegisteredClient> securityToRegisteredClientConverter;
    private SecurityApplicationRepository clientRepository;
    private SecurityApplicationScopeRepository clientScopeRepository;
    private SecurityApplicationSecretRepository clientSecretRepository;
    private SecurityApplicationRedirectUriRepository clientRedirectUriRepository;
    private SecurityApplicationAuthenticationRepository clientAuthenticationRepository;

    public JdbcOnSecurityRegisteredClientRepository(JdbcOperations jdbcOperations, PlatformTransactionManager platformTransactionManager) {
        Assert.notNull(jdbcOperations, "jdbcOperations cannot be null");
        Assert.notNull(platformTransactionManager, "platformTransactionManager cannot be null");
        this.platformTransactionManager = platformTransactionManager;
        this.registeredToSecurityClientConverter = new RegisteredToSecurityApplicationConverter();
        this.securityToRegisteredClientConverter = new SecurityApplicationToRegisteredClientConverter();
        this.clientRepository = new SecurityApplicationJdbcRepository(jdbcOperations);
        this.clientScopeRepository = new SecurityApplicationScopeJdbcRepository(jdbcOperations);
        this.clientSecretRepository = new SecurityApplicationSecretJdbcRepository(jdbcOperations);
        this.clientRedirectUriRepository = new SecurityApplicationRedirectUriJdbcRepository(jdbcOperations);
        this.clientAuthenticationRepository = new SecurityApplicationAuthenticationJdbcRepository(jdbcOperations);
    }

    @Override
    public void save(RegisteredClient registeredClient) {
        SecurityApplication securityApplication = registeredToSecurityClientConverter.convert(registeredClient);
        securityApplication = SecurityApplication.with(securityApplication)
                .regionId(DEFAULT_SECURITY_REGION_ID) // use default regionId
                .protocol(ClientProtocol.OpenID_Connect_1_0)
                .build();
        TransactionStatus transactionStatus = this.platformTransactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            // Save client
            clientRepository.save(securityApplication);

            // Save client authentication
            clientAuthenticationRepository.save(securityApplication.getAuthentication());

            // Save client scopes
            if (!ObjectUtils.isEmpty(securityApplication.getScopes())) {
                for (SecurityApplicationScope scope : securityApplication.getScopes()) {
                    clientScopeRepository.save(scope);
                }
            }
            // Save client secret
            if (!ObjectUtils.isEmpty(securityApplication.getSecrets())) {
                clientSecretRepository.save(securityApplication.getSecrets().get(0));
            }
            // Save redirect uris
            if (!ObjectUtils.isEmpty(securityApplication.getRedirectUris())) {
                for (SecurityApplicationRedirectUri redirectUri : securityApplication.getRedirectUris()) {
                    clientRedirectUriRepository.save(redirectUri);
                }
            }
            this.platformTransactionManager.commit(transactionStatus);
        } catch (Exception e) {
            this.platformTransactionManager.rollback(transactionStatus);
            throw e;
        }
    }

    @Override
    public RegisteredClient findById(String id) {
        Assert.hasText(id, "id cannot be empty");
        // Load client
        SecurityApplication securityApplication = clientRepository.findById(id);
        if (securityApplication == null) {
            return null;
        }
        // Build client
        securityApplication = this.buildSecurityClient(securityApplication);
        return securityToRegisteredClientConverter.convert(securityApplication);
    }

    @Override
    public RegisteredClient findByClientId(String applicationId) {
        Assert.hasText(applicationId, "applicationId cannot be empty");
        // Load client
        SecurityApplication securityApplication = clientRepository.findByClientId(applicationId);
        if (securityApplication == null) {
            return null;
        }
        // Build client
        securityApplication = this.buildSecurityClient(securityApplication);
        return securityToRegisteredClientConverter.convert(securityApplication);
    }

    /**
     * 根据客户端基本信息构建相关数据
     *
     * @param securityApplication {@link SecurityApplication}
     * @return 构建填充数据后的客户端对象实例 {@link SecurityApplication}
     */
    private SecurityApplication buildSecurityClient(SecurityApplication securityApplication) {
        // Load client authentication
        SecurityApplication.Builder builder = SecurityApplication.with(securityApplication);
        SecurityApplicationAuthentication clientAuthentication = clientAuthenticationRepository.findByClientId(securityApplication.getId());
        Assert.notNull(clientAuthentication, "No client authentication information was retrieved based on client ID: " + securityApplication.getId());
        builder.authentication(clientAuthentication);

        // Load client scopes
        List<SecurityApplicationScope> clientScopeList = clientScopeRepository.findByClientId(securityApplication.getId());
        if (!ObjectUtils.isEmpty(clientScopeList)) {
            builder.scopes(clientScopeList);
        }

        // Load client redirect uris
        List<SecurityApplicationRedirectUri> redirectUris = clientRedirectUriRepository.findByClientId(securityApplication.getId());
        if (!ObjectUtils.isEmpty(redirectUris)) {
            builder.redirectUris(redirectUris);
        }

        // Load client secrets
        List<SecurityApplicationSecret> secrets = clientSecretRepository.findByClientId(securityApplication.getId());
        if (!ObjectUtils.isEmpty(secrets)) {
            builder.secrets(secrets);
        }
        return builder.build();
    }
}
