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

package org.minbox.framework.on.security.core.authorization.jdbc.mapper;

import org.minbox.framework.on.security.core.authorization.jdbc.definition.OnSecurityColumns;
import org.minbox.framework.on.security.core.authorization.jdbc.mapper.type.TypeMapper;
import org.minbox.framework.on.security.core.authorization.jdbc.utils.ObjectClassUtils;
import org.minbox.framework.on.security.core.authorization.util.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.ObjectUtils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据行与实体封装映射器
 *
 * @author 恒宇少年
 * @since 0.0.8
 */
public class ResultRowMapper implements RowMapper<Object> {
    /**
     * 映射封装实体类型
     */
    private Class mapEntityClass;

    public ResultRowMapper(Class mapEntityClass) {
        this.mapEntityClass = mapEntityClass;
    }

    /**
     * 映射封装返回对象
     *
     * @param rs     {@link ResultSet}
     * @param rowNum 当前行的索引
     * @return 映射封装后的对象，类型为{@link #mapEntityClass}
     * @throws SQLException
     */
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        try {
            List<String> columnNameList = this.getResultSetColumnNameList(rs);
            // Instance Result Object
            Object mapEntityObject = this.mapEntityClass.getConstructor().newInstance();
            if (!ObjectUtils.isEmpty(columnNameList)) {
                Map<String, Object> columnValueMap = new HashMap();
                for (String columnName : columnNameList) {
                    OnSecurityColumns onSecurityColumn = OnSecurityColumns.valueOfColumnName(columnName);
                    // Get the converted column value
                    TypeMapper typeMapper = onSecurityColumn.getTypeMapper();
                    Object columnValue = typeMapper.accept(rs, columnName);
                    String setMethodName = ObjectClassUtils.getSetMethodName(StringUtils.toUpperCamelName(columnName));
                    columnValueMap.put(setMethodName, columnValue);
                }
                // Invoke Result Object Set Methods
                ObjectClassUtils.invokeObjectSetMethod(mapEntityObject, columnValueMap);
                return mapEntityObject;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取{@link ResultSetMetaData}返回的全部列名
     *
     * @param rs {@link ResultSet}
     * @return 列名列表
     * @throws SQLException
     */
    private List<String> getResultSetColumnNameList(ResultSet rs) throws SQLException {
        List<String> columnNameList = new ArrayList<>();
        ResultSetMetaData metaData = rs.getMetaData();
        for (int i = 0; i < metaData.getColumnCount(); i++) {
            columnNameList.add(metaData.getColumnName(i + 1));
        }
        return columnNameList;
    }
}
