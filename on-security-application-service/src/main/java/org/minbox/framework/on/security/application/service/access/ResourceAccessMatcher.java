package org.minbox.framework.on.security.application.service.access;

import javax.servlet.http.HttpServletRequest;

/**
 * 资源访问匹配器
 * <p>
 * 对"RBAC"、"ABAC"等多种资源访问控制（Access Control）方式的接口定义
 *
 * @author 恒宇少年
 * @see ResourceRoleBasedAccessControlMatcher
 * @since 0.0.7
 */
public interface ResourceAccessMatcher {
    /**
     * 匹配指定请求，判定是否允许访问
     *
     * @param request 等待匹配验证的请求
     * @return 返回true时标识请求URI允许被访问，否者拒绝访问
     */
    boolean match(HttpServletRequest request);
}
