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

package org.minbox.framework.on.security.core.authorization.data.role;

import org.minbox.framework.on.security.core.authorization.data.user.SecurityUserAuthorizeRole;
import org.minbox.framework.on.security.core.authorization.data.user.SecurityUserAuthorizeRoleJdbcRepository;
import org.minbox.framework.on.security.core.authorization.data.user.SecurityUserAuthorizeRoleRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 安全角色业务逻辑类
 *
 * @author 恒宇少年
 * @since 0.0.5
 */
public class SecurityRoleService {
    private SecurityRoleRepository roleRepository;
    private SecurityUserAuthorizeRoleRepository userAuthorizeRoleRepository;

    public SecurityRoleService(JdbcOperations jdbcOperations) {
        Assert.notNull(jdbcOperations, "jdbcOperations cannot be null");
        this.roleRepository = new SecurityRoleJdbcRepository(jdbcOperations);
        this.userAuthorizeRoleRepository = new SecurityUserAuthorizeRoleJdbcRepository(jdbcOperations);
    }

    /**
     * 查询指定用户授权的角色列表
     *
     * @param userId 用户ID {@link SecurityUserAuthorizeRole#getUserId()}
     * @return 用户授权的角色 {@link UserAuthorizationRole}
     */
    public List<UserAuthorizationRole> findByUserId(String userId) {
        List<SecurityUserAuthorizeRole> securityUserAuthorizeRoleList = userAuthorizeRoleRepository.findByUserId(userId);
        if (ObjectUtils.isEmpty(securityUserAuthorizeRoleList)) {
            return null;
        }
        // @formatter:off
        return securityUserAuthorizeRoleList.stream().map(authorizeRole -> {
                    SecurityRole role = roleRepository.selectOne(authorizeRole.getRoleId());
                    if (role == null || role.isDeleted()) {
                        return null;
                    }
                    return UserAuthorizationRole.withRoleId(role.getId())
                            .roleName(role.getName())
                            .roleCode(role.getCode())
                            .roleDescribe(role.getDescribe())
                            .build();
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        // @formatter:on
    }
}
