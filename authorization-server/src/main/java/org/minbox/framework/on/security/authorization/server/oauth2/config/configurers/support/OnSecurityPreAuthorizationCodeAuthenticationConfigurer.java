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

package org.minbox.framework.on.security.authorization.server.oauth2.config.configurers.support;

import org.minbox.framework.on.security.authorization.server.oauth2.authentication.support.OnSecurityPreAuthorizationCodeAuthenticationProvider;
import org.minbox.framework.on.security.core.authorization.configurer.AbstractOnSecurityOAuth2Configurer;
import org.minbox.framework.on.security.authorization.server.oauth2.web.OnSecurityPreAuthorizationCodeAuthenticationFilter;
import org.minbox.framework.on.security.core.authorization.util.HttpSecuritySharedObjectUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * 授权码方式前置认证配置
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class OnSecurityPreAuthorizationCodeAuthenticationConfigurer extends AbstractOnSecurityOAuth2Configurer {
    private RequestMatcher preAuthenticationRequestMatcher;
    private AuthenticationFailureHandler authenticationFailureHandler;

    public OnSecurityPreAuthorizationCodeAuthenticationConfigurer(ObjectPostProcessor<Object> objectPostProcessor) {
        super(objectPostProcessor);
    }

    @Override
    public void init(HttpSecurity httpSecurity) {
        AuthorizationServerSettings authorizationServerSettings = HttpSecuritySharedObjectUtils.getAuthorizationServerSettings(httpSecurity);
        this.initPreAuthenticationMatcher(authorizationServerSettings);
        OnSecurityPreAuthorizationCodeAuthenticationProvider preAuthenticationProvider =
                new OnSecurityPreAuthorizationCodeAuthenticationProvider(httpSecurity.getSharedObjects());
        httpSecurity.authenticationProvider(preAuthenticationProvider);
    }

    @Override
    public void configure(HttpSecurity httpSecurity) {
        AuthenticationManager authenticationManager = HttpSecuritySharedObjectUtils.getAuthenticationManager(httpSecurity);
        OnSecurityPreAuthorizationCodeAuthenticationFilter preAuthenticationFilter =
                new OnSecurityPreAuthorizationCodeAuthenticationFilter(this.preAuthenticationRequestMatcher, authenticationManager);
        if (this.authenticationFailureHandler != null) {
            preAuthenticationFilter.setAuthenticationFailureHandler(this.authenticationFailureHandler);
        }
        httpSecurity.addFilterAfter(postProcess(preAuthenticationFilter), LogoutFilter.class);
    }

    /**
     * 初始化前置认证请求匹配器
     *
     * @param authorizationServerSettings {@link AuthorizationServerSettings}
     */
    private void initPreAuthenticationMatcher(AuthorizationServerSettings authorizationServerSettings) {
        // @formatter:off
        this.preAuthenticationRequestMatcher = new OrRequestMatcher(
                // ALL /oauth2/authorize
                new AntPathRequestMatcher(
                        authorizationServerSettings.getAuthorizationEndpoint()),
                // POST /oauth2/token
                new AntPathRequestMatcher(
                        authorizationServerSettings.getTokenEndpoint(),
                        HttpMethod.POST.name()),
                // POST /oauth2/revoke
                new AntPathRequestMatcher(
                        authorizationServerSettings.getTokenRevocationEndpoint(),
                        HttpMethod.POST.name()),
                // POST /oauth2/introspect
                new AntPathRequestMatcher(
                        authorizationServerSettings.getTokenIntrospectionEndpoint(),
                        HttpMethod.POST.name()));
        // @formatter:on
    }

    /**
     * 设置认证失败后执行的处理器
     * <p>
     * 该处理器会在遇到{@link OAuth2AuthenticationException}异常后执行
     *
     * @param authenticationFailureHandler {@link AuthenticationFailureHandler}
     */
    public void authenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }
}
