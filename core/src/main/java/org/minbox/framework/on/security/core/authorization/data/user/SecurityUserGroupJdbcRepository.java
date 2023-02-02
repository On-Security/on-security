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
 * 用户绑定角色数据存储库JDBC实现类
 *
 * @author 恒宇少年
 * @since 0.0.5
 */
public class SecurityUserGroupJdbcRepository implements SecurityUserGroupRepository {
    // @formatter:off
    private static final String COLUMN_NAMES = "user_id, "
            + "group_id, "
            + "bind_time";
    // @formatter:on
    private static final String TABLE_NAME = "security_user_groups";
    private static final String USER_ID_FILTER = "user_id = ?";
    private static final String SELECT_USER_GROUP_SQL = "SELECT " + COLUMN_NAMES + " FROM " + TABLE_NAME + " WHERE ";

    private JdbcOperations jdbcOperations;
    private RowMapper<SecurityUserGroup> userAuthorizeRoleRowMapper;

    public SecurityUserGroupJdbcRepository(JdbcOperations jdbcOperations) {
        Assert.notNull(jdbcOperations, "jdbcOperations cannot be null");
        this.jdbcOperations = jdbcOperations;
        this.userAuthorizeRoleRowMapper = new SecurityUserGroupJdbcRepository.SecurityUserGroupRowMapper();
    }

    @Override
    public List<SecurityUserGroup> findByUserId(String userId) {
        Assert.hasText(userId, "userId cannot be empty");
        return this.findListBy(USER_ID_FILTER, userId);
    }

    private SecurityUserGroup findBy(String filter, Object... args) {
        List<SecurityUserGroup> result = this.jdbcOperations.query(
                SELECT_USER_GROUP_SQL + filter, this.userAuthorizeRoleRowMapper, args);
        return !result.isEmpty() ? result.get(0) : null;
    }

    private List<SecurityUserGroup> findListBy(String filter, Object... args) {
        List<SecurityUserGroup> result = this.jdbcOperations.query(
                SELECT_USER_GROUP_SQL + filter, this.userAuthorizeRoleRowMapper, args);
        return result;
    }

    /**
     * 将{@link ResultSet}映射成{@link SecurityUserGroup}对象实例
     */
    public static class SecurityUserGroupRowMapper implements RowMapper<SecurityUserGroup> {
        @Override
        public SecurityUserGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
            // @formatter:off
            SecurityUserGroup.Builder builder =
                    SecurityUserGroup
                            .withUserId(rs.getString("user_id"))
                            .groupId(rs.getString("group_id"))
                            .bindTime(rs.getTimestamp("bind_time").toLocalDateTime());
            // @formatter:on
            return builder.build();
        }
    }
}
