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

import org.springframework.util.Assert;

/**
 * 应用上下文持有者{@link ThreadLocal}线程副本策略
 *
 * @author 恒宇少年
 * @since 0.0.6
 */
public class InheritableThreadLocalOnSecurityApplicationContextHolderStrategy implements OnSecurityApplicationContextHolderStrategy {
    private static final ThreadLocal<OnSecurityApplicationContext> APPLICATION_CONTEXT_THREAD_LOCAL = new InheritableThreadLocal<>();

    @Override
    public OnSecurityApplicationContext createEmptyContext() {
        return OnSecurityApplicationContextImpl.createEmptyContext();
    }

    @Override
    public OnSecurityApplicationContext getContext() {
        OnSecurityApplicationContext ctx = APPLICATION_CONTEXT_THREAD_LOCAL.get();
        if (ctx == null) {
            ctx = createEmptyContext();
            APPLICATION_CONTEXT_THREAD_LOCAL.set(ctx);
        }
        return ctx;
    }

    @Override
    public void setContext(OnSecurityApplicationContext context) {
        Assert.notNull(context, "OnSecurityApplicationContext cannot be null");
        APPLICATION_CONTEXT_THREAD_LOCAL.set(context);
    }

    @Override
    public void clearContext() {
        APPLICATION_CONTEXT_THREAD_LOCAL.remove();
    }
}
