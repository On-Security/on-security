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

package org.minbox.framework.on.security.core.authorization.data.attribute;

import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * 安全属性JDBC方式存储库
 *
 * @author 恒宇少年
 * @since 0.0.4
 */
public class SecurityAttributeJdbcRepository implements SecurityAttributeRepository {
    // @formatter:off
    private static final String COLUMN_NAMES = "id, "
            + "region_id, "
            + "`key`, "
            + "`value`, "
            + "create_time, "
            + "mark, "
            + "deleted";
    // @formatter:on
    private static final String TABLE_NAME = "security_attribute";
    private static final String ID_FILTER = "id = ?";
    private static final String ID_IN_FILTER = "id in (:ids) and deleted = false";
    private static final String REGION_ID_FILTER = "region_id = ?";
    private static final String ASSERT_UNIQUE_FILTER = "region_id = ? and `key` = ? and `value` = ?";
    private static final String SELECT_ATTRIBUTE_SQL = "SELECT " + COLUMN_NAMES + " FROM " + TABLE_NAME + " WHERE ";
    // @formatter:off
    private static final String INSERT_ATTRIBUTE_SQL = "INSERT INTO " + TABLE_NAME
            + "(" + COLUMN_NAMES + ") VALUES (?, ?, ?, ?, ?, ?, ?)";
    // @formatter:on
    private static final String UPDATE_ATTRIBUTE_SQL = "UPDATE " + TABLE_NAME +
            " SET key = ?, value = ?, mark = ?, deleted = ?" +
            " WHERE " + ID_FILTER;

    private JdbcOperations jdbcOperations;
    private RowMapper<SecurityAttribute> attributeRowMapper;
    private Function<SecurityAttribute, List<SqlParameterValue>> attributeParametersMapper;

    public SecurityAttributeJdbcRepository(JdbcOperations jdbcOperations) {
        Assert.notNull(jdbcOperations, "jdbcOperations cannot be null");
        this.jdbcOperations = jdbcOperations;
        this.attributeRowMapper = new SecurityAttributeRowMapper();
        this.attributeParametersMapper = new SecurityAttributeParameterMapper();
    }

    @Override
    public void save(SecurityAttribute attribute) {
        Assert.notNull(attribute, "attribute cannot be null");
        SecurityAttribute storedAttribute = this.findBy(ID_FILTER, attribute.getId());
        if (storedAttribute != null) {
            this.updateAttribute(attribute);
        } else {
            this.insertAttribute(attribute);
        }
    }

    private void updateAttribute(SecurityAttribute attribute) {
        List<SqlParameterValue> parameters = new ArrayList<>(this.attributeParametersMapper.apply(attribute));
        SqlParameterValue id = parameters.remove(0); // remove id
        parameters.remove(0); // remove region_id
        parameters.remove(2); // remove create_time
        parameters.add(id); // add where id
        PreparedStatementSetter pss = new ArgumentPreparedStatementSetter(parameters.toArray());
        this.jdbcOperations.update(UPDATE_ATTRIBUTE_SQL, pss);
    }

    private void insertAttribute(SecurityAttribute attribute) {
        this.assertUniqueIdentifiers(attribute);
        List<SqlParameterValue> parameters = this.attributeParametersMapper.apply(attribute);
        PreparedStatementSetter pss = new ArgumentPreparedStatementSetter(parameters.toArray());
        this.jdbcOperations.update(INSERT_ATTRIBUTE_SQL, pss);
    }

    private void assertUniqueIdentifiers(SecurityAttribute attribute) {
        SecurityAttribute checkObject = findBy(ASSERT_UNIQUE_FILTER, attribute.getRegionId(), attribute.getKey(), attribute.getValue());
        Assert.isNull(checkObject, "Attribute must be unique，duplicate Key & Value：" + attribute.getKey() + "," + attribute.getValue());
    }

    @Override
    public SecurityAttribute findById(String attributeId) {
        Assert.hasText(attributeId, "attributeId cannot be empty");
        return this.findBy(ID_FILTER, attributeId);
    }

    @Override
    public List<SecurityAttribute> findByRegionId(String regionId) {
        Assert.hasText(regionId, "regionId cannot be empty");
        return this.findListBy(REGION_ID_FILTER, regionId);
    }

    @Override
    public List<SecurityAttribute> findByIds(List<String> ids) {
        Assert.notEmpty(ids, "attribute ids cannot be empty");
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(this.jdbcOperations);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("ids", ids);
        return namedParameterJdbcTemplate.query(SELECT_ATTRIBUTE_SQL + ID_IN_FILTER, parameterSource, this.attributeRowMapper);
    }

    private SecurityAttribute findBy(String filter, Object... args) {
        List<SecurityAttribute> result = this.jdbcOperations.query(
                SELECT_ATTRIBUTE_SQL + filter, this.attributeRowMapper, args);
        return !result.isEmpty() ? result.get(0) : null;
    }

    private List<SecurityAttribute> findListBy(String filter, Object... args) {
        return this.jdbcOperations.query(
                SELECT_ATTRIBUTE_SQL + filter, this.attributeRowMapper, args);
    }

    public static class SecurityAttributeRowMapper implements RowMapper<SecurityAttribute> {
        @Override
        public SecurityAttribute mapRow(ResultSet rs, int rowNum) throws SQLException {
            // @formatter:off
            SecurityAttribute.Builder builder = SecurityAttribute.withId(rs.getString("id"));
            builder.regionId(rs.getString("region_id"))
                    .key(rs.getString("key"))
                    .value(rs.getString("value"))
                    .createTime(rs.getTimestamp("create_time").toLocalDateTime())
                    .mark(rs.getString("mark"))
                    .deleted(rs.getBoolean("deleted"));
            // @formatter:on
            return builder.build();
        }
    }

    public static class SecurityAttributeParameterMapper implements Function<SecurityAttribute, List<SqlParameterValue>> {
        @Override
        public List<SqlParameterValue> apply(SecurityAttribute attribute) {
            // @formatter:off
            return Arrays.asList(
                    new SqlParameterValue(Types.VARCHAR, attribute.getId()),
                    new SqlParameterValue(Types.VARCHAR, attribute.getRegionId()),
                    new SqlParameterValue(Types.VARCHAR, attribute.getKey()),
                    new SqlParameterValue(Types.VARCHAR, attribute.getValue()),
                    new SqlParameterValue(Types.TIMESTAMP, Timestamp.valueOf(attribute.getCreateTime())),
                    new SqlParameterValue(Types.VARCHAR, attribute.getMark()),
                    new SqlParameterValue(Types.BOOLEAN, attribute.isDeleted())
            );
            // @formatter:on
        }
    }
}
