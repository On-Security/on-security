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

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 安全域数据存储库JDBC实现类
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class SecurityRegionJdbcRepository implements SecurityRegionRepository {
    // @formatter:off
    private static final String COLUMN_NAMES = "id, "
            + "region_id, "
            + "display_name, "
            + "enabled, "
            + "deleted, "
            + "create_time, "
            + "`describe`";
    // @formatter:on
    private static final String TABLE_NAME = "security_region";
    private static final String ID_FILTER = "id = ?";
    private static final String SELECT_REGION_SQL = "SELECT " + COLUMN_NAMES + " FROM " + TABLE_NAME + " WHERE ";

    private JdbcOperations jdbcOperations;
    private RowMapper<SecurityRegion> regionRowMapper;

    public SecurityRegionJdbcRepository(JdbcOperations jdbcOperations) {
        Assert.notNull(jdbcOperations, "jdbcOperations cannot be null");
        this.jdbcOperations = jdbcOperations;
        this.regionRowMapper = new SecurityRegionJdbcRepository.SecurityRegionRowMapper();
    }

    @Override
    public SecurityRegion findById(String id) {
        Assert.hasText(id, "id cannot be empty");
        return this.findBy(ID_FILTER, id);
    }

    private SecurityRegion findBy(String filter, Object... args) {
        List<SecurityRegion> result = this.jdbcOperations.query(
                SELECT_REGION_SQL + filter, this.regionRowMapper, args);
        return !result.isEmpty() ? result.get(0) : null;
    }

    /**
     * 将{@link ResultSet}数据行映射绑定到{@link SecurityRegion}
     */
    public static class SecurityRegionRowMapper implements RowMapper<SecurityRegion> {
        @Override
        public SecurityRegion mapRow(ResultSet rs, int rowNum) throws SQLException {
            // @formatter:off
            SecurityRegion region = SecurityRegion.withId(rs.getString("id"))
                    .regionId(rs.getString("region_id"))
                    .displayName(rs.getString("display_name"))
                    .enabled(rs.getBoolean("enabled"))
                    .deleted(rs.getBoolean("deleted"))
                    .createTime(rs.getTimestamp("create_time").toLocalDateTime())
                    .describe(rs.getString("describe"))
                    .build();
            // @formatter:on
            return region;
        }
    }
}
