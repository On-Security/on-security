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

import com.google.common.collect.Lists;
import com.nimbusds.jose.util.ArrayUtils;
import org.minbox.framework.on.security.core.authorization.jdbc.definition.OnSecurityColumns;
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
public class OnSecurityBaseJdbcRepository<T extends Serializable, PK> {
    private static final int MAP_ENTITY_GENERIC_INDEX = 0;
    private Table table;
    private JdbcOperations jdbcOperations;
    private Class mapEntityClass;

    public OnSecurityBaseJdbcRepository(Table table, JdbcOperations jdbcOperations) {
        this.table = table;
        this.jdbcOperations = jdbcOperations;
        ResolvableType superResolvableType = ResolvableType.forClass(this.getClass()).getSuperType();
        this.mapEntityClass = superResolvableType.getGeneric(MAP_ENTITY_GENERIC_INDEX).resolve();
    }

    /**
     * 插入单个对象数据
     *
     * @param object 等待插入的对象
     * @return 返回插入数据的行数
     */
    public int insert(T object) {
        List<TableColumn> insertableTableColumns = this.table.getInsertableTableColumns();
        Map<String, Object> methodValueMap = ObjectClassUtils.invokeObjectGetMethod(object);

        // Execute Insert
        SqlParameterValue[] parameterValues = SqlParameterValueUtils.getWithTableColumn(insertableTableColumns, methodValueMap);
        PreparedStatementSetter pass = new ArgumentPreparedStatementSetter(parameterValues);
        return this.jdbcOperations.update(this.table.getInsertSql(), pass);
    }

    /**
     * 根据主键删除数据
     * <p>
     * 根据{@link Table#getPk()}主键列创建过滤条件{@link Condition}
     * 注意：表必须配置主键
     *
     * @param id 主键的值
     * @return 删除所影响的数据行数
     */
    public int delete(PK id) {
        OnSecurityColumns pkColumn = this.table.getPk().getColumn();
        Condition idFilterCondition = Condition.withColumn(pkColumn, id).build();
        // Build Delete SQL
        StringBuffer sql = new StringBuffer();
        sql.append(this.table.getDeleteSql());
        sql.append(SqlUtils.getConditionSql(SqlLogicalOperator.AND, idFilterCondition));

        // Execute Delete
        SqlParameterValue[] parameterValues = SqlParameterValueUtils.getWithCondition(idFilterCondition);
        PreparedStatementSetter pass = new ArgumentPreparedStatementSetter(parameterValues);
        return this.jdbcOperations.update(sql.toString(), pass);
    }

    /**
     * 根据条件{@link Condition}删除数据
     * <pre>
     *     {@code
     *      baseJdbcRepository.delete(
     *                 Condition.withColumn(OnSecurityColumns.RegionId, "7f776d47-6b03-11ed-b779-0242ac110003").build(),
     *                 Condition.withColumn(OnSecurityColumns.Enabled, false).build(),
     *                 Condition.withColumn(OnSecurityColumns.Deleted, false).build()
     *         );
     *     }
     * </pre>
     *
     * @param conditions 删除数据的过滤条件 {@link Condition}
     * @return 删除所影响的行数
     */
    public int delete(Condition... conditions) {
        Assert.notEmpty(conditions, "请至少传递一个Condition.");
        // Build Delete SQL
        String conditionSql = SqlUtils.getConditionSql(SqlLogicalOperator.AND, conditions);
        StringBuffer sql = new StringBuffer();
        sql.append(this.table.getDeleteSql());
        sql.append(conditionSql);

        // Execute Delete
        SqlParameterValue[] parameterValues = SqlParameterValueUtils.getWithCondition(conditions);
        PreparedStatementSetter pass = new ArgumentPreparedStatementSetter(parameterValues);
        return this.jdbcOperations.update(sql.toString(), pass);
    }

    /**
     * 根据条件分组进行删除数据
     * <pre>
     *     {@code
     *      baseJdbcRepository.delete(
     *                 ConditionGroup
     *                         .withCondition(Condition.withColumn(OnSecurityColumns.RegionId, "7f776d47-6b03-11ed-b779-0242ac110003").build())
     *                         .condition(Condition.withColumn(OnSecurityColumns.Enabled, false).build())
     *                         .build(),
     *                 ConditionGroup.withCondition(
     *                         Condition.withColumn(OnSecurityColumns.Deleted, true).build()
     *                 ).operator(SqlLogicalOperator.OR).build()
     *         );
     *     }
     * </pre>
     *
     * @param conditionGroups 删除数据的过滤条件分组 {@link ConditionGroup}
     * @return 删除所影响的行数
     */
    public int delete(ConditionGroup... conditionGroups) {
        Assert.notEmpty(conditionGroups, "请至少传递一个ConditionGroup.");
        // Build Delete SQL
        String conditionSql = SqlUtils.getConditionGroupSql(conditionGroups);
        StringBuffer sql = new StringBuffer();
        sql.append(this.table.getDeleteSql());
        sql.append(conditionSql);

        // Execute Delete
        SqlParameterValue[] parameterValues = SqlParameterValueUtils.getWithConditionGroup(conditionGroups);
        PreparedStatementSetter pass = new ArgumentPreparedStatementSetter(parameterValues);
        return this.jdbcOperations.update(sql.toString(), pass);
    }

    /**
     * 根据自定义过滤SQL删除数据
     *
     * <pre>
     *     {@code
     *      baseJdbcRepository.delete(" and region_id != ?",
     *                 ColumnValue.with(OnSecurityColumns.RegionId, "7f776d47-6b03-11ed-b779-0242ac110003").build()
     *         );
     *     }
     * </pre>
     *
     * @param filterSql          自定义过滤数据的SQL
     * @param filterColumnValues 对应SQL查询列的值 {@link ColumnValue}
     * @return 删除所影响的行数
     */
    public int delete(String filterSql, ColumnValue... filterColumnValues) {
        Assert.notEmpty(filterColumnValues, "请至少传递一个FilterColumnValue.");
        // Build Delete SQL
        StringBuffer sql = new StringBuffer();
        sql.append(this.table.getDeleteSql());
        sql.append(filterSql);

        // Execute Delete
        SqlParameterValue[] parameterValues = SqlParameterValueUtils.getWithColumnValue(filterColumnValues);
        PreparedStatementSetter pass = new ArgumentPreparedStatementSetter(parameterValues);
        return this.jdbcOperations.update(sql.toString(), pass);
    }

    /**
     * 根据主键更新单个对象数据
     *
     * @param object 等待更新的对象
     * @return 更新数据的行数
     */
    public int update(T object) {
        OnSecurityColumns pkColumn = this.table.getPk().getColumn();
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

    /**
     * 根据条件更新指定列的值
     *
     * <pre>
     *     {@code
     *      baseJdbcRepository.update(Arrays.asList(
     *                 ColumnValue.with(OnSecurityColumns.DeleteTime, LocalDateTime.now()).build(),
     *                 ColumnValue.with(OnSecurityColumns.Enabled, false).build()
     *         ), Condition.withColumn(OnSecurityColumns.Deleted, true).build());
     *     }
     * </pre>
     *
     * @param setColumnValueList set列与值关系实体列表
     * @param conditions         过滤更新数据的条件数组
     * @return 更新数据的行数
     */
    public int update(List<ColumnValue> setColumnValueList, Condition... conditions) {
        // @formatter:off
        List<OnSecurityColumns> setColumnList = setColumnValueList.stream()
                .map(ColumnValue::getColumn)
                .collect(Collectors.toList());
        // @formatter:on

        // Build Update SQL
        StringBuffer updateSql = new StringBuffer();
        updateSql.append(this.table.getUpdateSql(setColumnList));
        updateSql.append(SqlUtils.getConditionSql(SqlLogicalOperator.AND, conditions));

        // Execute Update
        SqlParameterValue[] parameterValues =
                ArrayUtils.concat(SqlParameterValueUtils.getWithColumnValue(setColumnValueList), SqlParameterValueUtils.getWithCondition(conditions));
        PreparedStatementSetter pass = new ArgumentPreparedStatementSetter(parameterValues);
        return this.jdbcOperations.update(updateSql.toString(), pass);
    }

    /**
     * 根据条件分组更新指定列的值
     *
     * <pre>
     *     {@code
     *       baseJdbcRepository.update(Arrays.asList(ColumnValue.with(OnSecurityColumns.Enabled, false).build()),
     *                 // Deleted is true
     *                 ConditionGroup.withCondition(Condition.withColumn(OnSecurityColumns.Deleted, true).build()).build(),
     *                 // CreateTime <= now()
     *                 ConditionGroup.withCondition(Condition.withColumn(OnSecurityColumns.CreateTime, LocalDateTime.now())
     *                                 .operator(SqlComparisonOperator.LessThanOrEqualTo)
     *                                 .build())
     *                         .operator(SqlLogicalOperator.OR)
     *                         .build()
     *     }
     * </pre>
     *
     * @param setColumnValueList {@link ColumnValue} set列与值关系实体列表
     * @param conditionGroups    {@link ConditionGroup} 查询条件分组列表
     * @return 更新数据的行数
     */
    public int update(List<ColumnValue> setColumnValueList, ConditionGroup... conditionGroups) {
        // @formatter:off
        List<OnSecurityColumns> setColumnList = setColumnValueList.stream()
                .map(ColumnValue::getColumn)
                .collect(Collectors.toList());
        // @formatter:on

        // Build Update SQL
        StringBuffer updateSql = new StringBuffer();
        updateSql.append(this.table.getUpdateSql(setColumnList));
        updateSql.append(SqlUtils.getConditionGroupSql(conditionGroups));

        // Execute Update
        SqlParameterValue[] parameterValues =
                ArrayUtils.concat(SqlParameterValueUtils.getWithColumnValue(setColumnValueList), SqlParameterValueUtils.getWithConditionGroup(conditionGroups));
        PreparedStatementSetter pass = new ArgumentPreparedStatementSetter(parameterValues);
        return this.jdbcOperations.update(updateSql.toString(), pass);
    }

    /**
     * 根据自定义过滤条件SQL更新数据
     * <pre>
     *      {@code
     *          baseJdbcRepository.update(Arrays.asList(ColumnValue.with(OnSecurityColumns.Enabled, true).build()),
     *                 " and deleted = ? or create_time <= ?",
     *                 ColumnValue.with(OnSecurityColumns.Deleted, true).build(),
     *                 ColumnValue.with(OnSecurityColumns.CreateTime, LocalDateTime.now()).build()
     *         );
     *      }
     *  </pre>
     *
     * @param setColumnValueList {@link ColumnValue} set列与值关系实体列表
     * @param filterSql          自定义过滤SQL
     * @param filterColumnValues {@link ColumnValue} 过滤SQL列值关系实体列表
     * @return 更新数据的行数
     */
    public int update(List<ColumnValue> setColumnValueList, String filterSql, ColumnValue... filterColumnValues) {
        // @formatter:off
        List<OnSecurityColumns> setColumnList = setColumnValueList.stream()
                .map(ColumnValue::getColumn)
                .collect(Collectors.toList());
        // @formatter:on

        // Build Update SQL
        StringBuffer updateSql = new StringBuffer();
        updateSql.append(this.table.getUpdateSql(setColumnList));
        updateSql.append(filterSql);

        // Execute Update
        SqlParameterValue[] parameterValues =
                ArrayUtils.concat(SqlParameterValueUtils.getWithColumnValue(setColumnValueList), SqlParameterValueUtils.getWithColumnValue(filterColumnValues));
        PreparedStatementSetter pass = new ArgumentPreparedStatementSetter(parameterValues);
        return this.jdbcOperations.update(updateSql.toString(), pass);
    }

    /**
     * 根据主键ID查询单条数据
     * <p>
     * 返回实体的类型使用父类的第一个泛型定义，也就是继承该类实现类所维护的数据实体
     *
     * @param id 主键的值
     * @return 返回的泛型类型对象实例
     */
    public T selectOne(PK id) {
        OnSecurityColumns pkColumn = this.table.getPk().getColumn();
        Condition pkFilterCondition = Condition.withColumn(pkColumn, id).build();
        return this.selectOne(pkFilterCondition);
    }

    /**
     * 根据条件查询单条数据
     * <pre>
     *     {@code
     *      consoleManagerJdbcRepository.selectOne(
     *                         Condition.withColumn(OnSecurityColumns.Id_String, "083b2f2c-a2dc-11ed-8423-0242ac110002").build(),
     *                         Condition.withColumn(OnSecurityColumns.RegionId, "7f776d47-6b03-11ed-b779-0242ac110003").build()
     *                 );
     *     }
     * </pre>
     *
     * @param conditions 查询条件列表 {@link Condition}
     * @return 返回的泛型类型对象实例
     */
    public T selectOne(Condition... conditions) {
        List<T> resultList = this.select(conditions);
        return !ObjectUtils.isEmpty(resultList) ? resultList.get(0) : null;
    }

    /**
     * 根据条件查询多条数据
     *
     * <pre>
     *     {@code
     *      List<SecurityConsoleManager> consoleManagerList = consoleManagerJdbcRepository.select(
     *                 Condition.withColumn(OnSecurityColumns.RegionId, "7f776d47-6b03-11ed-b779-0242ac110003").build()
     *         );
     *     }
     * </pre>
     *
     * @param conditions 查询条件列表 {@link Condition}
     * @return 查询到的对象列表
     */
    public List<T> select(Condition... conditions) {
        // Build Query SQL
        StringBuffer querySql = new StringBuffer();
        querySql.append(this.table.getQuerySql());
        querySql.append(SqlUtils.getConditionSql(SqlLogicalOperator.AND, conditions));

        // Execute Query SQL
        return this.doConditionQuery(querySql.toString(), conditions);
    }

    /**
     * 根据条件分组查询多条数据
     * <pre>
     *     {@code
     *      List<SecurityConsoleManager> consoleManagerList = consoleManagerJdbcRepository.select(
     *                 ConditionGroup.withCondition(
     *                         Condition.withColumn(OnSecurityColumns.RegionId, "7f776d47-6b03-11ed-b779-0242ac110003").build()
     *                 ).build(),
     *                 ConditionGroup.withCondition(
     *                         Condition.withColumn(OnSecurityColumns.Id_String, "083b2f2c-a2dc-11ed-8423-0242ac110002").build()
     *                 ).operator(SqlLogicalOperator.OR)
     *                 .build()
     *         );
     *     }
     * </pre>
     *
     * @param conditionGroups 查询分组列表 {@link ConditionGroup}
     * @return 查询到的对象列表
     */
    public List<T> select(ConditionGroup... conditionGroups) {
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
        List<Object> resultList = this.jdbcOperations.query(querySql, new ResultRowMapper(mapEntityClass), parameterValues);
        return !ObjectUtils.isEmpty(resultList) ? Lists.transform(resultList, resultObject -> (T) resultObject) : null;
    }
}
