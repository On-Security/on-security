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
 * 控制台管理会话
 *
 * @author 恒宇少年
 * @since 0.0.9
 */
public class SecurityConsoleManagerSession implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
    private String id;
    private String regionId;
    private String authenticateType;
    private String managerId;
    private String regionSecretId;
    private String manageTokenValue;
    private LocalDateTime manageTokenIssuedAt;
    private LocalDateTime manageTokenExpiresAt;

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

    public String getAuthenticateType() {
        return authenticateType;
    }

    public void setAuthenticateType(String authenticateType) {
        this.authenticateType = authenticateType;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getRegionSecretId() {
        return regionSecretId;
    }

    public void setRegionSecretId(String regionSecretId) {
        this.regionSecretId = regionSecretId;
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

    @Override
    public String toString() {
        return "SecurityConsoleManagerSession{" +
                "id='" + id + '\'' +
                ", regionId='" + regionId + '\'' +
                ", authenticateType='" + authenticateType + '\'' +
                ", managerId='" + managerId + '\'' +
                ", regionSecretId='" + regionSecretId + '\'' +
                ", manageTokenValue='" + manageTokenValue + '\'' +
                ", manageTokenIssuedAt=" + manageTokenIssuedAt +
                ", manageTokenExpiresAt=" + manageTokenExpiresAt +
                '}';
    }
}
