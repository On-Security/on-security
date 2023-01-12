package org.minbox.framework.on.security.core.authorization.jackson2;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 封装Jackson序列化与反序列化映射类
 * <p>
 * 默认注册{@link OnSecuritySerializableModule}序列化模式
 *
 * @author 恒宇少年
 * @see OnSecuritySerializableModule
 * @see ObjectMapper
 * @since 0.0.6
 */
public final class OnSecurityJsonMapper extends ObjectMapper {
    public OnSecurityJsonMapper() {
        super();
        this.registerModule(new OnSecuritySerializableModule());
    }
}
