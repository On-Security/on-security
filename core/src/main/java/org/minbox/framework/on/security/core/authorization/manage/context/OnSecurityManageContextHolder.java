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

import org.springframework.util.Assert;

/**
 * 管理上下文持有者
 * @author 恒宇少年
 * @since 0.0.9
 */
public class OnSecurityManageContextHolder {
    /**
     * 应用上下文持有者策略，默认使用线程副本的存储方式{@link InheritableThreadLocalOnSecurityManageContextHolderStrategy}
     */
    private static OnSecurityManageContextHolderStrategy contextHolderStrategy = new InheritableThreadLocalOnSecurityManageContextHolderStrategy();

    /**
     * 创建一个空的应用上下文实例
     *
     * @return {@link OnSecurityManageContextHolderStrategy#createEmptyContext()}
     */
    public static OnSecurityManageContext createEmptyContext() {
        return contextHolderStrategy.createEmptyContext();
    }

    /**
     * 获取应用上下文实例
     *
     * @return {@link OnSecurityManageContextHolderStrategy#getContext()}
     */
    public static OnSecurityManageContext getContext() {
        return contextHolderStrategy.getContext();
    }

    /**
     * 设置应用上下文实例
     * <p>
     * 调用应用上下文持有者策略{@link OnSecurityManageContextHolderStrategy#setContext(OnSecurityManageContext)}的设置上下文方法
     *
     * @param context 应用上下文实例
     */
    public static void setContext(OnSecurityManageContext context) {
        contextHolderStrategy.setContext(context);
    }

    /**
     * 清空应用上下文实例
     * <p>
     * 调用应用上下文持有者策略{@link OnSecurityManageContextHolderStrategy#clearContext()}的清空上下文方法
     */
    public static void clearContext() {
        contextHolderStrategy.clearContext();
    }

    /**
     * 设置应用上下文持有者加载策略
     *
     * @param contextHolderStrategy {@link OnSecurityManageContextHolderStrategy}实现类实例
     */
    public static void setContextHolderStrategy(OnSecurityManageContextHolderStrategy contextHolderStrategy) {
        Assert.notNull(contextHolderStrategy, "contextHolderStrategy cannot be null");
        OnSecurityManageContextHolder.contextHolderStrategy = contextHolderStrategy;
    }
}
