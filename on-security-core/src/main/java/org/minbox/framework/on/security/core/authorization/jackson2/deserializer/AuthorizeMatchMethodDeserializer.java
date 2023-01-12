package org.minbox.framework.on.security.core.authorization.jackson2.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.minbox.framework.on.security.core.authorization.AuthorizeMatchMethod;

import java.io.IOException;

/**
 * {@link AuthorizeMatchMethod}Jackson自定义反序列化
 *
 * @author 恒宇少年
 * @since 0.0.6
 */
public class AuthorizeMatchMethodDeserializer extends JsonDeserializer<AuthorizeMatchMethod> {
    @Override
    public AuthorizeMatchMethod deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException, JacksonException {
        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
        JsonNode jsonNode = mapper.readTree(jsonParser);
        String value = jsonNode.asText();
        return new AuthorizeMatchMethod(value);
    }
}
