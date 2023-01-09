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

package org.minbox.framework.on.security.authorization.server.oauth2.authentication.token.customizer;

import org.minbox.framework.on.security.core.authorization.OnSecurityJwtTokenClaims;
import org.minbox.framework.on.security.core.authorization.adapter.OnSecurityUserDetails;
import org.minbox.framework.on.security.core.authorization.data.attribute.SecurityAttribute;
import org.minbox.framework.on.security.core.authorization.data.attribute.SecurityAttributeJdbcRepository;
import org.minbox.framework.on.security.core.authorization.data.attribute.SecurityAttributeRepository;
import org.minbox.framework.on.security.core.authorization.data.user.SecurityUserAuthorizeAttribute;
import org.minbox.framework.on.security.core.authorization.data.user.SecurityUserAuthorizeAttributeJdbcRepository;
import org.minbox.framework.on.security.core.authorization.data.user.SecurityUserAuthorizeAttributeRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsContext;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户授权属性写入JWT令牌主体
 * <p>
 * 仅针对{@link OAuth2TokenFormat#SELF_CONTAINED}方式创建的令牌生效，将用户授权的属性写入到JWT主体中
 *
 * @author 恒宇少年
 * @see OAuth2TokenFormat
 * @see OAuth2TokenClaimsContext
 * @since 0.0.4
 */
public class OnSecurityUserAuthorizeAttributeJwtClaimsCustomizer implements OnSecuritySortTokenCustomizer<JwtEncodingContext> {
    private SecurityAttributeRepository attributeRepository;
    private SecurityUserAuthorizeAttributeRepository userAuthorizeAttributeRepository;

    public OnSecurityUserAuthorizeAttributeJwtClaimsCustomizer(JdbcOperations jdbcOperations) {
        Assert.notNull(jdbcOperations, "jdbcOperations cannot be null");
        this.attributeRepository = new SecurityAttributeJdbcRepository(jdbcOperations);
        this.userAuthorizeAttributeRepository = new SecurityUserAuthorizeAttributeJdbcRepository(jdbcOperations);
    }

    @Override
    public void customize(JwtEncodingContext context) {
        // 令牌类型不为ACCESS_TOKEN也不为ID_TOKEN时，跳过
        if (context.getTokenType() == null ||
                (!OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType()) &&
                        !OidcParameterNames.ID_TOKEN.equals(context.getTokenType().getValue()))) {
            return;
        }
        // 令牌为ACCESS_TOKEN时，并且令牌格式化方式不为SELF_CONTAINED时，跳过
        OAuth2TokenFormat tokenFormat = context.getRegisteredClient().getTokenSettings().getAccessTokenFormat();
        if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType()) && !OAuth2TokenFormat.SELF_CONTAINED.equals(tokenFormat)) {
            return;
        }
        if (!(context.getPrincipal() instanceof UsernamePasswordAuthenticationToken)) {
            return;
        }
        UsernamePasswordAuthenticationToken authenticationToken = context.getPrincipal();
        OnSecurityUserDetails userDetails = (OnSecurityUserDetails) authenticationToken.getPrincipal();
        List<SecurityUserAuthorizeAttribute> userAuthorizeAttributeList = userAuthorizeAttributeRepository.findByUserId(userDetails.getUserId());
        if (ObjectUtils.isEmpty(userAuthorizeAttributeList)) {
            return;
        }
        // @formatter:off
        List<String> authorizeAttributeIds =
                userAuthorizeAttributeList.stream()
                        .map(SecurityUserAuthorizeAttribute::getAttributeId)
                        .collect(Collectors.toList());
        // @formatter:on
        List<SecurityAttribute> authorizeAttributeList = attributeRepository.findByIds(authorizeAttributeIds);
        if (ObjectUtils.isEmpty(authorizeAttributeList)) {
            return;
        }
        JwtClaimsSet.Builder claimsBuilder = context.getClaims();
        // @formatter:off
        Map<String, String> attributeMap =
                authorizeAttributeList.stream().collect(
                        Collectors.toMap(SecurityAttribute::getKey, attribute -> attribute.getValue())
                );
        // @formatter:on
        claimsBuilder.claim(OnSecurityJwtTokenClaims.AUTH_ATTR, attributeMap);
    }
}
