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

import org.minbox.framework.on.security.core.authorization.jdbc.definition.OnSecurityColumns;
import org.springframework.util.Assert;

/**
 * 执行SQL时所需要的列与值的封装对象
 *
 * @author 恒宇少年
 * @since 0.0.8
 */
public class ColumnValue {
    private OnSecurityColumns column;
    private Object value;

    // @formatter:off
    private ColumnValue() { }
    // @formatter:on

    public OnSecurityColumns getColumn() {
        return column;
    }

    public Object getValue() {
        return value;
    }

    public static Builder with(OnSecurityColumns column, Object value) {
        Assert.notNull(column, "The filter column cannot be null.");
        Assert.notNull(value, "The filter column value cannot be null.");
        return new Builder(column, value);
    }

    /**
     * The {@link ColumnValue} Builder
     */
    public static class Builder {
        private OnSecurityColumns column;
        private Object value;

        public Builder(OnSecurityColumns column, Object value) {
            this.column = column;
            this.value = value;
        }

        public ColumnValue build() {
            ColumnValue columnValue = new ColumnValue();
            columnValue.column = this.column;
            columnValue.value = this.value;
            return columnValue;
        }
    }
}
