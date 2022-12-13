package org.minbox.framework.on.security.authorization.server.utils;

import org.minbox.framework.on.security.authorization.server.oauth2.authentication.OnSecurityError;
import org.minbox.framework.on.security.authorization.server.oauth2.authentication.OnSecurityErrorCodes;
import org.minbox.framework.on.security.authorization.server.oauth2.authentication.OnSecurityOAuth2AuthenticationException;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

/**
 * {@link OnSecurityOAuth2AuthenticationException}抛出异常工具类
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class OnSecurityThrowErrorUtils {
    /**
     * 默认的异常帮助路径
     */
    public static final String DEFAULT_HELP_URI = "https://github.com/On-Security/on-security/issues";
    /**
     * 默认的错误描述格式
     */
    private static final String DEFAULT_DESCRIPTION_FORMAT = "Parameters: %s, related validation failed.";

    public static void throwError(OnSecurityErrorCodes errorCode, String parameterName) {
        Assert.hasText(parameterName, "parameterName cannot be empty.");
        throwError(errorCode, parameterName, String.format(DEFAULT_DESCRIPTION_FORMAT, parameterName));
    }

    public static void throwError(OnSecurityErrorCodes errorCode, String parameterName, String description) {
        Assert.hasText(parameterName, "parameterName cannot be empty.");
        Assert.hasText(description, "description cannot be empty.");
        throwError(errorCode, parameterName, description, DEFAULT_HELP_URI);
    }

    public static void throwError(OnSecurityErrorCodes errorCode, String parameterName, String description, String errorUri) {
        Assert.notNull(errorCode, "errorCode cannot be null.");
        errorUri = ObjectUtils.isEmpty(errorUri) ? DEFAULT_HELP_URI : errorUri;
        OnSecurityError error = new OnSecurityError(errorCode.getValue(), parameterName, description, errorUri);
        throw new OnSecurityOAuth2AuthenticationException(error);
    }

}
