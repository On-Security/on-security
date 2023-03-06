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

package org.minbox.framework.on.security.console.configuration;

import com.nimbusds.jose.KeySourceException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.minbox.framework.on.security.core.authorization.util.OnSecurityVersion;
import org.minbox.framework.on.security.core.authorization.util.RSAKeyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;

/**
 * 控制台服务{@link JWKSource}
 * <p>
 * 加载指定位置的"RSA密钥对"提供给授权服务器进行授权、鉴权使用，密钥对存储位置"{user.home}/on-security-console/key"
 *
 * @since 0.1.0
 */
public class OnSecurityConsoleServiceJwkSource implements JWKSource<SecurityContext> {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(AuthorizationServerConfiguration.class);
    private static final String KEY_ID = String.valueOf(OnSecurityVersion.SERIAL_VERSION_UID);
    private static final String USER_DIR = System.getProperty("user.home");
    private static final String KEY_PAIR_BASE_DIR = USER_DIR + File.separator + "on-security-console" + File.separator + "key";
    public static final String RSA_PRIVATE_PEM_FILE_PATH = KEY_PAIR_BASE_DIR + File.separator + "rsa_private.pem";
    public static final String RSA_PUBLIC_PEM_FILE_PATH = KEY_PAIR_BASE_DIR + File.separator + "rsa_public.pem";
    private RSAKey rsaKey;

    @Override
    public List<JWK> get(JWKSelector jwkSelector, SecurityContext context) throws KeySourceException {
        try {
            JWKSet jwkSet = new JWKSet(this.getRsaKey());
            return jwkSelector.select(jwkSet);
        } catch (Exception e) {
            logger.error("An exception was encountered when jwk selector.", e);
        }
        return null;
    }

    private RSAKey loadRSAKeyFromPemFile() {
        try {
            PrivateKey privateKey = RSAKeyUtils.readPemPrivateKey(RSA_PRIVATE_PEM_FILE_PATH);
            PublicKey publicKey = RSAKeyUtils.readPemPublicKey(RSA_PUBLIC_PEM_FILE_PATH);
            // @formatter:off
            return new RSAKey.Builder((RSAPublicKey) publicKey)
                    .privateKey(privateKey)
                    .keyID(KEY_ID)
                    .build();
            // @formatter:on
        } catch (Exception e) {
            logger.error("build RSAKey exception.", e);
        }
        throw new RuntimeException("build RSAKey exception.");
    }

    public RSAKey getRsaKey() {
        if (rsaKey == null) {
            this.rsaKey = this.loadRSAKeyFromPemFile();
        }
        return rsaKey;
    }
}
