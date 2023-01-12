package org.minbox.framework.on.security.core.authorization.jackson2.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.minbox.framework.on.security.core.authorization.SessionState;

import java.io.IOException;

/**
 * {@link SessionState}Jackson自定义序列化
 *
 * @author 恒宇少年
 * @since 0.0.6
 */
public class SessionStateSerializer extends JsonSerializer<SessionState> {
    @Override
    public void serialize(SessionState sessionState, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(sessionState.getValue());
    }
}
