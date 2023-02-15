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

package org.minbox.framework.on.security.core.authorization.data.role;

import org.minbox.framework.on.security.core.authorization.jdbc.OnSecurityBaseJdbcRepositorySupport;
import org.minbox.framework.on.security.core.authorization.jdbc.definition.OnSecurityTables;
import org.minbox.framework.on.security.core.authorization.jdbc.mapper.ResultRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 角色数据存储库JDBC实现类
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class SecurityRoleJdbcRepository extends OnSecurityBaseJdbcRepositorySupport<SecurityRole, String> implements SecurityRoleRepository {
    public SecurityRoleJdbcRepository(JdbcOperations jdbcOperations) {
        super(OnSecurityTables.SecurityRole, jdbcOperations);
    }

    private static final String ID_IN_FILTER = " and id in (:ids) and application_id = :applicationId and deleted = false";

    @Override
    public List<SecurityRole> findByIds(String applicationId, List<String> roleIds) {
        Assert.hasText(applicationId, "applicationId cannot be empty");
        Assert.notEmpty(roleIds, "role ids cannot be empty");
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(this.jdbcOperations);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("ids", roleIds);
        parameterSource.addValue("applicationId", applicationId);
        return namedParameterJdbcTemplate.query(this.table.getQuerySql() + ID_IN_FILTER, parameterSource,
                new ResultRowMapper(OnSecurityTables.SecurityRole, SecurityRole.class));
    }
}
