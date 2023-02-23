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

package org.minbox.framework.on.security.console.authorization.token;

import org.minbox.framework.on.security.console.authorization.authentication.ManageTokenAuthenticateType;
import org.minbox.framework.on.security.core.authorization.data.console.SecurityConsoleManager;
import org.minbox.framework.on.security.core.authorization.data.region.SecurityRegionSecret;
import org.springframework.util.Assert;

import java.time.Duration;

/**
 * 管理令牌上下文对象
 *
 * @author 恒宇少年
 * @since 0.0.9
 */
public class ConsoleManageTokenContext {
    private static final long DEFAULT_LIVE_SECONDS = 60 * 60 * 12;
    private Duration manageTokenTimeToLive;
    private ManageTokenAuthenticateType authenticateType;
    private SecurityConsoleManager consoleManager;
    private SecurityRegionSecret regionSecret;

    private ConsoleManageTokenContext(ManageTokenAuthenticateType authenticateType) {
        this.authenticateType = authenticateType;
    }

    public Duration getManageTokenTimeToLive() {
        return manageTokenTimeToLive;
    }

    public ManageTokenAuthenticateType getAuthenticateType() {
        return authenticateType;
    }

    public SecurityConsoleManager getConsoleManager() {
        return consoleManager;
    }

    public SecurityRegionSecret getRegionSecret() {
        return regionSecret;
    }

    public String getRegionId() {
        if (ManageTokenAuthenticateType.username_password == this.authenticateType) {
            return this.consoleManager.getRegionId();
        } else if (ManageTokenAuthenticateType.id_secret == this.authenticateType) {
            return this.regionSecret.getRegionId();
        }
        return null;
    }

    public String getManagerId() {
        return this.consoleManager != null ? this.consoleManager.getId() : null;
    }

    public String getSecretId() {
        return this.regionSecret != null ? this.regionSecret.getId() : null;
    }

    public static ConsoleManageTokenContext withUsernamePassword(SecurityConsoleManager manager) {
        return withUsernamePassword(manager, Duration.ofSeconds(DEFAULT_LIVE_SECONDS));
    }

    public static ConsoleManageTokenContext withUsernamePassword(SecurityConsoleManager manager, Duration manageTokenTimeToLive) {
        Assert.notNull(manager, "manager cannot be null.");
        ConsoleManageTokenContext tokenContext = new ConsoleManageTokenContext(ManageTokenAuthenticateType.username_password);
        tokenContext.consoleManager = manager;
        tokenContext.manageTokenTimeToLive = manageTokenTimeToLive;
        return tokenContext;
    }

    public static ConsoleManageTokenContext withIdSecret(SecurityRegionSecret regionSecret) {
        return withIdSecret(regionSecret, Duration.ofSeconds(DEFAULT_LIVE_SECONDS));
    }

    public static ConsoleManageTokenContext withIdSecret(SecurityRegionSecret regionSecret, Duration manageTokenTimeToLive) {
        Assert.notNull(regionSecret, "regionSecret cannot be null.");
        ConsoleManageTokenContext tokenContext = new ConsoleManageTokenContext(ManageTokenAuthenticateType.id_secret);
        tokenContext.regionSecret = regionSecret;
        tokenContext.manageTokenTimeToLive = manageTokenTimeToLive;
        return tokenContext;
    }
}
