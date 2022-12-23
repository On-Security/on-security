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

package org.minbox.framework.on.security.identity.provider.config.configurers;

import org.minbox.framework.on.security.core.authorization.configurer.AbstractOnSecurityOAuth2Configurer;
import org.minbox.framework.on.security.identity.provider.config.configurers.support.OnSecurityIdentityProviderBrokerEndpointConfigurer;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Identity Provider身份供应商代理配置类
 * <p>
 * 本类会将{@link #createConfigurers()}方法所返回的全部{@link AbstractOnSecurityOAuth2Configurer}实例
 * 执行"#init"以及"#configure"方法，实现安全配置
 *
 * @author 恒宇少年
 * @see OnSecurityIdentityProviderBrokerEndpointConfigurer
 * @since 0.0.3
 */
public final class OnSecurityIdentityProviderBrokerConfigurer extends AbstractOnSecurityOAuth2Configurer {
    private RequestMatcher idpBrokerRequestMatcher;
    private Map<Class<? extends AbstractOnSecurityOAuth2Configurer>, AbstractOnSecurityOAuth2Configurer> configurers = createConfigurers();

    public OnSecurityIdentityProviderBrokerConfigurer(ObjectPostProcessor<Object> objectPostProcessor) {
        super(objectPostProcessor);
    }

    @Override
    public void init(HttpSecurity httpSecurity) {
        List<RequestMatcher> requestMatchers = new ArrayList<>();
        this.configurers.values().forEach(configurer -> {
            configurer.init(httpSecurity);
            RequestMatcher requestMatcher = configurer.getRequestMatcher();
            if (requestMatcher != null) {
                requestMatchers.add(requestMatcher);
            }
        });
        this.idpBrokerRequestMatcher = new OrRequestMatcher(requestMatchers);
    }

    @Override
    public void configure(HttpSecurity httpSecurity) {
        this.configurers.values().forEach(configurer -> configurer.configure(httpSecurity));
    }

    @Override
    public RequestMatcher getRequestMatcher() {
        return this.idpBrokerRequestMatcher;
    }

    private Map<Class<? extends AbstractOnSecurityOAuth2Configurer>, AbstractOnSecurityOAuth2Configurer> createConfigurers() {
        Map<Class<? extends AbstractOnSecurityOAuth2Configurer>, AbstractOnSecurityOAuth2Configurer> configurers = new LinkedHashMap<>();
        // @formatter:off
        // Put OnSecurityIdentityProviderBrokerEndpointConfigurer
        configurers.put(OnSecurityIdentityProviderBrokerEndpointConfigurer.class,
                postProcess(new OnSecurityIdentityProviderBrokerEndpointConfigurer(this::postProcess)));
        // @formatter:on
        return configurers;
    }
}
