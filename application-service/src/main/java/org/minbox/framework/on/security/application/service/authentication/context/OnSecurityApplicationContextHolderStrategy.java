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

package org.minbox.framework.on.security.application.service.authentication.context;

/**
 * 应用上下文持有者加载策略接口定义
 *
 * @author 恒宇少年
 * @since 0.0.6
 */
public interface OnSecurityApplicationContextHolderStrategy {
    /**
     * 创建一个空的应用上下文实例
     *
     * @return {@link OnSecurityApplicationContext} 应用上下文实例
     */
    OnSecurityApplicationContext createEmptyContext();

    /**
     * 获取应用上下文实例
     *
     * @return {@link OnSecurityApplicationContext}
     */
    OnSecurityApplicationContext getContext();

    /**
     * 设置应用上下文实例
     *
     * @param context {@link OnSecurityApplicationContext} 应用上下文实例
     */
    void setContext(OnSecurityApplicationContext context);

    /**
     * 清空应用上下文实例
     */
    void clearContext();
}
