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

import org.minbox.framework.on.security.core.authorization.jdbc.mapper.type.support.BooleanTypeMapper;
import org.minbox.framework.on.security.core.authorization.jdbc.mapper.type.support.LocalDateTimeTypeMapper;
import org.minbox.framework.on.security.core.authorization.jdbc.mapper.type.support.StringTypeMapper;
import org.minbox.framework.on.security.core.authorization.jdbc.mapper.type.support.TimestampTypeMapper;

import java.sql.Types;

/**
 * 定义全部{@link TypeMapper}接口实现类的实例
 *
 * @author 恒宇少年
 * @since 0.0.8
 */
public class TypeMappers {
    /**
     * {@link Types#VARCHAR}类型列值映射器
     */
    public static final StringTypeMapper STRING_MAPPER = new StringTypeMapper();
    /**
     * {@link Types#BOOLEAN}类型列值映射器
     */
    public static final BooleanTypeMapper BOOLEAN_MAPPER = new BooleanTypeMapper();
    /**
     * {@link Types#TIMESTAMP}类型列值映射器
     */
    public static final TimestampTypeMapper TIMESTAMP_MAPPER = new TimestampTypeMapper();
    /**
     * 将{@link Types#TIMESTAMP}类型列值转换为{@link java.time.LocalDateTime}的映射器
     */
    public static final LocalDateTimeTypeMapper LOCAL_DATE_TIME_MAPPER = new LocalDateTimeTypeMapper();
}
