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

package org.minbox.framework.on.security.core.authorization.jdbc.definition;

import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 数据库中单张表结构定义
 * <p>
 * 定义一张表的表名、数据列列表、主键，可以根据配置生成单表的查询、插入、更新、删除等基本SQL
 *
 * @author 恒宇少年
 * @since 0.0.8
 */
public class Table {
    private static final String DEFAULT_FILTER = " where 1 = 1";
    private static final String PLACEHOLDER = "?";
    private static final String DELIMITER = ", ";
    private static final String COLUMN_EQ_VALUE = "%s = ?";
    private static final String INSERT_SQL_STENCIL = "insert into %s (%s) values (%s)";
    private static final String DELETE_SQL_STENCIL = "delete from %s" + DEFAULT_FILTER;
    private static final String UPDATE_SQL_STENCIL = "update %s set %s" + DEFAULT_FILTER;
    private static final String QUERY_SQL_STENCIL = "select %s from %s" + DEFAULT_FILTER;
    private String tableName;
    private TableColumn[] columnArray;

    private Table(String tableName) {
        this.tableName = tableName;
    }

    public static Table withTableName(String tableName) {
        return new Table(tableName);
    }

    public Table columns(TableColumn... columns) {
        Assert.notEmpty(columns, "Table columns cannot be empty.");
        this.columnArray = columns;
        return this;
    }

    public String getTableName() {
        return tableName;
    }

    public TableColumn getPk() {
        // @formatter:off
        Optional<TableColumn> optional =
                this.getTableColumns().stream()
                        .filter(tableColumn -> tableColumn.isPk()).findFirst();
        // @formatter:on
        return optional.isPresent() ? optional.get() : null;
    }

    public List<TableColumn> getTableColumns() {
        return Arrays.asList(columnArray);
    }

    public List<TableColumn> getInsertableTableColumns() {
        // @formatter:off
        return this.getTableColumns()
                .stream()
                .filter(tableColumn -> tableColumn.isInsertable())
                .collect(Collectors.toList());
        // @formatter:on
    }

    public List<TableColumn> getUpdatableTableColumns() {
        // @formatter:off
        return this.getTableColumns()
                .stream()
                .filter(tableColumn -> tableColumn.isUpdatable())
                .collect(Collectors.toList());
        // @formatter:on
    }

    public String getQuerySql() {
        // @formatter:off
        return String.format(QUERY_SQL_STENCIL,
                this.getColumnSql(false, false),
                this.getTableName());
        // @formatter:on
    }

    public String getInsertSql() {
        // @formatter:off
        String values = this.getInsertableTableColumns()
                .stream()
                .map(tableColumn -> PLACEHOLDER)
                .collect(Collectors.joining(DELIMITER));
        return String.format(INSERT_SQL_STENCIL,
                this.getTableName(),
                this.getColumnSql(true,false),
                values);
        // @formatter:on
    }

    public String getUpdateSql() {
        // @formatter:off
        String updateColumnSql = this.getUpdatableTableColumns()
                .stream()
                .map(tableColumn -> String.format(COLUMN_EQ_VALUE, tableColumn.getColumn().getName()))
                .collect(Collectors.joining(DELIMITER));
        // @formatter:on
        return String.format(UPDATE_SQL_STENCIL, this.getTableName(), updateColumnSql);
    }

    public String getUpdateSql(List<OnSecurityColumns> columnList) {
        if (!ObjectUtils.isEmpty(columnList)) {
            // @formatter:off
            String updateColumnSql = columnList.stream()
                    .map(column -> String.format(COLUMN_EQ_VALUE, column.getName()))
                    .collect(Collectors.joining(DELIMITER));
            // @formatter:on
            return String.format(UPDATE_SQL_STENCIL, this.getTableName(), updateColumnSql);
        }
        return null;
    }

    public String getDeleteSql() {
        return String.format(DELETE_SQL_STENCIL, this.getTableName());
    }

    private String getColumnSql(boolean filterInsertable, boolean filterUpdatable) {
        // @formatter:off
        return this.getTableColumns().stream()
                .filter(tableColumn -> {
                    if(filterInsertable) {
                        return tableColumn.isInsertable();
                    } else if(filterUpdatable) {
                        return tableColumn.isUpdatable();
                    }
                    return true;
                })
                .map(tableColumn -> tableColumn.getColumn().getName()).distinct()
                .collect(Collectors.joining(DELIMITER));
        // @formatter:on
    }

    @Override
    public String toString() {
        return "Table(tableName=" + this.tableName + ", columnArray=" + this.columnArray + ")";
    }
}
