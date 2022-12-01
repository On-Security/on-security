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

import org.minbox.framework.on.security.core.authorization.ClientRedirectUriType;
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
 * 客户端跳转地址存储库JDBC实现类
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class SecurityClientRedirectUriJdbcRepository implements SecurityClientRedirectUriRepository {
    private static final String COLUMN_NAMES = "id, "
            + "client_id, "
            + "redirect_type, "
            + "redirect_uri, "
            + "create_time";
    // @formatter:on
    private static final String TABLE_NAME = "security_client_redirect_uris";
    private static final String ID_FILTER = "id = ?";
    private static final String SELECT_CLIENT_REDIRECT_URI_SQL = "SELECT " + COLUMN_NAMES + " FROM " + TABLE_NAME + " WHERE ";
    // @formatter:off
    private static final String INSERT_CLIENT_REDIRECT_URI_SQL = "INSERT INTO " + TABLE_NAME
            + "(" + COLUMN_NAMES + ") VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_CLIENT_REDIRECT_URI_SQL = "UPDATE " + TABLE_NAME
            + " SET redirect_type = ?, redirect_uri = ?"
            + " WHERE " + ID_FILTER;
    // @formatter:on
    private JdbcOperations jdbcOperations;
    private RowMapper<SecurityClientRedirectUri> clientRedirectUriRowMapper;
    private Function<SecurityClientRedirectUri, List<SqlParameterValue>> clientRedirectUriParametersMapper;

    public SecurityClientRedirectUriJdbcRepository(JdbcOperations jdbcOperations) {
        Assert.notNull(jdbcOperations, "jdbcOperations cannot be null");
        this.jdbcOperations = jdbcOperations;
        this.clientRedirectUriRowMapper = new SecurityClientRedirectUriJdbcRepository.SecurityClientRedirectUriRowMapper();
        this.clientRedirectUriParametersMapper = new SecurityClientRedirectUriJdbcRepository.SecurityClientRedirectUriParametersMapper();
    }

    @Override
    public void save(SecurityClientRedirectUri clientRedirectUri) {
        Assert.notNull(clientRedirectUri, "clientRedirectUri cannot be null");
        SecurityClientRedirectUri storedClientScope = this.findBy(ID_FILTER, clientRedirectUri.getId());
        if (storedClientScope != null) {
            this.updateClientScope(clientRedirectUri);
        } else {
            this.insertClientScope(clientRedirectUri);
        }
    }

    private void updateClientScope(SecurityClientRedirectUri clientRedirectUri) {
        List<SqlParameterValue> parameters = new ArrayList<>(this.clientRedirectUriParametersMapper.apply(clientRedirectUri));
        SqlParameterValue id = parameters.remove(0); // remove id
        parameters.remove(0); // remove client_id
        parameters.remove(2); // remove create_time
        parameters.add(id); // add where id
        PreparedStatementSetter pss = new ArgumentPreparedStatementSetter(parameters.toArray());
        this.jdbcOperations.update(UPDATE_CLIENT_REDIRECT_URI_SQL, pss);
    }

    private void insertClientScope(SecurityClientRedirectUri clientRedirectUri) {
        List<SqlParameterValue> parameters = this.clientRedirectUriParametersMapper.apply(clientRedirectUri);
        PreparedStatementSetter pss = new ArgumentPreparedStatementSetter(parameters.toArray());
        this.jdbcOperations.update(INSERT_CLIENT_REDIRECT_URI_SQL, pss);
    }

    private SecurityClientRedirectUri findBy(String filter, Object... args) {
        List<SecurityClientRedirectUri> result = this.jdbcOperations.query(
                SELECT_CLIENT_REDIRECT_URI_SQL + filter, this.clientRedirectUriRowMapper, args);
        return !result.isEmpty() ? result.get(0) : null;
    }


    public static class SecurityClientRedirectUriRowMapper implements RowMapper<SecurityClientRedirectUri> {
        @Override
        public SecurityClientRedirectUri mapRow(ResultSet rs, int rowNum) throws SQLException {
            // @formatter:off
            SecurityClientRedirectUri clientRedirectUri = SecurityClientRedirectUri.withId(rs.getString("id"))
                    .clientId(rs.getString("client_id"))
                    .redirectType(new ClientRedirectUriType(rs.getString("redirect_type")))
                    .redirectUri(rs.getString("redirect_uri"))
                    .createTime(rs.getTimestamp("create_time").toLocalDateTime())
                    .build();
            return clientRedirectUri;
            // @formatter:on
        }
    }

    public static class SecurityClientRedirectUriParametersMapper implements Function<SecurityClientRedirectUri, List<SqlParameterValue>> {
        @Override
        public List<SqlParameterValue> apply(SecurityClientRedirectUri clientRedirectUri) {
            // @formatter:off
            return Arrays.asList(
                    new SqlParameterValue(Types.VARCHAR, clientRedirectUri.getId()),
                    new SqlParameterValue(Types.VARCHAR, clientRedirectUri.getClientId()),
                    new SqlParameterValue(Types.VARCHAR, clientRedirectUri.getRedirectType().getValue()),
                    new SqlParameterValue(Types.VARCHAR, clientRedirectUri.getRedirectUri()),
                    new SqlParameterValue(Types.TIMESTAMP, Timestamp.valueOf(clientRedirectUri.getCreateTime()))
            );
            // @formatter:on
        }
    }
}
