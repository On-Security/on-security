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

package org.minbox.framework.on.security.core.authorization.jackson2.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import org.minbox.framework.on.security.core.authorization.adapter.OnSecurityUserDetails;
import org.springframework.security.core.GrantedAuthority;

import java.io.IOException;
import java.util.Set;

/**
 * {@link OnSecurityUserDetails} Jackson反序列化定义
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class OnSecurityUserDetailsDeserializer extends JsonDeserializer<OnSecurityUserDetails> {

    // @formatter:off
    private static final TypeReference<Set<GrantedAuthority>> GRANTED_AUTHORITY_LIST = new TypeReference<Set<GrantedAuthority>>() {};
    // @formatter:on
    @Override
    public OnSecurityUserDetails deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
        JsonNode jsonNode = mapper.readTree(jsonParser);
        String userId = readJsonNode(jsonNode, "userId").asText();
        String regionId = readJsonNode(jsonNode, "regionId").asText();
        String username = readJsonNode(jsonNode, "username").asText();
        String password = readJsonNode(jsonNode, "password").asText();
        Set<GrantedAuthority> authorities = mapper.readValue(readJsonNode(jsonNode, "authorities").traverse(mapper),
                GRANTED_AUTHORITY_LIST);
        return new OnSecurityUserDetails(userId, regionId, username, password, authorities);
    }

    private JsonNode readJsonNode(JsonNode jsonNode, String field) {
        return jsonNode.has(field) ? jsonNode.get(field) : MissingNode.getInstance();
    }
}
