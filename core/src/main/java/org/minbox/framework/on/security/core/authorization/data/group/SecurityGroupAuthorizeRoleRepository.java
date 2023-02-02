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

package org.minbox.framework.on.security.core.authorization.data.group;

import java.util.List;

/**
 * 安全组授权角色数据存储库
 *
 * @author 恒宇少年
 * @since 0.0.5
 */
public interface SecurityGroupAuthorizeRoleRepository {
    /**
     * 查询分组授权的角色列表
     *
     * @param groupId {@link SecurityGroupAuthorizeRole#getGroupId()}
     * @return {@link SecurityGroupAuthorizeRole}
     */
    List<SecurityGroupAuthorizeRole> findByGroupId(String groupId);
}
