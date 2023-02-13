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

import org.minbox.framework.on.security.core.authorization.jdbc.mapper.type.TypeMapper;
import org.minbox.framework.on.security.core.authorization.jdbc.mapper.type.TypeMappers;
import org.minbox.framework.on.security.core.authorization.util.StringUtils;

import java.sql.Types;
import java.util.Arrays;
import java.util.Optional;

/**
 * On-Security在数据库中定义的全部列
 *
 * @author 恒宇少年
 * @see Types
 * @since 0.0.8
 */
public enum OnSecurityColumns {
    Id_String("id", Types.VARCHAR, TypeMappers.STRING_MAPPER),
    RegionId("region_id", Types.VARCHAR, TypeMappers.STRING_MAPPER),
    Username("username", Types.VARCHAR, TypeMappers.STRING_MAPPER),
    Password("password", Types.VARCHAR, TypeMappers.STRING_MAPPER),
    Enabled("enabled", Types.BOOLEAN, TypeMappers.BOOLEAN_MAPPER),
    Deleted("deleted", Types.BOOLEAN, TypeMappers.BOOLEAN_MAPPER),
    LastLoginTime("last_login_time", Types.TIMESTAMP, TypeMappers.LOCAL_DATE_TIME_MAPPER),
    Describe("`describe`", Types.VARCHAR, TypeMappers.STRING_MAPPER),
    CreateTime("create_time", Types.TIMESTAMP, TypeMappers.LOCAL_DATE_TIME_MAPPER),
    DeleteTime("delete_time", Types.TIMESTAMP, TypeMappers.LOCAL_DATE_TIME_MAPPER);
    private String name;
    private int sqlType;
    private TypeMapper typeMapper;

    OnSecurityColumns(String name, int sqlType, TypeMapper typeMapper) {
        this.name = name;
        this.sqlType = sqlType;
        this.typeMapper = typeMapper;
    }

    public String getName() {
        return name;
    }

    /**
     * 根据列名获取{@link OnSecurityColumns}枚举实例
     *
     * @param columnName 列名
     * @return {@link OnSecurityColumns}
     */
    public static OnSecurityColumns valueOfColumnName(String columnName) {
        OnSecurityColumns[] columnArray = OnSecurityColumns.values();
        Optional<OnSecurityColumns> optional = Arrays.stream(columnArray)
                .filter(onSecurityColumn -> {
                    String keywordFormat = String.format("`%s`", columnName);
                    return onSecurityColumn.getName().equals(columnName) || onSecurityColumn.getName().equals(keywordFormat);
                })
                .findFirst();
        return optional.isPresent() ? optional.get() : null;
    }

    public String getUpperCamelName() {
        return StringUtils.toUpperCamelName(this.name);
    }

    public String getLowerCamelName() {
        return StringUtils.toLowerCamelName(this.name);
    }

    public int getSqlType() {
        return sqlType;
    }

    public TypeMapper getTypeMapper() {
        return typeMapper;
    }
}
