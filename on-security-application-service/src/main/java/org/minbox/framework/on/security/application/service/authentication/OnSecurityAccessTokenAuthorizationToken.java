package org.minbox.framework.on.security.application.service.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.util.Assert;

import java.util.Collections;

/**
 * 引用资源认证请求实体
 *
 * @author 恒宇少年
 * @since 0.0.6
 */
public final class OnSecurityAccessTokenAuthorizationToken extends AbstractAuthenticationToken {
    /**
     * 请求时携带的"AccessToken"
     */
    private String accessToken;
    /**
     * 请求的会话ID
     */
    private String requestSessionId;
    /**
     * 请求的资源路径
     */
    private String requestUri;


    public OnSecurityAccessTokenAuthorizationToken(String accessToken, String requestUri, String requestSessionId) {
        super(Collections.emptyList());
        Assert.hasText(requestUri, "requestUri cannot be empty");
        this.accessToken = accessToken;
        this.requestUri = requestUri;
        this.requestSessionId = requestSessionId;
    }

    @Override
    public Object getCredentials() {
        return accessToken;
    }

    @Override
    public Object getPrincipal() {
        return accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRequestSessionId() {
        return requestSessionId;
    }

    public String getRequestUri() {
        return requestUri;
    }
}
