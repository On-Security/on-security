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

package org.minbox.framework.on.security.authorization.server.oauth2.authentication.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.minbox.framework.on.security.core.authorization.AbstractOnSecurityAuthenticationProvider;
import org.minbox.framework.on.security.core.authorization.exception.OnSecurityError;
import org.minbox.framework.on.security.core.authorization.exception.OnSecurityErrorCodes;
import org.minbox.framework.on.security.core.authorization.exception.OnSecurityOAuth2AuthenticationException;
import org.minbox.framework.on.security.core.authorization.util.OnSecurityThrowErrorUtils;
import org.minbox.framework.on.security.core.authorization.data.region.SecurityRegion;
import org.minbox.framework.on.security.core.authorization.data.region.SecurityRegionJdbcRepository;
import org.minbox.framework.on.security.core.authorization.data.region.SecurityRegionRepository;
import org.minbox.framework.on.security.core.authorization.data.user.*;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 基于用户名密码方式的身份验证提供者
 *
 * @author 恒宇少年
 * @see org.springframework.security.oauth2.core.AuthorizationGrantType#PASSWORD
 * @since 0.0.1
 */
public class OnSecurityOAuth2UsernamePasswordAuthenticationProvider extends AbstractOnSecurityAuthenticationProvider {
    private final Log logger = LogFactory.getLog(getClass());
    private static final OAuth2TokenType ID_TOKEN_TOKEN_TYPE = new OAuth2TokenType(OidcParameterNames.ID_TOKEN);
    private SecurityUserRepository userRepository;
    private SecurityUserAuthorizeApplicationRepository userAuthorizeClientRepository;
    private SecurityRegionRepository regionRepository;
    private PasswordEncoder passwordEncoder;
    private OAuth2AuthorizationService authorizationService;
    private UserDetailsService userDetailsService;

    public OnSecurityOAuth2UsernamePasswordAuthenticationProvider(Map<Class<?>, Object> sharedObjects) {
        super(sharedObjects);
        ApplicationContext applicationContext = (ApplicationContext) sharedObjects.get(ApplicationContext.class);
        JdbcOperations jdbcOperations = applicationContext.getBean(JdbcOperations.class);
        this.authorizationService = applicationContext.getBean(OAuth2AuthorizationService.class);
        this.userDetailsService = applicationContext.getBean(UserDetailsService.class);
        this.userRepository = new SecurityUserJdbcRepository(jdbcOperations);
        this.userAuthorizeClientRepository = new SecurityUserAuthorizeApplicationJdbcRepository(jdbcOperations);
        this.regionRepository = new SecurityRegionJdbcRepository(jdbcOperations);
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OAuth2TokenGenerator tokenGenerator = getSharedObject(OAuth2TokenGenerator.class);
        OnSecurityOAuth2UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                (OnSecurityOAuth2UsernamePasswordAuthenticationToken) authentication;
        RegisteredClient registeredClient = usernamePasswordAuthenticationToken.getRegisteredClient();
        String applicationId = registeredClient.getId();
        try {
            if (ObjectUtils.isEmpty(registeredClient.getAuthorizationGrantTypes()) ||
                    !registeredClient.getAuthorizationGrantTypes().contains(AuthorizationGrantType.PASSWORD)) {
                // @formatter:off
                OnSecurityThrowErrorUtils.throwError(OnSecurityErrorCodes.UNSUPPORTED_GRANT_TYPE,
                        OAuth2ParameterNames.GRANT_TYPE,
                        "Unauthorized grant_type : " + AuthorizationGrantType.PASSWORD.getValue());
                // @formatter:on
            }
            SecurityUser securityUser = userRepository.findByUsername(usernamePasswordAuthenticationToken.getUsername());
            if (securityUser == null || !securityUser.isEnabled() || securityUser.isDeleted()) {
                // @formatter:off
                OnSecurityThrowErrorUtils.throwError(OnSecurityErrorCodes.INVALID_USER,
                        OAuth2ParameterNames.USERNAME,
                        "Username: " + usernamePasswordAuthenticationToken.getUsername() + ", no valid user found.");
                // @formatter:on
            }
            List<SecurityUserAuthorizeApplication> userAuthorizeClientList = userAuthorizeClientRepository.findByUserId(securityUser.getId());
            if (ObjectUtils.isEmpty(userAuthorizeClientList)) {
                // @formatter:off
                OnSecurityThrowErrorUtils.throwError(OnSecurityErrorCodes.UNAUTHORIZED_CLIENT,
                        OAuth2ParameterNames.CLIENT_ID,
                        "Username: " + usernamePasswordAuthenticationToken.getUsername() +
                                ", did not authorize client: " + applicationId + ".");
                // @formatter:on
            }
            Set<String> userAuthorizeClientIdSet = userAuthorizeClientList.stream().map(SecurityUserAuthorizeApplication::getApplicationId).collect(Collectors.toSet());
            if (!userAuthorizeClientIdSet.contains(applicationId)) {
                // @formatter:off
                OnSecurityThrowErrorUtils.throwError(OnSecurityErrorCodes.UNAUTHORIZED_CLIENT,
                        OAuth2ParameterNames.CLIENT_ID,
                        "Username: " + usernamePasswordAuthenticationToken.getUsername() +
                                ", did not authorize client: " + applicationId + ".");
                // @formatter:on
            }
            SecurityRegion securityRegion = regionRepository.findById(securityUser.getRegionId());
            if (securityRegion == null || !securityRegion.isEnabled() || securityRegion.isDeleted()) {
                //@formatter:off
                OnSecurityThrowErrorUtils.throwError(OnSecurityErrorCodes.INVALID_REGION,
                        null,
                        "Invalid Region：" + (securityRegion == null ? securityUser.getRegionId() : securityRegion.getRegionId()) +
                                "，Please check data validity.");
                // @formatter:on
            }
            if (!this.passwordEncoder.matches(usernamePasswordAuthenticationToken.getPassword(), securityUser.getPassword())) {
                // @formatter:off
                OnSecurityThrowErrorUtils.throwError(OnSecurityErrorCodes.AUTHENTICATION_FAILED,
                        OAuth2ParameterNames.PASSWORD,
                        "Username: " + usernamePasswordAuthenticationToken.getUsername() + ", password authentication failed.");
                // @formatter:on
            }

            UserDetails userDetails = userDetailsService.loadUserByUsername(securityUser.getUsername());
            usernamePasswordAuthenticationToken.setUserDetails(userDetails);
            SecurityContext securityContext = SecurityContextHolder.getContext();
            OAuth2ClientAuthenticationToken clientAuthenticationToken = (OAuth2ClientAuthenticationToken) securityContext.getAuthentication();
            UsernamePasswordAuthenticationToken principalAuthentication = new UsernamePasswordAuthenticationToken(userDetails, null);
            // @formatter:off
            DefaultOAuth2TokenContext.Builder tokenContextBuilder = DefaultOAuth2TokenContext.builder()
                    .registeredClient(registeredClient)
                    .principal(principalAuthentication)
                    .authorizationServerContext(AuthorizationServerContextHolder.getContext())
                    .authorizationGrant(clientAuthenticationToken)
                    .authorizedScopes(registeredClient.getScopes())
                    .authorizationGrantType(AuthorizationGrantType.PASSWORD);
            // @formatter:on

            // @formatter:off
            OAuth2Authorization.Builder authorizationBuilder = OAuth2Authorization
                    .withRegisteredClient(registeredClient)
                    .id(UUID.randomUUID().toString())
                    .principalName(usernamePasswordAuthenticationToken.getUsername())
                    .authorizedScopes(registeredClient.getScopes())
                    .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                    .attribute(Principal.class.getName(), principalAuthentication);
            // @formatter:on

            // ----- Access token -----
            OAuth2TokenContext tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.ACCESS_TOKEN).build();
            OAuth2Token generatedAccessToken = tokenGenerator.generate(tokenContext);
            if (generatedAccessToken == null) {
                // @formatter:off
                OnSecurityError onSecurityError = new OnSecurityError(OnSecurityErrorCodes.UNKNOWN_EXCEPTION.getValue(),
                        null,
                        "The token generator failed to generate the access token.",
                        OnSecurityThrowErrorUtils.DEFAULT_HELP_URI);
                // @formatter:on
                throw new OnSecurityOAuth2AuthenticationException(onSecurityError);
            }

            if (this.logger.isTraceEnabled()) {
                this.logger.trace("Generated access token");
            }

            OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
                    generatedAccessToken.getTokenValue(), generatedAccessToken.getIssuedAt(),
                    generatedAccessToken.getExpiresAt(), tokenContext.getAuthorizedScopes());
            if (generatedAccessToken instanceof ClaimAccessor) {
                authorizationBuilder.token(accessToken, (metadata) ->
                        metadata.put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME, ((ClaimAccessor) generatedAccessToken).getClaims()));
            } else {
                authorizationBuilder.accessToken(accessToken);
            }

            // ----- Refresh token -----
            OAuth2RefreshToken refreshToken = null;
            if (registeredClient.getAuthorizationGrantTypes().contains(AuthorizationGrantType.REFRESH_TOKEN) &&
                    // Do not issue refresh token to public client
                    !registeredClient.getClientAuthenticationMethods().contains(ClientAuthenticationMethod.NONE)) {

                tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.REFRESH_TOKEN).build();
                OAuth2Token generatedRefreshToken = tokenGenerator.generate(tokenContext);
                if (!(generatedRefreshToken instanceof OAuth2RefreshToken)) {
                    // @formatter:off
                    OnSecurityError onSecurityError = new OnSecurityError(OnSecurityErrorCodes.UNKNOWN_EXCEPTION.getValue(),
                            null,
                            "The token generator failed to generate the refresh token.",
                            OnSecurityThrowErrorUtils.DEFAULT_HELP_URI);
                    // @formatter:on
                    throw new OnSecurityOAuth2AuthenticationException(onSecurityError);
                }

                if (this.logger.isTraceEnabled()) {
                    this.logger.trace("Generated refresh token");
                }

                refreshToken = (OAuth2RefreshToken) generatedRefreshToken;
                authorizationBuilder.refreshToken(refreshToken);
            }

            // ----- ID token -----
            OidcIdToken idToken;
            if (registeredClient.getScopes().contains(OidcScopes.OPENID)) {
                // @formatter:off
                tokenContext = tokenContextBuilder
                        .tokenType(ID_TOKEN_TOKEN_TYPE)
                        .authorization(authorizationBuilder.build())	// ID token customizer may need access to the access token and/or refresh token
                        .build();
                // @formatter:on
                OAuth2Token generatedIdToken = tokenGenerator.generate(tokenContext);
                if (!(generatedIdToken instanceof Jwt)) {
                    // @formatter:off
                    OnSecurityError onSecurityError = new OnSecurityError(OnSecurityErrorCodes.UNKNOWN_EXCEPTION.getValue(),
                            null,
                            "The token generator failed to generate the ID token.",
                            OnSecurityThrowErrorUtils.DEFAULT_HELP_URI);
                    // @formatter:on
                    throw new OnSecurityOAuth2AuthenticationException(onSecurityError);
                }

                if (this.logger.isTraceEnabled()) {
                    this.logger.trace("Generated id token");
                }

                idToken = new OidcIdToken(generatedIdToken.getTokenValue(), generatedIdToken.getIssuedAt(),
                        generatedIdToken.getExpiresAt(), ((Jwt) generatedIdToken).getClaims());
                authorizationBuilder.token(idToken, (metadata) ->
                        metadata.put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME, idToken.getClaims()));
            } else {
                idToken = null;
            }

            OAuth2Authorization authorization = authorizationBuilder.build();

            this.authorizationService.save(authorization);

            if (this.logger.isTraceEnabled()) {
                this.logger.trace("Saved authorization");
            }

            if (this.logger.isTraceEnabled()) {
                this.logger.trace("Authenticated token request");
            }

            OAuth2AccessTokenAuthenticationToken accessTokenAuthenticationToken = new OAuth2AccessTokenAuthenticationToken(
                    registeredClient, usernamePasswordAuthenticationToken, accessToken, refreshToken, clientAuthenticationToken.getAdditionalParameters());
            accessTokenAuthenticationToken.setAuthenticated(clientAuthenticationToken.isAuthenticated());
            return accessTokenAuthenticationToken;

        } catch (OnSecurityOAuth2AuthenticationException oae) {
            throw oae;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            // @formatter:off
            OnSecurityError onSecurityError = new OnSecurityError(OnSecurityErrorCodes.UNKNOWN_EXCEPTION.getValue(),
                    null,
                    "Authentication encountered an unknown exception.",
                    OnSecurityThrowErrorUtils.DEFAULT_HELP_URI);
            // @formatter:on
            throw new OnSecurityOAuth2AuthenticationException(onSecurityError);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OnSecurityOAuth2UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    /**
     * Sets the {@link PasswordEncoder} used to validate
     * the {@link RegisteredClient#getClientSecret()}.
     * If not set, the client secret will be compared using
     * {@link PasswordEncoderFactories#createDelegatingPasswordEncoder()}.
     *
     * @param passwordEncoder the {@link PasswordEncoder} used to validate the client secret
     */
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");
        this.passwordEncoder = passwordEncoder;
    }
}
