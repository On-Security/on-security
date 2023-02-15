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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 控制台打印SQL
 *
 * @author 恒宇少年
 * @since 0.0.8
 */
public class ConsoleSqlPrinter implements SqlPrinter {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ConsoleSqlPrinter.class);
    private static final String DELIMITER = ", ";
    private static final String PRINT_NULL_STRING = "null";

    @Override
    public void print(String sql, SqlParameterValue[] parameterValues, int affectedRows) {
        logger.debug("===>\tPreparing: {}", sql);
        String parameters = Arrays.stream(parameterValues)
                .map(sqlParameterValue -> {
                    if (sqlParameterValue.getValue() != null) {
                        String typeName = sqlParameterValue.getValue().getClass().getSimpleName();
                        return sqlParameterValue.getValue() + "(" + typeName + ")";
                    }
                    return PRINT_NULL_STRING;
                })
                .collect(Collectors.joining(DELIMITER));
        logger.debug("===>\tParameters: {}", parameters);
        logger.debug("===>\tAffected Rows: {}", affectedRows);
    }

    @Override
    public void print(String sql, Object[] parameterValues, List<Map<String, Object>> multiColumnValueMap, int queryRows) {
        logger.debug("===>\tPreparing: {}", sql);
        String parameters = Arrays.stream(parameterValues)
                .map(sqlParameterValue -> {
                    if (sqlParameterValue != null) {
                        String typeName = sqlParameterValue.getClass().getSimpleName();
                        return sqlParameterValue + "(" + typeName + ")";
                    }
                    return PRINT_NULL_STRING;
                })
                .collect(Collectors.joining(DELIMITER));
        logger.debug("===>\tParameters: {}", parameters);
        if (!ObjectUtils.isEmpty(multiColumnValueMap)) {
            multiColumnValueMap.stream().forEach(columnValueMap -> logger.debug("===>\tRow: {}", columnValueMap));
        }
        logger.debug("===>\tQuery Rows: {}", queryRows);
    }
}
