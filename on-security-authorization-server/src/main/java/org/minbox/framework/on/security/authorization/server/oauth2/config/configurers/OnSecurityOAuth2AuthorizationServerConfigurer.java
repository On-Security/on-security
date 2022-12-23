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

package org.minbox.framework.on.security.authorization.server.oauth2.config.configurers;

import org.minbox.framework.on.security.authorization.server.oauth2.config.configurers.support.OnSecurityOAuth2UsernamePasswordConfigurer;
import org.minbox.framework.on.security.authorization.server.oauth2.config.configurers.support.OnSecurityPreAuthorizationCodeAuthenticationConfigurer;
import org.minbox.framework.on.security.core.authorization.configurer.AbstractOnSecurityOAuth2Configurer;
import org.minbox.framework.on.security.identity.provider.config.configurers.OnSecurityIdentityProviderBrokerConfigurer;
import org.minbox.framework.on.security.identity.provider.config.configurers.support.OnSecurityIdentityProviderBrokerEndpointConfigurer;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.*;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * On-Security授权服务器{@link AbstractHttpConfigurer}实现配置
 * <p>
 * 将继承自{@link AbstractOnSecurityOAuth2Configurer}的全部子类全部进行初始化以及配置，
 * 并且将On-Security提供的身份认证器{@link AuthenticationProvider}进行注册
 *
 * @author 恒宇少年
 * @see HttpSecurity#authenticationProvider
 * @see AbstractOnSecurityOAuth2Configurer
 */
public final class OnSecurityOAuth2AuthorizationServerConfigurer extends AbstractHttpConfigurer<OnSecurityOAuth2AuthorizationServerConfigurer, HttpSecurity> {

    private Map<Class<? extends AbstractOnSecurityOAuth2Configurer>, AbstractOnSecurityOAuth2Configurer> configurers = createConfigurers();
    private RequestMatcher endpointsMatcher;
    private OAuth2AuthorizationServerConfigurer authorizationServerConfigurer;

    public OnSecurityOAuth2AuthorizationServerConfigurer() {
        this.authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
    }

    /**
     * Sets the repository of registered clients.
     *
     * @param registeredClientRepository the repository of registered clients
     * @return the {@link OnSecurityOAuth2AuthorizationServerConfigurer} for further configuration
     */
    public OnSecurityOAuth2AuthorizationServerConfigurer registeredClientRepository(RegisteredClientRepository registeredClientRepository) {
        Assert.notNull(registeredClientRepository, "registeredClientRepository cannot be null");
        this.authorizationServerConfigurer.registeredClientRepository(registeredClientRepository);
        return this;
    }

    /**
     * Sets the authorization service.
     *
     * @param authorizationService the authorization service
     * @return the {@link OnSecurityOAuth2AuthorizationServerConfigurer} for further configuration
     */
    public OnSecurityOAuth2AuthorizationServerConfigurer authorizationService(OAuth2AuthorizationService authorizationService) {
        Assert.notNull(authorizationService, "authorizationService cannot be null");
        this.authorizationServerConfigurer.authorizationService(authorizationService);
        return this;
    }

    /**
     * Sets the authorization consent service.
     *
     * @param authorizationConsentService the authorization consent service
     * @return the {@link OnSecurityOAuth2AuthorizationServerConfigurer} for further configuration
     */
    public OnSecurityOAuth2AuthorizationServerConfigurer authorizationConsentService(OAuth2AuthorizationConsentService authorizationConsentService) {
        Assert.notNull(authorizationConsentService, "authorizationConsentService cannot be null");
        this.authorizationServerConfigurer.authorizationConsentService(authorizationConsentService);
        return this;
    }

    /**
     * Sets the authorization server settings.
     *
     * @param authorizationServerSettings the authorization server settings
     * @return the {@link OnSecurityOAuth2AuthorizationServerConfigurer} for further configuration
     */
    public OnSecurityOAuth2AuthorizationServerConfigurer authorizationServerSettings(AuthorizationServerSettings authorizationServerSettings) {
        Assert.notNull(authorizationServerSettings, "authorizationServerSettings cannot be null");
        this.authorizationServerConfigurer.authorizationServerSettings(authorizationServerSettings);
        return this;
    }

    /**
     * Sets the token generator.
     *
     * @param tokenGenerator the token generator
     * @return the {@link OnSecurityOAuth2AuthorizationServerConfigurer} for further configuration
     */
    public OnSecurityOAuth2AuthorizationServerConfigurer tokenGenerator(OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {
        Assert.notNull(tokenGenerator, "tokenGenerator cannot be null");
        this.authorizationServerConfigurer.tokenGenerator(tokenGenerator);
        return this;
    }

    /**
     * Configures OAuth 2.0 Client Authentication.
     *
     * @param clientAuthenticationCustomizer the {@link Customizer} providing access to the {@link OAuth2ClientAuthenticationConfigurer}
     * @return the {@link OnSecurityOAuth2AuthorizationServerConfigurer} for further configuration
     */
    public OnSecurityOAuth2AuthorizationServerConfigurer clientAuthentication(Customizer<OAuth2ClientAuthenticationConfigurer> clientAuthenticationCustomizer) {
        this.authorizationServerConfigurer.clientAuthentication(clientAuthenticationCustomizer);
        return this;
    }

    /**
     * Configures the OAuth 2.0 Authorization Server Metadata Endpoint.
     *
     * @param authorizationServerMetadataEndpointCustomizer the {@link Customizer} providing access to the {@link OAuth2AuthorizationServerMetadataEndpointConfigurer}
     * @return the {@link OnSecurityOAuth2AuthorizationServerConfigurer} for further configuration
     */
    public OnSecurityOAuth2AuthorizationServerConfigurer authorizationServerMetadataEndpoint(Customizer<OAuth2AuthorizationServerMetadataEndpointConfigurer> authorizationServerMetadataEndpointCustomizer) {
        this.authorizationServerConfigurer.authorizationServerMetadataEndpoint(authorizationServerMetadataEndpointCustomizer);
        return this;
    }

    /**
     * Configures the OAuth 2.0 Authorization Endpoint.
     *
     * @param authorizationEndpointCustomizer the {@link Customizer} providing access to the {@link OAuth2AuthorizationEndpointConfigurer}
     * @return the {@link OnSecurityOAuth2AuthorizationServerConfigurer} for further configuration
     */
    public OnSecurityOAuth2AuthorizationServerConfigurer authorizationEndpoint(Customizer<OAuth2AuthorizationEndpointConfigurer> authorizationEndpointCustomizer) {
        this.authorizationServerConfigurer.authorizationEndpoint(authorizationEndpointCustomizer);
        return this;
    }

    /**
     * Configures the OAuth 2.0 Token Endpoint.
     *
     * @param tokenEndpointCustomizer the {@link Customizer} providing access to the {@link OAuth2TokenEndpointConfigurer}
     * @return the {@link OnSecurityOAuth2AuthorizationServerConfigurer} for further configuration
     */
    public OnSecurityOAuth2AuthorizationServerConfigurer tokenEndpoint(Customizer<OAuth2TokenEndpointConfigurer> tokenEndpointCustomizer) {
        this.authorizationServerConfigurer.tokenEndpoint(tokenEndpointCustomizer);
        return this;
    }

    /**
     * Configures the OAuth 2.0 Token Introspection Endpoint.
     *
     * @param tokenIntrospectionEndpointCustomizer the {@link Customizer} providing access to the {@link OAuth2TokenIntrospectionEndpointConfigurer}
     * @return the {@link OnSecurityOAuth2AuthorizationServerConfigurer} for further configuration
     */
    public OnSecurityOAuth2AuthorizationServerConfigurer tokenIntrospectionEndpoint(Customizer<OAuth2TokenIntrospectionEndpointConfigurer> tokenIntrospectionEndpointCustomizer) {
        this.authorizationServerConfigurer.tokenIntrospectionEndpoint(tokenIntrospectionEndpointCustomizer);
        return this;
    }

    /**
     * Configures the OAuth 2.0 Token Revocation Endpoint.
     *
     * @param tokenRevocationEndpointCustomizer the {@link Customizer} providing access to the {@link OAuth2TokenRevocationEndpointConfigurer}
     * @return the {@link OnSecurityOAuth2AuthorizationServerConfigurer} for further configuration
     */
    public OnSecurityOAuth2AuthorizationServerConfigurer tokenRevocationEndpoint(Customizer<OAuth2TokenRevocationEndpointConfigurer> tokenRevocationEndpointCustomizer) {
        this.authorizationServerConfigurer.tokenRevocationEndpoint(tokenRevocationEndpointCustomizer);
        return this;
    }

    /**
     * Configures OpenID Connect 1.0 support (disabled by default).
     *
     * @param oidcCustomizer the {@link Customizer} providing access to the {@link OidcConfigurer}
     * @return the {@link OnSecurityOAuth2AuthorizationServerConfigurer} for further configuration
     */
    public OnSecurityOAuth2AuthorizationServerConfigurer oidc(Customizer<OidcConfigurer> oidcCustomizer) {
        this.authorizationServerConfigurer.oidc(oidcCustomizer);
        return this;
    }

    /**
     * Configures Pre Authentication
     *
     * @param preAuthenticationCustomizer the {@link Customizer} providing access to the {@link OnSecurityPreAuthorizationCodeAuthenticationConfigurer}
     * @return the {@link OnSecurityOAuth2AuthorizationServerConfigurer} for further configuration
     */
    public OnSecurityOAuth2AuthorizationServerConfigurer preAuthentication(Customizer<OnSecurityPreAuthorizationCodeAuthenticationConfigurer> preAuthenticationCustomizer) {
        preAuthenticationCustomizer.customize(getConfigurer(OnSecurityPreAuthorizationCodeAuthenticationConfigurer.class));
        return this;
    }

    /**
     * 配置支持启用身份供应商（Identity Provider）
     *
     * @param identityProviderCustomizer the {@link Customizer} providing access to the {@link OnSecurityIdentityProviderBrokerEndpointConfigurer}
     * @return the {@link OnSecurityOAuth2AuthorizationServerConfigurer} for further configuration
     */
    public OnSecurityOAuth2AuthorizationServerConfigurer identityProvider(Customizer<OnSecurityIdentityProviderBrokerConfigurer> identityProviderCustomizer) {
        // @formatter:off
        OnSecurityIdentityProviderBrokerConfigurer identityProviderBrokerConfigurer =
                getConfigurer(OnSecurityIdentityProviderBrokerConfigurer.class);
        if (identityProviderBrokerConfigurer == null) {
            addConfigurer(OnSecurityIdentityProviderBrokerConfigurer.class,
                    new OnSecurityIdentityProviderBrokerConfigurer(this::postProcess));
            identityProviderBrokerConfigurer = getConfigurer(OnSecurityIdentityProviderBrokerConfigurer.class);
        }
        // @formatter:on
        identityProviderCustomizer.customize(identityProviderBrokerConfigurer);
        return this;
    }

    @Override
    public void init(HttpSecurity httpSecurity) throws Exception {
        // Apply OAuth2AuthorizationServerConfigurer
        httpSecurity.apply(this.authorizationServerConfigurer);
        List<RequestMatcher> requestMatchers = new ArrayList<>();
        requestMatchers.add(this.authorizationServerConfigurer.getEndpointsMatcher());
        this.configurers.values().forEach(configurer -> {
            configurer.init(httpSecurity);
            RequestMatcher requestMatcher = configurer.getRequestMatcher();
            if (requestMatcher != null) {
                requestMatchers.add(requestMatcher);
            }
        });
        this.endpointsMatcher = new OrRequestMatcher(requestMatchers);
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        this.configurers.values().forEach(configurer -> configurer.configure(httpSecurity));
    }

    private Map<Class<? extends AbstractOnSecurityOAuth2Configurer>, AbstractOnSecurityOAuth2Configurer> createConfigurers() {
        Map<Class<? extends AbstractOnSecurityOAuth2Configurer>, AbstractOnSecurityOAuth2Configurer> configurers = new LinkedHashMap<>();
        // @formatter:off
        // Put OnSecurityPreAuthenticationConfigurer
        configurers.put(OnSecurityPreAuthorizationCodeAuthenticationConfigurer.class,
                postProcess(new OnSecurityPreAuthorizationCodeAuthenticationConfigurer(this::postProcess)));
        // Put OnSecurityOAuth2UsernamePasswordConfigurer
        configurers.put(OnSecurityOAuth2UsernamePasswordConfigurer.class,
                postProcess(new OnSecurityOAuth2UsernamePasswordConfigurer(this::postProcess)));
        // @formatter:on
        return configurers;
    }

    @SuppressWarnings("unchecked")
    private <T> T getConfigurer(Class<T> type) {
        return (T) this.configurers.get(type);
    }

    private <T extends AbstractOnSecurityOAuth2Configurer> void addConfigurer(Class<T> configurerType, T configurer) {
        this.configurers.put(configurerType, configurer);
    }

    private <T extends AbstractOnSecurityOAuth2Configurer> RequestMatcher getRequestMatcher(Class<T> configurerType) {
        T configurer = getConfigurer(configurerType);
        return configurer != null ? configurer.getRequestMatcher() : null;
    }

    public RequestMatcher getEndpointsMatcher() {
        // Return a deferred RequestMatcher
        // since endpointsMatcher is constructed in init(HttpSecurity).
        return (request) -> this.endpointsMatcher.matches(request);
    }
}
