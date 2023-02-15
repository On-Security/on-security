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

import org.minbox.framework.on.security.core.authorization.jdbc.OnSecurityBaseJdbcRepositorySupport;
import org.minbox.framework.on.security.core.authorization.jdbc.definition.OnSecurityColumnName;
import org.minbox.framework.on.security.core.authorization.jdbc.definition.OnSecurityTables;
import org.minbox.framework.on.security.core.authorization.jdbc.mapper.ResultRowMapper;
import org.minbox.framework.on.security.core.authorization.jdbc.sql.Condition;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 安全属性JDBC方式存储库
 *
 * @author 恒宇少年
 * @since 0.0.4
 */
public class SecurityAttributeJdbcRepository extends OnSecurityBaseJdbcRepositorySupport<SecurityAttribute, String>
        implements SecurityAttributeRepository {
    private static final String ID_IN_FILTER = " and id in (:ids) and deleted = false";

    public SecurityAttributeJdbcRepository(JdbcOperations jdbcOperations) {
        super(OnSecurityTables.SecurityAttribute, jdbcOperations);
    }

    @Override
    public void save(SecurityAttribute attribute) {
        Assert.notNull(attribute, "attribute cannot be null");
        SecurityAttribute storedAttribute = this.selectOne(attribute.getId());
        if (storedAttribute != null) {
            this.update(attribute);
        } else {
            this.assertUniqueIdentifiers(attribute);
            this.insert(attribute);
        }
    }

    private void assertUniqueIdentifiers(SecurityAttribute attribute) {
        SecurityAttribute checkObject = this.selectOne(
                Condition.withColumn(OnSecurityColumnName.RegionId, attribute.getRegionId()).build(),
                Condition.withColumn(OnSecurityColumnName.Key, attribute.getKey()).build(),
                Condition.withColumn(OnSecurityColumnName.Value, attribute.getValue()).build()
        );
        Assert.isNull(checkObject, "Attribute must be unique，duplicate Key & Value：" + attribute.getKey() + "," + attribute.getValue());
    }

    @Override
    public List<SecurityAttribute> findByRegionId(String regionId) {
        Assert.hasText(regionId, "regionId cannot be empty");
        return this.select(Condition.withColumn(OnSecurityColumnName.RegionId, regionId).build());
    }

    @Override
    public List<SecurityAttribute> findByIds(List<String> ids) {
        Assert.notEmpty(ids, "attribute ids cannot be empty");
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(this.jdbcOperations);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("ids", ids);
        return namedParameterJdbcTemplate.query(this.table.getQuerySql() + ID_IN_FILTER, parameterSource,
                new ResultRowMapper(OnSecurityTables.SecurityAttribute, SecurityAttribute.class));
    }
}
