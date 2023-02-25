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
 * 管理上下文持有者{@link ThreadLocal}线程副本策略
 *
 * @author 恒宇少年
 * @since 0.0.9
 */
public class InheritableThreadLocalOnSecurityManageContextHolderStrategy implements OnSecurityManageContextHolderStrategy {
    private static final ThreadLocal<OnSecurityManageContext> MANAGE_CONTEXT_THREAD_LOCAL = new InheritableThreadLocal<>();

    @Override
    public OnSecurityManageContext createEmptyContext() {
        return OnSecurityManageContextImpl.createEmptyContext();
    }

    @Override
    public OnSecurityManageContext getContext() {
        OnSecurityManageContext ctx = MANAGE_CONTEXT_THREAD_LOCAL.get();
        if (ctx == null) {
            ctx = createEmptyContext();
            MANAGE_CONTEXT_THREAD_LOCAL.set(ctx);
        }
        return ctx;
    }

    @Override
    public void setContext(OnSecurityManageContext context) {
        Assert.notNull(context, "OnSecurityManageContext cannot be null");
        MANAGE_CONTEXT_THREAD_LOCAL.set(context);
    }

    @Override
    public void clearContext() {
        MANAGE_CONTEXT_THREAD_LOCAL.remove();
    }
}
