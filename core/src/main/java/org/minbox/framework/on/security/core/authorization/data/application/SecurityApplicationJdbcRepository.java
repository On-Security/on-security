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

/**
 * 客户端数据存储库JDBC方式实现类
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class SecurityApplicationJdbcRepository extends OnSecurityBaseJdbcRepositorySupport<SecurityApplication, String>
        implements SecurityApplicationRepository {

    public SecurityApplicationJdbcRepository(JdbcOperations jdbcOperations) {
        super(OnSecurityTables.SecurityApplication, jdbcOperations);
    }

    @Override
    public void save(SecurityApplication client) {
        Assert.notNull(client, "client cannot be null");
        SecurityApplication storedClient = this.selectOne(client.getId());
        if (storedClient != null) {
            this.update(client);
        } else {
            this.assertUniqueIdentifiers(client);
            this.insert(client);
        }
    }

    @Override
    public SecurityApplication findByApplicationId(String applicationId) {
        Assert.hasText(applicationId, "applicationId cannot be empty");
        Condition applicationIdCondition = Condition.withColumn(OnSecurityColumnName.ApplicationId, applicationId);
        return this.selectOne(applicationIdCondition);
    }

    private void assertUniqueIdentifiers(SecurityApplication client) {
        SecurityApplication checkObject = this.findByApplicationId(client.getApplicationId());
        Assert.isNull(checkObject, "Application ID must be unique，duplicate ID：" + client.getApplicationId());
    }
}
