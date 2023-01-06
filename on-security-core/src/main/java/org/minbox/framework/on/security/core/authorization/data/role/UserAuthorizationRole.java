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

import org.minbox.framework.on.security.core.authorization.util.OnSecurityVersion;

import java.io.Serializable;

/**
 * 用户授权角色封装实体定义
 *
 * @author 恒宇少年
 * @since 0.0.5
 */
public final class UserAuthorizationRole implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
    private String roleId;
    private String roleName;
    private String roleCode;
    private String roleDescribe;

    public UserAuthorizationRole(String roleId, String roleName, String roleCode, String roleDescribe) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.roleCode = roleCode;
        this.roleDescribe = roleDescribe;
    }

    public String getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public String getRoleDescribe() {
        return roleDescribe;
    }
}
