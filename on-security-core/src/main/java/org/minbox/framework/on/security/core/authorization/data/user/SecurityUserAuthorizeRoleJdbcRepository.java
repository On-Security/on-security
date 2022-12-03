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

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 用户授权角色数据存储库JDBC实现类
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class SecurityUserAuthorizeRoleJdbcRepository implements SecurityUserAuthorizeRoleRepository {
    // @formatter:off
    private static final String COLUMN_NAMES = "user_id, "
            + "role_id, "
            + "authorize_time";
    // @formatter:on
    private static final String TABLE_NAME = "security_user_authorize_roles";
    private static final String USER_ID_FILTER = "user_id = ?";
    private static final String SELECT_USER_AUTHORIZE_ROLE_SQL = "SELECT " + COLUMN_NAMES + " FROM " + TABLE_NAME + " WHERE ";

    private JdbcOperations jdbcOperations;
    private RowMapper<SecurityUserAuthorizeRole> userAuthorizeRoleRowMapper;

    public SecurityUserAuthorizeRoleJdbcRepository(JdbcOperations jdbcOperations) {
        Assert.notNull(jdbcOperations, "jdbcOperations cannot be null");
        this.jdbcOperations = jdbcOperations;
        this.userAuthorizeRoleRowMapper = new SecurityUserAuthorizeRoleRowMapper();
    }

    @Override
    public List<SecurityUserAuthorizeRole> findByUserId(String userId) {
        Assert.hasText(userId, "userId cannot be empty");
        return this.findListBy(USER_ID_FILTER, userId);
    }

    private SecurityUserAuthorizeRole findBy(String filter, Object... args) {
        List<SecurityUserAuthorizeRole> result = this.jdbcOperations.query(
                SELECT_USER_AUTHORIZE_ROLE_SQL + filter, this.userAuthorizeRoleRowMapper, args);
        return !result.isEmpty() ? result.get(0) : null;
    }

    private List<SecurityUserAuthorizeRole> findListBy(String filter, Object... args) {
        List<SecurityUserAuthorizeRole> result = this.jdbcOperations.query(
                SELECT_USER_AUTHORIZE_ROLE_SQL + filter, this.userAuthorizeRoleRowMapper, args);
        return result;
    }

    /**
     * 将{@link ResultSet}映射成{@link SecurityUserAuthorizeRole}对象实例
     */
    public static class SecurityUserAuthorizeRoleRowMapper implements RowMapper<SecurityUserAuthorizeRole> {
        @Override
        public SecurityUserAuthorizeRole mapRow(ResultSet rs, int rowNum) throws SQLException {
            // @formatter:off
            SecurityUserAuthorizeRole.Builder builder =
                    SecurityUserAuthorizeRole
                            .withUserId(rs.getString("user_id"))
                            .roleId(rs.getString("role_id"))
                            .authorizeTime(rs.getTimestamp("authorize_time").toLocalDateTime());
            // @formatter:on
            return builder.build();
        }
    }
}
