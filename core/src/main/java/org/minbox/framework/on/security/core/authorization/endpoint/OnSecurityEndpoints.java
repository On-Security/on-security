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

package org.minbox.framework.on.security.core.authorization.endpoint;

/**
 * On-Security内部提供的端点列表
 *
 * @author 恒宇少年
 * @since 0.1.0
 */
public interface OnSecurityEndpoints {
    /**
     * 获取用户访问令牌（Access Token）
     */
    String ACCESS_TOKEN_ENDPOINT = "/access/token";
    /**
     * 撤销用户访问令牌（Access Token）
     */
    String ACCESS_TOKEN_REVOKE_ENDPOINT = "/access/token/revoke";
    /**
     * 查询用户访问令牌（Access Token）信息
     */
    String ACCESS_TOKEN_INTROSPECT_ENDPOINT = "/access/token/introspect";
    /**
     * 访问授权授权
     */
    String AUTHORIZATION_ENDPOINT = "/access/authorize";
    /**
     * OIDC协议客户端注册
     */
    String OIDC_CONNECT_REGISTER_ENDPOINT = "/oidc/connect/register";
    /**
     * OIDC协议获取访问令牌（Access Token）所属的用户信息
     */
    String OIDC_ME_ENDPOINT = "/oidc/me";
    /**
     * Json Web Key 集合
     */
    String JWK_SET_ENDPOINT = "/jwks";
    /**
     * 获取管理令牌（Manage Token）
     */
    String MANAGE_TOKEN_ENDPOINT = "/manage/token";
    /**
     * 获取管理令牌（Manage Token）访问授权信息端点地址
     */
    String MANAGE_TOKEN_ACCESS_AUTHORIZATION_ENDPOINT = "/manage/token/access/authorization";
}
