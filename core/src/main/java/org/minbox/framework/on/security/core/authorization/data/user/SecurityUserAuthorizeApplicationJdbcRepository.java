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

package org.minbox.framework.on.security.core.authorization.data.user;

import org.springframework.jdbc.core.*;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * 用户授权客户端数据存储库JDBC实现类
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class SecurityUserAuthorizeApplicationJdbcRepository implements SecurityUserAuthorizeApplicationRepository {
    // @formatter:off
    private static final String COLUMN_NAMES = "user_id, "
            + "application_id, "
            + "authorize_time";
    // @formatter:on
    private static final String TABLE_NAME = "security_user_authorize_applications";
    private static final String USER_ID_FILTER = "user_id = ?";
    private static final String SELECT_USER_AUTHORIZE_CLIENT_SQL = "SELECT " + COLUMN_NAMES + " FROM " + TABLE_NAME + " WHERE ";
    // @formatter:off
    private static final String INSERT_USER_AUTHORIZE_CLIENT_SQL = "INSERT INTO " + TABLE_NAME
            + "(" + COLUMN_NAMES + ") VALUES (?, ?, ?)";
    // @formatter:on
    private static final String REMOVE_USER_AUTHORIZE_CLIENT_SQL = "DELETE FROM " + TABLE_NAME + " WHERE " + USER_ID_FILTER;

    private JdbcOperations jdbcOperations;
    private RowMapper<SecurityUserAuthorizeApplication> userAuthorizeClientRowMapper;
    private Function<SecurityUserAuthorizeApplication, List<SqlParameterValue>> userAuthorizeClientParametersMapper;

    public SecurityUserAuthorizeApplicationJdbcRepository(JdbcOperations jdbcOperations) {
        Assert.notNull(jdbcOperations, "jdbcOperations cannot be null");
        this.jdbcOperations = jdbcOperations;
        this.userAuthorizeClientRowMapper = new SecurityUserAuthorizeApplicationJdbcRepository.SecurityUserAuthorizeClientRowMapper();
        this.userAuthorizeClientParametersMapper = new SecurityUserAuthorizeApplicationJdbcRepository.SecurityUserAuthorizeClientParametersMapper();
    }

    @Override
    public void insert(SecurityUserAuthorizeApplication userAuthorizeClient) {
        Assert.notNull(userAuthorizeClient, "userAuthorizeClient cannot be null");
        List<SqlParameterValue> parameters = this.userAuthorizeClientParametersMapper.apply(userAuthorizeClient);
        PreparedStatementSetter pss = new ArgumentPreparedStatementSetter(parameters.toArray());
        this.jdbcOperations.update(INSERT_USER_AUTHORIZE_CLIENT_SQL, pss);
    }

    private SecurityUserAuthorizeApplication findBy(String filter, Object... args) {
        List<SecurityUserAuthorizeApplication> result = this.jdbcOperations.query(
                SELECT_USER_AUTHORIZE_CLIENT_SQL + filter, this.userAuthorizeClientRowMapper, args);
        return !result.isEmpty() ? result.get(0) : null;
    }

    private List<SecurityUserAuthorizeApplication> findListBy(String filter, Object... args) {
        List<SecurityUserAuthorizeApplication> result = this.jdbcOperations.query(
                SELECT_USER_AUTHORIZE_CLIENT_SQL + filter, this.userAuthorizeClientRowMapper, args);
        return result;
    }

    @Override
    public void remove(String securityUserId, String securityClientId) {
        Assert.hasText(securityClientId, "securityClientId cannot be empty");
        Assert.hasText(securityUserId, "securityUserId cannot be empty");
        PreparedStatementSetter pass = new ArgumentPreparedStatementSetter(new SqlParameterValue[]{
                new SqlParameterValue(Types.VARCHAR, securityUserId),
                new SqlParameterValue(Types.VARCHAR, securityClientId)
        });
        this.jdbcOperations.update(REMOVE_USER_AUTHORIZE_CLIENT_SQL, pass);
    }

    @Override
    public List<SecurityUserAuthorizeApplication> findByUserId(String userId) {
        return this.findListBy(USER_ID_FILTER, userId);
    }

    public static class SecurityUserAuthorizeClientRowMapper implements RowMapper<SecurityUserAuthorizeApplication> {
        @Override
        public SecurityUserAuthorizeApplication mapRow(ResultSet rs, int rowNum) throws SQLException {
            // @formatter:off
            return SecurityUserAuthorizeApplication.withUserId(rs.getString("user_id"))
                    .applicationId(rs.getString("application_id"))
                    .authorizeTime(rs.getTimestamp("authorize_time").toLocalDateTime())
                    .build();
            // @formatter:on
        }
    }

    public static class SecurityUserAuthorizeClientParametersMapper implements Function<SecurityUserAuthorizeApplication, List<SqlParameterValue>> {
        @Override
        public List<SqlParameterValue> apply(SecurityUserAuthorizeApplication userAuthorizeClient) {
            return Arrays.asList(
                    new SqlParameterValue(Types.VARCHAR, userAuthorizeClient.getUserId()),
                    new SqlParameterValue(Types.VARCHAR, userAuthorizeClient.getApplicationId()),
                    new SqlParameterValue(Types.TIMESTAMP, Timestamp.valueOf(userAuthorizeClient.getAuthorizeTime()))

            );
        }
    }
}
