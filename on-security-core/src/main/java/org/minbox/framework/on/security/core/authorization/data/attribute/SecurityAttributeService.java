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

package org.minbox.framework.on.security.core.authorization.data.attribute;

import org.minbox.framework.on.security.core.authorization.data.user.SecurityUserAuthorizeAttribute;
import org.minbox.framework.on.security.core.authorization.data.user.SecurityUserAuthorizeAttributeJdbcRepository;
import org.minbox.framework.on.security.core.authorization.data.user.SecurityUserAuthorizeAttributeRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 属性业务逻辑类
 *
 * @author 恒宇少年
 * @since 0.0.5
 */
public class SecurityAttributeService {
    private SecurityUserAuthorizeAttributeRepository userAuthorizeAttributeRepository;
    private SecurityAttributeRepository attributeRepository;

    public SecurityAttributeService(JdbcOperations jdbcOperations) {
        Assert.notNull(jdbcOperations, "jdbcOperations cannot be null");
        this.userAuthorizeAttributeRepository = new SecurityUserAuthorizeAttributeJdbcRepository(jdbcOperations);
        this.attributeRepository = new SecurityAttributeJdbcRepository(jdbcOperations);
    }

    /**
     * 查询用户授权的属性列表
     *
     * @param userId {@link SecurityUserAuthorizeAttribute#getUserId()} 用户ID
     * @return {@link SecurityAttribute}
     */
    public List<UserAuthorizationAttribute> findByUserId(String userId) {
        List<SecurityUserAuthorizeAttribute> userAuthorizeAttributeList = this.userAuthorizeAttributeRepository.findByUserId(userId);
        if (!ObjectUtils.isEmpty(userAuthorizeAttributeList)) {
            // @formatter:off
            List<String> attributeIds = userAuthorizeAttributeList
                    .stream()
                    .map(SecurityUserAuthorizeAttribute::getAttributeId)
                    .collect(Collectors.toList());
            List<SecurityAttribute>  attributeList = this.attributeRepository.findByIds(attributeIds);
            List<UserAuthorizationAttribute> userAuthorizationAttributeList =
                    attributeList.stream()
                            .map(attribute ->
                                    new UserAuthorizationAttribute(
                                            attribute.getId(),
                                            attribute.getKey(),
                                            attribute.getValue()))
                            .collect(Collectors.toList());
            // @formatter:on
            return userAuthorizationAttributeList;
        }
        return null;
    }
}
