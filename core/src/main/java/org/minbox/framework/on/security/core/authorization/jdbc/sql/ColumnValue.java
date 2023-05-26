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
 * 执行SQL时所需要的列与值的封装对象
 *
 * @author 恒宇少年
 * @since 0.0.8
 */
public class ColumnValue {
    private OnSecurityColumnName columnName;
    private Object value;

    // @formatter:off
    private ColumnValue(OnSecurityColumnName columnName,Object value) {
        this.columnName = columnName;
        this.value = value;
    }
    // @formatter:on

    public OnSecurityColumnName getColumnName() {
        return columnName;
    }

    public Object getValue() {
        return value;
    }

    public static ColumnValue with(OnSecurityColumnName columnName, Object value) {
        Assert.notNull(columnName, "The filter column cannot be null.");
        Assert.notNull(value, "The filter column value cannot be null.");
        return new ColumnValue(columnName, value);
    }
}
