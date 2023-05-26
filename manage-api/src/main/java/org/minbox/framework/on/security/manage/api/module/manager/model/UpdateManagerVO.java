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

package org.minbox.framework.on.security.manage.api.module.manager.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 更新管理员请求实体
 *
 * @author 恒宇少年
 * @since 0.1.2
 */
public class UpdateManagerVO {
    /**
     * 管理员ID
     */
    @NotBlank(message = "管理员ID不可以为空")
    private String managerId;
    /**
     * 管理员头像
     */
    private String avatar;
    /**
     * 是否启用
     */
    private boolean enabled;
    /**
     * 描述
     */
    private String describe;
    /**
     * 授权菜单ID列表
     */
    @NotEmpty(message = "请至少授权一个菜单")
    private List<String> authorizeMenuIds;

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public List<String> getAuthorizeMenuIds() {
        return authorizeMenuIds;
    }

    public void setAuthorizeMenuIds(List<String> authorizeMenuIds) {
        this.authorizeMenuIds = authorizeMenuIds;
    }
}
