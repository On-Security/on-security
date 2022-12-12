package org.minbox.framework.on.security.authorization.server.utils;

import org.minbox.framework.on.security.authorization.server.oauth2.authentication.OnSecurityOAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;

/**
 * {@link OnSecurityOAuth2AuthenticationException}抛出异常工具类
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class OnSecurityThrowErrorUtils {
    
    public static void throwError(String errorCode, String parameterName) {
        throwError(errorCode, parameterName, null);
    }

    public static void throwError(String errorCode, String parameterName, String errorUri) {
        OAuth2Error error = new OAuth2Error(errorCode, "OAuth 2.0 Parameter: " + parameterName, errorUri);
        throw new OnSecurityOAuth2AuthenticationException(error);
    }
}
