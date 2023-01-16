package org.minbox.framework.on.security.application.service.authentication.context;

import org.minbox.framework.on.security.core.authorization.data.attribute.UserAuthorizationAttribute;
import org.minbox.framework.on.security.core.authorization.data.resource.UserAuthorizationResource;
import org.minbox.framework.on.security.core.authorization.data.role.UserAuthorizationRole;
import org.minbox.framework.on.security.core.authorization.endpoint.AccessTokenAuthorization;
import org.minbox.framework.on.security.core.authorization.endpoint.AccessTokenSession;

import java.util.List;
import java.util.Map;

/**
 * 应用上下文{@link OnSecurityApplicationContext}实现类
 *
 * @author 恒宇少年
 * @see AccessTokenAuthorization
 * @see org.minbox.framework.on.security.core.authorization.data.resource.UserAuthorizationResource
 * @see org.minbox.framework.on.security.core.authorization.data.attribute.UserAuthorizationAttribute
 * @see org.minbox.framework.on.security.core.authorization.data.role.UserAuthorizationRole
 * @see AccessTokenSession
 * @since 0.0.6
 */
public class OnSecurityApplicationContextImpl implements OnSecurityApplicationContext {
    private String accessToken;
    private AccessTokenAuthorization accessTokenAuthorization;

    private OnSecurityApplicationContextImpl() {
    }

    public static OnSecurityApplicationContextImpl createEmptyContext() {
        return new OnSecurityApplicationContextImpl();
    }

    public static OnSecurityApplicationContextImpl.Builder withAccessToken(String accessToken) {
        return new OnSecurityApplicationContextImpl.Builder(accessToken);
    }

    @Override
    public String getAccessToken() {
        return this.accessToken;
    }

    @Override
    public Map<String, Object> getUserMetadata() {
        return this.accessTokenAuthorization.getUser();
    }

    @Override
    public AccessTokenSession getSession() {
        return this.accessTokenAuthorization.getSession();
    }

    @Override
    public List<UserAuthorizationResource> getUserAuthorizationResource() {
        return this.accessTokenAuthorization.getUserAuthorizationResource();
    }

    @Override
    public List<UserAuthorizationAttribute> getUserAuthorizationAttribute() {
        return this.accessTokenAuthorization.getUserAuthorizationAttribute();
    }

    @Override
    public List<UserAuthorizationRole> getUserAuthorizationRole() {
        return this.accessTokenAuthorization.getUserAuthorizationRole();
    }

    /**
     * The {@link OnSecurityApplicationContextImpl} Builder
     */
    public static class Builder {
        private String accessToken;
        private AccessTokenAuthorization accessTokenAuthorization;

        public Builder(String accessToken) {
            this.accessToken = accessToken;
        }

        public Builder accessTokenAuthorization(AccessTokenAuthorization accessTokenAuthorization) {
            this.accessTokenAuthorization = accessTokenAuthorization;
            return this;
        }


        public OnSecurityApplicationContextImpl build() {
            OnSecurityApplicationContextImpl applicationContext = new OnSecurityApplicationContextImpl();
            applicationContext.accessToken = this.accessToken;
            applicationContext.accessTokenAuthorization = this.accessTokenAuthorization;
            return applicationContext;
        }
    }
}
