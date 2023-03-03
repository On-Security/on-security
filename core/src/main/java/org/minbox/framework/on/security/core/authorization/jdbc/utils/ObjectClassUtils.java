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

package org.minbox.framework.on.security.core.authorization.jdbc.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 对象操作工具类
 *
 * @author 恒宇少年
 * @since 0.0.8
 */
public class ObjectClassUtils {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ObjectClassUtils.class);
    private static final String GET_METHOD_PREFIX = "get";
    private static final String IS_BOOLEAN_METHOD_PREFIX = "is";
    private static final String SET_METHOD_PREFIX = "set";

    public static String getGetMethodName(String fieldUpperCamelName) {
        return GET_METHOD_PREFIX + fieldUpperCamelName;
    }

    public static String getSetMethodName(String fieldUpperCamelName) {
        return SET_METHOD_PREFIX + fieldUpperCamelName;
    }

    /**
     * 获取指定类定义的Get方法列表
     *
     * @param clazz 获取Get方法的类
     * @return {@link Method} 对象列表
     */
    public static Method[] getClassGetMethod(Class<?> clazz) {
        // @formatter:off
        Method[] declaredMethods = ReflectionUtils.getDeclaredMethods(clazz);
        return Arrays.stream(declaredMethods)
                .filter(method -> method.getName().startsWith(GET_METHOD_PREFIX) || method.getName().startsWith(IS_BOOLEAN_METHOD_PREFIX))
                .collect(Collectors.toList())
                .stream()
                .toArray(Method[]::new);
        // @formatter:on
    }

    /**
     * 获取指定类定义的Set方法列表
     *
     * @param clazz 获取Get方法的类
     * @return {@link Method} 对象列表
     */
    public static Method[] getClassSetMethod(Class<?> clazz) {
        // @formatter:off
        Method[] declaredMethods = ReflectionUtils.getDeclaredMethods(clazz);
        return Arrays.stream(declaredMethods)
                .filter(method -> method.getName().startsWith(SET_METHOD_PREFIX))
                .collect(Collectors.toList())
                .stream()
                .toArray(Method[]::new);
        // @formatter:on
    }

    /**
     * 执行指定对象的全部Get方法并返回每个方法的执行结果
     *
     * @param object 执行Get方法的目标对象
     * @return Get方法执行结果集合
     */
    public static Map<String, Object> invokeObjectGetMethod(Object object) {
        Method[] getMethods = getClassGetMethod(object.getClass());
        Map<String, Object> getMethodValueMap = new HashMap<>();
        Arrays.stream(getMethods).forEach(getMethod -> {
            Object methodResultValue = ReflectionUtils.invokeMethod(getMethod, object);
            getMethodValueMap.put(getMethod.getName(), methodResultValue);
        });
        return getMethodValueMap;
    }

    /**
     * 执行指定对象的Set方法
     *
     * @param object                执行Set方法的目标对象
     * @param setMethodParameterMap Set方法对应的参数值
     */
    public static void invokeObjectSetMethod(Object object, Map<String, Object> setMethodParameterMap) {
        Method[] setMethods = getClassSetMethod(object.getClass());
        if (ObjectUtils.isEmpty(setMethods)) {
            logger.warn("Class: {}, does not define setter methods.", object.getClass().getName());
        }
        Map<String, Method> setMethodMap = Arrays.stream(setMethods).collect(Collectors.toMap(Method::getName, v -> v));
        setMethodParameterMap.forEach((setMethodName, value) -> {
            if (!setMethodMap.containsKey(setMethodName)) {
                logger.warn("The [{}#{}] not defined.", object.getClass().getSimpleName(), setMethodName);
                return;
            }
            Method setMethod = setMethodMap.get(setMethodName);
            Object setMethodParameter = setMethodParameterMap.get(setMethodName);
            if (!ObjectUtils.isEmpty(setMethodParameter)) {
                ReflectionUtils.invokeMethod(setMethod, object, setMethodParameter);
            }
        });
    }
}
