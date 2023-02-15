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
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 排序条件
 *
 * @author 恒宇少年
 * @since 0.0.8
 */
public class SortCondition {
    private final Map<OnSecurityColumnName, SortBy> SortColumnMap = new HashMap();
    private static final String SORT_COLUMN_DELIMITER = ", ";
    private static final String SPACE = " ";
    private static final String SORT_SQL_PREFIX = " order by ";

    private SortCondition(OnSecurityColumnName columnName, SortBy sortBy) {
        SortColumnMap.put(columnName, sortBy);
    }

    /**
     * 根据排序条件实例化{@link SortCondition}
     *
     * @param columnName 列名 {@link OnSecurityColumnName}
     * @param sortBy     排序方式 {@link SortBy}
     * @return {@link SortCondition}
     */
    public static SortCondition withSort(OnSecurityColumnName columnName, SortBy sortBy) {
        return new SortCondition(columnName, sortBy);
    }

    /**
     * 添加一个排序条件
     *
     * @param columnName 列名 {@link OnSecurityColumnName}
     * @param sortBy     排序方式 {@link SortBy}
     * @return {@link SortCondition}
     */
    public SortCondition addSort(OnSecurityColumnName columnName, SortBy sortBy) {
        this.SortColumnMap.put(columnName, sortBy);
        return this;
    }

    /**
     * 获取格式化后的排序SQL
     *
     * @return 排序SQL
     */
    public String getSortSql() {
        if (!ObjectUtils.isEmpty(SortColumnMap)) {
            String sortColumnSql = this.SortColumnMap.keySet().stream().map(columnName -> {
                SortBy sortBy = SortColumnMap.get(columnName);
                return columnName.getName() + SPACE + sortBy.name();
            }).collect(Collectors.joining(SORT_COLUMN_DELIMITER));
            return SORT_SQL_PREFIX + sortColumnSql;
        }
        return null;
    }
}
