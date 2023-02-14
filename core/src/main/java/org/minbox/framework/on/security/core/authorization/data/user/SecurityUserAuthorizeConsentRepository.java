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

package org.minbox.framework.on.security.core.authorization.data.user;

import org.minbox.framework.on.security.core.authorization.data.application.SecurityApplication;
import org.minbox.framework.on.security.core.authorization.jdbc.OnSecurityBaseJdbcRepository;

/**
 * 用户授权许可数据存储库
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public interface SecurityUserAuthorizeConsentRepository extends OnSecurityBaseJdbcRepository<SecurityUserAuthorizeConsent, String> {
    /**
     * 保存授权许可信息
     * <p>
     * 根据{@link SecurityUserAuthorizeConsent#getApplicationId()}以及{@link SecurityUserAuthorizeConsent#getUserId()}判定是否存在
     * 如果已经存在则更新{@link SecurityUserAuthorizeConsent#getAuthorities()}，否则执行新增
     *
     * @param userAuthorizeConsent 授权许可对象实例 {@link SecurityUserAuthorizeConsent}
     */
    void save(SecurityUserAuthorizeConsent userAuthorizeConsent);

    /**
     * 删除授权许可
     *
     * @param applicationId 应用ID {@link SecurityApplication#getId()}
     * @param securityUserId   用户ID {@link SecurityUser#getId()}
     */
    void remove(String securityUserId, String applicationId);

    /**
     * 查询授权许可
     *
     * @param applicationId 应用ID {@link SecurityApplication#getId()}
     * @param securityUserId   用户ID {@link SecurityUser#getId()}
     * @return 用户授权许可对象实例 {@link SecurityUserAuthorizeConsent}
     */
    SecurityUserAuthorizeConsent findByUserIdAndClientId(String securityUserId, String applicationId);
}
