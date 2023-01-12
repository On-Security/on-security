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
