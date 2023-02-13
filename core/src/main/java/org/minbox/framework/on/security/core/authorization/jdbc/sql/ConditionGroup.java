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

import java.util.ArrayList;
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

    // @formatter:off
    private ConditionGroup() { }
    // @formatter:on

    public SqlLogicalOperator getOperator() {
        return operator;
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

    public static Builder withCondition(Condition condition) {
        Assert.notNull(condition, "Condition cannot be null.");
        return new Builder(condition, SqlLogicalOperator.AND);
    }

    /**
     * The {@link ConditionGroup} Builder
     */
    public static class Builder {
        private SqlLogicalOperator operator;
        private List<Condition> conditions;

        public Builder(Condition condition, SqlLogicalOperator operator) {
            this.operator = operator;
            this.conditions = new ArrayList();
            this.conditions.add(condition);
        }

        public Builder condition(Condition condition) {
            Assert.notNull(condition, "Condition cannot be null.");
            this.conditions.add(condition);
            return this;
        }

        public Builder operator(SqlLogicalOperator operator) {
            Assert.notNull(operator, "SqlLogicalOperator cannot be null.");
            this.operator = operator;
            return this;
        }

        public ConditionGroup build() {
            ConditionGroup conditionGroup = new ConditionGroup();
            conditionGroup.operator = this.operator;
            conditionGroup.conditions = this.conditions;
            return conditionGroup;
        }
    }
}
