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
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 身份提供商授权范围数据JDBC实现类
 *
 * @author 恒宇少年
 * @since 0.0.2
 */
public class SecurityIdentityProviderScopeJdbcRepository implements SecurityIdentityProviderScopeRepository {
    // @formatter:off
    private static final String COLUMN_NAMES = "id, "
            + "idp_id, "
            + "pid, "
            + "name, "
            + "`describe`, "
            + "enabled, "
            + "required_authorization";
    // @formatter:on
    private static final String TABLE_NAME = "security_identity_provider_scopes";
    private static final String ID_FILTER = "id = ?";
    private static final String IDP_ID_FILTER = "idp_id = ?";
    private static final String SELECT_IDENTITY_PROVIDER_SCOPE_SQL = "SELECT " + COLUMN_NAMES + " FROM " + TABLE_NAME + " WHERE ";

    private JdbcOperations jdbcOperations;
    private RowMapper<SecurityIdentityProviderScope> identityProviderScopeRowMapper;

    public SecurityIdentityProviderScopeJdbcRepository(JdbcOperations jdbcOperations) {
        Assert.notNull(jdbcOperations, "jdbcOperations cannot be null");
        this.jdbcOperations = jdbcOperations;
        this.identityProviderScopeRowMapper = new SecurityIdentityProviderScopeRowMapper();
    }

    @Override
    public SecurityIdentityProviderScope findById(String id) {
        Assert.hasText(id, "id cannot be empty");
        return this.findBy(ID_FILTER, id);
    }

    @Override
    public List<SecurityIdentityProviderScope> findByIdpId(String idpId) {
        Assert.hasText(idpId, "idpId cannot be empty");
        return this.findListBy(IDP_ID_FILTER, idpId);
    }

    private SecurityIdentityProviderScope findBy(String filter, Object... args) {
        List<SecurityIdentityProviderScope> result = this.jdbcOperations.query(
                SELECT_IDENTITY_PROVIDER_SCOPE_SQL + filter, this.identityProviderScopeRowMapper, args);
        return !result.isEmpty() ? result.get(0) : null;
    }

    private List<SecurityIdentityProviderScope> findListBy(String filter, Object... args) {
        return this.jdbcOperations.query(
                SELECT_IDENTITY_PROVIDER_SCOPE_SQL + filter, this.identityProviderScopeRowMapper, args);
    }

    public static class SecurityIdentityProviderScopeRowMapper implements RowMapper<SecurityIdentityProviderScope> {
        @Override
        public SecurityIdentityProviderScope mapRow(ResultSet rs, int rowNum) throws SQLException {
            SecurityIdentityProviderScope.Builder builder = SecurityIdentityProviderScope.withId(rs.getString("id"));
            builder.idpId(rs.getString("idp_id"))
                    .pid(rs.getString("pid"))
                    .name(rs.getString("name"))
                    .describe(rs.getString("describe"))
                    .enabled(rs.getBoolean("enabled"))
                    .requiredAuthorization(rs.getBoolean("required_authorization"));
            return builder.build();
        }
    }
}
