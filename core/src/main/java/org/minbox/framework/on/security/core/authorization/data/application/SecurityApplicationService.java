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

package org.minbox.framework.on.security.core.authorization.data.application;

import org.minbox.framework.on.security.core.authorization.data.user.SecurityUserAuthorizeApplication;
import org.minbox.framework.on.security.core.authorization.data.user.SecurityUserAuthorizeApplicationJdbcRepository;
import org.minbox.framework.on.security.core.authorization.data.user.SecurityUserAuthorizeApplicationRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 应用业务逻辑
 *
 * @author 恒宇少年
 * @since 0.1.1
 */
public class SecurityApplicationService {
    private SecurityApplicationRepository applicationRepository;
    private SecurityUserAuthorizeApplicationRepository userAuthorizeApplicationRepository;

    public SecurityApplicationService(JdbcOperations jdbcOperations) {
        this.applicationRepository = new SecurityApplicationJdbcRepository(jdbcOperations);
        this.userAuthorizeApplicationRepository = new SecurityUserAuthorizeApplicationJdbcRepository(jdbcOperations);
    }

    public SecurityApplication findById(String id) {
        return this.applicationRepository.selectOne(id);
    }

    /**
     * 查询用户授权的应用列表
     *
     * @param userId 用户ID {@link SecurityUserAuthorizeApplication#getUserId()}
     * @return {@link SecurityApplication}
     */
    public List<SecurityApplication> findByUserAuthorize(String userId) {
        List<SecurityUserAuthorizeApplication> userAuthorizeApplicationList = userAuthorizeApplicationRepository.findByUserId(userId);
        if (!ObjectUtils.isEmpty(userAuthorizeApplicationList)) {
            // @formatter:off
            return userAuthorizeApplicationList.stream()
                    .map(userAuthorizeApplication -> this.applicationRepository.selectOne(userAuthorizeApplication.getApplicationId()))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            // @formatter:on
        }
        return null;
    }
}
