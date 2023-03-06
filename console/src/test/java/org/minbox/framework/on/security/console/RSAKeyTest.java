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

package org.minbox.framework.on.security.console;

import com.nimbusds.jose.jwk.RSAKey;
import org.apache.tomcat.util.codec.binary.Base64;
import org.minbox.framework.on.security.core.authorization.util.RSAKeyUtils;
import org.springframework.security.crypto.keygen.Base64StringKeyGenerator;
import org.springframework.security.crypto.keygen.StringKeyGenerator;

import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

/**
 * RSA加密、解密测试类
 *
 * @author 恒宇少年
 */
public class RSAKeyTest {
    public static void main(String[] args) throws Exception {
        KeyPair keyPair = RSAKeyUtils.generateRandomRSAKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // @formatter:off
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();

        // @formatter:on
        // 加密原始内容
        StringKeyGenerator manageTokenGenerator =
                new Base64StringKeyGenerator(java.util.Base64.getUrlEncoder().withoutPadding(), 128);
        String original = "1";//manageTokenGenerator.generateKey();
        // 公钥加密
        Cipher publicCipher = Cipher.getInstance("RSA");
        publicCipher.init(Cipher.ENCRYPT_MODE, rsaKey.toRSAPublicKey());
        String encryption = Base64.encodeBase64String(publicCipher.doFinal(original.getBytes("UTF-8")));
        System.out.println("原文：" + original);
        System.out.println("加密后：" + encryption);

        // 私钥解密
        Cipher privateCipher = Cipher.getInstance("RSA");
        privateCipher.init(Cipher.DECRYPT_MODE, rsaKey.toRSAPrivateKey());
        byte[] encryptionByte = Base64.decodeBase64(encryption.getBytes("UTF-8"));
        String decrypt = new String(privateCipher.doFinal(encryptionByte));
        System.out.println("解密后：" + decrypt);
    }
}
