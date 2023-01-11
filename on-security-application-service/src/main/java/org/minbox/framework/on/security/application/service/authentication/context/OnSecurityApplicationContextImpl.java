package org.minbox.framework.on.security.application.service.authentication.context;

import org.minbox.framework.on.security.application.service.authentication.AccessTokenAuthorization;
import org.minbox.framework.on.security.core.authorization.data.attribute.UserAuthorizationAttribute;
import org.minbox.framework.on.security.core.authorization.data.resource.UserAuthorizationResource;
import org.minbox.framework.on.security.core.authorization.data.role.UserAuthorizationRole;

import java.util.List;
import java.util.Map;

/**
 * 应用上下文{@link OnSecurityApplicationContext}实现类
 *
 * @author 恒宇少年
 * @see AccessTokenAuthorization
 * @see UserAuthorizationResource
 * @see UserAuthorizationAttribute
 * @see UserAuthorizationRole
 * @see AccessTokenAuthorization.AccessTokenSession
 * @since 0.0.6
 */
public class OnSecurityApplicationContextImpl implements OnSecurityApplicationContext {
    private String accessToken;
    private Map<String, Object> user;
    private AccessTokenAuthorization.AccessTokenSession session;
    private List<AccessTokenAuthorization.AuthorizationResource> userAuthorizationResource;
    private List<AccessTokenAuthorization.AuthorizationAttribute> userAuthorizationAttribute;
    private List<AccessTokenAuthorization.AuthorizationRole> userAuthorizationRole;

    private OnSecurityApplicationContextImpl() {
    }

    public static OnSecurityApplicationContextImpl createEmptyContext() {
        return new OnSecurityApplicationContextImpl();
    }

    public static OnSecurityApplicationContextImpl.Builder withAccessTokenAuthorization(AccessTokenAuthorization accessTokenAuthorization) {
        // @formatter:off
        OnSecurityApplicationContextImpl.Builder builder =
                new Builder(accessTokenAuthorization.getSession())
                        .user(accessTokenAuthorization.getUser())
                        .userAuthorizationResource(accessTokenAuthorization.getUserAuthorizationResource())
                        .userAuthorizationAttribute(accessTokenAuthorization.getUserAuthorizationAttribute())
                        .userAuthorizationRole(accessTokenAuthorization.getUserAuthorizationRole());
        // @formatter:on
        return builder;
    }

    @Override
    public String getAccessToken() {
        return this.accessToken;
    }

    @Override
    public Map<String, Object> getUserMetadata() {
        return this.user;
    }

    @Override
    public AccessTokenAuthorization.AccessTokenSession getSession() {
        return this.session;
    }

    @Override
    public List<AccessTokenAuthorization.AuthorizationResource> getUserAuthorizationResource() {
        return this.userAuthorizationResource;
    }

    @Override
    public List<AccessTokenAuthorization.AuthorizationAttribute> getUserAuthorizationAttribute() {
        return this.userAuthorizationAttribute;
    }

    @Override
    public List<AccessTokenAuthorization.AuthorizationRole> getUserAuthorizationRole() {
        return this.userAuthorizationRole;
    }

    /**
     * The {@link OnSecurityApplicationContextImpl} Builder
     */
    public static class Builder {
        private String accessToken;
        private Map<String, Object> user;
        private AccessTokenAuthorization.AccessTokenSession session;
        private List<AccessTokenAuthorization.AuthorizationResource> userAuthorizationResource;
        private List<AccessTokenAuthorization.AuthorizationAttribute> userAuthorizationAttribute;
        private List<AccessTokenAuthorization.AuthorizationRole> userAuthorizationRole;

        public Builder(AccessTokenAuthorization.AccessTokenSession session) {
            this.session = session;
        }

        public Builder user(Map<String, Object> user) {
            this.user = user;
            return this;
        }

        public Builder accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public Builder userAuthorizationResource(List<AccessTokenAuthorization.AuthorizationResource> userAuthorizationResource) {
            this.userAuthorizationResource = userAuthorizationResource;
            return this;
        }

        public Builder userAuthorizationAttribute(List<AccessTokenAuthorization.AuthorizationAttribute> userAuthorizationAttribute) {
            this.userAuthorizationAttribute = userAuthorizationAttribute;
            return this;
        }

        public Builder userAuthorizationRole(List<AccessTokenAuthorization.AuthorizationRole> userAuthorizationRole) {
            this.userAuthorizationRole = userAuthorizationRole;
            return this;
        }

        public OnSecurityApplicationContextImpl build() {
            OnSecurityApplicationContextImpl applicationContext = new OnSecurityApplicationContextImpl();
            applicationContext.accessToken = this.accessToken;
            applicationContext.user = this.user;
            applicationContext.session = this.session;
            applicationContext.userAuthorizationResource = this.userAuthorizationResource;
            applicationContext.userAuthorizationAttribute = this.userAuthorizationAttribute;
            applicationContext.userAuthorizationRole = this.userAuthorizationRole;
            return applicationContext;
        }
    }
}
