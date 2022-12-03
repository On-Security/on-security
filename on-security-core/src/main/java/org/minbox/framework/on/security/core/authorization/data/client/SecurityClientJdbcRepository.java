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
public class SecurityClientJdbcRepository implements SecurityClientRepository {
    // @formatter:off
    private static final String COLUMN_NAMES = "id, "
            + "client_id, "
            + "region_id, "
            + "protocol_id, "
            + "display_name, "
            + "`describe`, "
            + "enabled, "
            + "deleted, "
            + "create_time";
    // @formatter:on
    private static final String TABLE_NAME = "security_client";
    private static final String ID_FILTER = "id = ?";
    private static final String CLIENT_ID_FILTER = "client_id = ?";
    private static final String SELECT_CLIENT_SQL = "SELECT " + COLUMN_NAMES + " FROM " + TABLE_NAME + " WHERE ";
    // @formatter:off
    private static final String INSERT_CLIENT_SQL = "INSERT INTO " + TABLE_NAME
            + "(" + COLUMN_NAMES + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_CLIENT_SQL = "UPDATE " + TABLE_NAME
            + " SET display_name = ?, `describe` = ?, enabled = ?, deleted = ?"
            + " WHERE " + ID_FILTER;
    // @formatter:on

    private JdbcOperations jdbcOperations;
    private RowMapper<SecurityClient> clientRowMapper;
    private Function<SecurityClient, List<SqlParameterValue>> clientParametersMapper;

    public SecurityClientJdbcRepository(JdbcOperations jdbcOperations) {
        Assert.notNull(jdbcOperations, "jdbcOperations cannot be null");
        this.jdbcOperations = jdbcOperations;
        this.clientRowMapper = new SecurityClientJdbcRepository.SecurityClientRowMapper();
        this.clientParametersMapper = new SecurityClientJdbcRepository.SecurityClientParametersMapper();
    }

    @Override
    public void save(SecurityClient client) {
        Assert.notNull(client, "client cannot be null");
        SecurityClient storedClient = this.findBy(ID_FILTER, client.getId());
        if (storedClient != null) {
            this.updateClient(client);
        } else {
            this.insertClient(client);
        }
    }

    @Override
    public SecurityClient findById(String id) {
        Assert.hasText(id, "id cannot be empty");
        return this.findBy(ID_FILTER, id);
    }

    @Override
    public SecurityClient findByClientId(String clientId) {
        Assert.hasText(clientId, "clientId cannot be empty");
        return this.findBy(CLIENT_ID_FILTER, clientId);
    }

    private void updateClient(SecurityClient client) {
        List<SqlParameterValue> parameters = new ArrayList<>(this.clientParametersMapper.apply(client));
        SqlParameterValue id = parameters.remove(0); // remove id
        parameters.remove(0); // remove client_id
        parameters.remove(0); // remove region_id
        parameters.remove(0); // remove protocol_id
        parameters.remove(4); // remove create_time
        parameters.add(id); // add where id
        PreparedStatementSetter pss = new ArgumentPreparedStatementSetter(parameters.toArray());
        this.jdbcOperations.update(UPDATE_CLIENT_SQL, pss);
    }

    private void insertClient(SecurityClient client) {
        this.assertUniqueIdentifiers(client);
        List<SqlParameterValue> parameters = this.clientParametersMapper.apply(client);
        PreparedStatementSetter pss = new ArgumentPreparedStatementSetter(parameters.toArray());
        this.jdbcOperations.update(INSERT_CLIENT_SQL, pss);
    }

    private void assertUniqueIdentifiers(SecurityClient client) {
        SecurityClient checkObject = findBy("client_id = ?", client.getClientId());
        Assert.isNull(checkObject, "Client ID must be unique，duplicate ID：" + client.getClientId());
    }

    private SecurityClient findBy(String filter, Object... args) {
        List<SecurityClient> result = this.jdbcOperations.query(
                SELECT_CLIENT_SQL + filter, this.clientRowMapper, args);
        return !result.isEmpty() ? result.get(0) : null;
    }

    /**
     * 将{@link ResultSet}数据行映射绑定到{@link SecurityClient}
     */
    public static class SecurityClientRowMapper implements RowMapper<SecurityClient> {
        @Override
        public SecurityClient mapRow(ResultSet rs, int rowNum) throws SQLException {
            // @formatter:off
            SecurityClient client = SecurityClient.withId(rs.getString("id"))
                    .clientId(rs.getString("client_id"))
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
     * {@link SecurityClient}与{@link SqlParameterValue}映射
     */
    public static class SecurityClientParametersMapper implements Function<SecurityClient, List<SqlParameterValue>> {
        @Override
        public List<SqlParameterValue> apply(SecurityClient client) {
            // @formatter:off
            return Arrays.asList(
                    new SqlParameterValue(Types.VARCHAR, client.getId()),
                    new SqlParameterValue(Types.VARCHAR, client.getClientId()),
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
