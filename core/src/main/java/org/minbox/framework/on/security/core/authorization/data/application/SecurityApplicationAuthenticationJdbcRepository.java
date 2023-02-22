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
 * 客户端认证数据存储JDBC方式实现类
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class SecurityApplicationAuthenticationJdbcRepository extends OnSecurityBaseJdbcRepositorySupport<SecurityApplicationAuthentication, String>
        implements SecurityApplicationAuthenticationRepository {

    public SecurityApplicationAuthenticationJdbcRepository(JdbcOperations jdbcOperations) {
        super(OnSecurityTables.SecurityApplicationAuthentication, jdbcOperations);
    }

    @Override
    public void save(SecurityApplicationAuthentication clientAuthentication) {
        Assert.notNull(clientAuthentication, "clientAuthentication cannot be null");
        SecurityApplicationAuthentication storedClientAuthentication = this.selectOne(clientAuthentication.getId());
        if (storedClientAuthentication != null) {
            this.update(clientAuthentication);
        } else {
            this.assertUniqueIdentifiers(clientAuthentication);
            this.insert(clientAuthentication);
        }
    }

    @Override
    public SecurityApplicationAuthentication findByApplicationId(String applicationId) {
        Assert.hasText(applicationId, "applicationId cannot be empty");
        Condition applicationIdCondition = Condition.withColumn(OnSecurityColumnName.ApplicationId, applicationId);
        return this.selectOne(applicationIdCondition);
    }

    private void assertUniqueIdentifiers(SecurityApplicationAuthentication clientAuthentication) {
        SecurityApplicationAuthentication checkObject = findByApplicationId(clientAuthentication.getApplicationId());
        Assert.isNull(checkObject, "Application ID must be unique，duplicate ID：" + clientAuthentication.getApplicationId());
    }
}
