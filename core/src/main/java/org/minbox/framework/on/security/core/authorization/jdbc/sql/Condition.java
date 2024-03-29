/*
 *   Copyright (C) 2022  恒宇少年
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.minbox.framework.on.security.core.authorization.jdbc.sql;

import org.minbox.framework.on.security.core.authorization.jdbc.definition.OnSecurityColumnName;
import org.minbox.framework.on.security.core.authorization.jdbc.sql.operator.SqlComparisonOperator;
import org.springframework.util.Assert;

/**
 * 数据过滤条件
 *
 * @author 恒宇少年
 * @since 0.0.8
 */
public final class Condition {
    private static final String CONDITION_SQL_FORMAT = "%s %s ?";
    private SqlComparisonOperator operator;
    private ColumnValue columnValue;

    private Condition(SqlComparisonOperator operator, ColumnValue columnValue) {
        this.operator = operator;
        this.columnValue = columnValue;
    }

    public OnSecurityColumnName getColumnName() {
        return columnValue.getColumnName();
    }

    public Object getValue() {
        return columnValue.getValue();
    }

    public String getSql() {
        return String.format(CONDITION_SQL_FORMAT, this.getColumnName().getName(), this.operator.getValue());
    }

    public static Condition withColumn(OnSecurityColumnName columnName, Object value) {
        return withColumn(SqlComparisonOperator.EqualTo, columnName, value);
    }

    public static Condition withColumn(SqlComparisonOperator operator, OnSecurityColumnName columnName, Object value) {
        Assert.notNull(operator, "The operator cannot be null");
        Assert.notNull(columnName, "The column cannot be null");
        Assert.notNull(value, "value cannot be null");
        return new Condition(operator, ColumnValue.with(columnName, value));
    }
}
