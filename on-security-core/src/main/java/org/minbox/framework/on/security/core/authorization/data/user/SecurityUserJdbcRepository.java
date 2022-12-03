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

import org.minbox.framework.on.security.core.authorization.UserGender;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 用户数据存储库JDBC实现类
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class SecurityUserJdbcRepository implements SecurityUserRepository {
    // @formatter:off
    private static final String COLUMN_NAMES = "id, "
            + "region_id, "
            + "business_id, "
            + "username, "
            + "password, "
            + "email, "
            + "name, "
            + "phone, "
            + "nickname, "
            + "birthday, "
            + "gender, "
            + "zip_code, "
            + "enabled, "
            + "deleted, "
            + "`describe`, "
            + "create_time";
    // @formatter:on
    private static final String TABLE_NAME = "security_user";
    private static final String ID_FILTER = "id = ?";
    private static final String USER_NAME_FILTER = "username = ?";
    private static final String SELECT_USER_SQL = "SELECT " + COLUMN_NAMES + " FROM " + TABLE_NAME + " WHERE ";

    private JdbcOperations jdbcOperations;
    private RowMapper<SecurityUser> userRowMapper;

    public SecurityUserJdbcRepository(JdbcOperations jdbcOperations) {
        Assert.notNull(jdbcOperations, "jdbcOperations cannot be null");
        this.jdbcOperations = jdbcOperations;
        this.userRowMapper = new SecurityUserRowMapper();
    }

    @Override
    public SecurityUser findById(String id) {
        Assert.hasText(id, "id cannot be empty");
        return this.findBy(ID_FILTER, id);
    }

    @Override
    public SecurityUser findByUsername(String username) {
        Assert.hasText(username, "username cannot be empty");
        return this.findBy(USER_NAME_FILTER, username);
    }

    private SecurityUser findBy(String filter, Object... args) {
        List<SecurityUser> result = this.jdbcOperations.query(
                SELECT_USER_SQL + filter, this.userRowMapper, args);
        return !result.isEmpty() ? result.get(0) : null;
    }

    /**
     * 将{@link ResultSet}数据行映射绑定到{@link SecurityUser}
     */
    public static class SecurityUserRowMapper implements RowMapper<SecurityUser> {
        @Override
        public SecurityUser mapRow(ResultSet rs, int rowNum) throws SQLException {
            String gender = rs.getString("gender");
            // @formatter:off
            SecurityUser client = SecurityUser.withId(rs.getString("id"))
                    .regionId(rs.getString("region_id"))
                    .businessId(rs.getString("business_id"))
                    .username(rs.getString("username"))
                    .password(rs.getString("password"))
                    .email(rs.getString("email"))
                    .phone(rs.getString("phone"))
                    .name(rs.getString("name"))
                    .nickname(rs.getString("nickname"))
                    .birthday(rs.getDate("birthday").toLocalDate())
                    .gender(!ObjectUtils.isEmpty(gender) ? new UserGender(gender) : null)
                    .zipCode(rs.getString("zip_code"))
                    .enabled(rs.getBoolean("enabled"))
                    .deleted(rs.getBoolean("deleted"))
                    .describe(rs.getString("describe"))
                    .createTime(rs.getTimestamp("create_time").toLocalDateTime())
                    .build();
            // @formatter:on
            return client;
        }
    }
}
