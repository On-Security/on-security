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

package org.minbox.framework.on.security.core.authorization.util;

import javax.crypto.Cipher;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * RSA密钥工具类
 *
 * @author 恒宇少年
 * @since 0.1.0
 */
public final class RSAKeyUtils {
    public static final String ALGORITHM = "RSA";
    public static final int KEY_SIZE = 2048;
    private static final String PRIVATE_KEY_PREFIX = "-----BEGIN PRIVATE KEY-----";
    private static final String PRIVATE_KEY_SUFFIX = "-----END PRIVATE KEY-----";
    private static final String PUBLIC_KEY_PREFIX = "-----BEGIN PUBLIC KEY-----";
    private static final String PUBLIC_KEY_SUFFIX = "-----END PUBLIC KEY-----";


    /**
     * 生成随机的RSA密钥对
     *
     * @return {@link KeyPair}
     */
    public static KeyPair generateRandomRSAKeyPair() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
            keyPairGenerator.initialize(KEY_SIZE);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }

    /**
     * 读取Pem格式的私钥文件内容
     *
     * @param filename 文件地址
     * @return {@link PrivateKey}
     * @throws Exception
     */
    public static PrivateKey readPemPrivateKey(String filename) throws Exception {
        String key = new String(Files.readAllBytes(Paths.get(filename)), Charset.defaultCharset());
        // @formatter:off
        String privateKeyPEM = key
                .replace(PRIVATE_KEY_PREFIX, "")
                .replaceAll("\n", "")
                .replace(PRIVATE_KEY_SUFFIX, "");
        // @formatter:on
        byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);
        PKCS8EncodedKeySpec pkcs8 = new PKCS8EncodedKeySpec(encoded);
        KeyFactory kf = KeyFactory.getInstance(ALGORITHM);
        return kf.generatePrivate(pkcs8);
    }

    /**
     * 生成Pem格式的密钥对并保存到文件
     *
     * @param privateKeyFilename 私钥文件地址
     * @param publicKeyFilename  公钥文件地址
     * @throws Exception
     */
    public static void generatePemKeyPairToFile(String privateKeyFilename, String publicKeyFilename) throws Exception {
        KeyPair kp = generateRandomRSAKeyPair();
        // Save private key to file
        saveKeyValueToFile(privateKeyFilename, PRIVATE_KEY_PREFIX, Base64.getEncoder().encodeToString(kp.getPrivate().getEncoded()), PRIVATE_KEY_SUFFIX);
        // Save public key to file
        saveKeyValueToFile(publicKeyFilename, PUBLIC_KEY_PREFIX, Base64.getEncoder().encodeToString(kp.getPublic().getEncoded()), PUBLIC_KEY_SUFFIX);
    }

    /**
     * 将Key值写入到指定文件
     *
     * @param filename 文件名
     * @param prefix   Key的前缀
     * @param value    Key值
     * @param suffix   Key的后缀
     * @throws Exception
     */
    private static void saveKeyValueToFile(String filename, String prefix, String value, String suffix) throws Exception {
        File keyFile = new File(filename);
        if (!keyFile.exists()) {
            if (!keyFile.getParentFile().exists()) {
                keyFile.getParentFile().mkdirs();
            }
            keyFile.createNewFile();
        }
        StringBuffer keyString = new StringBuffer();
        keyString.append(prefix);
        keyString.append("\n");
        keyString.append(value);
        keyString.append("\n");
        keyString.append(suffix);
        com.google.common.io.Files.write(keyString.toString().getBytes(StandardCharsets.UTF_8), keyFile);
    }

    /**
     * 读取Pem格式的公钥文件内容
     *
     * @param filename 文件地址
     * @return {@link PublicKey}
     * @throws Exception
     */
    public static PublicKey readPemPublicKey(String filename) throws Exception {
        String key = new String(Files.readAllBytes(Paths.get(filename)), Charset.defaultCharset());
        // @formatter:off
        String publicKeyPEM = key
                .replace(PUBLIC_KEY_PREFIX, "")
                .replaceAll(System.lineSeparator(), "")
                .replace(PUBLIC_KEY_SUFFIX, "");
        // @formatter:on
        byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);

        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 使用公钥加密字符串
     *
     * @param publicKey 公钥
     * @param original  原文
     * @return 加密后字符串
     * @throws Exception
     */
    public static String encryptionByPublicKey(RSAPublicKey publicKey, String original) throws Exception {
        Cipher publicCipher = Cipher.getInstance(ALGORITHM);
        publicCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return Base64.getEncoder().encodeToString(publicCipher.doFinal(original.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * 使用私钥解密
     *
     * @param privateKey 私钥
     * @param encryption 加密字符串
     * @return 解密后的原文
     * @throws Exception
     */
    public static String decryptByPrivateKey(RSAPrivateKey privateKey, String encryption) throws Exception {
        Cipher privateCipher = Cipher.getInstance(ALGORITHM);
        privateCipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] encryptionByte = Base64.getDecoder().decode(encryption.getBytes(StandardCharsets.UTF_8));
        return new String(privateCipher.doFinal(encryptionByte));
    }
}
