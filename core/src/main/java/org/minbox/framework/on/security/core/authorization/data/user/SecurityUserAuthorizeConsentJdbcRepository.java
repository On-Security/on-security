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

import org.minbox.framework.on.security.core.authorization.jdbc.OnSecurityBaseJdbcRepositorySupport;
import org.minbox.framework.on.security.core.authorization.jdbc.definition.OnSecurityColumnName;
import org.minbox.framework.on.security.core.authorization.jdbc.definition.OnSecurityTables;
import org.minbox.framework.on.security.core.authorization.jdbc.sql.Condition;
import org.springframework.jdbc.core.JdbcOperations;

/**
 * 用户授权许可数据存储库JDBC实现类
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class SecurityUserAuthorizeConsentJdbcRepository extends OnSecurityBaseJdbcRepositorySupport<SecurityUserAuthorizeConsent, String>
        implements SecurityUserAuthorizeConsentRepository {
    public SecurityUserAuthorizeConsentJdbcRepository(JdbcOperations jdbcOperations) {
        super(OnSecurityTables.SecurityUserAuthorizeConsents, jdbcOperations);
    }

    @Override
    public void save(SecurityUserAuthorizeConsent userAuthorizeConsent) {
        SecurityUserAuthorizeConsent storedUserAuthorizeConsent =
                this.findByUserIdAndClientId(userAuthorizeConsent.getUserId(), userAuthorizeConsent.getApplicationId());
        if (storedUserAuthorizeConsent != null) {
            this.update(userAuthorizeConsent);
        } else {
            this.insert(userAuthorizeConsent);
        }
    }

    @Override
    public void remove(String securityUserId, String applicationId) {
        this.delete(Condition.withColumn(OnSecurityColumnName.UserId, securityUserId),
                Condition.withColumn(OnSecurityColumnName.ApplicationId, applicationId));
    }

    @Override
    public SecurityUserAuthorizeConsent findByUserIdAndClientId(String securityUserId, String applicationId) {
        return this.selectOne(Condition.withColumn(OnSecurityColumnName.UserId, securityUserId),
                Condition.withColumn(OnSecurityColumnName.ApplicationId, applicationId));
    }
}
