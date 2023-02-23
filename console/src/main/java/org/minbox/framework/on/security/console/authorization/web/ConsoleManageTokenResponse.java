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

package org.minbox.framework.on.security.console.authorization.web;

import org.minbox.framework.on.security.console.authorization.authentication.ManageTokenAuthenticateType;
import org.minbox.framework.on.security.console.authorization.token.ConsoleManageToken;
import org.springframework.util.Assert;

/**
 * 控制台管理令牌响应实体
 *
 * @author 恒宇少年
 * @since 0.0.9
 */
public class ConsoleManageTokenResponse {
    private String regionId;
    private ManageTokenAuthenticateType authenticateType;
    private ConsoleManageToken manageToken;

    private ConsoleManageTokenResponse(String regionId, ManageTokenAuthenticateType authenticateType, ConsoleManageToken manageToken) {
        this.regionId = regionId;
        this.authenticateType = authenticateType;
        this.manageToken = manageToken;
    }

    public String getRegionId() {
        return regionId;
    }

    public ManageTokenAuthenticateType getAuthenticateType() {
        return authenticateType;
    }

    public ConsoleManageToken getManageToken() {
        return manageToken;
    }

    public static Builder withConsoleManageToken(ConsoleManageToken manageToken) {
        Assert.notNull(manageToken, "Console manage token cannot be null.");
        return new Builder(manageToken);
    }

    /**
     * The {@link ConsoleManageTokenResponse} Builder
     */
    public static class Builder {
        private String regionId;
        private ManageTokenAuthenticateType authenticateType;
        private ConsoleManageToken manageToken;

        public Builder(ConsoleManageToken manageToken) {
            this.manageToken = manageToken;
        }

        public Builder regionId(String regionId) {
            this.regionId = regionId;
            return this;
        }

        public Builder authenticateType(ManageTokenAuthenticateType authenticateType) {
            this.authenticateType = authenticateType;
            return this;
        }

        public ConsoleManageTokenResponse build() {
            Assert.hasText(this.regionId, "regionId cannot be empty.");
            Assert.notNull(this.authenticateType, "ManageTokenAuthenticateType cannot be null.");
            Assert.notNull(this.manageToken, "ConsoleManageToken cannot be null.");
            return new ConsoleManageTokenResponse(this.regionId, this.authenticateType, this.manageToken);
        }
    }
}
