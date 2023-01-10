package org.minbox.framework.on.security.application.service.authentication.context;

import org.springframework.util.Assert;

/**
 * 应用上下文持有者
 *
 * @author 恒宇少年
 * @since 0.0.6
 */
public final class OnSecurityApplicationContextHolder {
    /**
     * 应用上下文持有者策略，默认使用线程副本的存储方式{@link InheritableThreadLocalOnSecurityApplicationContextHolderStrategy}
     */
    private static OnSecurityApplicationContextHolderStrategy contextHolderStrategy = new InheritableThreadLocalOnSecurityApplicationContextHolderStrategy();

    /**
     * 创建一个空的应用上下文实例
     *
     * @return {@link OnSecurityApplicationContextHolderStrategy#createEmptyContext()}
     */
    public static OnSecurityApplicationContext createEmptyContext() {
        return contextHolderStrategy.createEmptyContext();
    }

    /**
     * 获取应用上下文实例
     *
     * @return {@link OnSecurityApplicationContextHolderStrategy#getContext()}
     */
    public static OnSecurityApplicationContext getContext() {
        return contextHolderStrategy.getContext();
    }

    /**
     * 设置应用上下文实例
     * <p>
     * 调用应用上下文持有者策略{@link OnSecurityApplicationContextHolderStrategy#setContext(OnSecurityApplicationContext)}的设置上下文方法
     *
     * @param context 应用上下文实例
     */
    public static void setContext(OnSecurityApplicationContext context) {
        contextHolderStrategy.setContext(context);
    }

    /**
     * 清空应用上下文实例
     * <p>
     * 调用应用上下文持有者策略{@link OnSecurityApplicationContextHolderStrategy#clearContext()}的清空上下文方法
     */
    public static void clearContext() {
        contextHolderStrategy.clearContext();
    }

    /**
     * 设置应用上下文持有者加载策略
     *
     * @param contextHolderStrategy {@link OnSecurityApplicationContextHolderStrategy}实现类实例
     */
    public static void setContextHolderStrategy(OnSecurityApplicationContextHolderStrategy contextHolderStrategy) {
        Assert.notNull(contextHolderStrategy, "contextHolderStrategy cannot be null");
        OnSecurityApplicationContextHolder.contextHolderStrategy = contextHolderStrategy;
    }
}
