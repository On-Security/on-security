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

import java.util.Set;

/**
 * 管理上下文实例
 *
 * @author 恒宇少年
 * @see SecurityRegion
 * @see SecurityConsoleManager
 * @see SecurityConsoleManagerSession
 * @see SecurityRegionSecret
 * @see SecurityConsoleMenu
 * @since 0.0.9
 */
public interface OnSecurityManageContext {
    /**
     * 获取访问资源所携带的"ManageToken"
     *
     * @return 从Header从提取的"ManageToken"
     */
    String getManageToken();

    /**
     * 获取"ManageToken"所管理的安全域
     *
     * @return {@link SecurityRegion}
     */
    SecurityRegion getRegion();

    /**
     * 获取"username_password"方式获取"ManageToken"的管理员
     *
     * @return {@link SecurityConsoleManager}
     */
    SecurityConsoleManager getManager();

    /**
     * 获取"id_secret"方式获取"ManageToken"的安全域密钥
     *
     * @return {@link SecurityRegionSecret}
     */
    SecurityRegionSecret getRegionSecret();

    /**
     * 获取管理员授权的控制台菜单列表
     *
     * @return {@link SecurityConsoleMenu}
     */
    Set<SecurityConsoleMenu> getManagerAuthorizeMenu();

    /**
     * 获取"ManageToken"所属的会话信息
     *
     * @return {@link SecurityConsoleManagerSession}
     */
    SecurityConsoleManagerSession getSession();
}
