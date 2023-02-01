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

import org.minbox.framework.on.security.core.authorization.AuthorizeMatchMethod;
import org.minbox.framework.on.security.core.authorization.data.resource.UserAuthorizationResource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * "Role-Based Access Control（RBAC）"资源访问控制匹配器
 *
 * @author 恒宇少年
 * @see UserAuthorizationResource
 * @see AntPathRequestMatcher
 * @since 0.0.7
 */
public class ResourceRoleBasedAccessControlMatcher implements ResourceAccessMatcher {
    private List<UserAuthorizationResource> authorizationResourceList;

    /**
     * RBAC资源访问控制构造函数
     *
     * @param authorizationResourceList 请求令牌"AccessToken"授权的资源列表
     */
    public ResourceRoleBasedAccessControlMatcher(List<UserAuthorizationResource> authorizationResourceList) {
        this.authorizationResourceList = authorizationResourceList;
    }

    @Override
    public boolean match(HttpServletRequest request) {
        Map<AuthorizeMatchMethod, Set<String>> authorizationResourceMap = this.getUrisGroupByMatchMethod();
        RequestMatcher requestMatcher = null;
        // Match reject resource uris
        Set<String> rejectResourceUriSet = authorizationResourceMap.get(AuthorizeMatchMethod.REJECT);
        if (!ObjectUtils.isEmpty(rejectResourceUriSet)) {
            requestMatcher = this.resourceUriToRequestMatcher(rejectResourceUriSet);
            if (requestMatcher.matches(request)) {
                return false;
            }
        }
        // Match allow resource uris
        Set<String> allowResourceUriSet = authorizationResourceMap.get(AuthorizeMatchMethod.ALLOW);
        if (!ObjectUtils.isEmpty(allowResourceUriSet)) {
            requestMatcher = this.resourceUriToRequestMatcher(allowResourceUriSet);
        }
        return requestMatcher != null ? requestMatcher.matches(request) : false;
    }

    /**
     * 将资源Uri转换为{@link OrRequestMatcher}
     *
     * @param resourceUriSet 资源Uri集合
     * @return OR方式请求匹配器实例 {@link OrRequestMatcher}
     */
    private OrRequestMatcher resourceUriToRequestMatcher(Set<String> resourceUriSet) {
        // @formatter:off
        List<AntPathRequestMatcher> antPathRequestMatcherList = resourceUriSet.stream()
                .map(resourceUri -> new AntPathRequestMatcher(resourceUri))
                .collect(Collectors.toList());
        // @formatter:on
        return new OrRequestMatcher(antPathRequestMatcherList.stream().toArray(AntPathRequestMatcher[]::new));
    }

    /**
     * 将授权资源列表{@link #authorizationResourceList}根据{@link AuthorizeMatchMethod}进行分组
     */
    private Map<AuthorizeMatchMethod, Set<String>> getUrisGroupByMatchMethod() {
        if (!ObjectUtils.isEmpty(this.authorizationResourceList)) {
            // @formatter:off
            return this.authorizationResourceList.stream()
                    .collect(Collectors
                            .toMap(UserAuthorizationResource::getMatchMethod, UserAuthorizationResource::getResourceUris,
                                    (Set<String> oldSet, Set<String> newSet) -> {
                                        oldSet.addAll(newSet);
                                        return oldSet;
                                    })
                    );
            // @formatter:on
        }
        return Collections.emptyMap();
    }
}
