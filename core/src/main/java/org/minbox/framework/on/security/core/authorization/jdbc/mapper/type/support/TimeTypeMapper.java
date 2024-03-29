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

package org.minbox.framework.on.security.core.authorization.jdbc.mapper.type.support;

import org.minbox.framework.on.security.core.authorization.jdbc.mapper.type.TypeMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;

/**
 * 获取{@link java.sql.Types#TIME}类型的列值
 *
 * @author 恒宇少年
 * @since 0.0.8
 */
public class TimeTypeMapper implements TypeMapper<LocalTime, Time> {
    @Override
    public Time toColumn(LocalTime originalValue, String columnName) {
        return originalValue != null ? Time.valueOf(originalValue) : null;
    }

    @Override
    public LocalTime fromColumn(ResultSet rs, String columnName) throws SQLException {
        Time time = rs.getTime(columnName);
        return time != null ? time.toLocalTime() : null;
    }
}
