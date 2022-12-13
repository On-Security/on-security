package org.minbox.framework.on.security.authorization.server.jose;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

/**
 * Key生成工具类
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class KeyGeneratorUtils {
    private KeyGeneratorUtils() {
    }

    static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }
}
