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

import org.minbox.framework.on.security.core.authorization.jdbc.definition.Table;
import org.minbox.framework.on.security.core.authorization.jdbc.definition.TableColumn;
import org.minbox.framework.on.security.core.authorization.jdbc.sql.ColumnValue;
import org.minbox.framework.on.security.core.authorization.jdbc.sql.Condition;
import org.minbox.framework.on.security.core.authorization.jdbc.sql.ConditionGroup;
import org.springframework.jdbc.core.SqlParameterValue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * {@link SqlParameterValue}操作工具类
 *
 * @author 恒宇少年
 * @since 0.0.8
 */
public class SqlParameterValueUtils {
    /**
     * 根据查询条件{@link Condition}转换{@link SqlParameterValue}
     *
     * @param table      获取查询条件的表{@link Table}
     * @param conditions {@link Condition} 对象列表
     * @return {@link SqlParameterValue} 对象列表
     */
    public static SqlParameterValue[] getWithCondition(Table table, Condition... conditions) {
        // @formatter:off
        return Arrays.stream(conditions)
                .filter(condition -> table.containsColumn(condition.getColumnName()))
                .map(condition -> {
                    TableColumn tableColumn = table.getColumn(condition.getColumnName());
                    Object convertedValue = tableColumn.toColumnValue(condition.getValue());
                    return new SqlParameterValue(tableColumn.getSqlType(), convertedValue);
                })
                .toArray(SqlParameterValue[]::new);
        // @formatter:on
    }

    /**
     * 根据查询条件分组{@link ConditionGroup}转换{@link SqlParameterValue}
     *
     * @param table           获取查询条件的表{@link Table}
     * @param conditionGroups {@link ConditionGroup} 对象列表
     * @return {@link SqlParameterValue} 对象列表
     */
    public static SqlParameterValue[] getWithConditionGroup(Table table, ConditionGroup... conditionGroups) {
        // @formatter:off
        Condition[] conditions = Arrays.stream(conditionGroups)
                .flatMap(conditionGroup -> conditionGroup.getConditions().stream())
                .toArray(Condition[]::new);
        // @formatter:on
        return getWithCondition(table, conditions);
    }

    /**
     * 根据列值关系实体{@link ColumnValue}转换{@link SqlParameterValue}
     *
     * @param table        获取查询条件的表{@link Table}
     * @param columnValues {@link ColumnValue} 对象列表
     * @return {@link SqlParameterValue} 对象列表
     */
    public static SqlParameterValue[] getWithColumnValue(Table table, ColumnValue... columnValues) {
        // @formatter:off
        return Arrays.stream(columnValues)
                .filter(columnValue -> table.containsColumn(columnValue.getColumnName()))
                .map(columnValue -> {
                    TableColumn tableColumn = table.getColumn(columnValue.getColumnName());
                    Object convertedValue = tableColumn.toColumnValue(columnValue.getValue());
                    return new SqlParameterValue(tableColumn.getSqlType(), convertedValue);
                })
                .toArray(SqlParameterValue[]::new);
        // @formatter:on
    }

    /**
     * 根据表列定义{@link TableColumn}转换{@link SqlParameterValue}
     *
     * @param tableColumns       {@link TableColumn} 对象列表
     * @param getMethodResultMap 列对应字段{@link java.lang.reflect.Field}的对象Get方法值集合
     * @return {@link SqlParameterValue} 对象列表
     */
    public static SqlParameterValue[] getWithTableColumn(List<TableColumn> tableColumns, Map<String, Object> getMethodResultMap) {
        // @formatter:off
        return tableColumns.stream()
                .map(tableColumn -> {
                    String getMethodName = ObjectClassUtils.getGetMethodName(tableColumn.getColumnName().getUpperCamelName());
                    Object getMethodResult = getMethodResultMap.get(getMethodName);
                    Object convertedValue = tableColumn.toColumnValue(getMethodResult);
                    return new SqlParameterValue(tableColumn.getSqlType(), convertedValue);
                })
                .toArray(SqlParameterValue[]::new);
        // @formatter:on
    }

    /**
     * 根据列值{@link ColumnValue}转换{@link SqlParameterValue}
     *
     * @param columnValues {@link ColumnValue} 对象列表
     * @return {@link SqlParameterValue} 对象列表
     */
    public static SqlParameterValue[] getWithColumnValue(Table table, List<ColumnValue> columnValues) {
        // @formatter:off
        return columnValues.stream()
                .filter(columnValue -> table.containsColumn(columnValue.getColumnName()))
                .map(columnValue -> {
                    TableColumn tableColumn = table.getColumn(columnValue.getColumnName());
                    Object convertedValue = tableColumn.toColumnValue(columnValue.getValue());
                    return new SqlParameterValue(tableColumn.getSqlType(), convertedValue);
                })
                .toArray(SqlParameterValue[]::new);
        // @formatter:on
    }
}
