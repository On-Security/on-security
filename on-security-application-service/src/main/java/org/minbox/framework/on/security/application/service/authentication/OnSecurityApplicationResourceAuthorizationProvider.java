package org.minbox.framework.on.security.application.service.authentication;

import org.minbox.framework.on.security.core.authorization.AbstractOnSecurityAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Map;

/**
 * @author 恒宇少年
 * @since 0.0.6
 */
public final class OnSecurityApplicationResourceAuthorizationProvider extends AbstractOnSecurityAuthenticationProvider {
    public OnSecurityApplicationResourceAuthorizationProvider(Map<Class<?>, Object> sharedObjects) {
        super(sharedObjects);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
