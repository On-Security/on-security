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

package org.minbox.framework.on.security.console.authorization.web.converter;

/**
 * 管理令牌参数名定义
 *
 * @author 恒宇少年
 * @since 0.0.9
 */
public interface ManageTokenParameterNames {
    /**
     * Manage Token
     */
    String MANAGE_TOKEN = "manage_token";
    /**
     * Manage Token 剩余过期时间
     */
    String EXPIRES_IN = "expires_in";
    /**
     * Manage Token认证类型
     */
    String AUTHENTICATE_TYPE = "authenticate_type";
}
