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
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * 用户授权许可数据存储库JDBC实现类
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class SecurityUserAuthorizeConsentJdbcRepository implements SecurityUserAuthorizeConsentRepository {
    // @formatter:off
    private static final String COLUMN_NAMES = "user_id, "
            + "username, "
            + "client_id, "
            + "authorities, "
            + "authorize_time";
    // @formatter:on
    private static final String TABLE_NAME = "security_user_authorize_consents";
    private static final String USER_ID_AND_CLIENT_ID_FILTER = "user_id = ? and client_id = ?";
    private static final String SELECT_USER_AUTHORIZE_CONSENT_SQL = "SELECT " + COLUMN_NAMES + " FROM " + TABLE_NAME + " WHERE ";
    // @formatter:off
    private static final String INSERT_USER_AUTHORIZE_CONSENT_SQL = "INSERT INTO " + TABLE_NAME
            + "(" + COLUMN_NAMES + ") VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_USER_AUTHORIZE_CONSENT_SQL = "UPDATE " + TABLE_NAME
            + " SET username = ?, authorities = ?"
            + " WHERE " + USER_ID_AND_CLIENT_ID_FILTER;
    // @formatter:on
    private static final String REMOVE_USER_AUTHORIZE_CONSENT_SQL = "DELETE FROM " + TABLE_NAME + " WHERE " + USER_ID_AND_CLIENT_ID_FILTER;

    private JdbcOperations jdbcOperations;
    private RowMapper<SecurityUserAuthorizeConsent> userAuthorizeConsentRowMapper;
    private Function<SecurityUserAuthorizeConsent, List<SqlParameterValue>> userAuthorizeConsentParametersMapper;

    public SecurityUserAuthorizeConsentJdbcRepository(JdbcOperations jdbcOperations) {
        Assert.notNull(jdbcOperations, "jdbcOperations cannot be null");
        this.jdbcOperations = jdbcOperations;
        this.userAuthorizeConsentRowMapper = new SecurityUserAuthorizeConsentRowMapper();
        this.userAuthorizeConsentParametersMapper = new SecurityUserAuthorizeConsentParametersMapper();
    }

    @Override
    public void save(SecurityUserAuthorizeConsent userAuthorizeConsent) {
        Assert.notNull(userAuthorizeConsent, "userAuthorizeConsent cannot be null");
        SecurityUserAuthorizeConsent storedUserAuthorizeConsent =
                this.findBy(USER_ID_AND_CLIENT_ID_FILTER, userAuthorizeConsent.getUserId(), userAuthorizeConsent.getClientId());
        if (storedUserAuthorizeConsent != null) {
            this.updateUserAuthorizeConsent(userAuthorizeConsent);
        } else {
            this.insertUserAuthorizeConsent(userAuthorizeConsent);
        }
    }

    private void updateUserAuthorizeConsent(SecurityUserAuthorizeConsent userAuthorizeConsent) {
        List<SqlParameterValue> parameters = new ArrayList<>(this.userAuthorizeConsentParametersMapper.apply(userAuthorizeConsent));
        SqlParameterValue userId = parameters.remove(0); // remove user_id
        SqlParameterValue clientId = parameters.remove(1); // remove client_id
        parameters.remove(2); // remove authorize_time
        parameters.add(userId); // add where user_id
        parameters.add(clientId); // add where client_id
        PreparedStatementSetter pss = new ArgumentPreparedStatementSetter(parameters.toArray());
        this.jdbcOperations.update(UPDATE_USER_AUTHORIZE_CONSENT_SQL, pss);
    }

    private void insertUserAuthorizeConsent(SecurityUserAuthorizeConsent userAuthorizeConsent) {
        List<SqlParameterValue> parameters = this.userAuthorizeConsentParametersMapper.apply(userAuthorizeConsent);
        PreparedStatementSetter pss = new ArgumentPreparedStatementSetter(parameters.toArray());
        this.jdbcOperations.update(INSERT_USER_AUTHORIZE_CONSENT_SQL, pss);
    }

    private SecurityUserAuthorizeConsent findBy(String filter, Object... args) {
        List<SecurityUserAuthorizeConsent> result = this.jdbcOperations.query(
                SELECT_USER_AUTHORIZE_CONSENT_SQL + filter, this.userAuthorizeConsentRowMapper, args);
        return !result.isEmpty() ? result.get(0) : null;
    }

    @Override
    public void remove(String securityUserId, String securityClientId) {
        Assert.hasText(securityClientId, "securityClientId cannot be empty");
        Assert.hasText(securityUserId, "securityUserId cannot be empty");
        PreparedStatementSetter pass = new ArgumentPreparedStatementSetter(new SqlParameterValue[]{
                new SqlParameterValue(Types.VARCHAR, securityUserId),
                new SqlParameterValue(Types.VARCHAR, securityClientId)
        });
        this.jdbcOperations.update(REMOVE_USER_AUTHORIZE_CONSENT_SQL, pass);
    }

    @Override
    public SecurityUserAuthorizeConsent findByUserIdAndClientId(String securityUserId, String securityClientId) {
        Assert.hasText(securityClientId, "securityClientId cannot be empty");
        Assert.hasText(securityUserId, "securityUserId cannot be empty");
        return this.findBy(USER_ID_AND_CLIENT_ID_FILTER, securityUserId, securityClientId);
    }


    public static class SecurityUserAuthorizeConsentRowMapper implements RowMapper<SecurityUserAuthorizeConsent> {
        @Override
        public SecurityUserAuthorizeConsent mapRow(ResultSet rs, int rowNum) throws SQLException {
            SecurityUserAuthorizeConsent.Builder builder = SecurityUserAuthorizeConsent.withUserId(rs.getString("user_id"));
            // @formatter:off
            builder.clientId(rs.getString("client_id"))
                    .username(rs.getString("username"))
                    .authorizeTime(rs.getTimestamp("authorize_time").toLocalDateTime());
            // @formatter:on
            String authorizationConsentAuthorities = rs.getString("authorities");
            if (authorizationConsentAuthorities != null) {
                builder.authorities(StringUtils.commaDelimitedListToSet(authorizationConsentAuthorities));
            }
            return builder.build();
        }
    }

    public static class SecurityUserAuthorizeConsentParametersMapper implements Function<SecurityUserAuthorizeConsent, List<SqlParameterValue>> {
        @Override
        public List<SqlParameterValue> apply(SecurityUserAuthorizeConsent userAuthorizeConsent) {
            return Arrays.asList(
                    new SqlParameterValue(Types.VARCHAR, userAuthorizeConsent.getUserId()),
                    new SqlParameterValue(Types.VARCHAR, userAuthorizeConsent.getUsername()),
                    new SqlParameterValue(Types.VARCHAR, userAuthorizeConsent.getClientId()),
                    new SqlParameterValue(Types.VARCHAR, StringUtils.collectionToDelimitedString(userAuthorizeConsent.getAuthorities(), ",")),
                    new SqlParameterValue(Types.TIMESTAMP, Timestamp.valueOf(userAuthorizeConsent.getAuthorizeTime()))

            );
        }
    }
}
