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

package org.minbox.framework.on.security.core.authorization.data.idp;

import org.minbox.framework.on.security.core.authorization.jdbc.OnSecurityBaseJdbcRepositorySupport;
import org.minbox.framework.on.security.core.authorization.jdbc.definition.OnSecurityColumnName;
import org.minbox.framework.on.security.core.authorization.jdbc.definition.OnSecurityTables;
import org.minbox.framework.on.security.core.authorization.jdbc.sql.Condition;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.List;

/**
 * 身份提供商授权范围数据JDBC实现类
 *
 * @author 恒宇少年
 * @since 0.0.2
 */
public class SecurityIdentityProviderScopeJdbcRepository extends OnSecurityBaseJdbcRepositorySupport<SecurityIdentityProviderScope, String>
        implements SecurityIdentityProviderScopeRepository {
    public SecurityIdentityProviderScopeJdbcRepository(JdbcOperations jdbcOperations) {
        super(OnSecurityTables.SecurityIdentityProviderScopes, jdbcOperations);
    }

    @Override
    public List<SecurityIdentityProviderScope> findByIdpId(String idpId) {
        Condition condition = Condition.withColumn(OnSecurityColumnName.IdpId, idpId).build();
        return this.select(condition);
    }
}
