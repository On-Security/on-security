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

import org.minbox.framework.on.security.core.authorization.AuthorizeMatchMethod;
import org.springframework.jdbc.core.*;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * 资源授权属性JDBC数据存储库
 *
 * @author 恒宇少年
 * @since 0.0.4
 */
public class SecurityResourceAuthorizeAttributeJdbcRepository implements SecurityResourceAuthorizeAttributeRepository {
    // @formatter:off
    private static final String COLUMN_NAMES = "id, "
            + "region_id, "
            + "resource_id, "
            + "attribute_id, "
            + "match_method, "
            + "authorize_time";
    // @formatter:on
    private static final String TABLE_NAME = "security_resource_authorize_attributes";
    private static final String ID_FILTER = "id = ?";
    private static final String RESOURCE_ID_FILTER = "resource_id = ?";
    private static final String SELECT_ALL_COLUMNS_SQL = "SELECT " + COLUMN_NAMES + " FROM " + TABLE_NAME + " WHERE ";
    // @formatter:off
    private static final String INSERT_SQL = "INSERT INTO " + TABLE_NAME
            + "(" + COLUMN_NAMES + ") VALUES (?, ?, ?, ?, ?, ?)";
    // @formatter:on
    private static final String REMOVE_SQL = "DELETE FROM " + TABLE_NAME + " WHERE ";
    private JdbcOperations jdbcOperations;
    private RowMapper<SecurityResourceAuthorizeAttribute> resourceAuthorizeAttributeRowMapper;
    private Function<SecurityResourceAuthorizeAttribute, List<SqlParameterValue>> resourceAuthorizeAttributeParametersMapper;

    public SecurityResourceAuthorizeAttributeJdbcRepository(JdbcOperations jdbcOperations) {
        Assert.notNull(jdbcOperations, "jdbcOperations cannot be null");
        this.jdbcOperations = jdbcOperations;
        this.resourceAuthorizeAttributeRowMapper = new SecurityResourceAuthorizeAttributeRowMapper();
        this.resourceAuthorizeAttributeParametersMapper = new SecurityResourceAuthorizeAttributeParameterMapper();
    }

    @Override
    public void insert(SecurityResourceAuthorizeAttribute resourceAuthorizeAttribute) {
        List<SqlParameterValue> parameters = this.resourceAuthorizeAttributeParametersMapper.apply(resourceAuthorizeAttribute);
        PreparedStatementSetter pss = new ArgumentPreparedStatementSetter(parameters.toArray());
        this.jdbcOperations.update(INSERT_SQL, pss);
    }

    @Override
    public List<SecurityResourceAuthorizeAttribute> findByResourceId(String resourceId) {
        Assert.hasText(resourceId, "resourceId cannot be empty");
        return this.findListBy(RESOURCE_ID_FILTER, resourceId);
    }

    @Override
    public void removeById(String id) {
        PreparedStatementSetter pss = new ArgumentPreparedStatementSetter(new SqlParameterValue[]{
                new SqlParameterValue(Types.VARCHAR, id)
        });
        this.jdbcOperations.update(REMOVE_SQL + ID_FILTER, pss);
    }

    @Override
    public void removeByResourceId(String resourceId) {
        PreparedStatementSetter pss = new ArgumentPreparedStatementSetter(new SqlParameterValue[]{
                new SqlParameterValue(Types.VARCHAR, resourceId)
        });
        this.jdbcOperations.update(REMOVE_SQL + RESOURCE_ID_FILTER, pss);
    }

    private List<SecurityResourceAuthorizeAttribute> findListBy(String filter, Object... args) {
        return this.jdbcOperations.query(SELECT_ALL_COLUMNS_SQL + filter, this.resourceAuthorizeAttributeRowMapper, args);
    }

    public static class SecurityResourceAuthorizeAttributeRowMapper implements RowMapper<SecurityResourceAuthorizeAttribute> {
        @Override
        public SecurityResourceAuthorizeAttribute mapRow(ResultSet rs, int rowNum) throws SQLException {
            // @formatter:off
            SecurityResourceAuthorizeAttribute.Builder builder = SecurityResourceAuthorizeAttribute
                    .withId(rs.getString("id"))
                    .regionId(rs.getString("region_id"))
                    .resourceId(rs.getString("resource_id"))
                    .attributeId(rs.getString("attribute_id"))
                    .matchMethod(new AuthorizeMatchMethod(rs.getString("match_method")))
                    .authorizeTime(rs.getTimestamp("authorize_time").toLocalDateTime());
            // @formatter:on
            return builder.build();
        }
    }

    public static class SecurityResourceAuthorizeAttributeParameterMapper implements Function<SecurityResourceAuthorizeAttribute, List<SqlParameterValue>> {
        @Override
        public List<SqlParameterValue> apply(SecurityResourceAuthorizeAttribute resourceAuthorizeAttribute) {
            // @formatter:off
            return Arrays.asList(
                    new SqlParameterValue(Types.VARCHAR, resourceAuthorizeAttribute.getId()),
                    new SqlParameterValue(Types.VARCHAR, resourceAuthorizeAttribute.getRegionId()),
                    new SqlParameterValue(Types.VARCHAR, resourceAuthorizeAttribute.getResourceId()),
                    new SqlParameterValue(Types.VARCHAR, resourceAuthorizeAttribute.getAttributeId()),
                    new SqlParameterValue(Types.VARCHAR, resourceAuthorizeAttribute.getMatchMethod().getValue()),
                    new SqlParameterValue(Types.VARCHAR, Timestamp.valueOf(resourceAuthorizeAttribute.getAuthorizeTime()))
            );
            // @formatter:on
        }
    }
}
