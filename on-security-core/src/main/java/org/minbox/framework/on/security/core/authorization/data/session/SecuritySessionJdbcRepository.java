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

package org.minbox.framework.on.security.core.authorization.data.session;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.minbox.framework.on.security.core.authorization.AccessTokenType;
import org.minbox.framework.on.security.core.authorization.SessionState;
import org.minbox.framework.on.security.core.authorization.jackson2.OnSecurityAuthorizationServerJackson2Module;
import org.springframework.jdbc.core.*;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;

/**
 * 会话数据存储库JDBC实现类
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class SecuritySessionJdbcRepository implements SecuritySessionRepository {
    // @formatter:off
    private static final String COLUMN_NAMES = "id, "
            + "region_id, "
            + "client_id, "
            + "user_id, "
            + "username, "
            + "`state`, "
            + "session_state, "
            + "attributes, "
            + "authorization_grant_type, "
            + "authorization_scopes, "
            + "authorization_code_value, "
            + "authorization_code_issued_at, "
            + "authorization_code_expires_at, "
            + "authorization_code_metadata, "
            + "access_token_value, "
            + "access_token_issued_at, "
            + "access_token_expires_at, "
            + "access_token_metadata, "
            + "access_token_type, "
            + "access_token_scopes, "
            + "oidc_id_token_value, "
            + "oidc_id_token_issued_at, "
            + "oidc_id_token_expires_at, "
            + "oidc_id_token_metadata, "
            + "refresh_token_value, "
            + "refresh_token_issued_at, "
            + "refresh_token_expires_at, "
            + "refresh_token_metadata, "
            + "create_time";
    // @formatter:on

    private static final String TABLE_NAME = "security_session";

    private static final String PK_FILTER = "id = ?";
    private static final String UNKNOWN_TOKEN_TYPE_FILTER = "state = ? OR authorization_code_value = ? OR " +
            "access_token_value = ? OR refresh_token_value = ?";

    private static final String STATE_FILTER = "state = ?";
    private static final String AUTHORIZATION_CODE_FILTER = "authorization_code_value = ?";
    private static final String ACCESS_TOKEN_FILTER = "access_token_value = ?";
    private static final String REFRESH_TOKEN_FILTER = "refresh_token_value = ?";
    private static final String SELECT_SESSION_SQL = "SELECT " + COLUMN_NAMES + " FROM " + TABLE_NAME + " WHERE ";
    private static final String REMOVE_SESSION_BY_PK_SQL = "DELETE FROM " + TABLE_NAME + " WHERE " + PK_FILTER;
    // @formatter:off
    private static final String INSERT_SESSION_SQL = "INSERT INTO " + TABLE_NAME
            + "(" + COLUMN_NAMES + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_SESSION_SQL = "UPDATE " + TABLE_NAME
            + " SET `state` = ?,session_state = ?, attributes = ?, authorization_grant_type = ?, authorization_scopes = ?, authorization_code_value = ?,"
            + "authorization_code_issued_at = ?,authorization_code_expires_at = ?,authorization_code_metadata = ?,"
            + "access_token_value = ?,access_token_issued_at = ?,access_token_expires_at = ?,access_token_metadata = ?,"
            + "access_token_type = ?,access_token_scopes = ?,oidc_id_token_value = ?,oidc_id_token_issued_at = ?,oidc_id_token_expires_at = ?,"
            + "oidc_id_token_metadata = ?,refresh_token_value = ?,refresh_token_issued_at = ?,refresh_token_expires_at = ?,refresh_token_metadata = ?"
            + " WHERE " + PK_FILTER;
    // @formatter:on
    private JdbcOperations jdbcOperations;
    private RowMapper<SecuritySession> sessionRowMapper;
    private Function<SecuritySession, List<SqlParameterValue>> sessionParametersMapper;

    public SecuritySessionJdbcRepository(JdbcOperations jdbcOperations) {
        Assert.notNull(jdbcOperations, "jdbcOperations cannot be null");
        this.jdbcOperations = jdbcOperations;
        this.sessionRowMapper = new SecuritySessionRowMapper();
        this.sessionParametersMapper = new SecuritySessionParametersMapper();
    }

    @Override
    public void save(SecuritySession session) {
        Assert.notNull(session, "session cannot be null");
        SecuritySession securitySession = this.findById(session.getId());
        if (securitySession == null) {
            this.insertSecuritySession(session);
        } else {
            this.updateSecuritySession(session);
        }
    }

    private void insertSecuritySession(SecuritySession session) {
        List<SqlParameterValue> parameters = this.sessionParametersMapper.apply(session);
        PreparedStatementSetter pss = new ArgumentPreparedStatementSetter(parameters.toArray());
        this.jdbcOperations.update(INSERT_SESSION_SQL, pss);
    }

    private void updateSecuritySession(SecuritySession session) {
        List<SqlParameterValue> parameters = new ArrayList<>(this.sessionParametersMapper.apply(session));
        SqlParameterValue id = parameters.remove(0); // remove id
        parameters.remove(0); // remove region_id
        parameters.remove(0); // remove client_id
        parameters.remove(0); // remove user_id
        parameters.remove(0); // remove username
        parameters.remove(23);// remove create_time
        parameters.add(id); // add where id
        PreparedStatementSetter pss = new ArgumentPreparedStatementSetter(parameters.toArray());
        this.jdbcOperations.update(UPDATE_SESSION_SQL, pss);
    }

    @Override
    public void removeById(String sessionId) {
        Assert.hasText(sessionId, "sessionId cannot be null");
        SqlParameterValue[] parameterValues = new SqlParameterValue[]{
                new SqlParameterValue(Types.VARCHAR, sessionId)
        };
        PreparedStatementSetter pass = new ArgumentPreparedStatementSetter(parameterValues);
        this.jdbcOperations.update(REMOVE_SESSION_BY_PK_SQL, pass);
    }

    @Override
    public SecuritySession findById(String id) {
        Assert.hasText(id, "id cannot be empty");
        return this.findBy(PK_FILTER, id);
    }

    @Override
    public SecuritySession findByToken(String token, OAuth2TokenType tokenType) {
        Assert.hasText(token, "token cannot be empty");
        if (tokenType == null) {
            return findBy(UNKNOWN_TOKEN_TYPE_FILTER, token, token, token, token);
        } else if (OAuth2ParameterNames.STATE.equals(tokenType.getValue())) {
            return findBy(STATE_FILTER, token);
        } else if (OAuth2ParameterNames.CODE.equals(tokenType.getValue())) {
            return findBy(AUTHORIZATION_CODE_FILTER, token);
        } else if (OAuth2TokenType.ACCESS_TOKEN.equals(tokenType)) {
            return findBy(ACCESS_TOKEN_FILTER, token);
        } else if (OAuth2TokenType.REFRESH_TOKEN.equals(tokenType)) {
            return findBy(REFRESH_TOKEN_FILTER, token);
        }
        return null;
    }

    private SecuritySession findBy(String filter, Object... args) {
        List<SecuritySession> result = this.jdbcOperations.query(
                SELECT_SESSION_SQL + filter, this.sessionRowMapper, args);
        return !result.isEmpty() ? result.get(0) : null;
    }

    /**
     * 将{@link ResultSet}转换为{@link SecuritySession}对象实例
     */
    public static class SecuritySessionRowMapper implements RowMapper<SecuritySession> {
        private ObjectMapper objectMapper = new ObjectMapper();

        public SecuritySessionRowMapper() {
            ClassLoader classLoader = SecuritySessionJdbcRepository.class.getClassLoader();
            List<Module> securityModules = SecurityJackson2Modules.getModules(classLoader);
            this.objectMapper.registerModules(securityModules);
            this.objectMapper.registerModule(new OnSecurityAuthorizationServerJackson2Module());
        }

        @Override
        public SecuritySession mapRow(ResultSet rs, int rowNum) throws SQLException {
            SecuritySession.Builder builder = SecuritySession.withId(rs.getString("id"));
            // @formatter:off
            builder.regionId(rs.getString("region_id"))
                    .clientId(rs.getString("client_id"))
                    .userId(rs.getString("user_id"))
                    .username(rs.getString("username"))
                    .state(rs.getString("state"))
                    .sessionState(new SessionState(rs.getString("session_state")))
                    .createTime(this.timestampToLocalDateTime(rs,"create_time"));
            // @formatter:on
            String attributes = rs.getString("attributes");
            if (!ObjectUtils.isEmpty(attributes)) {
                builder.attributes(parseMap(attributes));
            }
            builder.authorizationGrantType(new AuthorizationGrantType(rs.getString("authorization_grant_type")));

            String authorizedScopesString = rs.getString("authorization_scopes");
            Set<String> authorizedScopes = Collections.emptySet();
            if (authorizedScopesString != null) {
                authorizedScopes = StringUtils.commaDelimitedListToSet(authorizedScopesString);
            }
            builder.authorizationScopes(authorizedScopes);

            // authorizationCode
            // @formatter:off
            builder.authorizationCodeValue(rs.getString("authorization_code_value"))
                    .authorizationCodeIssuedAt(this.timestampToLocalDateTime(rs,"authorization_code_issued_at"))
                    .authorizationCodeExpiresAt(this.timestampToLocalDateTime(rs,"authorization_code_expires_at"));
            // @formatter:on
            String authorizationCodeMetadata = rs.getString("authorization_code_metadata");
            if (!ObjectUtils.isEmpty(authorizationCodeMetadata)) {
                builder.authorizationCodeMetadata(parseMap(authorizationCodeMetadata));
            }

            // accessToken
            // @formatter:off
            builder.accessTokenValue(rs.getString("access_token_value"))
                    .accessTokenIssuedAt(this.timestampToLocalDateTime(rs,"access_token_issued_at"))
                    .accessTokenExpiresAt(this.timestampToLocalDateTime(rs,"access_token_expires_at"));
            // @formatter:on
            String accessTokenMetadata = rs.getString("access_token_metadata");
            if (!ObjectUtils.isEmpty(accessTokenMetadata)) {
                builder.accessTokenMetadata(parseMap(accessTokenMetadata));
            }
            String accessTokenType = rs.getString("access_token_type");
            if (!ObjectUtils.isEmpty(accessTokenType)) {
                builder.accessTokenType(new AccessTokenType(accessTokenType));
            }
            String accessTokenScopesString = rs.getString("access_token_scopes");
            Set<String> accessTokenScopes = Collections.emptySet();
            if (!ObjectUtils.isEmpty(accessTokenScopes)) {
                accessTokenScopes = StringUtils.commaDelimitedListToSet(accessTokenScopesString);
            }
            builder.accessTokenScopes(accessTokenScopes);

            // oidcIdToken
            // @formatter:off
            builder.oidcIdTokenValue(rs.getString("oidc_id_token_value"))
                    .oidcIdTokenIssuedAt(this.timestampToLocalDateTime(rs,"oidc_id_token_issued_at"))
                    .oidcIdTokenExpiresAt(this.timestampToLocalDateTime(rs,"oidc_id_token_expires_at"));
            String oidcIdTokenMetadata = rs.getString("oidc_id_token_metadata");
            // @formatter:on
            if (!ObjectUtils.isEmpty(oidcIdTokenMetadata)) {
                builder.oidcIdTokenMetadata(parseMap(oidcIdTokenMetadata));
            }

            // refreshToken
            // @formatter:off
            builder.refreshTokenValue(rs.getString("refresh_token_value"))
                    .refreshTokenIssuedAt(this.timestampToLocalDateTime(rs,"refresh_token_issued_at"))
                    .refreshTokenExpiresAt(this.timestampToLocalDateTime(rs,"refresh_token_expires_at"));
            // @formatter:on
            String refreshTokenMetadata = rs.getString("refresh_token_metadata");
            if (!ObjectUtils.isEmpty(refreshTokenMetadata)) {
                builder.refreshTokenMetadata(parseMap(refreshTokenMetadata));
            }
            return builder.build();
        }

        private Map<String, Object> parseMap(String data) {
            try {
                // @formatter:off
                if(ObjectUtils.isEmpty(data)){
                    return null;
                }
                return this.objectMapper.readValue(data, new TypeReference<Map<String, Object>>() {});
                // @formatter:on
            } catch (Exception ex) {
                throw new IllegalArgumentException(ex.getMessage(), ex);
            }
        }

        private LocalDateTime timestampToLocalDateTime(ResultSet rs, String columnName) throws SQLException {
            Timestamp timestamp = rs.getTimestamp(columnName);
            return timestamp != null ? timestamp.toLocalDateTime() : null;
        }
    }

    /**
     * 将{@link SecuritySession}转换为{@link SqlParameterValue}列表
     */
    public static class SecuritySessionParametersMapper implements Function<SecuritySession, List<SqlParameterValue>> {
        private ObjectMapper objectMapper = new ObjectMapper();

        public SecuritySessionParametersMapper() {
            ClassLoader classLoader = SecuritySessionJdbcRepository.class.getClassLoader();
            List<Module> securityModules = SecurityJackson2Modules.getModules(classLoader);
            this.objectMapper.registerModules(securityModules);
            this.objectMapper.registerModule(new OnSecurityAuthorizationServerJackson2Module());
        }

        @Override
        public List<SqlParameterValue> apply(SecuritySession session) {
            List<SqlParameterValue> parameters = new ArrayList<>();
            parameters.add(new SqlParameterValue(Types.VARCHAR, session.getId()));
            parameters.add(new SqlParameterValue(Types.VARCHAR, session.getRegionId()));
            parameters.add(new SqlParameterValue(Types.VARCHAR, session.getClientId()));
            parameters.add(new SqlParameterValue(Types.VARCHAR, session.getUserId()));
            parameters.add(new SqlParameterValue(Types.VARCHAR, session.getUsername()));
            parameters.add(new SqlParameterValue(Types.VARCHAR, session.getState()));
            String sessionState = null;
            if (!ObjectUtils.isEmpty(session.getSessionState())) {
                sessionState = session.getSessionState().getValue();
            }
            parameters.add(new SqlParameterValue(Types.VARCHAR, sessionState));
            parameters.add(new SqlParameterValue(Types.VARCHAR, writeMap(session.getAttributes())));
            parameters.add(new SqlParameterValue(Types.VARCHAR, session.getAuthorizationGrantType().getValue()));

            String authorizedScopes = null;
            if (!CollectionUtils.isEmpty(session.getAuthorizationScopes())) {
                authorizedScopes = StringUtils.collectionToDelimitedString(session.getAuthorizationScopes(), ",");
            }
            parameters.add(new SqlParameterValue(Types.VARCHAR, authorizedScopes));

            // authorizationCode
            parameters.add(new SqlParameterValue(Types.VARCHAR, session.getAuthorizationCodeValue()));
            parameters.add(new SqlParameterValue(Types.TIMESTAMP, this.localDateTimeToTimestamp(session.getAuthorizationCodeIssuedAt())));
            parameters.add(new SqlParameterValue(Types.TIMESTAMP, this.localDateTimeToTimestamp(session.getAuthorizationCodeExpiresAt())));
            parameters.add(new SqlParameterValue(Types.VARCHAR, writeMap(session.getAuthorizationCodeMetadata())));

            // accessToken
            parameters.add(new SqlParameterValue(Types.VARCHAR, session.getAccessTokenValue()));
            parameters.add(new SqlParameterValue(Types.TIMESTAMP, this.localDateTimeToTimestamp(session.getAccessTokenIssuedAt())));
            parameters.add(new SqlParameterValue(Types.TIMESTAMP, this.localDateTimeToTimestamp(session.getAccessTokenExpiresAt())));
            parameters.add(new SqlParameterValue(Types.VARCHAR, writeMap(session.getAccessTokenMetadata())));
            parameters.add(new SqlParameterValue(Types.VARCHAR,
                    session.getAccessTokenType() != null ? session.getAccessTokenType().getValue() : null));
            String accessTokenScopes = null;
            if (!ObjectUtils.isEmpty(session.getAccessTokenScopes())) {
                accessTokenScopes = StringUtils.collectionToDelimitedString(session.getAccessTokenScopes(), ",");
            }
            parameters.add(new SqlParameterValue(Types.VARCHAR, accessTokenScopes));

            // oidcIdToken
            parameters.add(new SqlParameterValue(Types.VARCHAR, session.getOidcIdTokenValue()));
            parameters.add(new SqlParameterValue(Types.TIMESTAMP, this.localDateTimeToTimestamp(session.getAccessTokenIssuedAt())));
            parameters.add(new SqlParameterValue(Types.TIMESTAMP, this.localDateTimeToTimestamp(session.getAccessTokenExpiresAt())));
            parameters.add(new SqlParameterValue(Types.VARCHAR, writeMap(session.getOidcIdTokenMetadata())));

            // refreshToken
            parameters.add(new SqlParameterValue(Types.VARCHAR, session.getRefreshTokenValue()));
            parameters.add(new SqlParameterValue(Types.TIMESTAMP, this.localDateTimeToTimestamp(session.getRefreshTokenIssuedAt())));
            parameters.add(new SqlParameterValue(Types.TIMESTAMP, this.localDateTimeToTimestamp(session.getRefreshTokenExpiresAt())));
            parameters.add(new SqlParameterValue(Types.VARCHAR, writeMap(session.getRefreshTokenMetadata())));

            LocalDateTime createTime = session.getCreateTime();
            if (session.getCreateTime() == null) {
                createTime = LocalDateTime.now();
            }
            parameters.add(new SqlParameterValue(Types.TIMESTAMP, Timestamp.valueOf(createTime)));

            return parameters;
        }

        private String writeMap(Map<String, Object> data) {
            try {
                if (ObjectUtils.isEmpty(data)) {
                    return null;
                }
                return this.objectMapper.writeValueAsString(data);
            } catch (Exception ex) {
                throw new IllegalArgumentException(ex.getMessage(), ex);
            }
        }

        private Timestamp localDateTimeToTimestamp(LocalDateTime dateTime) {
            return dateTime != null ? Timestamp.valueOf(dateTime) : null;
        }
    }
}
