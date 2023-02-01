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

import org.minbox.framework.on.security.core.authorization.ClientProtocol;
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
 * 客户端数据存储库JDBC方式实现类
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class SecurityApplicationJdbcRepository implements SecurityApplicationRepository {
    // @formatter:off
    private static final String COLUMN_NAMES = "id, "
            + "application_id, "
            + "region_id, "
            + "protocol_id, "
            + "display_name, "
            + "`describe`, "
            + "enabled, "
            + "deleted, "
            + "create_time";
    // @formatter:on
    private static final String TABLE_NAME = "security_application";
    private static final String ID_FILTER = "id = ?";
    private static final String CLIENT_ID_FILTER = "application_id = ?";
    private static final String SELECT_CLIENT_SQL = "SELECT " + COLUMN_NAMES + " FROM " + TABLE_NAME + " WHERE ";
    // @formatter:off
    private static final String INSERT_CLIENT_SQL = "INSERT INTO " + TABLE_NAME
            + "(" + COLUMN_NAMES + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_CLIENT_SQL = "UPDATE " + TABLE_NAME
            + " SET display_name = ?, `describe` = ?, enabled = ?, deleted = ?"
            + " WHERE " + ID_FILTER;
    // @formatter:on

    private JdbcOperations jdbcOperations;
    private RowMapper<SecurityApplication> clientRowMapper;
    private Function<SecurityApplication, List<SqlParameterValue>> clientParametersMapper;

    public SecurityApplicationJdbcRepository(JdbcOperations jdbcOperations) {
        Assert.notNull(jdbcOperations, "jdbcOperations cannot be null");
        this.jdbcOperations = jdbcOperations;
        this.clientRowMapper = new SecurityApplicationJdbcRepository.SecurityClientRowMapper();
        this.clientParametersMapper = new SecurityApplicationJdbcRepository.SecurityClientParametersMapper();
    }

    @Override
    public void save(SecurityApplication client) {
        Assert.notNull(client, "client cannot be null");
        SecurityApplication storedClient = this.findBy(ID_FILTER, client.getId());
        if (storedClient != null) {
            this.updateClient(client);
        } else {
            this.insertClient(client);
        }
    }

    @Override
    public SecurityApplication findById(String id) {
        Assert.hasText(id, "id cannot be empty");
        return this.findBy(ID_FILTER, id);
    }

    @Override
    public SecurityApplication findByClientId(String applicationId) {
        Assert.hasText(applicationId, "applicationId cannot be empty");
        return this.findBy(CLIENT_ID_FILTER, applicationId);
    }

    private void updateClient(SecurityApplication client) {
        List<SqlParameterValue> parameters = new ArrayList<>(this.clientParametersMapper.apply(client));
        SqlParameterValue id = parameters.remove(0); // remove id
        parameters.remove(0); // remove application_id
        parameters.remove(0); // remove region_id
        parameters.remove(0); // remove protocol_id
        parameters.remove(4); // remove create_time
        parameters.add(id); // add where id
        PreparedStatementSetter pss = new ArgumentPreparedStatementSetter(parameters.toArray());
        this.jdbcOperations.update(UPDATE_CLIENT_SQL, pss);
    }

    private void insertClient(SecurityApplication client) {
        this.assertUniqueIdentifiers(client);
        List<SqlParameterValue> parameters = this.clientParametersMapper.apply(client);
        PreparedStatementSetter pss = new ArgumentPreparedStatementSetter(parameters.toArray());
        this.jdbcOperations.update(INSERT_CLIENT_SQL, pss);
    }

    private void assertUniqueIdentifiers(SecurityApplication client) {
        SecurityApplication checkObject = findBy("application_id = ?", client.getApplicationId());
        Assert.isNull(checkObject, "Client ID must be unique，duplicate ID：" + client.getApplicationId());
    }

    private SecurityApplication findBy(String filter, Object... args) {
        List<SecurityApplication> result = this.jdbcOperations.query(
                SELECT_CLIENT_SQL + filter, this.clientRowMapper, args);
        return !result.isEmpty() ? result.get(0) : null;
    }

    /**
     * 将{@link ResultSet}数据行映射绑定到{@link SecurityApplication}
     */
    public static class SecurityClientRowMapper implements RowMapper<SecurityApplication> {
        @Override
        public SecurityApplication mapRow(ResultSet rs, int rowNum) throws SQLException {
            // @formatter:off
            SecurityApplication client = SecurityApplication.withId(rs.getString("id"))
                    .applicationId(rs.getString("application_id"))
                    .regionId(rs.getString("region_id"))
                    .protocol(new ClientProtocol(rs.getString("protocol_id")))
                    .displayName(rs.getString("display_name"))
                    .describe(rs.getString("describe"))
                    .enabled(rs.getBoolean("enabled"))
                    .deleted(rs.getBoolean("deleted"))
                    .createTime(rs.getTimestamp("create_time").toLocalDateTime())
                    .build();
            // @formatter:on
            return client;
        }
    }

    /**
     * {@link SecurityApplication}与{@link SqlParameterValue}映射
     */
    public static class SecurityClientParametersMapper implements Function<SecurityApplication, List<SqlParameterValue>> {
        @Override
        public List<SqlParameterValue> apply(SecurityApplication client) {
            // @formatter:off
            return Arrays.asList(
                    new SqlParameterValue(Types.VARCHAR, client.getId()),
                    new SqlParameterValue(Types.VARCHAR, client.getApplicationId()),
                    new SqlParameterValue(Types.VARCHAR, client.getRegionId()),
                    new SqlParameterValue(Types.VARCHAR, client.getProtocol().getName()),
                    new SqlParameterValue(Types.VARCHAR, client.getDisplayName()),
                    new SqlParameterValue(Types.VARCHAR, client.getDescribe()),
                    new SqlParameterValue(Types.BOOLEAN, client.isEnabled()),
                    new SqlParameterValue(Types.BOOLEAN, client.isDeleted()),
                    new SqlParameterValue(Types.TIMESTAMP, Timestamp.valueOf(client.getCreateTime()))
            );
            // @formatter:on
        }
    }
}
