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

import org.minbox.framework.on.security.core.authorization.*;
import org.minbox.framework.on.security.core.authorization.jdbc.mapper.type.support.*;
import org.springframework.security.oauth2.core.AuthenticationMethod;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;

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
    public static final StringTypeMapper STRING = new StringTypeMapper();
    /**
     * {@link Types#VARCHAR}转换字符串Set集合列值映射器
     */
    public static final StringSetTypeMapper STRING_SET = new StringSetTypeMapper();
    /**
     * {@link Types#BOOLEAN}类型列值映射器
     */
    public static final BooleanTypeMapper BOOLEAN = new BooleanTypeMapper();
    /**
     * {@link Types#INTEGER}类型列值映射器
     */
    public static final IntegerTypeMapper INTEGER = new IntegerTypeMapper();
    /**
     * 将{@link Types#TIMESTAMP}类型列值转换为{@link java.time.LocalDateTime}的映射器
     */
    public static final LocalDateTimeTypeMapper LOCAL_DATE_TIME = new LocalDateTimeTypeMapper();
    /**
     * 将{@link Types#DATE}类型列值转换为{@link java.time.LocalDate}的映射器
     */
    public static final LocalDateTypeMapper LOCAL_DATE = new LocalDateTypeMapper();
    /**
     * {@link AuthorizeMatchMethod}类型列值转换映射器
     */
    public static final AuthorizeMatchMethodTypeMapper AUTHORIZE_MATCH_METHOD = new AuthorizeMatchMethodTypeMapper();
    /**
     * {@link SignatureAlgorithm}列值转换器
     */
    public static final SignatureAlgorithmTypeMapper SIGNATURE_ALGORITHM = new SignatureAlgorithmTypeMapper();
    /**
     * {@link ClientAuthenticationMethod}Set集合列值转换器
     */
    public static final ClientAuthenticationMethodSetTypeMapper CLIENT_AUTHENTICATION_METHOD_SET = new ClientAuthenticationMethodSetTypeMapper();
    /**
     * {@link ClientAuthenticationMethod}列值类型映射器
     */
    public static final ClientAuthenticationMethodTypeMapper CLIENT_AUTHENTICATION_METHOD = new ClientAuthenticationMethodTypeMapper();
    /**
     * {@link AuthorizationGrantType}Set集合列值转换器
     */
    public static final AuthorizationGrantTypeSetMapper AUTHORIZATION_GRANT_TYPE_SET = new AuthorizationGrantTypeSetMapper();
    /**
     * {@link AuthorizationGrantType}列值类型映射器
     */
    public static final AuthorizationGrantTypeMapper AUTHORIZATION_GRANT_TYPE = new AuthorizationGrantTypeMapper();
    /**
     * {@link OAuth2TokenFormat}列值转换映射器
     */
    public static final OAuth2TokenFormatTypeMapper OAUTH2_TOKEN_FORMAT = new OAuth2TokenFormatTypeMapper();
    /**
     * {@link ClientRedirectUriType}列值转换映射器
     */
    public static final ClientRedirectUriTypeMapper CLIENT_REDIRECT_URI_TYPE = new ClientRedirectUriTypeMapper();
    /**
     * {@link ClientScopeType}列值转换映射器
     */
    public static final ClientScopeTypeMapper CLIENT_SCOPE_TYPE = new ClientScopeTypeMapper();
    /**
     * {@link AuthenticationMethod}列值类型映射器
     */
    public static final AuthenticationMethodTypeMapper AUTHENTICATION_METHOD = new AuthenticationMethodTypeMapper();
    /**
     * Map集合列值类型映射器
     */
    public static final MapTypeMapper MAP = new MapTypeMapper();
    /**
     * {@link ResourceType}列值类型映射器
     */
    public static final ResourceTypeMapper RESOURCE_TYPE = new ResourceTypeMapper();
    /**
     * {@link SessionState}列值类型映射器
     */
    public static final SessionStateTypeMapper SESSION_STATE = new SessionStateTypeMapper();
    /**
     * {@link AccessTokenType}列值类型映射器
     */
    public static final AccessTokenTypeMapper ACCESS_TOKEN_TYPE = new AccessTokenTypeMapper();
    /**
     * {@link UserGender}列值类型映射器
     */
    public static final UserGenderTypeMapper USER_GENDER = new UserGenderTypeMapper();
}
