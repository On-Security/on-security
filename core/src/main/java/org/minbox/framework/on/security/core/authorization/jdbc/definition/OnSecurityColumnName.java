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

package org.minbox.framework.on.security.core.authorization.jdbc.definition;

import org.minbox.framework.on.security.core.authorization.util.StringUtils;

import java.sql.Types;
import java.util.Arrays;
import java.util.Optional;

/**
 * On-Security在数据库中定义的全部列
 *
 * @author 恒宇少年
 * @see Types
 * @since 0.0.8
 */
public enum OnSecurityColumnName {
    Id("id"),
    RegionId("region_id"),
    Username("username"),
    Password("password"),
    Internal("internal"),
    Enabled("enabled"),
    Deleted("deleted"),
    LastLoginTime("last_login_time"),
    Describe("`describe`"),
    CreateTime("create_time"),
    DeleteTime("delete_time"),
    ApplicationId("application_id"),
    Name("name"),
    Code("code"),
    RoleId("role_id"),
    ResourceId("resource_id"),
    MatchMethod("match_method"),
    AuthorizeTime("authorize_time"),
    Key("`key`"),
    Value("`value`"),
    Mark("mark"),
    ProtocolId("protocol_id"),
    DisplayName("display_name"),
    Confidential("confidential"),
    JwksUrl("jwks_url"),
    AuthenticationMethods("authentication_methods"),
    AuthenticationSigningAlgorithm("authentication_signing_algorithm"),
    AuthorizationGrantTypes("authorization_grant_types"),
    ConsentRequired("consent_required"),
    IdTokenSignatureAlgorithm("id_token_signature_algorithm"),
    AuthorizationCodeExpirationTime("authorization_code_expiration_time"),
    AccessTokenFormat("access_token_format"),
    AccessTokenExpirationTime("access_token_expiration_time"),
    RefreshTokenExpirationTime("refresh_token_expiration_time"),
    ReuseRefreshToken("reuse_refresh_token"),
    RedirectType("redirect_type"),
    RedirectUri("redirect_uri"),
    ScopeName("scope_name"),
    ScopeCode("scope_code"),
    Type("type"),
    ApplicationSecret("application_secret"),
    SecretExpiresAt("secret_expires_at"),
    ManagerId("manager_id"),
    ManageTokenValue("manage_token_value"),
    ManageTokenIssuedAt("manage_token_issued_at"),
    ManageTokenExpiresAt("manage_token_expires_at"),
    ManageTokenMetadata("manage_token_metadata"),
    AuthenticateType("authenticate_type"),
    RegionSecretId("region_secret_id"),
    MenuId("menu_id"),
    BusinessCode("business_code"),
    Pid("pid"),
    GroupId("group_id"),
    IssuerUri("issuer_uri"),
    AuthorizationUri("authorization_uri"),
    TokenUri("token_uri"),
    UserInfoUri("user_info_uri"),
    UserInfoAuthenticationMethod("user_info_authentication_method"),
    UserNameAttribute("user_name_attribute"),
    EndSessionUri("end_session_uri"),
    JwkSetUri("jwk_set_uri"),
    ClientAuthenticationMethod("client_authentication_method"),
    AuthorizationGrantType("authorization_grant_type"),
    IdpId("idp_id"),
    RequiredAuthorization("required_authorization"),
    UniqueIdentifier("unique_identifier"),
    RegistrationId("registration_id"),
    CallbackUrl("callback_url"),
    AuthorizationScopes("authorization_scopes"),
    ExpandMetadata("expand_metadata"),
    RegionSecret("region_secret"),
    AttributeId("attribute_id"),
    Uri("uri"),
    UserId("user_id"),
    State("state"),
    SessionState("session_state"),
    Attributes("attributes"),
    AuthorizationCodeValue("authorization_code_value"),
    AuthorizationCodeIssuedAt("authorization_code_issued_at"),
    AuthorizationCodeExpiresAt("authorization_code_expires_at"),
    AuthorizationCodeMetadata("authorization_code_metadata"),
    AccessTokenValue("access_token_value"),
    AccessTokenIssuedAt("access_token_issued_at"),
    AccessTokenExpiresAt("access_token_expires_at"),
    AccessTokenMetadata("access_token_metadata"),
    AccessTokenType("access_token_type"),
    AccessTokenScopes("access_token_scopes"),
    OidcIdTokenValue("oidc_id_token_value"),
    OidcIdTokenIssuedAt("oidc_id_token_issued_at"),
    OidcIdTokenExpiresAt("oidc_id_token_expires_at"),
    OidcIdTokenMetadata("oidc_id_token_metadata"),
    RefreshTokenValue("refresh_token_value"),
    RefreshTokenIssuedAt("refresh_token_issued_at"),
    RefreshTokenExpiresAt("refresh_token_expires_at"),
    RefreshTokenMetadata("refresh_token_metadata"),
    BusinessId("business_id"),
    Email("email"),
    Phone("phone"),
    Nickname("nickname"),
    Birthday("birthday"),
    Gender("gender"),
    ZipCode("zip_code"),
    Authorities("authorities"),
    BindTime("bind_time"),
    UserGroupId("user_group_id"),
    SessionId("session_id"),
    LoginTime("login_time"),
    IpAddress("ip_address"),
    DeviceSystem("device_system"),
    Browser("browser"),
    Country("country"),
    Province("province"),
    City("city"),
    Expand("expand"),
    Avatar("avatar");
    private String name;

    OnSecurityColumnName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * 根据列名获取{@link OnSecurityColumnName}枚举实例
     *
     * @param columnName 列名
     * @return {@link OnSecurityColumnName}
     */
    public static OnSecurityColumnName valueOfColumnName(String columnName) {
        OnSecurityColumnName[] columnArray = OnSecurityColumnName.values();
        Optional<OnSecurityColumnName> optional = Arrays.stream(columnArray)
                .filter(onSecurityColumn -> {
                    String keywordFormat = String.format("`%s`", columnName);
                    return onSecurityColumn.getName().equals(columnName) || onSecurityColumn.getName().equals(keywordFormat);
                })
                .findFirst();
        return optional.isPresent() ? optional.get() : null;
    }

    public String getUpperCamelName() {
        return StringUtils.toUpperCamelName(this.name.replace("`", ""));
    }

    public String getLowerCamelName() {
        return StringUtils.toLowerCamelName(this.name);
    }
}
