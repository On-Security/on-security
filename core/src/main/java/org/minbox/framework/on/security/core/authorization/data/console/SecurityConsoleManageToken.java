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

/**
 * 控制台管理令牌（Manage Token）
 *
 * @author 恒宇少年
 * @since 0.0.8
 */
public class SecurityConsoleManageToken implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;

    private String id;
    private String regionId;
    private String managerId;
    private String manageTokenValue;
    private LocalDateTime manageTokenIssuedAt;
    private LocalDateTime manageTokenExpiresAt;
    private String manageTokenMetadata;

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

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getManageTokenValue() {
        return manageTokenValue;
    }

    public void setManageTokenValue(String manageTokenValue) {
        this.manageTokenValue = manageTokenValue;
    }

    public LocalDateTime getManageTokenIssuedAt() {
        return manageTokenIssuedAt;
    }

    public void setManageTokenIssuedAt(LocalDateTime manageTokenIssuedAt) {
        this.manageTokenIssuedAt = manageTokenIssuedAt;
    }

    public LocalDateTime getManageTokenExpiresAt() {
        return manageTokenExpiresAt;
    }

    public void setManageTokenExpiresAt(LocalDateTime manageTokenExpiresAt) {
        this.manageTokenExpiresAt = manageTokenExpiresAt;
    }

    public String getManageTokenMetadata() {
        return manageTokenMetadata;
    }

    public void setManageTokenMetadata(String manageTokenMetadata) {
        this.manageTokenMetadata = manageTokenMetadata;
    }
}
