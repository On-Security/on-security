/*
 *   Copyright (C) 2022  恒宇少年
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.minbox.framework.on.security.console.configuration;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.minbox.framework.on.security.authorization.server.oauth2.config.configuration.OnSecurityOAuth2AuthorizationServerConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * 控制台服务（Console Service）作为授权服务器的相关配置
 *
 * @author 恒宇少年
 * @see OnSecurityConsoleServiceJwkSource
 * @since 0.0.7
 */
@Configuration
public class AuthorizationServerConfiguration extends OnSecurityOAuth2AuthorizationServerConfiguration {
    @Override
    protected JWKSource<SecurityContext> defaultJwkSource() {
        return new OnSecurityConsoleServiceJwkSource();
    }
}