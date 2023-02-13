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

/**
 * 数据库表中单个列定义
 *
 * @author 恒宇少年
 * @since 0.0.8
 */
public class TableColumn {
    private OnSecurityColumns column;
    private boolean pk;
    private boolean insertable;
    private boolean updatable;

    private TableColumn(OnSecurityColumns column) {
        this(column, true, true);
    }

    private TableColumn(OnSecurityColumns column, boolean insertable, boolean updatable) {
        this.column = column;
        this.insertable = insertable;
        this.updatable = updatable;
    }

    public static TableColumn build(OnSecurityColumns onSecurityColumns) {
        return new TableColumn(onSecurityColumns);
    }

    public TableColumn primaryKey() {
        this.pk = true;
        return this;
    }

    public TableColumn insertable(boolean insertable) {
        this.insertable = insertable;
        return this;
    }

    public TableColumn updatable(boolean updatable) {
        this.updatable = updatable;
        return this;
    }

    /**
     * 获取列信息
     *
     * @return {@link OnSecurityColumns}
     */
    public OnSecurityColumns getColumn() {
        return column;
    }

    /**
     * 是否为主键
     */
    public boolean isPk() {
        return pk;
    }

    /**
     * 是否参与insert
     *
     * @return 返回true时表示参与insert
     */
    public boolean isInsertable() {
        return insertable;
    }

    /**
     * 是否参与update
     *
     * @return 返回true时表示参与update
     */
    public boolean isUpdatable() {
        return updatable;
    }

    @Override
    public String toString() {
        // @formatter:off
        return "TableColumn(column=" + this.column + ", pk=" + this.pk + ", insertable=" + this.insertable +
                ", updatable=" + this.updatable + ")";
        // @formatter:on
    }
}
