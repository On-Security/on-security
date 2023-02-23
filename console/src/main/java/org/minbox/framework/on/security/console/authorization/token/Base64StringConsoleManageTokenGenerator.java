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

import org.springframework.security.crypto.keygen.Base64StringKeyGenerator;
import org.springframework.security.crypto.keygen.StringKeyGenerator;

import java.time.Instant;
import java.util.Base64;

/**
 * Base64字符串生成管理令牌
 *
 * @author 恒宇少年
 * @since 0.0.9
 */
public final class Base64StringConsoleManageTokenGenerator implements ConsoleManageTokenGenerator {
    private final StringKeyGenerator manageTokenGenerator =
            new Base64StringKeyGenerator(Base64.getUrlEncoder().withoutPadding(), 256);
    @Override
    public ConsoleManageToken generator(ConsoleManageTokenContext context) {
        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plus(context.getManageTokenTimeToLive());
        return new ConsoleManageToken(this.manageTokenGenerator.generateKey(), issuedAt, expiresAt);
    }
}
