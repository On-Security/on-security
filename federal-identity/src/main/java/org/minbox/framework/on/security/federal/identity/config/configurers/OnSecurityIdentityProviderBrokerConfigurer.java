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

package org.minbox.framework.on.security.federal.identity.config.configurers;

import org.minbox.framework.on.security.core.authorization.configurer.AbstractOnSecurityOAuth2Configurer;
import org.minbox.framework.on.security.federal.identity.authentication.OnSecurityIdentityProviderAuthenticationEntryPoint;
import org.minbox.framework.on.security.federal.identity.authentication.OnSecurityIdentityProviderAuthenticationSuccessHandler;
import org.minbox.framework.on.security.federal.identity.config.configurers.support.OnSecurityIdentityProviderBrokerEndpointConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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
public final class OnSecurityIdentityProviderBrokerConfigurer extends AbstractHttpConfigurer<OnSecurityIdentityProviderBrokerConfigurer, HttpSecurity> {
    /**
     * IdP认证回调地址前缀
     */
    private static final String BROKER_CALLBACK_PREFIX = "/identity/broker/callback/**";
    private static final String IDENTITY_PROVIDER_LOGIN_URL = "/login";
    private RequestMatcher idpBrokerRequestMatcher;
    private Map<Class<? extends AbstractOnSecurityOAuth2Configurer>, AbstractOnSecurityOAuth2Configurer> configurers = createConfigurers();

    @Override
    public void init(HttpSecurity httpSecurity) throws Exception {
        List<RequestMatcher> requestMatchers = new ArrayList<>();
        this.configurers.values().forEach(configurer -> {
            configurer.init(httpSecurity);
            RequestMatcher requestMatcher = configurer.getRequestMatcher();
            if (requestMatcher != null) {
                requestMatchers.add(requestMatcher);
            }
        });
        OnSecurityIdentityProviderAuthenticationSuccessHandler identityProviderAuthenticationSuccessHandler =
                new OnSecurityIdentityProviderAuthenticationSuccessHandler();
        OnSecurityIdentityProviderAuthenticationEntryPoint identityProviderAuthenticationEntryPoint =
                new OnSecurityIdentityProviderAuthenticationEntryPoint(IDENTITY_PROVIDER_LOGIN_URL);
        httpSecurity
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(identityProviderAuthenticationEntryPoint))
                .oauth2Login(oauth2Login ->
                        oauth2Login.successHandler(identityProviderAuthenticationSuccessHandler)
                                //.failureHandler()
                                .loginProcessingUrl(BROKER_CALLBACK_PREFIX));
        this.idpBrokerRequestMatcher = new OrRequestMatcher(requestMatchers);
    }

    @Override
    public void configure(HttpSecurity httpSecurity) {
        this.configurers.values().forEach(configurer -> configurer.configure(httpSecurity));
    }


    public RequestMatcher getRequestMatcher() {
        // Return a deferred RequestMatcher
        // since endpointsMatcher is constructed in init(HttpSecurity).
        return (request) -> this.idpBrokerRequestMatcher.matches(request);
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
