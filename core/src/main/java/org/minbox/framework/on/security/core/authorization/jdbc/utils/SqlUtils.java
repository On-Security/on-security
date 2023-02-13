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

package org.minbox.framework.on.security.core.authorization.jdbc.utils;

import org.minbox.framework.on.security.core.authorization.jdbc.sql.Condition;
import org.minbox.framework.on.security.core.authorization.jdbc.sql.ConditionGroup;
import org.minbox.framework.on.security.core.authorization.jdbc.sql.operator.SqlLogicalOperator;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * SQL工具类
 *
 * @author 恒宇少年
 * @since 0.0.8
 */
public class SqlUtils {
    /**
     * 根据查询条件拼接SQL
     *
     * @param operator   拼接每个查询条件SQL的逻辑运算符
     * @param conditions {@link Condition}对象列表
     * @return 拼接后的SQL
     */
    public static String getConditionSql(SqlLogicalOperator operator, Condition... conditions) {
        StringBuffer conditionSql = new StringBuffer();
        conditionSql.append(operator.getValue());
        // @formatter:off
        conditionSql.append(Arrays
                .stream(conditions)
                .map(Condition::getSql)
                .collect(Collectors.joining(operator.getValue())));
        // @formatter:on
        return conditionSql.toString();
    }

    /**
     * 根据查询条件分组拼接SQL
     *
     * @param conditionGroups {@link ConditionGroup} 对象列表
     * @return 拼接后的SQL
     */
    public static String getConditionGroupSql(ConditionGroup... conditionGroups) {
        // @formatter:off
        return Arrays.stream(conditionGroups)
                .map(ConditionGroup::getSql)
                .collect(Collectors.joining());
        // @formatter:on
    }
}
