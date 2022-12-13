package org.minbox.framework.on.security.authorization.server.oauth2.authentication;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

/**
 * OAuth2认证异常定义
 *
 * @author 恒宇少年
 * @see OAuth2AuthenticationException
 * @since 0.0.1
 */
public class OnSecurityOAuth2AuthenticationException extends AuthenticationException {
    private OnSecurityError onSecurityError;

    public OnSecurityOAuth2AuthenticationException(OnSecurityError onSecurityError, String msg, Throwable cause) {
        super(msg, cause);
        this.onSecurityError = onSecurityError;
    }

    public OnSecurityOAuth2AuthenticationException(OnSecurityError onSecurityError) {
        super(onSecurityError.getDescription());
        this.onSecurityError = onSecurityError;
    }

    public OnSecurityError getOnSecurityError() {
        return onSecurityError;
    }
}
