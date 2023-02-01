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

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 资源路径数据存储库JDBC实现类
 *
 * @author 恒宇少年
 * @since 0.0.5
 */
public class SecurityResourceUriJdbcRepository implements SecurityResourceUriRepository {
    // @formatter:off
    private static final String COLUMN_NAMES = "id, "
            + "resource_id, "
            + "uri, "
            + "create_time";
    // @formatter:on
    private static final String TABLE_NAME = "security_resource_uris";
    private static final String RESOURCE_ID_FILTER = "resource_id = ?";
    private static final String SELECT_ALL_COLUMNS_SQL = "SELECT " + COLUMN_NAMES + " FROM " + TABLE_NAME + " WHERE ";

    private JdbcOperations jdbcOperations;
    private RowMapper<SecurityResourceUri> securityResourceRowMapper;

    public SecurityResourceUriJdbcRepository(JdbcOperations jdbcOperations) {
        Assert.notNull(jdbcOperations, "jdbcOperations cannot be null");
        this.jdbcOperations = jdbcOperations;
        this.securityResourceRowMapper = new SecurityResourceUriRowMapper();
    }

    @Override
    public List<SecurityResourceUri> findByResourceId(String resourceId) {
        Assert.hasText(resourceId, "resourceId cannot be empty");
        return this.findListBy(RESOURCE_ID_FILTER, resourceId);
    }

    private List<SecurityResourceUri> findListBy(String filter, Object... args) {
        return this.jdbcOperations.query(SELECT_ALL_COLUMNS_SQL + filter, this.securityResourceRowMapper, args);
    }

    /**
     * 将{@link ResultSet}映射为{@link SecurityResourceUri}对象实例
     */
    public static class SecurityResourceUriRowMapper implements RowMapper<SecurityResourceUri> {
        @Override
        public SecurityResourceUri mapRow(ResultSet rs, int rowNum) throws SQLException {
            // @formatter:off
            SecurityResourceUri.Builder builder = SecurityResourceUri
                    .withId(rs.getString("id"))
                    .resourceId(rs.getString("resource_id"))
                    .uri(rs.getString("uri"))
                    .createTime(rs.getTimestamp("create_time").toLocalDateTime());
            // @formatter:on
            return builder.build();
        }
    }
}
