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

package org.minbox.framework.on.security.core.authorization.jdbc.printer;

import org.springframework.jdbc.core.SqlParameterValue;

import java.util.List;
import java.util.Map;

/**
 * SQL打印接口定义
 *
 * @author 恒宇少年
 * @since 0.0.8
 */
public interface SqlPrinter {
    /**
     * SQL打印
     *
     * @param sql             执行的SQL语句
     * @param parameterValues 执行SQL时所携带的参数列表
     * @param affectedRows    执行SQL时所影响行数
     */
    void print(String sql, SqlParameterValue[] parameterValues, int affectedRows);

    /**
     * SQL打印
     *
     * @param sql             执行查询的SQL语句
     * @param parameterValues 执行查询时所携带的参数列表
     * @param queryRows       查询的行数
     */
    void print(String sql, Object[] parameterValues, List<Map<String, Object>> multiColumnValueMap, int queryRows);
}
