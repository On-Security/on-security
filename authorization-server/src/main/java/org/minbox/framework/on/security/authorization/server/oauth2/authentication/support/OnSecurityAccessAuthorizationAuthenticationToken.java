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

package org.minbox.framework.on.security.authorization.server.oauth2.authentication.support;

import org.minbox.framework.on.security.core.authorization.data.application.UserAuthorizationApplication;
import org.minbox.framework.on.security.core.authorization.data.attribute.UserAuthorizationAttribute;
import org.minbox.framework.on.security.core.authorization.data.resource.UserAuthorizationResource;
import org.minbox.framework.on.security.core.authorization.data.role.UserAuthorizationRole;
import org.minbox.framework.on.security.core.authorization.data.session.SecuritySession;
import org.minbox.framework.on.security.core.authorization.data.user.SecurityUser;
import org.minbox.framework.on.security.core.authorization.endpoint.AccessTokenAuthorization;
import org.minbox.framework.on.security.core.authorization.endpoint.AccessTokenSession;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;

/**
 * 获取请求授权信息认证响应实体
 *
 * @author 恒宇少年
 * @since 0.0.5
 */
public class OnSecurityAccessAuthorizationAuthenticationToken extends AbstractAuthenticationToken {
    /**
     * 用户基本信息
     */
    private SecurityUser user;
    /**
     * AccessToken所属的会话
     */
    private SecuritySession session;
    /**
     * AccessToken所属用户授权全部角色的全部授权的资源列表
     */
    private List<UserAuthorizationResource> userAuthorizationResourceList;
    /**
     * AccessToken所属用户授权的属性列表
     */
    private List<UserAuthorizationAttribute> userAuthorizationAttributeList;
    /**
     * AccessToken所属用户授权的角色列表
     */
    private List<UserAuthorizationRole> userAuthorizationRoleList;
    /**
     * AccessToken所属用户授权的应用列表
     */
    private List<UserAuthorizationApplication> userAuthorizationApplicationList;

    private OnSecurityAccessAuthorizationAuthenticationToken() {
        super(Collections.emptyList());
    }

    @Override
    public Object getCredentials() {
        return this.user;
    }

    @Override
    public Object getPrincipal() {
        return this.session;
    }

    public SecurityUser getUser() {
        return user;
    }

    public SecuritySession getSession() {
        return session;
    }

    public List<UserAuthorizationResource> getUserAuthorizationResourceList() {
        return userAuthorizationResourceList;
    }

    public List<UserAuthorizationAttribute> getUserAuthorizationAttributeList() {
        return userAuthorizationAttributeList;
    }

    public List<UserAuthorizationRole> getUserAuthorizationRoleList() {
        return userAuthorizationRoleList;
    }

    public List<UserAuthorizationApplication> getUserAuthorizationApplicationList() {
        return userAuthorizationApplicationList;
    }

    /**
     * 转换成令牌授权端点响应实体
     *
     * @return {@link AccessTokenAuthorization}
     */
    public AccessTokenAuthorization toEndpointResponse() {
        // @formatter:off
        AccessTokenSession accessTokenSession =
                AccessTokenSession.withIssuedAt(this.session.getAccessTokenIssuedAt())
                        .state(this.session.getSessionState())
                        .accessTokenExpiresAt(this.session.getAccessTokenExpiresAt())
                        .accessTokenScopes(this.session.getAccessTokenScopes())
                        .build();
        AccessTokenAuthorization.Builder builder =
                AccessTokenAuthorization.withUser(this.user)
                        .session(accessTokenSession)
                        .userAuthorizationResource(this.userAuthorizationResourceList)
                        .userAuthorizationAttribute(this.userAuthorizationAttributeList)
                        .userAuthorizationRole(this.userAuthorizationRoleList)
                        .userAuthorizationApplication(this.userAuthorizationApplicationList);
        // @formatter:on
        return builder.build();
    }

    public static Builder withUserAndSession(SecurityUser user, SecuritySession session) {
        Assert.notNull(user, "SecurityUser cannot be null.");
        Assert.notNull(session, "SecuritySession cannot be null.");
        return new Builder(user, session);
    }

    /**
     * The {@link OnSecurityAccessAuthorizationAuthenticationToken} Builder
     *
     * @since 0.0.5
     */
    public static class Builder {
        private SecurityUser user;
        private SecuritySession session;
        private List<UserAuthorizationResource> userAuthorizationResourceList;
        private List<UserAuthorizationAttribute> userAuthorizationAttributeList;
        private List<UserAuthorizationRole> userAuthorizationRoleList;
        private List<UserAuthorizationApplication> userAuthorizationApplicationList;

        public Builder(SecurityUser user, SecuritySession session) {
            this.user = user;
            this.session = session;
            this.userAuthorizationResourceList = Collections.emptyList();
            this.userAuthorizationAttributeList = Collections.emptyList();
            this.userAuthorizationApplicationList = Collections.emptyList();
        }

        public Builder userAuthorizationResourceList(List<UserAuthorizationResource> roleAuthorizationResourceList) {
            this.userAuthorizationResourceList = roleAuthorizationResourceList;
            return this;
        }

        public Builder userAuthorizationAttributeList(List<UserAuthorizationAttribute> userAuthorizationAttributeList) {
            this.userAuthorizationAttributeList = userAuthorizationAttributeList;
            return this;
        }

        public Builder userAuthorizationRoleList(List<UserAuthorizationRole> userAuthorizationRoleList) {
            this.userAuthorizationRoleList = userAuthorizationRoleList;
            return this;
        }

        public Builder userAuthorizationApplicationList(List<UserAuthorizationApplication> userAuthorizationApplicationList) {
            this.userAuthorizationApplicationList = userAuthorizationApplicationList;
            return this;
        }

        public OnSecurityAccessAuthorizationAuthenticationToken build() {
            OnSecurityAccessAuthorizationAuthenticationToken token = new OnSecurityAccessAuthorizationAuthenticationToken();
            token.user = this.user;
            token.session = this.session;
            token.userAuthorizationResourceList = this.userAuthorizationResourceList;
            token.userAuthorizationAttributeList = this.userAuthorizationAttributeList;
            token.userAuthorizationRoleList = this.userAuthorizationRoleList;
            token.userAuthorizationApplicationList = this.userAuthorizationApplicationList;
            return token;
        }
    }
}
