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

package org.minbox.framework.on.security.federal.identity.config.configuration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.minbox.framework.on.security.federal.identity.JdbcOnSecurityClientRegistrationRepository;
import org.minbox.framework.util.BeanUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

/**
 * OnSecurity身份提供商相关核心类注册器
 *
 * @author 恒宇少年
 * @see JdbcOnSecurityClientRegistrationRepository
 * @since 0.0.2
 */
public class OnSecurityIdentityProviderRegistrar implements ImportBeanDefinitionRegistrar {
    protected final Log logger = LogFactory.getLog(this.getClass());

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // @formatter:off
        boolean isImportClientClass = ClassUtils.isPresent(
                "org.springframework.security.oauth2.client.registration.ClientRegistrationRepository",
                OnSecurityIdentityProviderRegistrar.class.getClassLoader());
        if (isImportClientClass) {
            BeanUtils.registerInfrastructureBeanIfAbsent(registry,
                    JdbcOnSecurityClientRegistrationRepository.BEAN_NAME,
                    JdbcOnSecurityClientRegistrationRepository.class);
            logger.info("The OnSecurity JDBC ClientRegistrationRepository registration success.");
        } else {
            logger.warn("The ClientRegistrationRepository is not found in the ClassLoader, identity provider cannot be enabled.");
        }
        // @formatter:on
    }
}
