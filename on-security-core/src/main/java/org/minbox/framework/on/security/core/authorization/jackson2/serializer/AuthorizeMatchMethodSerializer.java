package org.minbox.framework.on.security.core.authorization.jackson2.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.minbox.framework.on.security.core.authorization.AuthorizeMatchMethod;

import java.io.IOException;

/**
 * {@link AuthorizeMatchMethod}Jackson自定义序列化
 *
 * @author 恒宇少年
 * @since 0.0.6
 */
public class AuthorizeMatchMethodSerializer extends JsonSerializer<AuthorizeMatchMethod> {
    @Override
    public void serialize(AuthorizeMatchMethod authorizeMatchMethod, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(authorizeMatchMethod.getValue());
    }
}
