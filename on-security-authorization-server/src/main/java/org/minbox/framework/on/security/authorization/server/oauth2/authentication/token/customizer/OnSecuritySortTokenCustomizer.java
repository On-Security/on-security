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

package org.minbox.framework.on.security.authorization.server.oauth2.authentication.token.customizer;

import org.springframework.core.Ordered;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

/**
 * OAuth令牌支持根据{@link #getOrder()}方法顺序排序的{@link OAuth2TokenCustomizer}
 *
 * @author 恒宇少年
 * @see OAuth2TokenCustomizer
 * @see Ordered
 * @since 0.0.4
 */
public interface OnSecuritySortTokenCustomizer<T extends OAuth2TokenContext> extends OAuth2TokenCustomizer<T>, Ordered {
    @Override
    default int getOrder() {
        return LOWEST_PRECEDENCE;
    }
}
