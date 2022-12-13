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

package org.minbox.framework.on.security.authorization.server.oauth2.config.configuration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.minbox.framework.on.security.authorization.server.JdbcOnSecurityOAuth2AuthorizationConsentService;
import org.minbox.framework.on.security.authorization.server.JdbcOnSecurityOAuth2AuthorizationService;
import org.minbox.framework.on.security.authorization.server.JdbcOnSecurityRegisteredClientRepository;
import org.minbox.framework.util.BeanUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * OnSecurity授权服务器相关核心类注册器
 * <p>
 * 当前注册器会被{@link OnSecurityOAuth2AuthorizationServerConfiguration}导入使用，从而实现相关核心类的自动化注册
 *
 * @author 恒宇少年
 * @see JdbcOnSecurityRegisteredClientRepository
 * @see JdbcOnSecurityOAuth2AuthorizationService
 * @see JdbcOnSecurityOAuth2AuthorizationConsentService
 * @since 0.0.1
 */
public class OnSecurityAuthorizationServerRegistrar implements ImportBeanDefinitionRegistrar {
    protected final Log logger = LogFactory.getLog(this.getClass());

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // @formatter:off
        BeanUtils.registerInfrastructureBeanIfAbsent(registry,
                JdbcOnSecurityRegisteredClientRepository.BEAN_NAME,
                JdbcOnSecurityRegisteredClientRepository.class);
        logger.info("The OnSecurity JDBC RegisteredClientRepository registration success.");

        BeanUtils.registerInfrastructureBeanIfAbsent(registry,
                JdbcOnSecurityOAuth2AuthorizationService.BEAN_NAME,
                JdbcOnSecurityOAuth2AuthorizationService.class);
        logger.info("The OnSecurity JDBC OAuth2AuthorizationService registration success.");

        BeanUtils.registerInfrastructureBeanIfAbsent(registry,
                JdbcOnSecurityOAuth2AuthorizationConsentService.BEAN_NAME,
                JdbcOnSecurityOAuth2AuthorizationConsentService.class);
        logger.info("The OnSecurity JDBC OAuth2AuthorizationConsentService registration success.");
        // @formatter:on
    }
}
