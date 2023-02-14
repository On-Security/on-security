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

package org.minbox.framework.on.security.core.authorization.data.application;

import org.minbox.framework.on.security.core.authorization.jdbc.OnSecurityBaseJdbcRepositorySupport;
import org.minbox.framework.on.security.core.authorization.jdbc.definition.OnSecurityColumnName;
import org.minbox.framework.on.security.core.authorization.jdbc.definition.OnSecurityTables;
import org.minbox.framework.on.security.core.authorization.jdbc.sql.Condition;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 客户端跳转地址存储库JDBC实现类
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class SecurityApplicationRedirectUriJdbcRepository extends OnSecurityBaseJdbcRepositorySupport<SecurityApplicationRedirectUri, String>
        implements SecurityApplicationRedirectUriRepository {

    public SecurityApplicationRedirectUriJdbcRepository(JdbcOperations jdbcOperations) {
        super(OnSecurityTables.SecurityApplicationRedirectUris, jdbcOperations);
    }

    @Override
    public void save(SecurityApplicationRedirectUri clientRedirectUri) {
        Assert.notNull(clientRedirectUri, "clientRedirectUri cannot be null");
        SecurityApplicationRedirectUri storedClientRedirectUri = this.selectOne(clientRedirectUri.getId());
        if (storedClientRedirectUri != null) {
            this.update(clientRedirectUri);
        } else {
            this.insert(clientRedirectUri);
        }
    }

    @Override
    public List<SecurityApplicationRedirectUri> findByApplicationId(String applicationId) {
        Assert.hasText(applicationId, "applicationId cannot be empty");
        Condition applicationIdCondition = Condition.withColumn(OnSecurityColumnName.ApplicationId, applicationId).build();
        return this.select(applicationIdCondition);
    }
}
