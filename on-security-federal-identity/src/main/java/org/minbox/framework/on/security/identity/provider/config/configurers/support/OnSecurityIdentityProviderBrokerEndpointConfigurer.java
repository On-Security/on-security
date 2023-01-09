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

package org.minbox.framework.on.security.identity.provider.config.configurers.support;

import org.minbox.framework.on.security.core.authorization.configurer.AbstractOnSecurityOAuth2Configurer;
import org.minbox.framework.on.security.core.authorization.util.HttpSecuritySharedObjectUtils;
import org.minbox.framework.on.security.identity.provider.authentication.OnSecurityIdentityProviderBrokerEndpointAuthenticationProvider;
import org.minbox.framework.on.security.identity.provider.web.OnSecurityIdentityProviderBrokerEndpointFilter;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * 身份供应商（Identity Provider）代理认证地址转发配置
 *
 * @author 恒宇少年
 * @since 0.0.3
 */
public final class OnSecurityIdentityProviderBrokerEndpointConfigurer extends AbstractOnSecurityOAuth2Configurer {
    private static final String BROKER_ENDPOINT_PREFIX = "/identity/broker/endpoint";
    private static final String BROKER_ENDPOINT_PREFIX_PATTERN = BROKER_ENDPOINT_PREFIX + "/**";

    private RequestMatcher requestMatcher;

    public OnSecurityIdentityProviderBrokerEndpointConfigurer(ObjectPostProcessor<Object> objectPostProcessor) {
        super(objectPostProcessor);
    }

    /**
     * 初始化身份供应商"Endpoint"端点
     * <p>
     * 将{@link OnSecurityIdentityProviderBrokerEndpointAuthenticationProvider}注册
     *
     * @param httpSecurity {@link HttpSecurity}
     */
    @Override
    public void init(HttpSecurity httpSecurity) {
        this.requestMatcher = new AntPathRequestMatcher(BROKER_ENDPOINT_PREFIX_PATTERN, HttpMethod.GET.name());
        OnSecurityIdentityProviderBrokerEndpointAuthenticationProvider identityProviderBrokerEndpointAuthenticationProvider
                = new OnSecurityIdentityProviderBrokerEndpointAuthenticationProvider(httpSecurity.getSharedObjects());
        httpSecurity.authenticationProvider(identityProviderBrokerEndpointAuthenticationProvider);
    }

    /**
     * 配置身份供应商"Endpoint"端点
     * <p>
     * 将{@link OnSecurityIdentityProviderBrokerEndpointFilter}代理转发的过滤器注册
     *
     * @param httpSecurity {@link HttpSecurity}
     */
    @Override
    public void configure(HttpSecurity httpSecurity) {
        ApplicationContext applicationContext = HttpSecuritySharedObjectUtils.getApplicationContext(httpSecurity);
        ClientRegistrationRepository clientRegistrationRepository = applicationContext.getBean(ClientRegistrationRepository.class);
        AuthenticationManager authenticationManager = HttpSecuritySharedObjectUtils.getAuthenticationManager(httpSecurity);
        OnSecurityIdentityProviderBrokerEndpointFilter identityProviderBrokerEndpointRedirectFilter =
                new OnSecurityIdentityProviderBrokerEndpointFilter(authenticationManager, clientRegistrationRepository, BROKER_ENDPOINT_PREFIX);
        httpSecurity.addFilterBefore(postProcess(identityProviderBrokerEndpointRedirectFilter), OAuth2AuthorizationRequestRedirectFilter.class);
    }

    @Override
    public RequestMatcher getRequestMatcher() {
        return this.requestMatcher;
    }
}
