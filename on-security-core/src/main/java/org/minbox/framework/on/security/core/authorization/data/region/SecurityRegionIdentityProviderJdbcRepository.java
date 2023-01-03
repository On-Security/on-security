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

package org.minbox.framework.on.security.core.authorization.data.region;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 安全域下身份供应商配置参数数据JDBC方式实现类
 *
 * @author 恒宇少年
 * @since 0.0.2
 */
public class SecurityRegionIdentityProviderJdbcRepository implements SecurityRegionIdentityProviderRepository {
    // @formatter:off
    private static final String COLUMN_NAMES = "id, "
            + "region_id, "
            + "idp_id, "
            + "unique_identifier, "
            + "registration_id, "
            + "application_id, "
            + "application_secret, "
            + "callback_url, "
            + "authorization_scopes, "
            + "expand_metadata, "
            + "`describe`, "
            + "create_time";
    // @formatter:on
    private static final String TABLE_NAME = "security_region_identity_provider";
    private static final String REGISTRATION_ID_FILTER = "registration_id = ?";
    private static final String SELECT_REGION_IDENTITY_PROVIDER_SQL = "SELECT " + COLUMN_NAMES + " FROM " + TABLE_NAME + " WHERE ";

    private JdbcOperations jdbcOperations;
    private RowMapper<SecurityRegionIdentityProvider> regionIdentityProviderRowMapper;

    public SecurityRegionIdentityProviderJdbcRepository(JdbcOperations jdbcOperations) {
        Assert.notNull(jdbcOperations, "jdbcOperations cannot be null");
        this.jdbcOperations = jdbcOperations;
        this.regionIdentityProviderRowMapper = new SecurityRegionIdentityProviderRowMapper();
    }

    @Override
    public SecurityRegionIdentityProvider findByRegistrationId(String registrationId) {
        Assert.hasText(registrationId, "registrationId cannot be empty");
        return this.findBy(REGISTRATION_ID_FILTER, registrationId);
    }

    private SecurityRegionIdentityProvider findBy(String filter, Object... args) {
        List<SecurityRegionIdentityProvider> result = this.jdbcOperations.query(
                SELECT_REGION_IDENTITY_PROVIDER_SQL + filter, this.regionIdentityProviderRowMapper, args);
        return !result.isEmpty() ? result.get(0) : null;
    }

    public static class SecurityRegionIdentityProviderRowMapper implements RowMapper<SecurityRegionIdentityProvider> {
        private ObjectMapper objectMapper = new ObjectMapper();

        public SecurityRegionIdentityProviderRowMapper() {
            ClassLoader classLoader = SecurityRegionIdentityProviderJdbcRepository.class.getClassLoader();
            List<Module> securityModules = SecurityJackson2Modules.getModules(classLoader);
            this.objectMapper.registerModules(securityModules);
        }

        @Override
        public SecurityRegionIdentityProvider mapRow(ResultSet rs, int rowNum) throws SQLException {
            // @formatter:off
            SecurityRegionIdentityProvider.Builder builder = SecurityRegionIdentityProvider.withId(rs.getString("id"));
            builder.regionId(rs.getString("region_id"))
                    .idpId(rs.getString("idp_id"))
                    .uniqueIdentifier(rs.getString("unique_identifier"))
                    .registrationId(rs.getString("registration_id"))
                    .applicationId(rs.getString("application_id"))
                    .clientSecret(rs.getString("application_secret"))
                    .callbackUrl(rs.getString("callback_url"))
                    .describe(rs.getString("describe"))
                    .createTime(rs.getTimestamp("create_time").toLocalDateTime());
            // authorization_scopes
            String authorizedScopesString = rs.getString("authorization_scopes");
            Set<String> authorizedScopes = Collections.emptySet();
            if (authorizedScopesString != null) {
                authorizedScopes = StringUtils.commaDelimitedListToSet(authorizedScopesString);
            }
            builder.authorizationScopes(authorizedScopes);
            // expandMetadata
            String metadata = rs.getString("expand_metadata");
            if (!ObjectUtils.isEmpty(metadata)) {
                builder.expandMetadata(parseMap(metadata));
            }
            // @formatter:on
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
    }
}
