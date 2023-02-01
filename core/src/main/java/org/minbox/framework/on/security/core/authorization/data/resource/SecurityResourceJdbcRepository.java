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

package org.minbox.framework.on.security.core.authorization.data.resource;

import org.minbox.framework.on.security.core.authorization.ResourceType;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 资源数据存储库JDBC实现类
 *
 * @author 恒宇少年
 * @since 0.0.5
 */
public class SecurityResourceJdbcRepository implements SecurityResourceRepository {
    // @formatter:off
    private static final String COLUMN_NAMES = "id, "
            + "region_id, "
            + "application_id, "
            + "name, "
            + "code, "
            + "`type`, "
            + "`describe`, "
            + "deleted, "
            + "create_time";
    // @formatter:on
    private static final String TABLE_NAME = "security_resource";
    private static final String ID_FILTER = "id = ?";
    private static final String SELECT_ALL_COLUMNS_SQL = "SELECT " + COLUMN_NAMES + " FROM " + TABLE_NAME + " WHERE ";

    private JdbcOperations jdbcOperations;
    private RowMapper<SecurityResource> securityResourceRowMapper;

    public SecurityResourceJdbcRepository(JdbcOperations jdbcOperations) {
        Assert.notNull(jdbcOperations, "jdbcOperations cannot be null");
        this.jdbcOperations = jdbcOperations;
        this.securityResourceRowMapper = new SecurityResourceJdbcRepository.SecurityResourceRowMapper();
    }

    @Override
    public SecurityResource findById(String id) {
        Assert.hasText(id, "id cannot be empty");
        return this.findBy(ID_FILTER, id);
    }

    private SecurityResource findBy(String filter, Object... args) {
        List<SecurityResource> result = this.jdbcOperations.query(
                SELECT_ALL_COLUMNS_SQL + filter, this.securityResourceRowMapper, args);
        return !result.isEmpty() ? result.get(0) : null;
    }

    /**
     * 将{@link ResultSet}映射为{@link SecurityResource}对象实例
     */
    public static class SecurityResourceRowMapper implements RowMapper<SecurityResource> {
        @Override
        public SecurityResource mapRow(ResultSet rs, int rowNum) throws SQLException {
            // @formatter:off
            SecurityResource.Builder builder = SecurityResource
                    .withId(rs.getString("id"))
                    .regionId(rs.getString("region_id"))
                    .applicationId(rs.getString("application_id"))
                    .name(rs.getString("name"))
                    .code(rs.getString("code"))
                    .type(new ResourceType(rs.getString("type")))
                    .describe(rs.getString("describe"))
                    .deleted(rs.getBoolean("deleted"))
                    .createTime(rs.getTimestamp("create_time").toLocalDateTime());
            // @formatter:on
            return builder.build();
        }
    }
}
