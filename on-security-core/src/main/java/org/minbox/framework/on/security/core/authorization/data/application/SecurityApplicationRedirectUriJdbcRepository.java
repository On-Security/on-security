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
public class SecurityApplicationRedirectUriJdbcRepository implements SecurityApplicationRedirectUriRepository {
    private static final String COLUMN_NAMES = "id, "
            + "application_id, "
            + "redirect_type, "
            + "redirect_uri, "
            + "create_time";
    // @formatter:on
    private static final String TABLE_NAME = "security_application_redirect_uris";
    private static final String ID_FILTER = "id = ?";
    private static final String CLIENT_ID_FILTER = "application_id = ?";
    private static final String SELECT_CLIENT_REDIRECT_URI_SQL = "SELECT " + COLUMN_NAMES + " FROM " + TABLE_NAME + " WHERE ";
    // @formatter:off
    private static final String INSERT_CLIENT_REDIRECT_URI_SQL = "INSERT INTO " + TABLE_NAME
            + "(" + COLUMN_NAMES + ") VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_CLIENT_REDIRECT_URI_SQL = "UPDATE " + TABLE_NAME
            + " SET redirect_type = ?, redirect_uri = ?"
            + " WHERE " + ID_FILTER;
    // @formatter:on
    private JdbcOperations jdbcOperations;
    private RowMapper<SecurityApplicationRedirectUri> clientRedirectUriRowMapper;
    private Function<SecurityApplicationRedirectUri, List<SqlParameterValue>> clientRedirectUriParametersMapper;

    public SecurityApplicationRedirectUriJdbcRepository(JdbcOperations jdbcOperations) {
        Assert.notNull(jdbcOperations, "jdbcOperations cannot be null");
        this.jdbcOperations = jdbcOperations;
        this.clientRedirectUriRowMapper = new SecurityApplicationRedirectUriJdbcRepository.SecurityClientRedirectUriRowMapper();
        this.clientRedirectUriParametersMapper = new SecurityApplicationRedirectUriJdbcRepository.SecurityClientRedirectUriParametersMapper();
    }

    @Override
    public void save(SecurityApplicationRedirectUri clientRedirectUri) {
        Assert.notNull(clientRedirectUri, "clientRedirectUri cannot be null");
        SecurityApplicationRedirectUri storedClientRedirectUri = this.findBy(ID_FILTER, clientRedirectUri.getId());
        if (storedClientRedirectUri != null) {
            this.updateClientRedirectUri(clientRedirectUri);
        } else {
            this.insertClientRedirectUri(clientRedirectUri);
        }
    }

    @Override
    public List<SecurityApplicationRedirectUri> findByClientId(String applicationId) {
        Assert.hasText(applicationId, "applicationId cannot be empty");
        return this.findListBy(CLIENT_ID_FILTER, applicationId);
    }

    private void updateClientRedirectUri(SecurityApplicationRedirectUri clientRedirectUri) {
        List<SqlParameterValue> parameters = new ArrayList<>(this.clientRedirectUriParametersMapper.apply(clientRedirectUri));
        SqlParameterValue id = parameters.remove(0); // remove id
        parameters.remove(0); // remove application_id
        parameters.remove(2); // remove create_time
        parameters.add(id); // add where id
        PreparedStatementSetter pss = new ArgumentPreparedStatementSetter(parameters.toArray());
        this.jdbcOperations.update(UPDATE_CLIENT_REDIRECT_URI_SQL, pss);
    }

    private void insertClientRedirectUri(SecurityApplicationRedirectUri clientRedirectUri) {
        List<SqlParameterValue> parameters = this.clientRedirectUriParametersMapper.apply(clientRedirectUri);
        PreparedStatementSetter pss = new ArgumentPreparedStatementSetter(parameters.toArray());
        this.jdbcOperations.update(INSERT_CLIENT_REDIRECT_URI_SQL, pss);
    }

    private SecurityApplicationRedirectUri findBy(String filter, Object... args) {
        List<SecurityApplicationRedirectUri> result = this.jdbcOperations.query(
                SELECT_CLIENT_REDIRECT_URI_SQL + filter, this.clientRedirectUriRowMapper, args);
        return !result.isEmpty() ? result.get(0) : null;
    }

    private List<SecurityApplicationRedirectUri> findListBy(String filter, Object... args) {
        List<SecurityApplicationRedirectUri> result = this.jdbcOperations.query(
                SELECT_CLIENT_REDIRECT_URI_SQL + filter, this.clientRedirectUriRowMapper, args);
        return result;
    }


    public static class SecurityClientRedirectUriRowMapper implements RowMapper<SecurityApplicationRedirectUri> {
        @Override
        public SecurityApplicationRedirectUri mapRow(ResultSet rs, int rowNum) throws SQLException {
            // @formatter:off
            SecurityApplicationRedirectUri clientRedirectUri = SecurityApplicationRedirectUri.withId(rs.getString("id"))
                    .applicationId(rs.getString("application_id"))
                    .redirectType(new ClientRedirectUriType(rs.getString("redirect_type")))
                    .redirectUri(rs.getString("redirect_uri"))
                    .createTime(rs.getTimestamp("create_time").toLocalDateTime())
                    .build();
            return clientRedirectUri;
            // @formatter:on
        }
    }

    public static class SecurityClientRedirectUriParametersMapper implements Function<SecurityApplicationRedirectUri, List<SqlParameterValue>> {
        @Override
        public List<SqlParameterValue> apply(SecurityApplicationRedirectUri clientRedirectUri) {
            // @formatter:off
            return Arrays.asList(
                    new SqlParameterValue(Types.VARCHAR, clientRedirectUri.getId()),
                    new SqlParameterValue(Types.VARCHAR, clientRedirectUri.getApplicationId()),
                    new SqlParameterValue(Types.VARCHAR, clientRedirectUri.getRedirectType().getValue()),
                    new SqlParameterValue(Types.VARCHAR, clientRedirectUri.getRedirectUri()),
                    new SqlParameterValue(Types.TIMESTAMP, Timestamp.valueOf(clientRedirectUri.getCreateTime()))
            );
            // @formatter:on
        }
    }
}
