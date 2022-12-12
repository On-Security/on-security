package org.minbox.framework.on.security.authorization.server.oauth2.authentication;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;

/**
 * OAuth2认证异常定义
 *
 * @author 恒宇少年
 * @see OAuth2AuthenticationException
 * @since 0.0.1
 */
public class OnSecurityOAuth2AuthenticationException extends OAuth2AuthenticationException {
    public OnSecurityOAuth2AuthenticationException(OAuth2Error error) {
        super(error);
    }

    public OnSecurityOAuth2AuthenticationException(OAuth2Error error, Throwable cause) {
        super(error, cause);
    }
}
