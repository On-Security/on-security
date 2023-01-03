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

import org.minbox.framework.on.security.core.authorization.SignatureAlgorithm;
import org.springframework.jdbc.core.*;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 客户端认证数据存储JDBC方式实现类
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class SecurityApplicationAuthenticationJdbcRepository implements SecurityApplicationAuthenticationRepository {
    // @formatter:off
    private static final String COLUMN_NAMES = "id, "
            + "application_id, "
            + "confidential, "
            + "jwks_url, "
            + "authentication_methods, "
            + "authentication_signing_algorithm, "
            + "authorization_grant_types, "
            + "consent_required, "
            + "id_token_signature_algorithm, "
            + "authorization_code_expiration_time, "
            + "access_token_format, "
            + "access_token_expiration_time, "
            + "refresh_token_expiration_time, "
            + "reuse_refresh_token, "
            + "create_time";
    // @formatter:on
    private static final String TABLE_NAME = "security_application_authentication";
    private static final String ID_FILTER = "id = ?";
    private static final String CLIENT_ID_FILTER = "application_id = ?";
    private static final String SELECT_CLIENT_AUTHENTICATION_SQL = "SELECT " + COLUMN_NAMES + " FROM " + TABLE_NAME + " WHERE ";
    // @formatter:off
    private static final String INSERT_CLIENT_AUTHENTICATION_SQL = "INSERT INTO " + TABLE_NAME
            + "(" + COLUMN_NAMES + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_CLIENT_AUTHENTICATION_SQL = "UPDATE " + TABLE_NAME
            + " SET confidential = ?, jwks_url = ?, authentication_methods = ?, authentication_signing_algorithm = ?, "
            + "authorization_grant_types = ?, consent_required = ?, id_token_signature_algorithm = ?, authorization_code_expiration_time = ?,"
            + "access_token_format = ?, access_token_expiration_time = ?, refresh_token_expiration_time = ?, reuse_refresh_token = ?"
            + " WHERE " + ID_FILTER;
    // @formatter:on

    private JdbcOperations jdbcOperations;
    private RowMapper<SecurityApplicationAuthentication> clientAuthenticationRowMapper;
    private Function<SecurityApplicationAuthentication, List<SqlParameterValue>> clientAuthenticationParametersMapper;

    public SecurityApplicationAuthenticationJdbcRepository(JdbcOperations jdbcOperations) {
        Assert.notNull(jdbcOperations, "jdbcOperations cannot be null");
        this.jdbcOperations = jdbcOperations;
        this.clientAuthenticationRowMapper = new SecurityApplicationAuthenticationJdbcRepository.SecurityClientAuthenticationRowMapper();
        this.clientAuthenticationParametersMapper = new SecurityApplicationAuthenticationJdbcRepository.SecurityClientAuthenticationParametersMapper();
    }

    @Override
    public void save(SecurityApplicationAuthentication clientAuthentication) {
        Assert.notNull(clientAuthentication, "clientAuthentication cannot be null");
        SecurityApplicationAuthentication storedClientAuthentication = this.findBy(ID_FILTER, clientAuthentication.getId());
        if (storedClientAuthentication != null) {
            this.updateClientAuthentication(clientAuthentication);
        } else {
            this.insertClientAuthentication(clientAuthentication);
        }
    }

    @Override
    public SecurityApplicationAuthentication findById(String id) {
        Assert.hasText(id, "id cannot be empty");
        return this.findBy(ID_FILTER, id);
    }

    @Override
    public SecurityApplicationAuthentication findByClientId(String applicationId) {
        Assert.hasText(applicationId, "applicationId cannot be empty");
        return this.findBy(CLIENT_ID_FILTER, applicationId);
    }

    private void updateClientAuthentication(SecurityApplicationAuthentication clientAuthentication) {
        List<SqlParameterValue> parameters = new ArrayList<>(this.clientAuthenticationParametersMapper.apply(clientAuthentication));
        SqlParameterValue id = parameters.remove(0); // remove id
        parameters.remove(0); // remove application_id
        parameters.remove(12); // remove create_time
        parameters.add(id); // add where id
        PreparedStatementSetter pss = new ArgumentPreparedStatementSetter(parameters.toArray());
        this.jdbcOperations.update(UPDATE_CLIENT_AUTHENTICATION_SQL, pss);
    }

    private void insertClientAuthentication(SecurityApplicationAuthentication clientAuthentication) {
        this.assertUniqueIdentifiers(clientAuthentication);
        List<SqlParameterValue> parameters = this.clientAuthenticationParametersMapper.apply(clientAuthentication);
        PreparedStatementSetter pss = new ArgumentPreparedStatementSetter(parameters.toArray());
        this.jdbcOperations.update(INSERT_CLIENT_AUTHENTICATION_SQL, pss);
    }

    private void assertUniqueIdentifiers(SecurityApplicationAuthentication clientAuthentication) {
        SecurityApplicationAuthentication checkObject = findBy("application_id = ?", clientAuthentication.getApplicationId());
        Assert.isNull(checkObject, "Client ID must be unique，duplicate ID：" + clientAuthentication.getApplicationId());
    }

    private SecurityApplicationAuthentication findBy(String filter, Object... args) {
        List<SecurityApplicationAuthentication> result = this.jdbcOperations.query(
                SELECT_CLIENT_AUTHENTICATION_SQL + filter, this.clientAuthenticationRowMapper, args);
        return !result.isEmpty() ? result.get(0) : null;
    }

    /**
     * 将{@link ResultSet}数据行映射绑定到{@link SecurityApplicationAuthentication}
     */
    public static class SecurityClientAuthenticationRowMapper implements RowMapper<SecurityApplicationAuthentication> {
        @Override
        public SecurityApplicationAuthentication mapRow(ResultSet rs, int rowNum) throws SQLException {
            Set<String> clientAuthenticationMethods = StringUtils.commaDelimitedListToSet(rs.getString("authentication_methods"));
            Set<String> authorizationGrantTypes = StringUtils.commaDelimitedListToSet(rs.getString("authorization_grant_types"));
            String authenticationSigningAlgorithm = rs.getString("authentication_signing_algorithm");
            String idTokenSignatureAlgorithm = rs.getString("id_token_signature_algorithm");

            // @formatter:off
            SecurityApplicationAuthentication clientAuthentication = SecurityApplicationAuthentication.withId(rs.getString("id"))
                    .applicationId(rs.getString("application_id"))
                    .confidential(rs.getBoolean("confidential"))
                    .jwksUrl(rs.getString("jwks_url"))
                    .authorizationMethods(
                            clientAuthenticationMethods.stream().map(method -> new ClientAuthenticationMethod(method))
                                    .collect(Collectors.toSet())
                    )
                    .signatureAlgorithm(!ObjectUtils.isEmpty(authenticationSigningAlgorithm) ?
                            new SignatureAlgorithm(authenticationSigningAlgorithm) : null)
                    .grantTypes(
                            authorizationGrantTypes.stream().map(grantType -> new AuthorizationGrantType(grantType))
                                    .collect(Collectors.toSet())
                    )
                    .consentRequired(rs.getBoolean("consent_required"))
                    .idTokenSignatureAlgorithm(!ObjectUtils.isEmpty(idTokenSignatureAlgorithm) ?
                            new SignatureAlgorithm(idTokenSignatureAlgorithm) : null)
                    .authorizationCodeExpirationTime(rs.getInt("authorization_code_expiration_time"))
                    .accessTokenFormat(new OAuth2TokenFormat(rs.getString("access_token_format")))
                    .accessTokenExpirationTime(rs.getInt("access_token_expiration_time"))
                    .refreshTokenExpirationTime(rs.getInt("refresh_token_expiration_time"))
                    .reuseRefreshToken(rs.getBoolean("reuse_refresh_token"))
                    .createTime(rs.getTimestamp("create_time").toLocalDateTime())
                    .build();
            // @formatter:on
            return clientAuthentication;
        }
    }

    /**
     * {@link SecurityApplicationAuthentication}与{@link SqlParameterValue}映射
     */
    public static class SecurityClientAuthenticationParametersMapper implements Function<SecurityApplicationAuthentication, List<SqlParameterValue>> {
        @Override
        public List<SqlParameterValue> apply(SecurityApplicationAuthentication clientAuthentication) {
            // @formatter:off
            Set<String> authenticationMethods = clientAuthentication.getAuthorizationMethods().stream()
                    .map(ClientAuthenticationMethod::getValue).collect(Collectors.toSet());
            Set<String> authenticationGrantTypes = clientAuthentication.getGrantTypes().stream()
                    .map(AuthorizationGrantType::getValue).collect(Collectors.toSet());

            return Arrays.asList(
                    new SqlParameterValue(Types.VARCHAR, clientAuthentication.getId()),
                    new SqlParameterValue(Types.VARCHAR, clientAuthentication.getApplicationId()),
                    new SqlParameterValue(Types.BOOLEAN, clientAuthentication.isConfidential()),
                    new SqlParameterValue(Types.VARCHAR, clientAuthentication.getJwksUrl()),
                    new SqlParameterValue(Types.VARCHAR, StringUtils.collectionToCommaDelimitedString(authenticationMethods)),
                    new SqlParameterValue(Types.VARCHAR, !ObjectUtils.isEmpty(clientAuthentication.getSignatureAlgorithm()) ?
                            clientAuthentication.getSignatureAlgorithm().getValue() : null),
                    new SqlParameterValue(Types.VARCHAR, StringUtils.collectionToCommaDelimitedString(authenticationGrantTypes)),
                    new SqlParameterValue(Types.BOOLEAN, clientAuthentication.isConsentRequired()),
                    new SqlParameterValue(Types.VARCHAR, !ObjectUtils.isEmpty(clientAuthentication.getIdTokenSignatureAlgorithm()) ?
                            clientAuthentication.getIdTokenSignatureAlgorithm().getValue() : null),
                    new SqlParameterValue(Types.INTEGER, clientAuthentication.getAuthorizationCodeExpirationTime()),
                    new SqlParameterValue(Types.VARCHAR, clientAuthentication.getAccessTokenFormat().getValue()),
                    new SqlParameterValue(Types.INTEGER, clientAuthentication.getAccessTokenExpirationTime()),
                    new SqlParameterValue(Types.INTEGER, clientAuthentication.getRefreshTokenExpirationTime()),
                    new SqlParameterValue(Types.BOOLEAN, clientAuthentication.isReuseRefreshToken()),
                    new SqlParameterValue(Types.TIMESTAMP, Timestamp.valueOf(clientAuthentication.getCreateTime()))
            );
            // @formatter:on
        }
    }
}
