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
import org.minbox.framework.on.security.core.authorization.data.group.SecurityGroupAuthorizeRole;
import org.minbox.framework.on.security.core.authorization.data.group.SecurityGroupAuthorizeRoleJdbcRepository;
import org.minbox.framework.on.security.core.authorization.data.group.SecurityGroupAuthorizeRoleRepository;
import org.minbox.framework.on.security.core.authorization.data.role.SecurityRole;
import org.minbox.framework.on.security.core.authorization.data.role.SecurityRoleJdbcRepository;
import org.minbox.framework.on.security.core.authorization.data.role.SecurityRoleRepository;
import org.minbox.framework.on.security.core.authorization.data.user.*;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 将用户以及用户绑定组所授权的角色列表写入到JWT主体中
 * <p>
 * 仅针对{@link OAuth2TokenFormat#SELF_CONTAINED}方式创建的令牌生效
 *
 * @author 恒宇少年
 * @see JwtEncodingContext
 * @see SecurityRole
 * @since 0.0.5
 */
public class OnSecurityUserBindGroupJwtClaimsCustomizer implements OnSecuritySortTokenCustomizer<JwtEncodingContext> {
    private SecurityUserGroupRepository userGroupRepository;
    private SecurityGroupAuthorizeRoleRepository groupAuthorizeRoleRepository;
    private SecurityUserAuthorizeRoleRepository userAuthorizeRoleRepository;
    private SecurityRoleRepository roleRepository;

    public OnSecurityUserBindGroupJwtClaimsCustomizer(JdbcOperations jdbcOperations) {
        Assert.notNull(jdbcOperations, "jdbcOperations cannot be null");
        this.userAuthorizeRoleRepository = new SecurityUserAuthorizeRoleJdbcRepository(jdbcOperations);
        this.userGroupRepository = new SecurityUserGroupJdbcRepository(jdbcOperations);
        this.groupAuthorizeRoleRepository = new SecurityGroupAuthorizeRoleJdbcRepository(jdbcOperations);
        this.roleRepository = new SecurityRoleJdbcRepository(jdbcOperations);
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
        List<String> authorizeRoleIds = new ArrayList<>();
        // 查询用户授权的角色列表
        List<SecurityUserAuthorizeRole> userAuthorizeRoleList = this.userAuthorizeRoleRepository.findByUserId(userDetails.getUserId());
        if (!ObjectUtils.isEmpty(userAuthorizeRoleList)) {
            // @formatter:off
            authorizeRoleIds.addAll(
                    userAuthorizeRoleList
                            .stream()
                            .map(SecurityUserAuthorizeRole::getRoleId)
                            .collect(Collectors.toList())
            );
            // @formatter:on
        }

        // 查询用户绑定每一个组授权的角色列表
        List<SecurityUserGroup> userGroupList = this.userGroupRepository.findByUserId(userDetails.getUserId());
        if (!ObjectUtils.isEmpty(userGroupList)) {
            // @formatter:off
            userGroupList.stream().forEach(securityUserGroup -> {
                List<SecurityGroupAuthorizeRole> groupAuthorizeRoleList =
                        groupAuthorizeRoleRepository.findByGroupId(securityUserGroup.getGroupId());
                if (!ObjectUtils.isEmpty(groupAuthorizeRoleList)) {
                    authorizeRoleIds.addAll(
                            groupAuthorizeRoleList
                                    .stream()
                                    .map(SecurityGroupAuthorizeRole::getRoleId)
                                    .collect(Collectors.toList())
                    );
                }
            });
            // @formatter:on
        }
        if (ObjectUtils.isEmpty(authorizeRoleIds)) {
            return;
        }
        RegisteredClient client = context.getRegisteredClient();
        List<SecurityRole> authorizeRoleList = roleRepository.findByIds(client.getId(), authorizeRoleIds);
        if (!ObjectUtils.isEmpty(authorizeRoleList)) {
            JwtClaimsSet.Builder claimsBuilder = context.getClaims();
            List<String> authorizeRoleCodeList = authorizeRoleList.stream().map(SecurityRole::getCode).collect(Collectors.toList());
            claimsBuilder.claim(OnSecurityJwtTokenClaims.AUTH_ROLE, authorizeRoleCodeList);
        }
    }
}
