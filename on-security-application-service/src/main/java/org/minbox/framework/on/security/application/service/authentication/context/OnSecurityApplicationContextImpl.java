package org.minbox.framework.on.security.application.service.authentication.context;

import org.minbox.framework.on.security.core.authorization.data.attribute.UserAuthorizationAttribute;
import org.minbox.framework.on.security.core.authorization.data.resource.UserAuthorizationResource;
import org.minbox.framework.on.security.core.authorization.data.role.UserAuthorizationRole;
import org.minbox.framework.on.security.core.authorization.endpoint.AccessAuthorizationEndpointResponse;

import java.util.List;
import java.util.Map;

/**
 * 应用上下文{@link OnSecurityApplicationContext}实现类
 *
 * @author 恒宇少年
 * @see AccessAuthorizationEndpointResponse
 * @see UserAuthorizationResource
 * @see UserAuthorizationAttribute
 * @see UserAuthorizationRole
 * @see AccessAuthorizationEndpointResponse.Session
 * @since 0.0.6
 */
public class OnSecurityApplicationContextImpl implements OnSecurityApplicationContext {
    private String accessToken;
    private Map<String, Object> user;
    private AccessAuthorizationEndpointResponse.Session session;
    private List<UserAuthorizationResource> userAuthorizationResource;
    private List<UserAuthorizationAttribute> userAuthorizationAttribute;
    private List<UserAuthorizationRole> userAuthorizationRole;

    private OnSecurityApplicationContextImpl() {
    }

    public static OnSecurityApplicationContextImpl createEmptyContext() {
        return new OnSecurityApplicationContextImpl();
    }

    public static OnSecurityApplicationContextImpl.Builder withAuthorizationEndpoint(AccessAuthorizationEndpointResponse endpointResponse) {
        // @formatter:off
        OnSecurityApplicationContextImpl.Builder builder =
                new Builder(endpointResponse.getSession())
                        .user(endpointResponse.getUser())
                        .userAuthorizationResource(endpointResponse.getUserAuthorizationResource())
                        .userAuthorizationAttribute(endpointResponse.getUserAuthorizationAttribute())
                        .userAuthorizationRole(endpointResponse.getUserAuthorizationRole());
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
    public AccessAuthorizationEndpointResponse.Session getSession() {
        return this.session;
    }

    @Override
    public List<UserAuthorizationResource> getUserAuthorizationResource() {
        return this.userAuthorizationResource;
    }

    @Override
    public List<UserAuthorizationAttribute> getUserAuthorizationAttribute() {
        return this.userAuthorizationAttribute;
    }

    @Override
    public List<UserAuthorizationRole> getUserAuthorizationRole() {
        return this.userAuthorizationRole;
    }

    /**
     * The {@link OnSecurityApplicationContextImpl} Builder
     */
    public static class Builder {
        private String accessToken;
        private Map<String, Object> user;
        private AccessAuthorizationEndpointResponse.Session session;
        private List<UserAuthorizationResource> userAuthorizationResource;
        private List<UserAuthorizationAttribute> userAuthorizationAttribute;
        private List<UserAuthorizationRole> userAuthorizationRole;

        public Builder(AccessAuthorizationEndpointResponse.Session session) {
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
