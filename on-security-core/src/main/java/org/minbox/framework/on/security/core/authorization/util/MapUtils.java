/*
 *     Copyright (C) 2022  恒宇少年
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.minbox.framework.on.security.core.authorization.util;

import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Map工具类
 *
 * @author 恒宇少年
 * @since 0.0.5
 */
public class MapUtils {
    public static <T> T mapToObject(Map<String, Object> map, Class<T> clazz) throws InstantiationException, IllegalAccessException {
        if (map == null) return null;
        T t = clazz.newInstance();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isFinal(mod) || Modifier.isStatic(mod)) continue;
            field.setAccessible(true);
            field.set(t, map.get(field.getName()));
        }
        return t;
    }

    public static Map<String, Object> objectToMap(Object obj, List<String> ignoreKeys) {
        Map<String, Object> map = objectToMap(obj);
        if (!ObjectUtils.isEmpty(ignoreKeys)) {
            Iterator<String> iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                if (ignoreKeys.contains(key)) {
                    iterator.remove();
                    map.remove(key);
                }
            }
        }
        return map;
    }

    public static Map<String, Object> objectToMap(Object obj) {
        if (obj == null) return null;
        Map<String, Object> map = new HashMap<>();
        try {
            Field[] declaredFields = obj.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }
}
