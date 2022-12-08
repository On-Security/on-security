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

package org.minbox.framework.on.security.authorization.server.oauth2.config.configurers;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * On-Security提供的OAuth2协议的配置接口
 *
 * @author 恒宇少年
 * @see HttpSecurity
 * @since 0.0.1
 */
public interface OnSecurityOAuth2Configurer {
    /**
     * 初始化认证配置
     *
     * @param httpSecurity {@link HttpSecurity}
     */
    void init(HttpSecurity httpSecurity);

    /**
     * 配置认证
     *
     * @param httpSecurity {@link HttpSecurity}
     */
    void configure(HttpSecurity httpSecurity);
}
