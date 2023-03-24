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

package org.minbox.framework.on.security.application.service.access;

import org.minbox.framework.on.security.core.authorization.data.application.UserAuthorizationApplication;
import org.minbox.framework.on.security.core.authorization.data.attribute.ResourceAuthorizeAttribute;
import org.minbox.framework.on.security.core.authorization.data.attribute.UserAuthorizationAttribute;
import org.minbox.framework.on.security.core.authorization.data.resource.ApplicationResource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * "Attribute-Based Access Control（ABAC）"资源访问控制匹配器
 *
 * @author 恒宇少年
 * @see AntPathRequestMatcher
 * @see UserAuthorizationAttribute
 * @see ResourceAuthorizeAttribute
 * @since 0.1.1
 */
public class ResourceAttributeBasedAccessControlMatcher implements ResourceAccessMatcher {
    private List<UserAuthorizationAttribute> userAuthorizationAttributeList;
    private UserAuthorizationApplication userAuthorizationApplication;

    public ResourceAttributeBasedAccessControlMatcher(List<UserAuthorizationAttribute> userAuthorizationAttributeList,
                                                      UserAuthorizationApplication userAuthorizationApplication) {
        this.userAuthorizationAttributeList = userAuthorizationAttributeList;
        this.userAuthorizationApplication = userAuthorizationApplication;
    }

    @Override
    public boolean match(HttpServletRequest request) {
        List<ApplicationResource> applicationResourceList = userAuthorizationApplication.getResourceList();
        // @formatter:off
        Optional<ApplicationResource> optional = applicationResourceList.stream()
                .filter(resource -> {
                    List<AntPathRequestMatcher> antMatchers = resource.getResourceUriList()
                            .stream()
                            .map(resourceUri -> new AntPathRequestMatcher(resourceUri.getUri()))
                            .collect(Collectors.toList());
                    OrRequestMatcher matcher = new OrRequestMatcher(antMatchers.stream().toArray(AntPathRequestMatcher[]::new));
                    return matcher.matches(request);
                }).findFirst();
        // @formatter:on
        if (!optional.isPresent()) {
            return false;
        }
        ApplicationResource resource = optional.get();
        // @formatter:off
        // All attribute IDs that match resource authorization
        List<String> resourceAuthorizeAttributeIds = resource.getResourceAuthorizeAttributeList().stream()
                .map(ResourceAuthorizeAttribute::getAttributeId)
                .collect(Collectors.toList());
        // All attribute IDs authorized by the user
        List<String> userAuthorizeAttributeIds = this.userAuthorizationAttributeList.stream()
                .map(UserAuthorizationAttribute::getAttributeId)
                .collect(Collectors.toList());
        // @formatter:on
        // If the user has not authorized the attribute
        if (ObjectUtils.isEmpty(userAuthorizeAttributeIds)) {
            return false;
        }
        return resourceAuthorizeAttributeIds.containsAll(userAuthorizeAttributeIds);
    }
}
