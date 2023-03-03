/*
 *   Copyright (C) 2022  恒宇少年
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.minbox.framework.on.security.authorization.server.oauth2.config.configuration;

import org.minbox.framework.on.security.core.authorization.OnSecurityClaimNames;
import org.minbox.framework.on.security.core.authorization.data.user.SecurityUser;
import org.minbox.framework.on.security.core.authorization.data.user.SecurityUserJdbcRepository;
import org.minbox.framework.on.security.core.authorization.data.user.SecurityUserRepository;
import org.minbox.framework.on.security.core.authorization.endpoint.OnSecurityEndpoints;
import org.minbox.framework.on.security.core.authorization.jackson2.OnSecurityJsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationContext;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.function.Function;

/**
 * Oidc协议用户信息端点{@link OnSecurityEndpoints#OIDC_ME_ENDPOINT}响应数据映射器
 *
 * @author 恒宇少年
 * @since 0.1.0
 */
public class OnSecurityOidcUserInfoMapper implements Function<OidcUserInfoAuthenticationContext, OidcUserInfo> {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(OnSecurityOidcUserInfoMapper.class);
    // @formatter:off
    private static final List<String> EMAIL_CLAIMS = Arrays.asList(
            StandardClaimNames.EMAIL,
            StandardClaimNames.EMAIL_VERIFIED
    );
    private static final List<String> PHONE_CLAIMS = Arrays.asList(
            StandardClaimNames.PHONE_NUMBER,
            StandardClaimNames.PHONE_NUMBER_VERIFIED
    );
    private static final List<String> PROFILE_CLAIMS = Arrays.asList(
            StandardClaimNames.NAME,
            StandardClaimNames.FAMILY_NAME,
            StandardClaimNames.GIVEN_NAME,
            StandardClaimNames.MIDDLE_NAME,
            StandardClaimNames.NICKNAME,
            StandardClaimNames.PREFERRED_USERNAME,
            StandardClaimNames.PROFILE,
            StandardClaimNames.PICTURE,
            StandardClaimNames.WEBSITE,
            StandardClaimNames.GENDER,
            StandardClaimNames.BIRTHDATE,
            StandardClaimNames.ZONEINFO,
            StandardClaimNames.LOCALE,
            StandardClaimNames.UPDATED_AT
    );
    private static final List<String> ON_SECURITY_CLAIMS = Arrays.asList(
            StandardClaimNames.EMAIL,
            StandardClaimNames.NICKNAME,
            StandardClaimNames.GENDER,
            OnSecurityClaimNames.PHONE,
            OnSecurityClaimNames.BIRTH_DAY,
            OnSecurityClaimNames.ZIP_CODE,
            OnSecurityClaimNames.EXPAND
    );
    // @formatter:on
    private JdbcOperations jdbcOperations;
    private SecurityUserRepository userRepository;
    private OnSecurityJsonMapper jsonMapper;

    public OnSecurityOidcUserInfoMapper(ApplicationContext applicationContext) {
        this.jdbcOperations = applicationContext.getBean(JdbcOperations.class);
        this.userRepository = new SecurityUserJdbcRepository(jdbcOperations);
        this.jsonMapper = new OnSecurityJsonMapper();
    }

    @Override
    public OidcUserInfo apply(OidcUserInfoAuthenticationContext authenticationContext) {
        OAuth2Authorization authorization = authenticationContext.getAuthorization();
        OidcIdToken idToken = authorization.getToken(OidcIdToken.class).getToken();
        OAuth2AccessToken accessToken = authenticationContext.getAccessToken();
        Map<String, Object> claims = getClaimsRequestedByScope(idToken.getClaims(), accessToken.getScopes());
        // addition security user claims
        SecurityUser securityUser = userRepository.findByUsername(authorization.getPrincipalName());
        if (securityUser != null) {
            this.putUserAdditionClaims(claims, securityUser);
        }
        return new OidcUserInfo(claims);
    }

    private void putUserAdditionClaims(Map<String, Object> claims, SecurityUser securityUser) {
        try {
            String userJsonString = this.jsonMapper.writeValueAsString(securityUser);
            Map userMap = this.jsonMapper.readValue(userJsonString, Map.class);
            ON_SECURITY_CLAIMS.stream().forEach(additionClaims -> {
                Object claimsValue = userMap.get(additionClaims);
                if (!ObjectUtils.isEmpty(claimsValue)) {
                    claims.put(additionClaims, claimsValue);
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private static Map<String, Object> getClaimsRequestedByScope(Map<String, Object> claims, Set<String> requestedScopes) {
        Set<String> scopeRequestedClaimNames = new HashSet<>(32);
        scopeRequestedClaimNames.add(StandardClaimNames.SUB);

        if (requestedScopes.contains(OidcScopes.ADDRESS)) {
            scopeRequestedClaimNames.add(StandardClaimNames.ADDRESS);
        }
        if (requestedScopes.contains(OidcScopes.EMAIL)) {
            scopeRequestedClaimNames.addAll(EMAIL_CLAIMS);
        }
        if (requestedScopes.contains(OidcScopes.PHONE)) {
            scopeRequestedClaimNames.addAll(PHONE_CLAIMS);
        }
        if (requestedScopes.contains(OidcScopes.PROFILE)) {
            scopeRequestedClaimNames.addAll(PROFILE_CLAIMS);
        }

        Map<String, Object> requestedClaims = new HashMap<>(claims);
        requestedClaims.keySet().removeIf(claimName -> !scopeRequestedClaimNames.contains(claimName));

        return requestedClaims;
    }
}
