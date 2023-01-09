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
