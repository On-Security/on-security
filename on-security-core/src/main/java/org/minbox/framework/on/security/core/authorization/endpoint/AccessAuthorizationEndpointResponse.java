/*
 *     Copyright (C) 2022  恒宇少年
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.minbox.framework.on.security.core.authorization.endpoint;

import org.minbox.framework.on.security.core.authorization.SessionState;
import org.minbox.framework.on.security.core.authorization.data.attribute.UserAuthorizationAttribute;
import org.minbox.framework.on.security.core.authorization.data.resource.UserAuthorizationResource;
import org.minbox.framework.on.security.core.authorization.data.role.UserAuthorizationRole;
import org.minbox.framework.on.security.core.authorization.data.user.SecurityUser;
import org.minbox.framework.on.security.core.authorization.util.MapUtils;
import org.minbox.framework.on.security.core.authorization.util.OnSecurityVersion;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 令牌所属用户授权访问的响应实体定义
 *
 * @author 恒宇少年
 * @see org.minbox.framework.on.security.core.authorization.data.resource.SecurityResource
 * @see org.minbox.framework.on.security.core.authorization.data.resource.SecurityResourceUri
 * @see org.minbox.framework.on.security.core.authorization.data.attribute.SecurityAttribute
 * @since 0.0.5
 */
public final class AccessAuthorizationEndpointResponse implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
    private Map<String, Object> user;
    private Session session;
    private List<UserAuthorizationResource> userAuthorizationResource;
    private List<UserAuthorizationAttribute> userAuthorizationAttribute;
    private List<UserAuthorizationRole> userAuthorizationRole;

    private AccessAuthorizationEndpointResponse() {
    }

    public Map<String, Object> getUser() {
        return user;
    }

    public Session getSession() {
        return session;
    }

    public List<UserAuthorizationResource> getUserAuthorizationResource() {
        return userAuthorizationResource;
    }

    public List<UserAuthorizationAttribute> getUserAuthorizationAttribute() {
        return userAuthorizationAttribute;
    }

    public List<UserAuthorizationRole> getUserAuthorizationRole() {
        return userAuthorizationRole;
    }

    public static Builder withUser(SecurityUser user) {
        Assert.notNull(user, "user cannot be null");
        return new Builder(user);
    }

    /**
     * The {@link AccessAuthorizationEndpointResponse} Builder
     */
    public static class Builder implements Serializable {
        private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
        // @formatter:off
        private static final List<String> USER_TO_MAP_IGNORE_KEYS = Arrays.asList(
                "serialVersionUID",
                "password",
                "authorizeClients",
                "authorizeConsents",
                "authorizeRoles",
                "groups"
        );
        // @formatter:on
        private SecurityUser securityUser;
        private Session session;
        private List<UserAuthorizationResource> userAuthorizationResource;
        private List<UserAuthorizationAttribute> userAuthorizationAttribute;
        private List<UserAuthorizationRole> userAuthorizationRole;

        public Builder(SecurityUser user) {
            this.securityUser = user;
        }

        public Builder session(Session session) {
            this.session = session;
            return this;
        }

        public Builder userAuthorizationResource(List<UserAuthorizationResource> userAuthorizationResource) {
            this.userAuthorizationResource = userAuthorizationResource;
            return this;
        }

        public Builder userAuthorizationAttribute(List<UserAuthorizationAttribute> userAuthorizationAttribute) {
            this.userAuthorizationAttribute = userAuthorizationAttribute;
            return this;
        }

        public Builder userAuthorizationRole(List<UserAuthorizationRole> userAuthorizationRole) {
            this.userAuthorizationRole = userAuthorizationRole;
            return this;
        }

        public AccessAuthorizationEndpointResponse build() {
            AccessAuthorizationEndpointResponse endpointResponse = new AccessAuthorizationEndpointResponse();
            endpointResponse.user = this.toUserMap();
            endpointResponse.session = this.session;
            endpointResponse.userAuthorizationResource = this.userAuthorizationResource;
            endpointResponse.userAuthorizationAttribute = this.userAuthorizationAttribute;
            endpointResponse.userAuthorizationRole = this.userAuthorizationRole;
            return endpointResponse;
        }

        /**
         * 将{@link SecurityUser}转换成Map集合
         *
         * @return Key为实体的字段，Value为字段的值
         */
        private Map<String, Object> toUserMap() {
            return MapUtils.objectToMap(this.securityUser, USER_TO_MAP_IGNORE_KEYS);
        }
    }

    /**
     * 会话响应实体
     */
    public static class Session implements Serializable {
        private SessionState state;
        private LocalDateTime accessTokenIssuedAt;
        private LocalDateTime accessTokenExpiresAt;
        private Set<String> accessTokenScopes;

        public Session(SessionState state, LocalDateTime accessTokenIssuedAt, LocalDateTime accessTokenExpiresAt, Set<String> accessTokenScopes) {
            this.state = state;
            this.accessTokenIssuedAt = accessTokenIssuedAt;
            this.accessTokenExpiresAt = accessTokenExpiresAt;
            this.accessTokenScopes = accessTokenScopes;
        }

        public SessionState getState() {
            return state;
        }

        public LocalDateTime getAccessTokenIssuedAt() {
            return accessTokenIssuedAt;
        }

        public LocalDateTime getAccessTokenExpiresAt() {
            return accessTokenExpiresAt;
        }

        public Set<String> getAccessTokenScopes() {
            return accessTokenScopes;
        }
    }
}
