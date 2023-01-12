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
    public AccessTokenAuthorization.AccessTokenSession getSession() {
        return this.accessTokenAuthorization.getSession();
    }

    @Override
    public List<AccessTokenAuthorization.AuthorizationResource> getUserAuthorizationResource() {
        return this.accessTokenAuthorization.getUserAuthorizationResource();
    }

    @Override
    public List<AccessTokenAuthorization.AuthorizationAttribute> getUserAuthorizationAttribute() {
        return this.accessTokenAuthorization.getUserAuthorizationAttribute();
    }

    @Override
    public List<AccessTokenAuthorization.AuthorizationRole> getUserAuthorizationRole() {
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
