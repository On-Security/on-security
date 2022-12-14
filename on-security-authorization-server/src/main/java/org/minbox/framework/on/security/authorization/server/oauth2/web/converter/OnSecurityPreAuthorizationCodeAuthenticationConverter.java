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

package org.minbox.framework.on.security.authorization.server.oauth2.web.converter;

import org.minbox.framework.on.security.authorization.server.oauth2.authentication.support.OnSecurityPreAuthorizationCodeAuthenticationToken;
import org.minbox.framework.on.security.authorization.server.utils.RequestParameterUtils;
import org.minbox.framework.on.security.core.authorization.adapter.OnSecurityUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.HttpServletRequest;

/**
 * 转换授权码方式前置认证所需要的Token实体
 *
 * @author 恒宇少年
 * @see OnSecurityPreAuthorizationCodeAuthenticationToken
 * @since 0.0.1
 */
public class OnSecurityPreAuthorizationCodeAuthenticationConverter implements AuthenticationConverter {

    @Override
    public Authentication convert(HttpServletRequest request) {
        MultiValueMap<String, String> parameters = RequestParameterUtils.getParameters(request);
        String clientId = parameters.getFirst(OAuth2ParameterNames.CLIENT_ID);
        String grantType = parameters.getFirst(OAuth2ParameterNames.GRANT_TYPE);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OnSecurityUserDetails onSecurityUserDetails = null;
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) authentication;
            onSecurityUserDetails = (OnSecurityUserDetails) usernamePasswordAuthenticationToken.getPrincipal();
        }
        return new OnSecurityPreAuthorizationCodeAuthenticationToken(clientId, grantType, onSecurityUserDetails);
    }


}
