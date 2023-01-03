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

package org.minbox.framework.on.security.core.authorization.data.role;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 角色数据存储库JDBC实现类
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class SecurityRoleJdbcRepository implements SecurityRoleRepository {
    // @formatter:off
    private static final String COLUMN_NAMES = "id, "
            + "region_id, "
            + "application_id, "
            + "name, "
            + "code, "
            + "`describe`, "
            + "deleted, "
            + "create_time";
    // @formatter:on
    private static final String TABLE_NAME = "security_role";
    private static final String ID_FILTER = "id = ?";
    private static final String SELECT_ROLE_SQL = "SELECT " + COLUMN_NAMES + " FROM " + TABLE_NAME + " WHERE ";

    private JdbcOperations jdbcOperations;
    private RowMapper<SecurityRole> securityRoleRowMapper;

    public SecurityRoleJdbcRepository(JdbcOperations jdbcOperations) {
        Assert.notNull(jdbcOperations, "jdbcOperations cannot be null");
        this.jdbcOperations = jdbcOperations;
        this.securityRoleRowMapper = new SecurityRoleRowMapper();
    }

    @Override
    public SecurityRole findById(String id) {
        Assert.hasText(id, "id cannot be empty");
        return this.findBy(ID_FILTER, id);
    }

    private SecurityRole findBy(String filter, Object... args) {
        List<SecurityRole> result = this.jdbcOperations.query(
                SELECT_ROLE_SQL + filter, this.securityRoleRowMapper, args);
        return !result.isEmpty() ? result.get(0) : null;
    }

    /**
     * 将{@link ResultSet}映射为{@link SecurityRole}对象实例
     */
    public static class SecurityRoleRowMapper implements RowMapper<SecurityRole> {
        @Override
        public SecurityRole mapRow(ResultSet rs, int rowNum) throws SQLException {
            // @formatter:off
            SecurityRole.Builder builder = SecurityRole
                    .withId(rs.getString("id"))
                    .regionId(rs.getString("region_id"))
                    .applicationId(rs.getString("application_id"))
                    .name(rs.getString("name"))
                    .code(rs.getString("code"))
                    .describe(rs.getString("describe"))
                    .deleted(rs.getBoolean("deleted"))
                    .createTime(rs.getTimestamp("create_time").toLocalDateTime());
            // @formatter:on
            return builder.build();
        }
    }
}
