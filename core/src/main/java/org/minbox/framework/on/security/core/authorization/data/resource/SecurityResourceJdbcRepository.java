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

import org.minbox.framework.on.security.core.authorization.jdbc.OnSecurityBaseJdbcRepositorySupport;
import org.minbox.framework.on.security.core.authorization.jdbc.definition.OnSecurityColumnName;
import org.minbox.framework.on.security.core.authorization.jdbc.definition.OnSecurityTables;
import org.minbox.framework.on.security.core.authorization.jdbc.sql.Condition;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.List;

/**
 * 资源数据存储库JDBC实现类
 *
 * @author 恒宇少年
 * @since 0.0.5
 */
public class SecurityResourceJdbcRepository extends OnSecurityBaseJdbcRepositorySupport<SecurityResource, String>
        implements SecurityResourceRepository {
    public SecurityResourceJdbcRepository(JdbcOperations jdbcOperations) {
        super(OnSecurityTables.SecurityResource, jdbcOperations);
    }

    @Override
    public List<SecurityResource> findByApplicationId(String applicationId) {
        Condition condition = Condition.withColumn(OnSecurityColumnName.ApplicationId, applicationId);
        return this.select(condition);
    }
}
