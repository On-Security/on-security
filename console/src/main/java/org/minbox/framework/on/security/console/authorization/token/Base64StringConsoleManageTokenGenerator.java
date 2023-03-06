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

import com.nimbusds.jose.jwk.RSAKey;
import org.minbox.framework.on.security.core.authorization.exception.OnSecurityError;
import org.minbox.framework.on.security.core.authorization.exception.OnSecurityErrorCodes;
import org.minbox.framework.on.security.core.authorization.exception.OnSecurityOAuth2AuthenticationException;
import org.minbox.framework.on.security.core.authorization.util.OnSecurityThrowErrorUtils;
import org.minbox.framework.on.security.core.authorization.util.RSAKeyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.keygen.Base64StringKeyGenerator;
import org.springframework.security.crypto.keygen.StringKeyGenerator;

import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Base64;

/**
 * Base64字符串生成管理令牌
 *
 * @author 恒宇少年
 * @since 0.0.9
 */
public final class Base64StringConsoleManageTokenGenerator implements ConsoleManageTokenGenerator {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(Base64StringConsoleManageTokenGenerator.class);
    private final StringKeyGenerator base64StringKeyGenerator =
            new Base64StringKeyGenerator(Base64.getUrlEncoder().withoutPadding(), 128);
    private RSAKey rsaKey;

    public Base64StringConsoleManageTokenGenerator(RSAKey rsaKey) {
        this.rsaKey = rsaKey;
    }

    @Override
    public ConsoleManageToken generator(ConsoleManageTokenContext context) {
        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plus(context.getManageTokenTimeToLive());
        String original = this.base64StringKeyGenerator.generateKey();
        String encryption = this.encryption(original);
        return new ConsoleManageToken(encryption, original, issuedAt, expiresAt);
    }

    private String encryption(String base64StringKey) {
        try {
            RSAPublicKey publicKey = this.rsaKey.toRSAPublicKey();
            return RSAKeyUtils.encryptionByPublicKey(publicKey, base64StringKey);
        } catch (Exception e) {
            logger.error("An exception was encountered while generating encrypted manageToken.", e);
        }
        // @formatter:off
        OnSecurityError onSecurityError = new OnSecurityError(OnSecurityErrorCodes.UNKNOWN_EXCEPTION.getValue(),
                null,
                "An exception was encountered while generating encrypted manageToken.",
                OnSecurityThrowErrorUtils.DEFAULT_HELP_URI);
        // @formatter:on
        throw new OnSecurityOAuth2AuthenticationException(onSecurityError);
    }
}
