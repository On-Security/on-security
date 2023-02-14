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

import org.minbox.framework.on.security.core.authorization.jdbc.mapper.type.TypeMapper;
import org.minbox.framework.on.security.core.authorization.jdbc.mapper.type.TypeMappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDateTime;

/**
 * 数据库表中单个列定义
 *
 * @author 恒宇少年
 * @since 0.0.8
 */
public class TableColumn {
    private OnSecurityColumnName column;
    private boolean pk;
    private boolean insertable;
    private boolean updatable;
    private int sqlType;
    private TypeMapper typeMapper;

    private TableColumn(OnSecurityColumnName column) {
        this(column, true, true);
    }

    private TableColumn(OnSecurityColumnName column, boolean insertable, boolean updatable) {
        this.column = column;
        this.insertable = insertable;
        this.updatable = updatable;
        this.sqlType = Types.VARCHAR;
        this.typeMapper = TypeMappers.STRING;
    }

    public static TableColumn withColumnName(OnSecurityColumnName column) {
        return new TableColumn(column);
    }

    /**
     * 配置该列为主键
     *
     * @return {@link TableColumn} 本类当前对象实例
     */
    public TableColumn primaryKey() {
        this.pk = true;
        this.updatable = false;
        return this;
    }

    /**
     * 配置该列为布尔类型的值
     *
     * @return {@link TableColumn} 本类当前对象实例
     */
    public TableColumn booleanValue() {
        this.sqlType = Types.BOOLEAN;
        this.typeMapper = TypeMappers.BOOLEAN;
        return this;
    }

    /**
     * 配置该列为整型类型的值
     *
     * @return {@link TableColumn} 本类当前对象实例
     */
    public TableColumn intValue() {
        this.sqlType = Types.INTEGER;
        this.typeMapper = TypeMappers.INTEGER;
        return this;
    }

    /**
     * 配置该列为{@link LocalDateTime}类型的值
     *
     * @return {@link TableColumn} 本类当前对象实例
     */
    public TableColumn localDateTimeValue() {
        this.sqlType = Types.TIMESTAMP;
        this.typeMapper = TypeMappers.LOCAL_DATE_TIME;
        return this;
    }

    /**
     * 配置该列是否参与insert
     *
     * @param insertable 配置为true时表示参与insert
     * @return {@link TableColumn} 本类当前对象实例
     */
    public TableColumn insertable(boolean insertable) {
        this.insertable = insertable;
        return this;
    }

    /**
     * 配置该列不参与insert也不参与update
     *
     * @return {@link TableColumn} 本类当前对象实例
     */
    public TableColumn notOperate() {
        this.insertable = false;
        this.updatable = false;
        return this;
    }

    /**
     * 配置该列是否参与update
     *
     * @param updatable 配置为true时表示参与update
     * @return {@link TableColumn} 本类当前对象实例
     */
    public TableColumn updatable(boolean updatable) {
        this.updatable = updatable;
        return this;
    }

    /**
     * 配置该列的SQL类型
     *
     * @param sqlType SqlType
     * @return {@link TableColumn} 本类当前对象实例
     * @see Types
     */
    public TableColumn sqlType(int sqlType) {
        this.sqlType = sqlType;
        return this;
    }

    /**
     * 配置该列使用的类型转换映射器
     *
     * @param typeMapper {@link TypeMapper} 对象实例
     * @return {@link TableColumn} 本类当前对象实例
     */
    public TableColumn typeMapper(TypeMapper typeMapper) {
        this.typeMapper = typeMapper;
        return this;
    }

    public OnSecurityColumnName getColumnName() {
        return column;
    }

    public boolean isPk() {
        return pk;
    }

    public boolean isInsertable() {
        return insertable;
    }

    public boolean isUpdatable() {
        return updatable;
    }

    public TypeMapper getTypeMapper() {
        return typeMapper;
    }

    public int getSqlType() {
        return sqlType;
    }

    public Object toColumnValue(Object originalValue) {
        return this.typeMapper.toColumn(originalValue, this.getColumnName().getName());
    }

    public Object fromColumnValue(ResultSet rs, String columnName) throws SQLException {
        return this.typeMapper.fromColumn(rs, columnName);
    }

    @Override
    public String toString() {
        // @formatter:off
        return "TableColumn(column=" + this.column + ", pk=" + this.pk + ", insertable=" + this.insertable +
                ", updatable=" + this.updatable + ")";
        // @formatter:on
    }
}
