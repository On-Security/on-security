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

package org.minbox.framework.on.security.core.authorization.jdbc;

import com.nimbusds.jose.util.ArrayUtils;
import org.minbox.framework.on.security.core.authorization.jdbc.definition.OnSecurityColumnName;
import org.minbox.framework.on.security.core.authorization.jdbc.definition.Table;
import org.minbox.framework.on.security.core.authorization.jdbc.definition.TableColumn;
import org.minbox.framework.on.security.core.authorization.jdbc.mapper.ResultRowMapper;
import org.minbox.framework.on.security.core.authorization.jdbc.sql.ColumnValue;
import org.minbox.framework.on.security.core.authorization.jdbc.sql.Condition;
import org.minbox.framework.on.security.core.authorization.jdbc.sql.ConditionGroup;
import org.minbox.framework.on.security.core.authorization.jdbc.sql.operator.SqlLogicalOperator;
import org.minbox.framework.on.security.core.authorization.jdbc.utils.ObjectClassUtils;
import org.minbox.framework.on.security.core.authorization.jdbc.utils.SqlParameterValueUtils;
import org.minbox.framework.on.security.core.authorization.jdbc.utils.SqlUtils;
import org.springframework.core.ResolvableType;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * JDBC数据操作基类
 *
 * @author 恒宇少年
 * @see JdbcOperations
 * @see Table
 * @see Condition
 * @see ConditionGroup
 * @see TableColumn
 * @see ColumnValue
 * @since 0.0.8
 */
public class OnSecurityBaseJdbcRepositorySupport<T extends Serializable, PK> implements OnSecurityBaseJdbcRepository<T, PK> {
    private static final int MAP_ENTITY_GENERIC_INDEX = 0;
    protected Table table;
    protected JdbcOperations jdbcOperations;
    private Class mapEntityClass;

    public OnSecurityBaseJdbcRepositorySupport(Table table, JdbcOperations jdbcOperations) {
        this.table = table;
        this.jdbcOperations = jdbcOperations;
        ResolvableType resolvableType = ResolvableType.forClass(this.getClass()).getSuperType();
        this.mapEntityClass = resolvableType.getGeneric(MAP_ENTITY_GENERIC_INDEX).resolve();
    }

    @Override
    public int insert(T object) {
        Assert.notNull(object, "目标新增对象不可以为null.");
        List<TableColumn> insertableTableColumns = this.table.getInsertableTableColumns();
        Map<String, Object> methodValueMap = ObjectClassUtils.invokeObjectGetMethod(object);

        // Execute Insert
        SqlParameterValue[] parameterValues = SqlParameterValueUtils.getWithTableColumn(insertableTableColumns, methodValueMap);
        PreparedStatementSetter pass = new ArgumentPreparedStatementSetter(parameterValues);
        return this.jdbcOperations.update(this.table.getInsertSql(), pass);
    }

    @Override
    public int delete(PK pk) {
        Assert.notNull(pk, "主键值不可以为null.");
        OnSecurityColumnName pkColumn = this.table.getPk().getColumnName();
        Condition idFilterCondition = Condition.withColumn(pkColumn, pk).build();
        // Build Delete SQL
        StringBuffer sql = new StringBuffer();
        sql.append(this.table.getDeleteSql());
        sql.append(SqlUtils.getConditionSql(SqlLogicalOperator.AND, idFilterCondition));

        // Execute Delete
        SqlParameterValue[] parameterValues = SqlParameterValueUtils.getWithCondition(this.table, idFilterCondition);
        PreparedStatementSetter pass = new ArgumentPreparedStatementSetter(parameterValues);
        return this.jdbcOperations.update(sql.toString(), pass);
    }

    @Override
    public int delete(Condition... conditions) {
        Assert.notEmpty(conditions, "请至少传递一个Condition.");
        // Build Delete SQL
        String conditionSql = SqlUtils.getConditionSql(SqlLogicalOperator.AND, conditions);
        StringBuffer sql = new StringBuffer();
        sql.append(this.table.getDeleteSql());
        sql.append(conditionSql);

        // Execute Delete
        SqlParameterValue[] parameterValues = SqlParameterValueUtils.getWithCondition(this.table, conditions);
        PreparedStatementSetter pass = new ArgumentPreparedStatementSetter(parameterValues);
        return this.jdbcOperations.update(sql.toString(), pass);
    }

    @Override
    public int delete(ConditionGroup... conditionGroups) {
        Assert.notEmpty(conditionGroups, "请至少传递一个ConditionGroup.");
        // Build Delete SQL
        String conditionSql = SqlUtils.getConditionGroupSql(conditionGroups);
        StringBuffer sql = new StringBuffer();
        sql.append(this.table.getDeleteSql());
        sql.append(conditionSql);

        // Execute Delete
        SqlParameterValue[] parameterValues = SqlParameterValueUtils.getWithConditionGroup(this.table, conditionGroups);
        PreparedStatementSetter pass = new ArgumentPreparedStatementSetter(parameterValues);
        return this.jdbcOperations.update(sql.toString(), pass);
    }

    @Override
    public int delete(String filterSql, ColumnValue... filterColumnValues) {
        Assert.notEmpty(filterColumnValues, "请至少传递一个FilterColumnValue.");
        // Build Delete SQL
        StringBuffer sql = new StringBuffer();
        sql.append(this.table.getDeleteSql());
        sql.append(filterSql);

        // Execute Delete
        SqlParameterValue[] parameterValues = SqlParameterValueUtils.getWithColumnValue(this.table, filterColumnValues);
        PreparedStatementSetter pass = new ArgumentPreparedStatementSetter(parameterValues);
        return this.jdbcOperations.update(sql.toString(), pass);
    }

    @Override
    public int update(T object) {
        Assert.notNull(object, "目标更新对象不可以为null.");
        OnSecurityColumnName pkColumn = this.table.getPk().getColumnName();
        Map<String, Object> methodValueMap = ObjectClassUtils.invokeObjectGetMethod(object);
        Object pkValue = methodValueMap.get(ObjectClassUtils.getGetMethodName(pkColumn.getUpperCamelName()));
        Condition pkFilterCondition = Condition.withColumn(pkColumn, pkValue).build();

        // Build Update SQL
        StringBuffer sql = new StringBuffer();
        sql.append(this.table.getUpdateSql());
        sql.append(SqlUtils.getConditionSql(SqlLogicalOperator.AND, pkFilterCondition));

        // Put PK TableColumn
        List<TableColumn> updatableTableColumnList = this.table.getUpdatableTableColumns();
        updatableTableColumnList.add(this.table.getPk());

        // Execute Update
        SqlParameterValue[] parameterValues = SqlParameterValueUtils.getWithTableColumn(updatableTableColumnList, methodValueMap);
        PreparedStatementSetter pass = new ArgumentPreparedStatementSetter(parameterValues);
        return this.jdbcOperations.update(sql.toString(), pass);
    }

    @Override
    public int update(List<ColumnValue> setColumnValueList, Condition... conditions) {
        Assert.notEmpty(setColumnValueList, "请至少传递一个列值.");
        Assert.notEmpty(conditions, "请至少传递一个Condition.");
        // @formatter:off
        List<OnSecurityColumnName> setColumnList = setColumnValueList.stream()
                .map(ColumnValue::getColumnName)
                .collect(Collectors.toList());
        // @formatter:on

        // Build Update SQL
        StringBuffer updateSql = new StringBuffer();
        updateSql.append(this.table.getUpdateSql(setColumnList));
        updateSql.append(SqlUtils.getConditionSql(SqlLogicalOperator.AND, conditions));

        // Execute Update
        // @formatter:off
        SqlParameterValue[] parameterValues =
                ArrayUtils.concat(SqlParameterValueUtils.getWithColumnValue(this.table, setColumnValueList),
                        SqlParameterValueUtils.getWithCondition(this.table, conditions));
        // @formatter:on
        PreparedStatementSetter pass = new ArgumentPreparedStatementSetter(parameterValues);
        return this.jdbcOperations.update(updateSql.toString(), pass);
    }

    @Override
    public int update(List<ColumnValue> setColumnValueList, ConditionGroup... conditionGroups) {
        Assert.notEmpty(setColumnValueList, "请至少传递一个列值.");
        Assert.notEmpty(conditionGroups, "请至少传递一个ConditionGroup.");
        // @formatter:off
        List<OnSecurityColumnName> setColumnList = setColumnValueList.stream()
                .map(ColumnValue::getColumnName)
                .collect(Collectors.toList());
        // @formatter:on

        // Build Update SQL
        StringBuffer updateSql = new StringBuffer();
        updateSql.append(this.table.getUpdateSql(setColumnList));
        updateSql.append(SqlUtils.getConditionGroupSql(conditionGroups));

        // Execute Update
        // @formatter:off
        SqlParameterValue[] parameterValues =
                ArrayUtils.concat(SqlParameterValueUtils.getWithColumnValue(this.table, setColumnValueList),
                        SqlParameterValueUtils.getWithConditionGroup(this.table, conditionGroups));
        // @formatter:on
        PreparedStatementSetter pass = new ArgumentPreparedStatementSetter(parameterValues);
        return this.jdbcOperations.update(updateSql.toString(), pass);
    }

    @Override
    public int update(List<ColumnValue> setColumnValueList, String filterSql, ColumnValue... filterColumnValues) {
        Assert.notEmpty(setColumnValueList, "请至少传递一个列值.");
        Assert.hasText(filterSql, "自定义过滤SQL不可以为空.");
        Assert.notEmpty(filterColumnValues, "请至少传递一个过滤数据的列值.");
        // @formatter:off
        List<OnSecurityColumnName> setColumnList = setColumnValueList.stream()
                .map(ColumnValue::getColumnName)
                .collect(Collectors.toList());
        // @formatter:on

        // Build Update SQL
        StringBuffer updateSql = new StringBuffer();
        updateSql.append(this.table.getUpdateSql(setColumnList));
        updateSql.append(filterSql);

        // Execute Update
        // @formatter:off
        SqlParameterValue[] parameterValues =
                ArrayUtils.concat(SqlParameterValueUtils.getWithColumnValue(this.table, setColumnValueList),
                        SqlParameterValueUtils.getWithColumnValue(this.table, filterColumnValues));
        // @formatter:on
        PreparedStatementSetter pass = new ArgumentPreparedStatementSetter(parameterValues);
        return this.jdbcOperations.update(updateSql.toString(), pass);
    }

    @Override
    public T selectOne(PK pk) {
        Assert.notNull(pk, "主键值不可以为null.");
        OnSecurityColumnName pkColumn = this.table.getPk().getColumnName();
        Condition pkFilterCondition = Condition.withColumn(pkColumn, pk).build();
        return this.selectOne(pkFilterCondition);
    }

    @Override
    public T selectOne(Condition... conditions) {
        Assert.notEmpty(conditions, "请至少传递一个Condition.");
        List<T> resultList = this.select(conditions);
        return !ObjectUtils.isEmpty(resultList) ? resultList.get(0) : null;
    }

    @Override
    public List<T> select(Condition... conditions) {
        Assert.notEmpty(conditions, "请至少传递一个Condition.");
        // Build Query SQL
        StringBuffer querySql = new StringBuffer();
        querySql.append(this.table.getQuerySql());
        querySql.append(SqlUtils.getConditionSql(SqlLogicalOperator.AND, conditions));

        // Execute Query SQL
        return this.doConditionQuery(querySql.toString(), conditions);
    }

    @Override
    public List<T> select(ConditionGroup... conditionGroups) {
        Assert.notEmpty(conditionGroups, "请至少传递一个ConditionGroup.");
        // Build Query SQL
        StringBuffer querySql = new StringBuffer();
        querySql.append(this.table.getQuerySql());
        querySql.append(SqlUtils.getConditionGroupSql(conditionGroups));

        // @formatter:off
        Condition[] conditions = Arrays.stream(conditionGroups)
                .flatMap(conditionGroup -> conditionGroup.getConditions().stream())
                .toArray(Condition[]::new);
        // @formatter:on

        // Execute Query SQL
        return this.doConditionQuery(querySql.toString(), conditions);
    }

    /**
     * 执行条件查询
     *
     * @param querySql   查询SQL
     * @param conditions 查询条件列表
     * @return 查询到的对象列表
     */
    private List<T> doConditionQuery(String querySql, Condition... conditions) {
        Object[] parameterValues = Arrays.stream(conditions).map(Condition::getValue).toArray(Object[]::new);
        List<T> resultList = this.jdbcOperations.query(querySql, new ResultRowMapper(this.table, this.mapEntityClass), parameterValues);
        return !ObjectUtils.isEmpty(resultList) ? resultList : null;
    }
}
