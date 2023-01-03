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

import org.springframework.jdbc.core.*;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

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
public class SecurityApplicationSecretJdbcRepository implements SecurityApplicationSecretRepository {
    private static final String COLUMN_NAMES = "id, "
            + "application_id, "
            + "application_secret, "
            + "secret_expires_at, "
            + "deleted, "
            + "create_time, "
            + "delete_time";
    // @formatter:on
    private static final String TABLE_NAME = "security_application_secret";
    private static final String ID_FILTER = "id = ?";
    private static final String CLIENT_ID_FILTER = "application_id = ?";
    private static final String SELECT_CLIENT_SECRET_SQL = "SELECT " + COLUMN_NAMES + " FROM " + TABLE_NAME + " WHERE ";
    // @formatter:off
    private static final String INSERT_CLIENT_SECRET_SQL = "INSERT INTO " + TABLE_NAME
            + "(" + COLUMN_NAMES + ") VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_CLIENT_SECRET_SQL = "UPDATE " + TABLE_NAME
            + " SET client_secret = ?, secret_expires_at = ?, deleted = ?, delete_time = ?"
            + " WHERE " + ID_FILTER;
    // @formatter:on
    private JdbcOperations jdbcOperations;
    private RowMapper<SecurityApplicationSecret> clientSecretRowMapper;
    private Function<SecurityApplicationSecret, List<SqlParameterValue>> clientSecretParametersMapper;

    public SecurityApplicationSecretJdbcRepository(JdbcOperations jdbcOperations) {
        Assert.notNull(jdbcOperations, "jdbcOperations cannot be null");
        this.jdbcOperations = jdbcOperations;
        this.clientSecretRowMapper = new SecurityApplicationSecretRowMapper();
        this.clientSecretParametersMapper = new SecurityApplicationSecretParametersMapper();
    }

    @Override
    public void save(SecurityApplicationSecret clientSecret) {
        Assert.notNull(clientSecret, "clientSecret cannot be null");
        SecurityApplicationSecret storedApplicationSecret = this.findBy(ID_FILTER, clientSecret.getId());
        if (storedApplicationSecret != null) {
            this.updateApplicationSecret(clientSecret);
        } else {
            this.insertApplicationSecret(clientSecret);
        }
    }

    @Override
    public List<SecurityApplicationSecret> findByClientId(String applicationId) {
        Assert.hasText(applicationId, "applicationId cannot be empty");
        return this.findListBy(CLIENT_ID_FILTER, applicationId);
    }

    private void updateApplicationSecret(SecurityApplicationSecret clientSecret) {
        List<SqlParameterValue> parameters = new ArrayList<>(this.clientSecretParametersMapper.apply(clientSecret));
        SqlParameterValue id = parameters.remove(0); // remove id
        parameters.remove(0); // remove application_id
        parameters.remove(3); // remove create_time
        parameters.add(id); // add where id
        PreparedStatementSetter pss = new ArgumentPreparedStatementSetter(parameters.toArray());
        this.jdbcOperations.update(UPDATE_CLIENT_SECRET_SQL, pss);
    }

    private void insertApplicationSecret(SecurityApplicationSecret clientSecret) {
        List<SqlParameterValue> parameters = this.clientSecretParametersMapper.apply(clientSecret);
        PreparedStatementSetter pss = new ArgumentPreparedStatementSetter(parameters.toArray());
        this.jdbcOperations.update(INSERT_CLIENT_SECRET_SQL, pss);
    }

    private SecurityApplicationSecret findBy(String filter, Object... args) {
        List<SecurityApplicationSecret> result = this.jdbcOperations.query(
                SELECT_CLIENT_SECRET_SQL + filter, this.clientSecretRowMapper, args);
        return !result.isEmpty() ? result.get(0) : null;
    }

    private List<SecurityApplicationSecret> findListBy(String filter, Object... args) {
        List<SecurityApplicationSecret> result = this.jdbcOperations.query(
                SELECT_CLIENT_SECRET_SQL + filter, this.clientSecretRowMapper, args);
        return result;
    }


    public static class SecurityApplicationSecretRowMapper implements RowMapper<SecurityApplicationSecret> {
        @Override
        public SecurityApplicationSecret mapRow(ResultSet rs, int rowNum) throws SQLException {
            Timestamp deleteTime = rs.getTimestamp("delete_time");
            Timestamp secretExpiresAt = rs.getTimestamp("secret_expires_at");
            // @formatter:off
            SecurityApplicationSecret clientSecret = SecurityApplicationSecret.withId(rs.getString("id"))
                    .applicationId(rs.getString("application_id"))
                    .clientSecret(rs.getString("application_secret"))
                    .secretExpiresAt(secretExpiresAt != null ? secretExpiresAt.toLocalDateTime() : null)
                    .createTime(rs.getTimestamp("create_time").toLocalDateTime())
                    .deleted(rs.getBoolean("deleted"))
                    .deleteTime(deleteTime != null ? deleteTime.toLocalDateTime() : null)
                    .build();
            return clientSecret;
            // @formatter:on
        }
    }

    public static class SecurityApplicationSecretParametersMapper implements Function<SecurityApplicationSecret, List<SqlParameterValue>> {
        @Override
        public List<SqlParameterValue> apply(SecurityApplicationSecret clientSecret) {
            // @formatter:off
            return Arrays.asList(
                    new SqlParameterValue(Types.VARCHAR, clientSecret.getId()),
                    new SqlParameterValue(Types.VARCHAR, clientSecret.getApplicationId()),
                    new SqlParameterValue(Types.VARCHAR, clientSecret.getApplicationSecret()),
                    new SqlParameterValue(Types.TIMESTAMP, Timestamp.valueOf(clientSecret.getSecretExpiresAt())),
                    new SqlParameterValue(Types.BOOLEAN, clientSecret.isDeleted()),
                    new SqlParameterValue(Types.TIMESTAMP, Timestamp.valueOf(clientSecret.getCreateTime())),
                    new SqlParameterValue(Types.TIMESTAMP, !ObjectUtils.isEmpty(clientSecret.getDeleteTime()) ?
                            Timestamp.valueOf(clientSecret.getDeleteTime()) : null)
            );
            // @formatter:on
        }
    }
}
