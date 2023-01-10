package org.minbox.framework.on.security.application.service.authentication;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 应用资源严重失败执行的处理器
 *
 * @author 恒宇少年
 * @see ApplicationResourceAccessDeniedAuthenticationEntryPoint
 * @since 0.0.6
 */
public class OnSecurityApplicationResourceAuthorizationFailureHandler implements AuthenticationFailureHandler {
    // @formatter:off
    private ApplicationResourceAccessDeniedAuthenticationEntryPoint accessDeniedAuthenticationEntryPoint =
            new ApplicationResourceAccessDeniedAuthenticationEntryPoint();
    // @formatter:on
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (exception instanceof AuthenticationServiceException) {
            throw exception;
        }
        this.accessDeniedAuthenticationEntryPoint.commence(request, response, exception);
    }
}
