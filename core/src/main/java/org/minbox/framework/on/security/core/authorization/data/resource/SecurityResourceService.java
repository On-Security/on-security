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

package org.minbox.framework.on.security.core.authorization.data.resource;

import org.minbox.framework.on.security.core.authorization.data.attribute.ResourceAuthorizeAttribute;
import org.minbox.framework.on.security.core.authorization.data.attribute.SecurityAttributeService;
import org.minbox.framework.on.security.core.authorization.data.role.SecurityRoleAuthorizeResource;
import org.minbox.framework.on.security.core.authorization.data.role.SecurityRoleAuthorizeResourceJdbcRepository;
import org.minbox.framework.on.security.core.authorization.data.role.SecurityRoleAuthorizeResourceRepository;
import org.minbox.framework.on.security.core.authorization.data.user.SecurityUserAuthorizeRole;
import org.minbox.framework.on.security.core.authorization.data.user.SecurityUserAuthorizeRoleJdbcRepository;
import org.minbox.framework.on.security.core.authorization.data.user.SecurityUserAuthorizeRoleRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 资源业务逻辑类
 *
 * @author 恒宇少年
 * @since 0.0.5
 */
public class SecurityResourceService {
    private SecurityUserAuthorizeRoleRepository userAuthorizeRoleRepository;
    private SecurityRoleAuthorizeResourceRepository roleAuthorizeResourceRepository;
    private SecurityResourceRepository resourceRepository;
    private SecurityResourceUriRepository resourceUriRepository;
    private SecurityResourceAuthorizeAttributeRepository resourceAuthorizeAttributeRepository;
    private SecurityAttributeService attributeService;

    public SecurityResourceService(JdbcOperations jdbcOperations) {
        Assert.notNull(jdbcOperations, "jdbcOperations cannot be null");
        this.userAuthorizeRoleRepository = new SecurityUserAuthorizeRoleJdbcRepository(jdbcOperations);
        this.roleAuthorizeResourceRepository = new SecurityRoleAuthorizeResourceJdbcRepository(jdbcOperations);
        this.resourceRepository = new SecurityResourceJdbcRepository(jdbcOperations);
        this.resourceUriRepository = new SecurityResourceUriJdbcRepository(jdbcOperations);
        this.resourceAuthorizeAttributeRepository = new SecurityResourceAuthorizeAttributeJdbcRepository(jdbcOperations);
        this.attributeService = new SecurityAttributeService(jdbcOperations);
    }

    public List<ApplicationResource> findByApplicationId(String applicationId) {
        List<SecurityResource> resourceList = this.resourceRepository.findByApplicationId(applicationId);
        if (!ObjectUtils.isEmpty(resourceList)) {
            // @formatter:off
            return resourceList.stream()
                    .map(resource -> {
                        List<SecurityResourceUri> resourceUriList = this.resourceUriRepository.findByResourceId(resource.getId());
                        List<ResourceAuthorizeAttribute> resourceAuthorizeAttributeList = this.attributeService.findByResourceId(resource.getId());
                        ApplicationResource applicationResource = ApplicationResource
                                .withResource(resource)
                                .resourceUriList(resourceUriList.stream().collect(Collectors.toSet()))
                                .resourceAuthorizeAttributeList(resourceAuthorizeAttributeList)
                                .build();
                        return applicationResource;
                    }).collect(Collectors.toList());
            // @formatter:on
        }
        return null;
    }

    public List<UserAuthorizationResource> findByUserIdAndApplicationId(String userId, String applicationId) {
        List<SecurityUserAuthorizeRole> userAuthorizeRoleList = userAuthorizeRoleRepository.findByUserId(userId);
        if (ObjectUtils.isEmpty(userAuthorizeRoleList)) {
            return Collections.emptyList();
        }
        // @formatter:off
        List<String> userAuthorizeRoleIds = userAuthorizeRoleList
                .stream()
                .map(SecurityUserAuthorizeRole::getRoleId)
                .collect(Collectors.toList());
        List<SecurityRoleAuthorizeResource> userRoleAuthorizeResourceList = roleAuthorizeResourceRepository.findByRoleIds(userAuthorizeRoleIds);
        List<SecurityResource> applicationResourceList = this.resourceRepository.findByApplicationId(applicationId);
        if (ObjectUtils.isEmpty(applicationResourceList)) {
            return null;
        }
        Map<String,SecurityResource> applicationResourceMap = applicationResourceList.stream().collect(Collectors.toMap(SecurityResource::getId,v -> v));
        List<UserAuthorizationResource> userAuthorizationResourceList = userRoleAuthorizeResourceList
                .stream()
                .map(authorizeResource -> {
                    SecurityResource resource = applicationResourceMap.get(authorizeResource.getResourceId());
                    if (resource == null || resource.getDeleted()) {
                        return null;
                    }
                    List<SecurityResourceUri> resourceUriList = resourceUriRepository.findByResourceId(authorizeResource.getResourceId());
                    if (ObjectUtils.isEmpty(resourceUriList)) {
                        return null;
                    }
                    Set<String> resourceUriPathSet = resourceUriList
                            .stream()
                            .map(SecurityResourceUri::getUri)
                            .collect(Collectors.toSet());

                    // Map UserAuthorizationResource
                    return UserAuthorizationResource
                            .withResourceId(resource.getId())
                            .resourceName(resource.getName())
                            .resourceCode(resource.getCode())
                            .resourceUris(resourceUriPathSet)
                            .resourceType(resource.getType())
                            .matchMethod(authorizeResource.getMatchMethod())
                            .build();
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        // @formatter:on
        return userAuthorizationResourceList;
    }
}
