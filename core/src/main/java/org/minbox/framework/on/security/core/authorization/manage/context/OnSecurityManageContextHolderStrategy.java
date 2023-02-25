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

/**
 * 管理上下文持有者创建策略
 *
 * @author 恒宇少年
 * @since 0.0.9
 */
public interface OnSecurityManageContextHolderStrategy {
    /**
     * 创建一个空的应用上下文实例
     *
     * @return {@link OnSecurityManageContext} 应用上下文实例
     */
    OnSecurityManageContext createEmptyContext();

    /**
     * 获取应用上下文实例
     *
     * @return {@link OnSecurityManageContext}
     */
    OnSecurityManageContext getContext();

    /**
     * 设置应用上下文实例
     *
     * @param context {@link OnSecurityManageContext} 应用上下文实例
     */
    void setContext(OnSecurityManageContext context);

    /**
     * 清空应用上下文实例
     */
    void clearContext();
}
