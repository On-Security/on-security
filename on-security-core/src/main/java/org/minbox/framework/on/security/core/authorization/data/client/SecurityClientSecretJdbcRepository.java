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

package org.minbox.framework.on.security.core.authorization.data.client;

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
 * 客户端秘钥存储库JDBC实现类
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class SecurityClientSecretJdbcRepository implements SecurityClientSecretRepository {
    private static final String COLUMN_NAMES = "id, "
            + "client_id, "
            + "client_secret, "
            + "secret_expires_at, "
            + "deleted, "
            + "create_time, "
            + "delete_time";
    // @formatter:on
    private static final String TABLE_NAME = "security_client_secret";
    private static final String ID_FILTER = "id = ?";
    private static final String SELECT_CLIENT_SECRET_SQL = "SELECT " + COLUMN_NAMES + " FROM " + TABLE_NAME + " WHERE ";
    // @formatter:off
    private static final String INSERT_CLIENT_SECRET_SQL = "INSERT INTO " + TABLE_NAME
            + "(" + COLUMN_NAMES + ") VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_CLIENT_SECRET_SQL = "UPDATE " + TABLE_NAME
            + " SET client_secret = ?, secret_expires_at = ?, deleted = ?, delete_time = ?"
            + " WHERE " + ID_FILTER;
    // @formatter:on
    private JdbcOperations jdbcOperations;
    private RowMapper<SecurityClientSecret> clientSecretRowMapper;
    private Function<SecurityClientSecret, List<SqlParameterValue>> clientSecretParametersMapper;

    public SecurityClientSecretJdbcRepository(JdbcOperations jdbcOperations) {
        Assert.notNull(jdbcOperations, "jdbcOperations cannot be null");
        this.jdbcOperations = jdbcOperations;
        this.clientSecretRowMapper = new SecurityClientSecretRowMapper();
        this.clientSecretParametersMapper = new SecurityClientSecretParametersMapper();
    }

    @Override
    public void save(SecurityClientSecret clientSecret) {
        Assert.notNull(clientSecret, "clientSecret cannot be null");
        SecurityClientSecret storedClientScope = this.findBy(ID_FILTER, clientSecret.getId());
        if (storedClientScope != null) {
            this.updateClientScope(clientSecret);
        } else {
            this.insertClientScope(clientSecret);
        }
    }

    private void updateClientScope(SecurityClientSecret clientSecret) {
        List<SqlParameterValue> parameters = new ArrayList<>(this.clientSecretParametersMapper.apply(clientSecret));
        SqlParameterValue id = parameters.remove(0); // remove id
        parameters.remove(0); // remove client_id
        parameters.remove(3); // remove create_time
        parameters.add(id); // add where id
        PreparedStatementSetter pss = new ArgumentPreparedStatementSetter(parameters.toArray());
        this.jdbcOperations.update(UPDATE_CLIENT_SECRET_SQL, pss);
    }

    private void insertClientScope(SecurityClientSecret clientSecret) {
        List<SqlParameterValue> parameters = this.clientSecretParametersMapper.apply(clientSecret);
        PreparedStatementSetter pss = new ArgumentPreparedStatementSetter(parameters.toArray());
        this.jdbcOperations.update(INSERT_CLIENT_SECRET_SQL, pss);
    }

    private SecurityClientSecret findBy(String filter, Object... args) {
        List<SecurityClientSecret> result = this.jdbcOperations.query(
                SELECT_CLIENT_SECRET_SQL + filter, this.clientSecretRowMapper, args);
        return !result.isEmpty() ? result.get(0) : null;
    }


    public static class SecurityClientSecretRowMapper implements RowMapper<SecurityClientSecret> {
        @Override
        public SecurityClientSecret mapRow(ResultSet rs, int rowNum) throws SQLException {
            // @formatter:off
            SecurityClientSecret clientSecret = SecurityClientSecret.withId(rs.getString("id"))
                    .clientId(rs.getString("client_id"))
                    .clientSecret(rs.getString("client_secret"))
                    .secretExpiresAt(rs.getTimestamp("secret_expires_at").toLocalDateTime())
                    .createTime(rs.getTimestamp("create_time").toLocalDateTime())
                    .deleted(rs.getBoolean("deleted"))
                    .deleteTime(rs.getTimestamp("delete_time").toLocalDateTime())
                    .build();
            return clientSecret;
            // @formatter:on
        }
    }

    public static class SecurityClientSecretParametersMapper implements Function<SecurityClientSecret, List<SqlParameterValue>> {
        @Override
        public List<SqlParameterValue> apply(SecurityClientSecret clientSecret) {
            // @formatter:off
            return Arrays.asList(
                    new SqlParameterValue(Types.VARCHAR, clientSecret.getId()),
                    new SqlParameterValue(Types.VARCHAR, clientSecret.getClientId()),
                    new SqlParameterValue(Types.VARCHAR, clientSecret.getClientSecret()),
                    new SqlParameterValue(Types.TIMESTAMP, Timestamp.valueOf(clientSecret.getSecretExpiresAt())),
                    new SqlParameterValue(Types.BOOLEAN, clientSecret.isDeleted()),
                    new SqlParameterValue(Types.TIMESTAMP, Timestamp.valueOf(clientSecret.getCreateTime())),
                    new SqlParameterValue(Types.TIMESTAMP, Timestamp.valueOf(clientSecret.getDeleteTime()))
            );
            // @formatter:on
        }
    }
}
