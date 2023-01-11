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

package org.minbox.framework.on.security.authorization.server.oauth2.authentication;

import org.minbox.framework.on.security.authorization.server.oauth2.config.configurers.support.OnSecurityPreAuthorizationCodeAuthenticationConfigurer;
import org.minbox.framework.on.security.authorization.server.oauth2.web.OnSecurityPreAuthorizationCodeAuthenticationFilter;
import org.minbox.framework.on.security.core.authorization.AuthenticationFailureResponse;
import org.minbox.framework.on.security.core.authorization.exception.OnSecurityError;
import org.minbox.framework.on.security.core.authorization.exception.OnSecurityErrorCodes;
import org.minbox.framework.on.security.core.authorization.exception.OnSecurityOAuth2AuthenticationException;
import org.minbox.framework.on.security.core.authorization.jackson2.OnSecurityJsonMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationEndpointConfigurer;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2TokenEndpointConfigurer;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2TokenIntrospectionEndpointConfigurer;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2TokenRevocationEndpointConfigurer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.minbox.framework.on.security.core.authorization.util.OnSecurityThrowErrorUtils.DEFAULT_HELP_URI;

/**
 * 定义默认的认证失败处理器
 * <p>
 * {@link OAuth2AuthorizationEndpointConfigurer#errorResponseHandler(AuthenticationFailureHandler)}、
 * {@link OAuth2TokenEndpointConfigurer#errorResponseHandler(AuthenticationFailureHandler)}、
 * {@link OAuth2TokenIntrospectionEndpointConfigurer#errorResponseHandler(AuthenticationFailureHandler)}、
 * {@link  OAuth2TokenRevocationEndpointConfigurer#errorResponseHandler(AuthenticationFailureHandler)}、
 * {@link OnSecurityPreAuthorizationCodeAuthenticationConfigurer#authenticationFailureHandler(AuthenticationFailureHandler)}
 * 默认情况下以上全部的异常处理方法都会使用本处理器进行处理{@link AuthenticationException}异常
 *
 * @author 恒宇少年
 * @see OnSecurityPreAuthorizationCodeAuthenticationFilter
 * @since 0.0.1
 */
public class OnSecurityDefaultAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private OnSecurityJsonMapper objectMapper;

    public OnSecurityDefaultAuthenticationFailureHandler() {
        this.objectMapper = new OnSecurityJsonMapper();
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        AuthenticationFailureResponse failureResponse = this.defaultFailureResponse(exception);
        // Handler OnSecurityOAuth2AuthenticationException
        if (exception instanceof OnSecurityOAuth2AuthenticationException) {
            // @formatter:off
            OnSecurityOAuth2AuthenticationException onSecurityOAuth2AuthenticationException =
                    (OnSecurityOAuth2AuthenticationException) exception;
            OnSecurityError onSecurityError = onSecurityOAuth2AuthenticationException.getOnSecurityError();
            failureResponse = AuthenticationFailureResponse
                    .withErrorCode(onSecurityError.getErrorCode())
                    .description(onSecurityError.getDescription())
                    .helpUri(onSecurityError.getHelpUri())
                    .build();
            // @formatter:on
        }
        // Handler OAuth2AuthenticationException
        else if (exception instanceof OAuth2AuthenticationException) {
            OAuth2AuthenticationException oAuth2AuthenticationException = (OAuth2AuthenticationException) exception;
            OAuth2Error oAuth2Error = oAuth2AuthenticationException.getError();
            failureResponse = AuthenticationFailureResponse
                    .withErrorCode(oAuth2Error.getErrorCode())
                    .description(oAuth2Error.getDescription())
                    .helpUri(oAuth2Error.getUri())
                    .build();
            if (!StringUtils.hasText(failureResponse.getHelpUri()) || !StringUtils.hasText(failureResponse.getDescription())) {
                failureResponse = AuthenticationFailureResponse.withFailureResponse(failureResponse)
                        .description("An unknown authentication exception has occurred.")
                        .helpUri(DEFAULT_HELP_URI)
                        .build();
            }
        }
        String responseJson = objectMapper.writeValueAsString(failureResponse);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON.toString());
        response.getWriter().write(responseJson);
    }

    private AuthenticationFailureResponse defaultFailureResponse(AuthenticationException exception) {
        // @formatter:off
        return AuthenticationFailureResponse
                .withErrorCode(OnSecurityErrorCodes.UNKNOWN_EXCEPTION.getValue())
                .description(exception.getMessage())
                .helpUri(DEFAULT_HELP_URI)
                .build();
        // @formatter:on
    }
}
