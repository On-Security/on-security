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

import org.minbox.framework.on.security.core.authorization.jdbc.sql.operator.SqlLogicalOperator;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据过滤条件分组
 * 将多个{@link Condition}通过{@link SqlLogicalOperator}进行关联在一起
 *
 * @author 恒宇少年
 * @see SqlLogicalOperator
 * @see Condition
 * @since 0.0.8
 */
public final class ConditionGroup {
    private SqlLogicalOperator operator;
    private List<Condition> conditions;

    private ConditionGroup(SqlLogicalOperator operator, List<Condition> conditions) {
        this.operator = operator;
        this.conditions = conditions;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public String getSql() {
        StringBuffer sql = new StringBuffer();
        sql.append(this.operator.getValue());
        sql.append("(");
        sql.append(this.conditions.stream().map(Condition::getSql)
                .collect(Collectors.joining(SqlLogicalOperator.AND.getValue())));
        sql.append(")");
        return sql.toString();
    }

    public static ConditionGroup withCondition(Condition... conditions) {
        Assert.notEmpty(conditions, "conditions cannot be empty.");
        return withCondition(SqlLogicalOperator.AND, conditions);
    }

    public static ConditionGroup withCondition(SqlLogicalOperator operator, Condition... conditions) {
        return new ConditionGroup(operator, Arrays.asList(conditions));
    }
}
