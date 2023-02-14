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

import org.minbox.framework.on.security.core.authorization.jdbc.definition.OnSecurityColumnName;
import org.springframework.util.Assert;

/**
 * 自定义SQL的过滤条件所对应的列值
 *
 * @author 恒宇少年
 * @since 0.0.8
 */
public class FilterColumnValue {
    private OnSecurityColumnName column;
    private Object value;

    // @formatter:off
    private FilterColumnValue() { }
    // @formatter:on

    public OnSecurityColumnName getColumn() {
        return column;
    }

    public Object getValue() {
        return value;
    }

    public static Builder withColumnValue(OnSecurityColumnName column, Object value) {
        Assert.notNull(column, "The filter column cannot be null.");
        Assert.notNull(value, "The filter column value cannot be null.");
        return new Builder(column, value);
    }

    /**
     * The {@link FilterColumnValue} Builder
     */
    public static class Builder {
        private OnSecurityColumnName column;
        private Object value;

        public Builder(OnSecurityColumnName column, Object value) {
            this.column = column;
            this.value = value;
        }

        public FilterColumnValue build() {
            FilterColumnValue filterColumnValue = new FilterColumnValue();
            filterColumnValue.column = this.column;
            filterColumnValue.value = this.value;
            return filterColumnValue;
        }
    }
}
