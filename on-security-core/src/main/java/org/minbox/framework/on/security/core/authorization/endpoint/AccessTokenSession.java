package org.minbox.framework.on.security.core.authorization.endpoint;

import org.minbox.framework.on.security.core.authorization.SessionState;
import org.minbox.framework.on.security.core.authorization.util.OnSecurityVersion;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * AccessToken令牌所属的会话基本信息
 *
 * @author 恒宇少年
 * @since 0.0.7
 */
public class AccessTokenSession implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
    private SessionState state;
    private LocalDateTime accessTokenIssuedAt;
    private LocalDateTime accessTokenExpiresAt;
    private Set<String> accessTokenScopes;

    /**
     * 获取会话状态
     *
     * @return {@link SessionState}
     */
    public SessionState getState() {
        return this.state;
    }

    /**
     * 获取授权服务器发布AccessToken的时间
     *
     * @return AccessToken发布时间
     */
    public LocalDateTime getAccessTokenIssuedAt() {
        return this.accessTokenIssuedAt;
    }

    /**
     * 获取AccessToken的过期时间
     *
     * @return AccessToken过期时间
     */
    public LocalDateTime getAccessTokenExpiresAt() {
        return this.accessTokenExpiresAt;
    }

    /**
     * 获取授权服务器发布AccessToken时所授权的"Scopes"
     *
     * @return AccessToken授权的"Scopes"
     */
    public Set<String> getAccessTokenScopes() {
        return this.accessTokenScopes;
    }

    public static Builder withIssuedAt(LocalDateTime accessTokenIssuedAt) {
        Assert.notNull(accessTokenIssuedAt, "accessTokenIssuedAt cannot be null");
        return new Builder(accessTokenIssuedAt);
    }

    /**
     * The {@link AccessTokenSession} Builder
     */
    public static class Builder implements Serializable {
        private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
        private SessionState state;
        private LocalDateTime accessTokenIssuedAt;
        private LocalDateTime accessTokenExpiresAt;
        private Set<String> accessTokenScopes;

        public Builder(LocalDateTime accessTokenIssuedAt) {
            this.accessTokenIssuedAt = accessTokenIssuedAt;
        }

        public Builder state(SessionState state) {
            this.state = state;
            return this;
        }

        public Builder accessTokenExpiresAt(LocalDateTime accessTokenExpiresAt) {
            this.accessTokenExpiresAt = accessTokenExpiresAt;
            return this;
        }

        public Builder accessTokenScopes(Set<String> accessTokenScopes) {
            this.accessTokenScopes = accessTokenScopes;
            return this;
        }

        public AccessTokenSession build() {
            AccessTokenSession tokenSession = new AccessTokenSession();
            tokenSession.state = this.state;
            tokenSession.accessTokenIssuedAt = this.accessTokenIssuedAt;
            tokenSession.accessTokenExpiresAt = this.accessTokenExpiresAt;
            tokenSession.accessTokenScopes = this.accessTokenScopes;
            return tokenSession;
        }
    }
}
