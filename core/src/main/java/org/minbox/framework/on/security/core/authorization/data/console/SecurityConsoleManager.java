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

package org.minbox.framework.on.security.core.authorization.data.console;

import org.minbox.framework.on.security.core.authorization.util.OnSecurityVersion;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 控制台管理员
 *
 * @author 恒宇少年
 * @since 0.0.8
 */
public class SecurityConsoleManager implements Serializable {
    public static final String ADMIN_USERNAME = "admin";
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
    private String id;
    private String regionId;
    private String username;
    private String password;
    private boolean enabled;
    private boolean deleted;
    private LocalDateTime lastLoginTime;
    private String describe;
    private LocalDateTime createTime;
    private LocalDateTime deleteTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(LocalDateTime deleteTime) {
        this.deleteTime = deleteTime;
    }

    public static SecurityConsoleManager createManager(String regionId, String username, String password) {
        SecurityConsoleManager manager = new SecurityConsoleManager();
        manager.setId(UUID.randomUUID().toString());
        manager.setRegionId(regionId);
        manager.setUsername(username);
        manager.setPassword(password);
        manager.setEnabled(true);
        manager.setDeleted(false);
        return manager;
    }

    @Override
    public String toString() {
        // @formatter:off
        return "SecurityConsoleManager{" +
                "id='" + id + '\'' +
                ", regionId='" + regionId + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", deleted=" + deleted +
                ", lastLoginTime=" + lastLoginTime +
                ", describe='" + describe + '\'' +
                ", createTime=" + createTime +
                ", deleteTime=" + deleteTime +
                '}';
        // @formatter:on
    }
}
