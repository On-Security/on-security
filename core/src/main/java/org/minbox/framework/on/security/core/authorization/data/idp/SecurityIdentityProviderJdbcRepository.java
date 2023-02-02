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

package org.minbox.framework.on.security.core.authorization.data.idp;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.oauth2.core.AuthenticationMethod;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 身份提供商数据接口JDBC方式实现类
 *
 * @author 恒宇少年
 * @since 0.0.2
 */
public class SecurityIdentityProviderJdbcRepository implements SecurityIdentityProviderRepository {
    // @formatter:off
    private static final String COLUMN_NAMES = "id, "
            + "region_id, "
            + "display_name, "
            + "protocol_id, "
            + "`describe`, "
            + "issuer_uri, "
            + "authorization_uri, "
            + "token_uri, "
            + "user_info_uri, "
            + "user_info_authentication_method, "
            + "user_name_attribute, "
            + "end_session_uri, "
            + "jwk_set_uri, "
            + "client_authentication_method, "
            + "authorization_grant_type, "
            + "enabled, "
            + "create_time";
    // @formatter:on
    private static final String TABLE_NAME = "security_identity_provider";
    private static final String ID_FILTER = "id = ?";
    private static final String SELECT_IDENTITY_PROVIDER_SQL = "SELECT " + COLUMN_NAMES + " FROM " + TABLE_NAME + " WHERE ";

    private JdbcOperations jdbcOperations;
    private RowMapper<SecurityIdentityProvider> identityProviderRowMapper;

    public SecurityIdentityProviderJdbcRepository(JdbcOperations jdbcOperations) {
        Assert.notNull(jdbcOperations, "jdbcOperations cannot be null");
        this.jdbcOperations = jdbcOperations;
        this.identityProviderRowMapper = new SecurityIdentityProviderRowMapper();
    }

    @Override
    public SecurityIdentityProvider findById(String id) {
        Assert.hasText(id, "id cannot be empty");
        return this.findBy(ID_FILTER, id);
    }

    private SecurityIdentityProvider findBy(String filter, Object... args) {
        List<SecurityIdentityProvider> result = this.jdbcOperations.query(
                SELECT_IDENTITY_PROVIDER_SQL + filter, this.identityProviderRowMapper, args);
        return !result.isEmpty() ? result.get(0) : null;
    }

    public static class SecurityIdentityProviderRowMapper implements RowMapper<SecurityIdentityProvider> {
        @Override
        public SecurityIdentityProvider mapRow(ResultSet rs, int rowNum) throws SQLException {
            SecurityIdentityProvider.Builder builder = SecurityIdentityProvider.withId(rs.getString("id"));
            // @formatter:off
            builder.regionId(rs.getString("region_id"))
                    .displayName(rs.getString("display_name"))
                    .protocolId(rs.getString("protocol_id"))
                    .describe(rs.getString("describe"))
                    .issuerUri(rs.getString("issuer_uri"))
                    .authorizationUri(rs.getString("authorization_uri"))
                    .tokenUri(rs.getString("token_uri"))
                    .userInfoUri(rs.getString("user_info_uri"))
                    .userInfoAuthenticationMethod(new AuthenticationMethod(rs.getString("user_info_authentication_method")))
                    .userNameAttribute(rs.getString("user_name_attribute"))
                    .endSessionUri(rs.getString("end_session_uri"))
                    .jwkSetUri(rs.getString("jwk_set_uri"))
                    .clientAuthenticationMethod(new ClientAuthenticationMethod(rs.getString("client_authentication_method")))
                    .authorizationGrantType(new AuthorizationGrantType(rs.getString("authorization_grant_type")))
                    .enabled(rs.getBoolean("enabled"))
                    .createTime(rs.getTimestamp("create_time").toLocalDateTime());
            // @formatter:on
            return builder.build();
        }
    }
}
