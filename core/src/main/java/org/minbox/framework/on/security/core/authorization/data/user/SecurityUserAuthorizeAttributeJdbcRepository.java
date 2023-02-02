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
 * 用户授权属性关系JDBC方式存储库
 *
 * @author 恒宇少年
 * @since 0.0.4
 */
public class SecurityUserAuthorizeAttributeJdbcRepository implements SecurityUserAuthorizeAttributeRepository {
    // @formatter:off
    private static final String COLUMN_NAMES = "user_id, "
            + "attribute_id, "
            + "authorize_time";
    // @formatter:on
    private static final String TABLE_NAME = "security_user_authorize_attributes";
    private static final String USER_ID_FILTER = "user_id = ?";
    private static final String ATTRIBUTE_ID_FILTER = "attribute_id = ?";
    private static final String SELECT_ALL_COLUMNS_SQL = "SELECT " + COLUMN_NAMES + " FROM " + TABLE_NAME + " WHERE ";
    private static final String INSERT_SQL = "INSERT INTO " + TABLE_NAME + "(" + COLUMN_NAMES + ") VALUES (?, ?, ?)";
    private JdbcOperations jdbcOperations;
    private RowMapper<SecurityUserAuthorizeAttribute> userAuthorizeAttributeRowMapper;
    private Function<SecurityUserAuthorizeAttribute, List<SqlParameterValue>> userAuthorizeAttributeParameterMapper;

    public SecurityUserAuthorizeAttributeJdbcRepository(JdbcOperations jdbcOperations) {
        Assert.notNull(jdbcOperations, "jdbcOperations cannot be null");
        this.jdbcOperations = jdbcOperations;
        this.userAuthorizeAttributeRowMapper = new SecurityUserAuthorizeAttributeRowMapper();
        this.userAuthorizeAttributeParameterMapper = new SecurityUserAuthorizeAttributeParameterMapper();
    }

    @Override
    public void insert(SecurityUserAuthorizeAttribute userAuthorizeAttribute) {
        Assert.notNull(userAuthorizeAttribute, "userAuthorizeAttribute cannot be null");
        List<SqlParameterValue> parameters = this.userAuthorizeAttributeParameterMapper.apply(userAuthorizeAttribute);
        PreparedStatementSetter pss = new ArgumentPreparedStatementSetter(parameters.toArray());
        this.jdbcOperations.update(INSERT_SQL, pss);
    }

    @Override
    public List<SecurityUserAuthorizeAttribute> findByUserId(String userId) {
        Assert.hasText(userId, "userId cannot be empty");
        return this.findListBy(USER_ID_FILTER, userId);
    }

    @Override
    public List<SecurityUserAuthorizeAttribute> findByAttributeId(String attributeId) {
        Assert.hasText(attributeId, "attributeId cannot be empty");
        return this.findListBy(ATTRIBUTE_ID_FILTER, attributeId);
    }

    private List<SecurityUserAuthorizeAttribute> findListBy(String filter, Object... args) {
        return this.jdbcOperations.query(SELECT_ALL_COLUMNS_SQL + filter, this.userAuthorizeAttributeRowMapper, args);
    }

    public static class SecurityUserAuthorizeAttributeRowMapper implements RowMapper<SecurityUserAuthorizeAttribute> {
        @Override
        public SecurityUserAuthorizeAttribute mapRow(ResultSet rs, int rowNum) throws SQLException {
            // @formatter:off
            SecurityUserAuthorizeAttribute.Builder builder =
                    SecurityUserAuthorizeAttribute
                        .withUserId(rs.getString("user_id"))
                        .attributeId(rs.getString("attribute_id"))
                        .authorizeTime(rs.getTimestamp("authorize_time").toLocalDateTime());
            // @formatter:on
            return builder.build();
        }
    }

    public static class SecurityUserAuthorizeAttributeParameterMapper implements Function<SecurityUserAuthorizeAttribute, List<SqlParameterValue>> {
        @Override
        public List<SqlParameterValue> apply(SecurityUserAuthorizeAttribute securityUserAuthorizeAttribute) {
            // @formatter:off
            return Arrays.asList(
                    new SqlParameterValue(Types.VARCHAR, securityUserAuthorizeAttribute.getUserId()),
                    new SqlParameterValue(Types.VARCHAR, securityUserAuthorizeAttribute.getAttributeId()),
                    new SqlParameterValue(Types.TIMESTAMP, Timestamp.valueOf(securityUserAuthorizeAttribute.getAuthorizeTime()))
            );
            // @formatter:on
        }
    }
}
