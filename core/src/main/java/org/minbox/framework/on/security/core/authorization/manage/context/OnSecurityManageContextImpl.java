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

package org.minbox.framework.on.security.core.authorization.manage.context;

import org.minbox.framework.on.security.core.authorization.data.console.SecurityConsoleManager;
import org.minbox.framework.on.security.core.authorization.data.console.SecurityConsoleManagerSession;
import org.minbox.framework.on.security.core.authorization.data.console.SecurityConsoleMenu;
import org.minbox.framework.on.security.core.authorization.data.region.SecurityRegion;
import org.minbox.framework.on.security.core.authorization.data.region.SecurityRegionSecret;
import org.minbox.framework.on.security.core.authorization.manage.ManageTokenAccessAuthorization;

import java.util.Set;

/**
 * 管理令牌上下文实现类
 *
 * @author 恒宇少年
 * @see ManageTokenAccessAuthorization
 * @since 0.0.9
 */
public class OnSecurityManageContextImpl implements OnSecurityManageContext {
    private String manageToken;
    private ManageTokenAccessAuthorization authorization;

    private OnSecurityManageContextImpl() {
    }

    public static OnSecurityManageContextImpl createEmptyContext() {
        return new OnSecurityManageContextImpl();
    }

    public static OnSecurityManageContextImpl.Builder withManageToken(String manageToken) {
        return new OnSecurityManageContextImpl.Builder(manageToken);
    }

    @Override
    public String toString() {
        return "OnSecurityManageContextImpl{" +
                "manageToken='" + manageToken + '\'' +
                ", authorization=" + authorization +
                '}';
    }

    @Override
    public String getManageToken() {
        return this.manageToken;
    }

    @Override
    public SecurityRegion getRegion() {
        return this.authorization.getRegion();
    }

    @Override
    public SecurityConsoleManager getManager() {
        return this.authorization.getManager();
    }

    @Override
    public String getManagerId() {
        return this.getManager().getId();
    }

    @Override
    public SecurityRegionSecret getRegionSecret() {
        return this.authorization.getRegionSecret();
    }

    @Override
    public Set<SecurityConsoleMenu> getManagerAuthorizeMenu() {
        return this.authorization.getManagerAuthorizeMenus();
    }

    @Override
    public SecurityConsoleManagerSession getSession() {
        return this.authorization.getManagerSession();
    }

    public ManageTokenAccessAuthorization getAuthorization() {
        return authorization;
    }

    /**
     * The {@link OnSecurityManageContextImpl} Builder
     */
    public static class Builder {
        private String manageToken;
        private ManageTokenAccessAuthorization authorization;

        public Builder(String manageToken) {
            this.manageToken = manageToken;
        }

        public Builder authorization(ManageTokenAccessAuthorization authorization) {
            this.authorization = authorization;
            return this;
        }

        public OnSecurityManageContextImpl build() {
            OnSecurityManageContextImpl manageContext = new OnSecurityManageContextImpl();
            manageContext.manageToken = this.manageToken;
            manageContext.authorization = this.authorization;
            return manageContext;
        }
    }
}
