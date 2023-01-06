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

import org.minbox.framework.on.security.core.authorization.AuthorizeMatchMethod;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * 角色授权资源JDBC数据存储库
 *
 * @author 恒宇少年
 * @since 0.0.4
 */
public class SecurityRoleAuthorizeResourceJdbcRepository implements SecurityRoleAuthorizeResourceRepository {
    // @formatter:off
    private static final String COLUMN_NAMES = "id, "
            + "region_id, "
            + "role_id, "
            + "resource_id, "
            + "match_method, "
            + "authorize_time";
    // @formatter:on
    private static final String TABLE_NAME = "security_role_authorize_resources";
    private static final String ID_FILTER = "id = ?";
    private static final String ROLE_ID_FILTER = "role_id = ?";
    private static final String ROLE_ID_IN_FILTER = "role_id in (:roleIds)";
    private static final String SELECT_ALL_COLUMNS_SQL = "SELECT " + COLUMN_NAMES + " FROM " + TABLE_NAME + " WHERE ";
    // @formatter:off
    private static final String INSERT_SQL = "INSERT INTO " + TABLE_NAME
            + "(" + COLUMN_NAMES + ") VALUES (?, ?, ?, ?, ?, ?)";
    // @formatter:on
    private static final String REMOVE_SQL = "DELETE FROM " + TABLE_NAME + " WHERE ";
    private JdbcOperations jdbcOperations;
    private RowMapper<SecurityRoleAuthorizeResource> roleAuthorizeResourceRowMapper;
    private Function<SecurityRoleAuthorizeResource, List<SqlParameterValue>> roleAuthorizeResourceParametersMapper;

    public SecurityRoleAuthorizeResourceJdbcRepository(JdbcOperations jdbcOperations) {
        Assert.notNull(jdbcOperations, "jdbcOperations cannot be null");
        this.jdbcOperations = jdbcOperations;
        this.roleAuthorizeResourceRowMapper = new SecurityRoleAuthorizeResourceJdbcRepository.SecurityRoleAuthorizeResourceRowMapper();
        this.roleAuthorizeResourceParametersMapper = new SecurityRoleAuthorizeResourceJdbcRepository.SecurityRoleAuthorizeResourceParameterMapper();
    }

    @Override
    public void insert(SecurityRoleAuthorizeResource roleAuthorizeResource) {
        List<SqlParameterValue> parameters = this.roleAuthorizeResourceParametersMapper.apply(roleAuthorizeResource);
        PreparedStatementSetter pss = new ArgumentPreparedStatementSetter(parameters.toArray());
        this.jdbcOperations.update(INSERT_SQL, pss);
    }

    @Override
    public List<SecurityRoleAuthorizeResource> findByRoleId(String roleId) {
        Assert.hasText(roleId, "roleId cannot be empty");
        return this.findListBy(ROLE_ID_FILTER, roleId);
    }

    @Override
    public List<SecurityRoleAuthorizeResource> findByRoleIds(List<String> roleIds) {
        Assert.notEmpty(roleIds, "role ids cannot be empty");
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(this.jdbcOperations);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("roleIds", roleIds);
        return namedParameterJdbcTemplate.query(SELECT_ALL_COLUMNS_SQL + ROLE_ID_IN_FILTER, parameterSource, this.roleAuthorizeResourceRowMapper);
    }

    @Override
    public void removeById(String id) {
        SqlParameterValue[] parameterValues = new SqlParameterValue[]{new SqlParameterValue(Types.VARCHAR, id)};
        PreparedStatementSetter pass = new ArgumentPreparedStatementSetter(parameterValues);
        this.jdbcOperations.update(REMOVE_SQL + ID_FILTER, pass);
    }

    @Override
    public void removeByRoleId(String roleId) {
        SqlParameterValue[] parameterValues = new SqlParameterValue[]{new SqlParameterValue(Types.VARCHAR, roleId)};
        PreparedStatementSetter pass = new ArgumentPreparedStatementSetter(parameterValues);
        this.jdbcOperations.update(REMOVE_SQL + ROLE_ID_FILTER, pass);
    }

    private List<SecurityRoleAuthorizeResource> findListBy(String filter, Object... args) {
        return this.jdbcOperations.query(SELECT_ALL_COLUMNS_SQL + filter, this.roleAuthorizeResourceRowMapper, args);
    }

    public static class SecurityRoleAuthorizeResourceRowMapper implements RowMapper<SecurityRoleAuthorizeResource> {
        @Override
        public SecurityRoleAuthorizeResource mapRow(ResultSet rs, int rowNum) throws SQLException {
            SecurityRoleAuthorizeResource.Builder builder = SecurityRoleAuthorizeResource.withId(rs.getString("id"));
            // @formatter:off
            builder.regionId(rs.getString("region_id"))
                    .roleId(rs.getString("role_id"))
                    .resourceId(rs.getString("resource_id"))
                    .matchMethod(new AuthorizeMatchMethod(rs.getString("match_method")))
                    .authorizeTime(rs.getTimestamp("authorize_time").toLocalDateTime());
            // @formatter:on
            return builder.build();
        }
    }

    public static class SecurityRoleAuthorizeResourceParameterMapper implements Function<SecurityRoleAuthorizeResource, List<SqlParameterValue>> {
        @Override
        public List<SqlParameterValue> apply(SecurityRoleAuthorizeResource resourceAuthorizeAttribute) {
            // @formatter:off
            return Arrays.asList(
                    new SqlParameterValue(Types.VARCHAR, resourceAuthorizeAttribute.getId()),
                    new SqlParameterValue(Types.VARCHAR, resourceAuthorizeAttribute.getRegionId()),
                    new SqlParameterValue(Types.VARCHAR, resourceAuthorizeAttribute.getRoleId()),
                    new SqlParameterValue(Types.VARCHAR, resourceAuthorizeAttribute.getResourceId()),
                    new SqlParameterValue(Types.VARCHAR, resourceAuthorizeAttribute.getMatchMethod().getValue()),
                    new SqlParameterValue(Types.TIMESTAMP, Timestamp.valueOf(resourceAuthorizeAttribute.getAuthorizeTime()))
            );
            // @formatter:on
        }
    }
}
