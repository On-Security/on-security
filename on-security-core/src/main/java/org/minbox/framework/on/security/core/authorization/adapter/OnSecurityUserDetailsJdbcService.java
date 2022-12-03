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

package org.minbox.framework.on.security.core.authorization.adapter;

import org.minbox.framework.on.security.core.authorization.data.role.SecurityRole;
import org.minbox.framework.on.security.core.authorization.data.role.SecurityRoleJdbcRepository;
import org.minbox.framework.on.security.core.authorization.data.role.SecurityRoleRepository;
import org.minbox.framework.on.security.core.authorization.data.user.*;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * On-Security提供的{@link UserDetailsService}JDBC数据存储方式实现类
 *
 * @author 恒宇少年
 * @see UserDetails
 * @see UserDetailsService
 * @see org.springframework.jdbc.core.JdbcOperations
 * @since 0.0.1
 */
public final class OnSecurityUserDetailsJdbcService implements UserDetailsService {
    private SecurityUserRepository userRepository;
    private SecurityUserAuthorizeRoleRepository userAuthorizeRoleRepository;
    private SecurityRoleRepository roleRepository;

    public OnSecurityUserDetailsJdbcService(JdbcOperations jdbcOperations) {
        this.userRepository = new SecurityUserJdbcRepository(jdbcOperations);
        this.userAuthorizeRoleRepository = new SecurityUserAuthorizeRoleJdbcRepository(jdbcOperations);
        this.roleRepository = new SecurityRoleJdbcRepository(jdbcOperations);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        OnSecurityUserDetails.Builder builder = OnSecurityUserDetails.withUsername(username);
        SecurityUser securityUser = userRepository.findByUsername(username);
        if (ObjectUtils.isEmpty(securityUser)) {
            throw new UsernameNotFoundException("According to the user name: " + username + ", no data is queried.");
        }
        // @formatter:off
        builder.userId(securityUser.getId())
                .regionId(securityUser.getRegionId())
                .username(securityUser.getUsername())
                .password(securityUser.getPassword())
                .accountLocked(!securityUser.isEnabled())
                .credentialsExpired(securityUser.isDeleted());
        // @formatter:on

        List<SecurityUserAuthorizeRole> userAuthorizeRoleList = userAuthorizeRoleRepository.findByUserId(securityUser.getId());
        if (!ObjectUtils.isEmpty(userAuthorizeRoleList)) {
            // @formatter:off
            List<String> roleCodeList = userAuthorizeRoleList.stream()
                    .map(uar -> roleRepository.findById(uar.getRoleId()))
                    .filter(securityRole -> !ObjectUtils.isEmpty(securityRole))
                    .map(SecurityRole::getCode)
                    .collect(Collectors.toList());
            // @formatter:on
            builder.roles(roleCodeList.toArray(new String[]{}));
        }
        return builder.build();
    }
}
