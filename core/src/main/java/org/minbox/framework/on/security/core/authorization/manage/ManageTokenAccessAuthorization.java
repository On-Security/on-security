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

package org.minbox.framework.on.security.core.authorization.manage;

import org.minbox.framework.on.security.core.authorization.data.console.SecurityConsoleManager;
import org.minbox.framework.on.security.core.authorization.data.console.SecurityConsoleManagerSession;
import org.minbox.framework.on.security.core.authorization.data.console.SecurityConsoleMenu;
import org.minbox.framework.on.security.core.authorization.data.region.SecurityRegion;
import org.minbox.framework.on.security.core.authorization.data.region.SecurityRegionSecret;
import org.minbox.framework.on.security.core.authorization.util.OnSecurityVersion;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

/**
 * ManageToken访问认证信息类
 *
 * @author 恒宇少年
 * @since 0.0.9
 */
public class ManageTokenAccessAuthorization extends AbstractAuthenticationToken implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
    private SecurityRegion region;
    private SecurityConsoleManager manager;
    private Set<SecurityConsoleMenu> managerAuthorizeMenus;
    private SecurityRegionSecret regionSecret;
    private SecurityConsoleManagerSession managerSession;

    private ManageTokenAccessAuthorization() {
        super(Collections.emptyList());
    }

    public SecurityRegion getRegion() {
        return region;
    }

    public SecurityConsoleManager getManager() {
        return manager;
    }

    public Set<SecurityConsoleMenu> getManagerAuthorizeMenus() {
        return managerAuthorizeMenus;
    }

    public SecurityRegionSecret getRegionSecret() {
        return regionSecret;
    }

    public SecurityConsoleManagerSession getManagerSession() {
        return managerSession;
    }

    @Override
    public Object getCredentials() {
        return managerSession;
    }

    @Override
    public Object getPrincipal() {
        return managerSession.getId();
    }

    @Override
    public String toString() {
        return "ManageTokenAccessAuthorization{" +
                "region=" + region +
                ", manager=" + manager +
                ", managerAuthorizeMenus=" + managerAuthorizeMenus +
                ", regionSecret=" + regionSecret +
                ", managerSession=" + managerSession +
                '}';
    }

    public static Builder withManageSession(SecurityConsoleManagerSession managerSession) {
        return new Builder(managerSession);
    }

    /**
     * The {@link ManageTokenAccessAuthorization} Builder
     */
    public static class Builder implements Serializable {
        private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
        private SecurityRegion region;
        private SecurityConsoleManager manager;
        private Set<SecurityConsoleMenu> managerAuthorizeMenus;
        private SecurityRegionSecret regionSecret;
        private SecurityConsoleManagerSession managerSession;

        public Builder(SecurityConsoleManagerSession managerSession) {
            this.managerSession = managerSession;
        }

        public Builder region(SecurityRegion region) {
            this.region = region;
            return this;
        }

        public Builder manager(SecurityConsoleManager manager) {
            this.manager = manager;
            return this;
        }

        public Builder managerAuthorizeMenus(Set<SecurityConsoleMenu> managerAuthorizeMenus) {
            this.managerAuthorizeMenus = managerAuthorizeMenus;
            return this;
        }

        public Builder regionSecret(SecurityRegionSecret regionSecret) {
            this.regionSecret = regionSecret;
            return this;
        }

        public ManageTokenAccessAuthorization build() {
            ManageTokenAccessAuthorization authorization = new ManageTokenAccessAuthorization();
            authorization.managerSession = this.managerSession;
            authorization.region = this.region;
            authorization.manager = this.manager;
            authorization.managerAuthorizeMenus = this.managerAuthorizeMenus;
            authorization.regionSecret = this.regionSecret;
            return authorization;
        }
    }
}
