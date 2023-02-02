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
