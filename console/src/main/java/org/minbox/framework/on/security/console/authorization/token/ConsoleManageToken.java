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

package org.minbox.framework.on.security.console.authorization.token;

import org.springframework.security.oauth2.core.AbstractOAuth2Token;

import java.time.Instant;

/**
 * 管理令牌
 *
 * @author 恒宇少年
 * @since 0.0.9
 */
public class ConsoleManageToken extends AbstractOAuth2Token {
    private String original;

    public ConsoleManageToken(String tokenValue, String original, Instant issuedAt, Instant expiresAt) {
        super(tokenValue, issuedAt, expiresAt);
        this.original = original;
    }

    public String getOriginal() {
        return original;
    }
}
