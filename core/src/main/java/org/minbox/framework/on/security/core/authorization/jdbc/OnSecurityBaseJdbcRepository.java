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

import org.minbox.framework.on.security.core.authorization.jdbc.definition.Table;
import org.minbox.framework.on.security.core.authorization.jdbc.sql.ColumnValue;
import org.minbox.framework.on.security.core.authorization.jdbc.sql.Condition;
import org.minbox.framework.on.security.core.authorization.jdbc.sql.ConditionGroup;
import org.minbox.framework.on.security.core.authorization.jdbc.sql.SortCondition;

import java.io.Serializable;
import java.util.List;

/**
 * 数据处理基础接口定义
 *
 * @author 恒宇少年
 * @since 0.0.8
 */
public interface OnSecurityBaseJdbcRepository<T extends Serializable, PK> {
    /**
     * 插入单个对象数据
     *
     * @param object 等待插入的对象
     * @return 返回插入数据的行数
     */
    int insert(T object);

    /**
     * 根据主键删除数据
     * <p>
     * 根据{@link Table#getPk()}主键列创建过滤条件{@link Condition}
     * 注意：表必须配置主键
     *
     * @param pk 主键的值
     * @return 删除所影响的数据行数
     */
    int delete(PK pk);

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
    int delete(Condition... conditions);

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
    int delete(ConditionGroup... conditionGroups);

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
    int delete(String filterSql, ColumnValue... filterColumnValues);

    /**
     * 根据主键更新单个对象数据
     *
     * @param object 等待更新的对象
     * @return 更新数据的行数
     */
    int update(T object);

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
    int update(List<ColumnValue> setColumnValueList, Condition... conditions);

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
    int update(List<ColumnValue> setColumnValueList, ConditionGroup... conditionGroups);

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
    int update(List<ColumnValue> setColumnValueList, String filterSql, ColumnValue... filterColumnValues);

    /**
     * 根据主键ID查询单条数据
     * <p>
     * 返回实体的类型使用父类的第一个泛型定义，也就是继承该类实现类所维护的数据实体
     *
     * @param pk 主键的值
     * @return 返回的泛型类型对象实例
     */
    T selectOne(PK pk);

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
    T selectOne(Condition... conditions);

    /**
     * 根据条件分组查询单条数据
     * <pre>
     *     {@code
     *      consoleManagerJdbcRepository.selectOne(
     *                 ConditionGroup.withCondition(
     *                         Condition.withColumn(OnSecurityColumnName.RegionId, "7f776d47-6b03-11ed-b779-0242ac110003").build()
     *                 ).build(),
     *                 ConditionGroup.withCondition(
     *                         Condition.withColumn(OnSecurityColumnName.Id, "083b2f2c-a2dc-11ed-8423-0242ac110002").build()
     *                 ).operator(SqlLogicalOperator.OR)
     *                 .build()
     *         );
     *     }
     * </pre>
     *
     * @param conditionGroups 查询条件分组列表 {@link ConditionGroup}
     * @return 返回的泛型类型对象实例
     */
    T selectOne(ConditionGroup... conditionGroups);

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
     * @return 查询结果对象列表
     */
    List<T> select(Condition... conditions);

    /**
     * 根据排序条件、查询条件查询多条数据
     * <pre>
     *     {@code
     *      consoleManagerJdbcRepository.select(
     *                 SortCondition.withSort(OnSecurityColumnName.CreateTime, SortBy.desc)
     *                         .addSort(OnSecurityColumnName.DeleteTime,SortBy.asc),
     *                 Condition.withColumn(OnSecurityColumnName.RegionId, "7f776d47-6b03-11ed-b779-0242ac110003").build()
     *         );
     *     }
     * </pre>
     *
     * @param sort       排序条件 {@link SortCondition}
     * @param conditions 查询条件列表 {@link Condition}
     * @return 查询结果对象列表
     */
    List<T> select(SortCondition sort, Condition... conditions);

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
    List<T> select(ConditionGroup... conditionGroups);

    /**
     * 根据排序条件、查询条件分组查询多条数据
     *
     * @param sort            排序条件 {@link SortCondition}
     * @param conditionGroups 查询条件分组列表 {@link ConditionGroup}
     * @return 查询结果对象列表
     */
    List<T> select(SortCondition sort, ConditionGroup... conditionGroups);
}
