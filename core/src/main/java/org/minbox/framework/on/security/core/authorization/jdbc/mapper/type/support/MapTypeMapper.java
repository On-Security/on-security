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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.Module;
import org.minbox.framework.on.security.core.authorization.data.region.SecurityRegionIdentityProviderJdbcRepository;
import org.minbox.framework.on.security.core.authorization.jackson2.OnSecurityJsonMapper;
import org.minbox.framework.on.security.core.authorization.jdbc.mapper.type.TypeMapper;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.util.ObjectUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * {@link Map}集合列值类型映射器
 *
 * @author 恒宇少年
 * @since 0.0.8
 */
public class MapTypeMapper implements TypeMapper<Map<String, Object>, String> {
    private OnSecurityJsonMapper objectMapper = new OnSecurityJsonMapper();

    public MapTypeMapper() {
        ClassLoader classLoader = SecurityRegionIdentityProviderJdbcRepository.class.getClassLoader();
        List<Module> securityModules = SecurityJackson2Modules.getModules(classLoader);
        this.objectMapper.registerModules(securityModules);
    }

    @Override
    public String toColumn(Map<String, Object> originalValue, String columnName) {
        return !ObjectUtils.isEmpty(originalValue) ? this.asString(originalValue) : null;
    }

    @Override
    public Map<String, Object> fromColumn(ResultSet rs, String columnName) throws SQLException {
        String columnValue = rs.getString(columnName);
        return !ObjectUtils.isEmpty(columnValue) ? this.parseMap(columnValue) : null;
    }

    private String asString(Map<String, Object> originalValue) {
        try {
            return this.objectMapper.writeValueAsString(originalValue);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    private Map<String, Object> parseMap(String data) {
        try {
            return this.objectMapper.readValue(data, new TypeReference<Map<String, Object>>() {
            });
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }
}
