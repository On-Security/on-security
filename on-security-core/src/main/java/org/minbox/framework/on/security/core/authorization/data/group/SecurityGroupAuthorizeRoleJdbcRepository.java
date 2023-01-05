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

package org.minbox.framework.on.security.core.authorization.data.group;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 安全组授权角色数据存储库JDBC实现类
 *
 * @author 恒宇少年
 * @since 0.0.5
 */
public class SecurityGroupAuthorizeRoleJdbcRepository implements SecurityGroupAuthorizeRoleRepository {
    // @formatter:off
    private static final String COLUMN_NAMES = "group_id, "
            + "role_id, "
            + "authorize_time";
    // @formatter:on
    private static final String TABLE_NAME = "security_group_authorize_roles";
    private static final String GROUP_ID_FILTER = "group_id = ?";
    private static final String SELECT_GROUP_AUTHORIZE_ROLE_SQL = "SELECT " + COLUMN_NAMES + " FROM " + TABLE_NAME + " WHERE ";

    private JdbcOperations jdbcOperations;
    private RowMapper<SecurityGroupAuthorizeRole> userAuthorizeRoleRowMapper;

    public SecurityGroupAuthorizeRoleJdbcRepository(JdbcOperations jdbcOperations) {
        Assert.notNull(jdbcOperations, "jdbcOperations cannot be null");
        this.jdbcOperations = jdbcOperations;
        this.userAuthorizeRoleRowMapper = new SecurityGroupAuthorizeRoleJdbcRepository.SecurityGroupAuthorizeRoleRowMapper();
    }

    @Override
    public List<SecurityGroupAuthorizeRole> findByGroupId(String groupId) {
        Assert.hasText(groupId, "groupId cannot be empty");
        return this.findListBy(GROUP_ID_FILTER, groupId);
    }

    private SecurityGroupAuthorizeRole findBy(String filter, Object... args) {
        List<SecurityGroupAuthorizeRole> result = this.jdbcOperations.query(
                SELECT_GROUP_AUTHORIZE_ROLE_SQL + filter, this.userAuthorizeRoleRowMapper, args);
        return !result.isEmpty() ? result.get(0) : null;
    }

    private List<SecurityGroupAuthorizeRole> findListBy(String filter, Object... args) {
        List<SecurityGroupAuthorizeRole> result = this.jdbcOperations.query(
                SELECT_GROUP_AUTHORIZE_ROLE_SQL + filter, this.userAuthorizeRoleRowMapper, args);
        return result;
    }

    /**
     * 将{@link ResultSet}映射成{@link SecurityGroupAuthorizeRole}对象实例
     */
    public static class SecurityGroupAuthorizeRoleRowMapper implements RowMapper<SecurityGroupAuthorizeRole> {
        @Override
        public SecurityGroupAuthorizeRole mapRow(ResultSet rs, int rowNum) throws SQLException {
            // @formatter:off
            SecurityGroupAuthorizeRole.Builder builder =
                    SecurityGroupAuthorizeRole
                            .withGroupId(rs.getString("group_id"))
                            .roleId(rs.getString("role_id"))
                            .authorizeTime(rs.getTimestamp("authorize_time").toLocalDateTime());
            // @formatter:on
            return builder.build();
        }
    }
}
