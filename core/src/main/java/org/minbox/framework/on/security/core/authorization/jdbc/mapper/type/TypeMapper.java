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

package org.minbox.framework.on.security.core.authorization.jdbc.mapper.type;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 列值类型映射器
 *
 * @author 恒宇少年
 * @since 0.0.8
 */
public interface TypeMapper<F, T> {
    /**
     * 根据原始值转换写入数据库列的值
     *
     * @param originalValue 原始值
     * @param columnName    列名称
     * @return 将原始值根据类型转换后的列值
     */
    T toColumn(F originalValue, String columnName);

    /**
     * 从{@link ResultSet}获取列的值并转换后返回
     *
     * @param rs         {@link ResultSet}
     * @param columnName 列名
     * @return 将列值根据类型转换后的值
     * @throws SQLException
     */
    F fromColumn(ResultSet rs, String columnName) throws SQLException;
}
