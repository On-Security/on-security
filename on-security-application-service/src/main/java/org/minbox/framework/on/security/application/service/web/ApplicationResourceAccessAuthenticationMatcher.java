/*
 *     Copyright (C) 2022  恒宇少年
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.minbox.framework.on.security.application.service.web;

import org.minbox.framework.on.security.application.service.authentication.context.OnSecurityApplicationContext;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

/**
 * 应用资源访问匹配者
 * <p>
 * 根据应用上下文{@link OnSecurityApplicationContext}来验证访问资源是否授权给访问用户
 *
 * @author 恒宇少年
 * @see OnSecurityApplicationContext
 * @since 0.0.7
 */
public class ApplicationResourceAccessAuthenticationMatcher implements RequestMatcher {
    private OnSecurityApplicationContext applicationContext;

    public ApplicationResourceAccessAuthenticationMatcher(OnSecurityApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        // TODO 根据授权资源，验证访问资源地址
        return false;
    }
}
