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

import org.minbox.framework.on.security.core.authorization.ClientScopeType;
import org.springframework.jdbc.core.*;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * 客户端范围存储库JDBC实现类
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class SecurityApplicationScopeJdbcRepository implements SecurityApplicationScopeRepository {
    // @formatter:off
    private static final String COLUMN_NAMES = "id, "
            + "application_id, "
            + "scope_name, "
            + "scope_code, "
            + "type, "
            + "create_time";
    // @formatter:on
    private static final String TABLE_NAME = "security_application_scope";
    private static final String ID_FILTER = "id = ?";
    private static final String CLIENT_ID_FILTER = "application_id = ?";
    private static final String SELECT_CLIENT_SCOPE_SQL = "SELECT " + COLUMN_NAMES + " FROM " + TABLE_NAME + " WHERE ";
    // @formatter:off
    private static final String INSERT_CLIENT_SCOPE_SQL = "INSERT INTO " + TABLE_NAME
            + "(" + COLUMN_NAMES + ") VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_CLIENT_SCOPE_SQL = "UPDATE " + TABLE_NAME
            + " SET scope_name = ?, scope_code = ?, type = ?"
            + " WHERE " + ID_FILTER;
    // @formatter:on

    private JdbcOperations jdbcOperations;
    private RowMapper<SecurityApplicationScope> clientScopeRowMapper;
    private Function<SecurityApplicationScope, List<SqlParameterValue>> clientScopeParametersMapper;

    public SecurityApplicationScopeJdbcRepository(JdbcOperations jdbcOperations) {
        Assert.notNull(jdbcOperations, "jdbcOperations cannot be null");
        this.jdbcOperations = jdbcOperations;
        this.clientScopeRowMapper = new SecurityClientScopeRowMapper();
        this.clientScopeParametersMapper = new SecurityClientScopeParametersMapper();
    }

    @Override
    public void save(SecurityApplicationScope clientScope) {
        Assert.notNull(clientScope, "clientScope cannot be null");
        SecurityApplicationScope storedClientScope = this.findBy(ID_FILTER, clientScope.getId());
        if (storedClientScope != null) {
            this.updateClientScope(clientScope);
        } else {
            this.insertClientScope(clientScope);
        }
    }

    @Override
    public List<SecurityApplicationScope> findByClientId(String applicationId) {
        Assert.hasText(applicationId, "applicationId cannot be empty");
        return this.findListBy(CLIENT_ID_FILTER, applicationId);
    }

    private void updateClientScope(SecurityApplicationScope clientScope) {
        List<SqlParameterValue> parameters = new ArrayList<>(this.clientScopeParametersMapper.apply(clientScope));
        SqlParameterValue id = parameters.remove(0); // remove id
        parameters.remove(0); // remove application_id
        parameters.remove(3); // remove create_time
        parameters.add(id); // add where id
        PreparedStatementSetter pss = new ArgumentPreparedStatementSetter(parameters.toArray());
        this.jdbcOperations.update(UPDATE_CLIENT_SCOPE_SQL, pss);
    }

    private void insertClientScope(SecurityApplicationScope clientScope) {
        List<SqlParameterValue> parameters = this.clientScopeParametersMapper.apply(clientScope);
        PreparedStatementSetter pss = new ArgumentPreparedStatementSetter(parameters.toArray());
        this.jdbcOperations.update(INSERT_CLIENT_SCOPE_SQL, pss);
    }

    private SecurityApplicationScope findBy(String filter, Object... args) {
        List<SecurityApplicationScope> result = this.jdbcOperations.query(
                SELECT_CLIENT_SCOPE_SQL + filter, this.clientScopeRowMapper, args);
        return !result.isEmpty() ? result.get(0) : null;
    }

    private List<SecurityApplicationScope> findListBy(String filter, Object... args) {
        List<SecurityApplicationScope> result = this.jdbcOperations.query(
                SELECT_CLIENT_SCOPE_SQL + filter, this.clientScopeRowMapper, args);
        return result;
    }

    /**
     * 将{@link ResultSet}数据行映射绑定到{@link SecurityApplicationScope}
     */
    public static class SecurityClientScopeRowMapper implements RowMapper<SecurityApplicationScope> {
        @Override
        public SecurityApplicationScope mapRow(ResultSet rs, int rowNum) throws SQLException {
            String scopeId = rs.getString("id");
            // @formatter:off
            SecurityApplicationScope clientScope = SecurityApplicationScope.withId(scopeId)
                    .applicationId(rs.getString("application_id"))
                    .scopeName(rs.getString("scope_name"))
                    .scopeCode(rs.getString("scope_code"))
                    .type(new ClientScopeType(rs.getString("type")))
                    .createTime(rs.getTimestamp("create_time").toLocalDateTime())
                    .build();
            // @formatter:on
            return clientScope;
        }
    }

    /**
     * {@link SecurityApplicationScope}与{@link SqlParameterValue}映射
     */
    public static class SecurityClientScopeParametersMapper implements Function<SecurityApplicationScope, List<SqlParameterValue>> {
        @Override
        public List<SqlParameterValue> apply(SecurityApplicationScope clientScope) {
            // @formatter:off
            return Arrays.asList(
                    new SqlParameterValue(Types.VARCHAR, clientScope.getId()),
                    new SqlParameterValue(Types.VARCHAR, clientScope.getApplicationId()),
                    new SqlParameterValue(Types.VARCHAR, clientScope.getScopeName()),
                    new SqlParameterValue(Types.VARCHAR, clientScope.getScopeCode()),
                    new SqlParameterValue(Types.VARCHAR, clientScope.getType().getValue()),
                    new SqlParameterValue(Types.TIMESTAMP, Timestamp.valueOf(clientScope.getCreateTime()))
            );
            // @formatter:on
        }
    }
}
