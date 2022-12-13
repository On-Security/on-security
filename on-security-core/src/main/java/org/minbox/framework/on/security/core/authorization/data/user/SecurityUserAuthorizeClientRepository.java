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

package org.minbox.framework.on.security.core.authorization.data.user;

import org.minbox.framework.on.security.core.authorization.data.client.SecurityClient;

import java.util.List;

/**
 * 用户授权客户端数据存储库
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public interface SecurityUserAuthorizeClientRepository {
    /**
     * 新增用户授权客户端
     *
     * @param userAuthorizeClient {@link SecurityUserAuthorizeClient}
     */
    void insert(SecurityUserAuthorizeClient userAuthorizeClient);

    /**
     * 删除用户授权的客户端
     *
     * @param securityUserId   用户ID {@link SecurityUser#getId()}
     * @param securityClientId 客户端ID {@link SecurityClient#getId()}
     */
    void remove(String securityUserId, String securityClientId);

    /**
     * 查询用户授权的客户端列表
     *
     * @param userId {@link SecurityUser#getId()} 用户ID
     * @return 用户授权的客户端列表 {@link SecurityUserAuthorizeClient}
     */
    List<SecurityUserAuthorizeClient> findByUserId(String userId);
}
