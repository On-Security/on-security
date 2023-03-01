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

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.minbox.framework.on.security.authorization.server.jose.Jwks;
import org.minbox.framework.on.security.authorization.server.oauth2.authentication.token.OnSecurityDelegatingOAuth2TokenGenerator;
import org.minbox.framework.on.security.authorization.server.oauth2.authentication.token.customizer.OnSecurityIdentityProviderIdTokenCustomizer;
import org.minbox.framework.on.security.authorization.server.oauth2.authentication.token.customizer.OnSecurityUserAuthorizeAttributeJwtClaimsCustomizer;
import org.minbox.framework.on.security.authorization.server.oauth2.authentication.token.customizer.OnSecurityUserBindGroupJwtClaimsCustomizer;
import org.minbox.framework.on.security.authorization.server.oauth2.config.configurers.OnSecurityOAuth2AuthorizationServerConfigurer;
import org.minbox.framework.on.security.core.authorization.OnSecurityDefaultAuthenticationFailureHandler;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Arrays;

/**
 * OnSecurity授权认证服务器默认配置类
 * <p>
 * 继承该类后可快速搭建OnSecurity授权认证服务器服务，
 * 如需修改默认的配置可重写{@link #defaultOnSecurityAuthorizationServer(HttpSecurity)}方法
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
@Configuration
@Import(OnSecurityAuthorizationServerRegistrar.class)
public class OnSecurityOAuth2AuthorizationServerConfiguration {
    private static final String DEFAULT_LOGIN_URL = "/login";

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain onSecurityAuthorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        this.defaultOnSecurityAuthorizationServer(http);
        return http.build();
    }

    /**
     * 默认的OnSecurity授权认证服务器配置
     * <p>
     * 如果需要自定义，则可以重写该方法
     *
     * @param http {@link HttpSecurity}
     * @throws Exception
     */
    protected void defaultOnSecurityAuthorizationServer(HttpSecurity http) throws Exception {
        OnSecurityOAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OnSecurityOAuth2AuthorizationServerConfigurer();
        // Configure default authentication failure handler
        AuthenticationFailureHandler onSecurityFailureHandler = new OnSecurityDefaultAuthenticationFailureHandler();
        // @formatter:off
        authorizationServerConfigurer
                .authorizationEndpoint(config -> config.errorResponseHandler(onSecurityFailureHandler))
                .tokenEndpoint(config -> config.errorResponseHandler(onSecurityFailureHandler))
                .tokenIntrospectionEndpoint(config -> config.errorResponseHandler(onSecurityFailureHandler))
                .tokenRevocationEndpoint(config -> config.errorResponseHandler(onSecurityFailureHandler))
                .clientAuthentication(config->config.errorResponseHandler(onSecurityFailureHandler))
                // Enable OpenID Connect 1.0
                .oidc(Customizer.withDefaults());
        RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();
        http.requestMatcher(endpointsMatcher)
                .authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
                .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
                .apply(authorizationServerConfigurer)
                .and()
                .exceptionHandling(exceptions -> exceptions.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint(DEFAULT_LOGIN_URL)))
                // Enable resource server using jwt encryption
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
        // @formatter:on
    }

    /**
     * 配置OnSecurity代理的令牌生成器
     *
     * @param jwkSource {@link JWKSource}
     * @return {@link OnSecurityDelegatingOAuth2TokenGenerator}
     */
    @Bean
    public OAuth2TokenGenerator onSecurityTokenGenerator(JWKSource<SecurityContext> jwkSource, ApplicationContext context) {
        OnSecurityDelegatingOAuth2TokenGenerator.Builder builder = OnSecurityDelegatingOAuth2TokenGenerator.withJWKSource(jwkSource);
        OAuth2TokenCustomizer<OAuth2TokenClaimsContext> accessTokenCustomizer = this.getAccessTokenCustomizer(context);
        if (accessTokenCustomizer != null) {
            builder.setAccessTokenCustomizer(accessTokenCustomizer);
        }
        // @formatter:off
        JdbcOperations jdbcOperations = context.getBean(JdbcOperations.class);
        builder.setJwtCustomizers(Arrays.asList(
                new OnSecurityIdentityProviderIdTokenCustomizer(),
                new OnSecurityUserAuthorizeAttributeJwtClaimsCustomizer(jdbcOperations),
                new OnSecurityUserBindGroupJwtClaimsCustomizer(jdbcOperations)
        ));
        // @formatter:on
        return builder.build();
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        return this.defaultJwkSource();
    }

    /**
     * 默认的{@link JWKSource}
     * <p>
     * 如果需要自定义则可以重写该方法
     *
     * @return {@link JWKSource}
     */
    protected JWKSource<SecurityContext> defaultJwkSource() {
        RSAKey rsaKey = Jwks.generateRsa();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return this.defaultJwtDecoder(jwkSource);
    }

    /**
     * 默认的{@link JwtDecoder}
     * <p>
     * 如果需要自定义则可以重写该方法
     *
     * @param jwkSource {@link JWKSource}
     * @return {@link JwtDecoder}
     */
    protected JwtDecoder defaultJwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return this.defaultAuthorizationServerSettings();
    }

    /**
     * 默认的授权服务器配置 {@link AuthorizationServerSettings}
     * <p>
     * 如果需要自定义则可以重写该方法
     *
     * @return {@link AuthorizationServerSettings}
     */
    protected AuthorizationServerSettings defaultAuthorizationServerSettings() {
        return OnSecurityAuthorizationServerSettingsBuilder.build();
    }

    private OAuth2TokenCustomizer<OAuth2TokenClaimsContext> getAccessTokenCustomizer(ApplicationContext context) {
        ResolvableType type = ResolvableType.forClassWithGenerics(OAuth2TokenCustomizer.class, OAuth2TokenClaimsContext.class);
        return getOptionalBean(context, type);
    }

    private <T> T getOptionalBean(ApplicationContext context, ResolvableType type) {
        String[] names = context.getBeanNamesForType(type);
        if (names.length > 1) {
            throw new NoUniqueBeanDefinitionException(type, names);
        }
        return names.length == 1 ? (T) context.getBean(names[0]) : null;
    }

    /**
     * {@link AuthorizationServerSettings}自定义构建器
     */
    private static class OnSecurityAuthorizationServerSettingsBuilder {
        private static final String ON_SECURITY_PREFIX = "/on-security";
        private static final String ON_SECURITY_TOKEN_ENDPOINT = ON_SECURITY_PREFIX + "/token";
        private static final String ON_SECURITY_AUTHORIZE_ENDPOINT = ON_SECURITY_PREFIX + "/authorize";
        private static final String ON_SECURITY_JWKS_ENDPOINT = ON_SECURITY_PREFIX + "/jwks";
        private static final String ON_SECURITY_REVOKE_ENDPOINT = ON_SECURITY_PREFIX + "/revoke";
        private static final String ON_SECURITY_INTROSPECT_ENDPOINT = ON_SECURITY_PREFIX + "/introspect";
        private static final String ON_SECURITY_OIDC_CONNECT_REGISTER_ENDPOINT = ON_SECURITY_PREFIX + "/connect/register";
        private static final String ON_SECURITY_OIDC_USERINFO_ENDPOINT = ON_SECURITY_PREFIX + "/userinfo";

        public static AuthorizationServerSettings build() {
            // @formatter:off
            return AuthorizationServerSettings.builder()
                    .tokenEndpoint(ON_SECURITY_TOKEN_ENDPOINT)
                    .authorizationEndpoint(ON_SECURITY_AUTHORIZE_ENDPOINT)
                    .jwkSetEndpoint(ON_SECURITY_JWKS_ENDPOINT)
                    .tokenRevocationEndpoint(ON_SECURITY_REVOKE_ENDPOINT)
                    .tokenIntrospectionEndpoint(ON_SECURITY_INTROSPECT_ENDPOINT)
                    .oidcClientRegistrationEndpoint(ON_SECURITY_OIDC_CONNECT_REGISTER_ENDPOINT)
                    .oidcUserInfoEndpoint(ON_SECURITY_OIDC_USERINFO_ENDPOINT)
                    .build();
            // @formatter:on
        }
    }
}
