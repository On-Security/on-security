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

package org.minbox.framework.on.security.core.authorization.data.session;

import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;

/**
 * 会话数据存储库
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public interface SecuritySessionRepository {
    /**
     * 保存会话
     * <p>
     * 新增或更新会话基本信息，根据{@link SecuritySession#getId()}查询如果已经存在则执行更新否则执行新增
     *
     * @param session 会话实例 {@link SecuritySession}
     */
    void save(SecuritySession session);

    /**
     * 删除会话
     *
     * @param session 会话实例 {@link SecuritySession}
     */
    void remove(SecuritySession session);

    /**
     * 根据会话ID查询
     *
     * @param id 会话ID {@link SecuritySession#getId()}
     * @return 会话实例 {@link SecuritySession}
     */
    SecuritySession findById(String id);

    /**
     * 根据会话令牌查询
     *
     * @param token     令牌内容
     * @param tokenType 令牌类型 {@link OAuth2TokenType}
     * @return {@link SecuritySession}
     */
    SecuritySession findByToken(String token, OAuth2TokenType tokenType);
}
