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

package org.minbox.framework.on.security.manage.api.module.region.service;

import org.minbox.framework.on.security.core.authorization.data.console.SecurityConsoleManager;
import org.minbox.framework.on.security.core.authorization.data.region.SecurityRegion;
import org.minbox.framework.on.security.core.authorization.manage.context.OnSecurityManageContext;

import java.util.List;

/**
 * 安全域业务逻辑定义
 *
 * @author 恒宇少年
 * @since 0.1.2
 */
public interface SecurityRegionService {
    /**
     * 查询全部有效未被删除的安全域
     *
     * @return {@link SecurityRegion}
     */
    List<SecurityRegion> selectAllAvailable();

    /**
     * 获取管理员授权的安全域列表
     * <p>
     * 如果管理员{@link SecurityConsoleManager#getInternal()}为"true"时返回全部的安全域，如果为"false"时仅返回管理员授权的安全域
     *
     * @param manageContext 管理上下文 {@link OnSecurityManageContext}
     * @return {@link SecurityRegion}
     */
    List<SecurityRegion> getManagerAuthorization(OnSecurityManageContext manageContext);
}
