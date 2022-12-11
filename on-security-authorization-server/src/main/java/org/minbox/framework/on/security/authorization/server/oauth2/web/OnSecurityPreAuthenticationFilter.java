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

package org.minbox.framework.on.security.authorization.server.oauth2.web;

import org.minbox.framework.on.security.authorization.server.oauth2.authentication.support.OnSecurityPreAuthenticationToken;
import org.minbox.framework.on.security.authorization.server.oauth2.web.converter.OnSecurityPreAuthenticationConverter;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 身份认证前置认证过滤器
 * <p>
 * 该过滤器需要拦截全部"认证地址"，每次执行认证之前都需要执行该过滤器
 *
 * @author 恒宇少年
 */
public class OnSecurityPreAuthenticationFilter extends OncePerRequestFilter {
    private final RequestMatcher allEndpointMatcher;
    private final AuthenticationManager authenticationManager;
    private AuthenticationConverter authenticationConverter;
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    private AuthenticationFailureHandler authenticationFailureHandler;

    public OnSecurityPreAuthenticationFilter(RequestMatcher allEndpointMatcher, AuthenticationManager authenticationManager) {
        Assert.notNull(authenticationManager, "authenticationManager cannot be null");
        Assert.notNull(allEndpointMatcher, "allEndpointMatcher cannot be null");
        this.allEndpointMatcher = allEndpointMatcher;
        this.authenticationManager = authenticationManager;
        this.authenticationConverter = new OnSecurityPreAuthenticationConverter();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!allEndpointMatcher.matches(request)) {
            filterChain.doFilter(request, response);
        }
        try {
            OnSecurityPreAuthenticationToken preAuthenticationToken = (OnSecurityPreAuthenticationToken) authenticationConverter.convert(request);
            Authentication authenticationResult = authenticationManager.authenticate(preAuthenticationToken);
            // TODO 根据认证结果处理后续业务
            authenticationSuccessHandler.onAuthenticationSuccess(request, response, authenticationResult);
        } catch (OAuth2AuthenticationException exception) {
            if (logger.isDebugEnabled()) {
                logger.debug(LogMessage.format("The pre-authentication filter encountered an exception: %s", exception.getError()), exception);
            }
            authenticationFailureHandler.onAuthenticationFailure(request, response, exception);
        }
    }

    public void setAuthenticationSuccessHandler(AuthenticationSuccessHandler authenticationSuccessHandler) {
        Assert.notNull(authenticationSuccessHandler, "authenticationSuccessHandler cannot be null");
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        Assert.notNull(authenticationFailureHandler, "authenticationFailureHandler cannot be null");
        this.authenticationFailureHandler = authenticationFailureHandler;
    }
}
