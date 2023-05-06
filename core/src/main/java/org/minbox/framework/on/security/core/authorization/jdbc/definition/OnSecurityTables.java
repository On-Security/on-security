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

import org.minbox.framework.on.security.core.authorization.jdbc.mapper.type.TypeMappers;

/**
 * 数据库全部表定义
 *
 * @author 恒宇少年
 * @since 0.0.8
 */
public interface OnSecurityTables {
    /**
     * The Table Names
     */
    interface TableNames {
        String GLOBAL_DATA_AUTHENTICATION_METHOD = "global_data_authentication_method";
        String GLOBAL_DATA_AUTHORIZATION_GRANT = "global_data_authorization_grant";
        String GLOBAL_DATA_CLIENT_PROTOCOL = "global_data_client_protocol";
        String GLOBAL_DATA_SIGNATURE_ALGORITHM = "global_data_signature_algorithm";
        String SECURITY_ATTRIBUTE = "security_attribute";
        String SECURITY_APPLICATION = "security_application";
        String SECURITY_APPLICATION_AUTHENTICATION = "security_application_authentication";
        String SECURITY_APPLICATION_REDIRECT_URIS = "security_application_redirect_uris";
        String SECURITY_APPLICATION_SCOPE = "security_application_scope";
        String SECURITY_APPLICATION_SECRET = "security_application_secret";
        String SECURITY_CONSOLE_MANAGE_TOKEN = "security_console_manage_token";
        String SECURITY_CONSOLE_MANAGER = "security_console_manager";
        String SECURITY_CONSOLE_MANAGER_AUTHORIZE_MENUS = "security_console_manager_authorize_menus";
        String SECURITY_CONSOLE_MENU = "security_console_menu";
        String SECURITY_GROUP = "security_group";
        String SECURITY_GROUP_AUTHORIZE_APPLICATIONS = "security_group_authorize_applications";
        String SECURITY_GROUP_AUTHORIZE_ROLES = "security_group_authorize_roles";
        String SECURITY_IDENTITY_PROVIDER = "security_identity_provider";
        String SECURITY_IDENTITY_PROVIDER_SCOPES = "security_identity_provider_scopes";
        String SECURITY_REGION = "security_region";
        String SECURITY_REGION_IDENTITY_PROVIDER = "security_region_identity_provider";
        String SECURITY_REGION_SECRETS = "security_region_secrets";
        String SECURITY_RESOURCE = "security_resource";
        String SECURITY_RESOURCE_AUTHORIZE_ATTRIBUTES = "security_resource_authorize_attributes";
        String SECURITY_RESOURCE_URIS = "security_resource_uris";
        String SECURITY_ROLE = "security_role";
        String SECURITY_ROLE_AUTHORIZE_RESOURCES = "security_role_authorize_resources";
        String SECURITY_SESSION = "security_session";
        String SECURITY_USER = "security_user";
        String SECURITY_USER_AUTHORIZE_APPLICATIONS = "security_user_authorize_applications";
        String SECURITY_USER_AUTHORIZE_ATTRIBUTES = "security_user_authorize_attributes";
        String SECURITY_USER_AUTHORIZE_CONSENTS = "security_user_authorize_consents";
        String SECURITY_USER_AUTHORIZE_ROLES = "security_user_authorize_roles";
        String SECURITY_USER_GROUPS = "security_user_groups";
        String SECURITY_USER_LOGIN_LOG = "security_user_login_log";
        String SECURITY_CONSOLE_MANAGER_SESSION = "security_console_manager_session";
    }


    //--------------------------------------------Table Definition---------------------------------------------//

    /**
     * {@link TableNames#SECURITY_APPLICATION}
     */
    Table SecurityApplication = Table
            .withTableName(TableNames.SECURITY_APPLICATION)
            .columns(
                    TableColumn.withColumnName(OnSecurityColumnName.Id).primaryKey(),
                    TableColumn.withColumnName(OnSecurityColumnName.ApplicationId),
                    TableColumn.withColumnName(OnSecurityColumnName.RegionId),
                    TableColumn.withColumnName(OnSecurityColumnName.ProtocolId).typeMapper(TypeMappers.CLIENT_PROTOCOL),
                    TableColumn.withColumnName(OnSecurityColumnName.DisplayName),
                    TableColumn.withColumnName(OnSecurityColumnName.Describe),
                    TableColumn.withColumnName(OnSecurityColumnName.Enabled).booleanValue(),
                    TableColumn.withColumnName(OnSecurityColumnName.Deleted).booleanValue(),
                    TableColumn.withColumnName(OnSecurityColumnName.CreateTime).localDateTimeValue().notOperate()
            );
    /**
     * {@link TableNames#SECURITY_APPLICATION_AUTHENTICATION}
     */
    Table SecurityApplicationAuthentication = Table
            .withTableName(TableNames.SECURITY_APPLICATION_AUTHENTICATION)
            .columns(
                    TableColumn.withColumnName(OnSecurityColumnName.Id).primaryKey(),
                    TableColumn.withColumnName(OnSecurityColumnName.ApplicationId),
                    TableColumn.withColumnName(OnSecurityColumnName.Confidential).booleanValue(),
                    TableColumn.withColumnName(OnSecurityColumnName.AuthenticationMethods).typeMapper(TypeMappers.CLIENT_AUTHENTICATION_METHOD_SET),
                    TableColumn.withColumnName(OnSecurityColumnName.AuthenticationSigningAlgorithm).typeMapper(TypeMappers.SIGNATURE_ALGORITHM),
                    TableColumn.withColumnName(OnSecurityColumnName.AuthorizationGrantTypes).typeMapper(TypeMappers.AUTHORIZATION_GRANT_TYPE_SET),
                    TableColumn.withColumnName(OnSecurityColumnName.ConsentRequired).booleanValue(),
                    TableColumn.withColumnName(OnSecurityColumnName.IdTokenSignatureAlgorithm).typeMapper(TypeMappers.SIGNATURE_ALGORITHM),
                    TableColumn.withColumnName(OnSecurityColumnName.AuthorizationCodeExpirationTime).intValue(),
                    TableColumn.withColumnName(OnSecurityColumnName.AccessTokenFormat).typeMapper(TypeMappers.OAUTH2_TOKEN_FORMAT),
                    TableColumn.withColumnName(OnSecurityColumnName.AccessTokenExpirationTime).intValue(),
                    TableColumn.withColumnName(OnSecurityColumnName.RefreshTokenExpirationTime).intValue(),
                    TableColumn.withColumnName(OnSecurityColumnName.ReuseRefreshToken).booleanValue(),
                    TableColumn.withColumnName(OnSecurityColumnName.CreateTime).localDateTimeValue().notOperate()
            );
    /**
     * {@link TableNames#SECURITY_APPLICATION_REDIRECT_URIS}
     */
    Table SecurityApplicationRedirectUris = Table
            .withTableName(TableNames.SECURITY_APPLICATION_REDIRECT_URIS)
            .columns(
                    TableColumn.withColumnName(OnSecurityColumnName.Id).primaryKey(),
                    TableColumn.withColumnName(OnSecurityColumnName.ApplicationId),
                    TableColumn.withColumnName(OnSecurityColumnName.RedirectType).typeMapper(TypeMappers.CLIENT_REDIRECT_URI_TYPE),
                    TableColumn.withColumnName(OnSecurityColumnName.RedirectUri),
                    TableColumn.withColumnName(OnSecurityColumnName.CreateTime).localDateTimeValue().notOperate()
            );
    /**
     * {@link TableNames#SECURITY_APPLICATION_SCOPE}
     */
    Table SecurityApplicationScope = Table
            .withTableName(TableNames.SECURITY_APPLICATION_SCOPE)
            .columns(
                    TableColumn.withColumnName(OnSecurityColumnName.Id).primaryKey(),
                    TableColumn.withColumnName(OnSecurityColumnName.ApplicationId),
                    TableColumn.withColumnName(OnSecurityColumnName.ScopeName),
                    TableColumn.withColumnName(OnSecurityColumnName.ScopeCode),
                    TableColumn.withColumnName(OnSecurityColumnName.Type).typeMapper(TypeMappers.CLIENT_SCOPE_TYPE),
                    TableColumn.withColumnName(OnSecurityColumnName.CreateTime).localDateTimeValue().notOperate()
            );
    /**
     * {@link TableNames#SECURITY_APPLICATION_SECRET}
     */
    Table SecurityApplicationSecret = Table
            .withTableName(TableNames.SECURITY_APPLICATION_SECRET)
            .columns(
                    TableColumn.withColumnName(OnSecurityColumnName.Id).primaryKey(),
                    TableColumn.withColumnName(OnSecurityColumnName.ApplicationId),
                    TableColumn.withColumnName(OnSecurityColumnName.ApplicationSecret),
                    TableColumn.withColumnName(OnSecurityColumnName.SecretExpiresAt).localDateTimeValue(),
                    TableColumn.withColumnName(OnSecurityColumnName.Deleted).booleanValue(),
                    TableColumn.withColumnName(OnSecurityColumnName.CreateTime).localDateTimeValue().notOperate(),
                    TableColumn.withColumnName(OnSecurityColumnName.DeleteTime).localDateTimeValue().insertable(false)
            );
    /**
     * {@link TableNames#SECURITY_ATTRIBUTE}
     */
    Table SecurityAttribute = Table
            .withTableName(TableNames.SECURITY_ATTRIBUTE)
            .columns(
                    TableColumn.withColumnName(OnSecurityColumnName.Id).primaryKey(),
                    TableColumn.withColumnName(OnSecurityColumnName.RegionId),
                    TableColumn.withColumnName(OnSecurityColumnName.Key),
                    TableColumn.withColumnName(OnSecurityColumnName.Value),
                    TableColumn.withColumnName(OnSecurityColumnName.CreateTime).localDateTimeValue().notOperate(),
                    TableColumn.withColumnName(OnSecurityColumnName.Mark),
                    TableColumn.withColumnName(OnSecurityColumnName.Deleted).booleanValue()
            );

    /**
     * {@link TableNames#SECURITY_CONSOLE_MANAGE_TOKEN}
     */
    Table SecurityConsoleManageToken = Table
            .withTableName(TableNames.SECURITY_CONSOLE_MANAGE_TOKEN)
            .columns(
                    TableColumn.withColumnName(OnSecurityColumnName.Id).primaryKey(),
                    TableColumn.withColumnName(OnSecurityColumnName.RegionId),
                    TableColumn.withColumnName(OnSecurityColumnName.ManagerId),
                    TableColumn.withColumnName(OnSecurityColumnName.ManageTokenValue),
                    TableColumn.withColumnName(OnSecurityColumnName.ManageTokenIssuedAt).localDateTimeValue(),
                    TableColumn.withColumnName(OnSecurityColumnName.ManageTokenExpiresAt).localDateTimeValue(),
                    TableColumn.withColumnName(OnSecurityColumnName.ManageTokenMetadata)
            );

    /**
     * {@link TableNames#SECURITY_CONSOLE_MANAGER}
     */
    Table SecurityConsoleManager = Table
            .withTableName(TableNames.SECURITY_CONSOLE_MANAGER)
            .columns(
                    TableColumn.withColumnName(OnSecurityColumnName.Id).primaryKey(),
                    TableColumn.withColumnName(OnSecurityColumnName.RegionId),
                    TableColumn.withColumnName(OnSecurityColumnName.Username),
                    TableColumn.withColumnName(OnSecurityColumnName.Password),
                    TableColumn.withColumnName(OnSecurityColumnName.Avatar),
                    TableColumn.withColumnName(OnSecurityColumnName.Internal).booleanValue(),
                    TableColumn.withColumnName(OnSecurityColumnName.Enabled).booleanValue(),
                    TableColumn.withColumnName(OnSecurityColumnName.Deleted).booleanValue(),
                    TableColumn.withColumnName(OnSecurityColumnName.LastLoginTime).localDateTimeValue().insertable(false),
                    TableColumn.withColumnName(OnSecurityColumnName.Describe),
                    TableColumn.withColumnName(OnSecurityColumnName.CreateTime).localDateTimeValue().notOperate(),
                    TableColumn.withColumnName(OnSecurityColumnName.DeleteTime).localDateTimeValue()
            );
    /**
     * {@link TableNames#SECURITY_CONSOLE_MANAGER_AUTHORIZE_MENUS}
     */
    Table SecurityConsoleManagerAuthorizeMenus = Table
            .withTableName(TableNames.SECURITY_CONSOLE_MANAGER_AUTHORIZE_MENUS)
            .columns(
                    TableColumn.withColumnName(OnSecurityColumnName.Id).primaryKey(),
                    TableColumn.withColumnName(OnSecurityColumnName.RegionId),
                    TableColumn.withColumnName(OnSecurityColumnName.ManagerId),
                    TableColumn.withColumnName(OnSecurityColumnName.MenuId),
                    TableColumn.withColumnName(OnSecurityColumnName.AuthorizeTime).localDateTimeValue()
            );
    /**
     * {@link TableNames#SECURITY_CONSOLE_MENU}
     */
    Table SecurityConsoleMenu = Table
            .withTableName(TableNames.SECURITY_CONSOLE_MENU)
            .columns(
                    TableColumn.withColumnName(OnSecurityColumnName.Id).primaryKey(),
                    TableColumn.withColumnName(OnSecurityColumnName.Name),
                    TableColumn.withColumnName(OnSecurityColumnName.BusinessCode),
                    TableColumn.withColumnName(OnSecurityColumnName.Pid),
                    TableColumn.withColumnName(OnSecurityColumnName.Enabled).booleanValue(),
                    TableColumn.withColumnName(OnSecurityColumnName.Deleted).booleanValue(),
                    TableColumn.withColumnName(OnSecurityColumnName.CreateTime).localDateTimeValue().notOperate(),
                    TableColumn.withColumnName(OnSecurityColumnName.DeleteTime).localDateTimeValue().insertable(false),
                    TableColumn.withColumnName(OnSecurityColumnName.Mark)
            );
    /**
     * {@link TableNames#SECURITY_GROUP}
     */
    Table SecurityGroup = Table
            .withTableName(TableNames.SECURITY_GROUP)
            .columns(
                    TableColumn.withColumnName(OnSecurityColumnName.Id).primaryKey(),
                    TableColumn.withColumnName(OnSecurityColumnName.RegionId),
                    TableColumn.withColumnName(OnSecurityColumnName.Name),
                    TableColumn.withColumnName(OnSecurityColumnName.Describe),
                    TableColumn.withColumnName(OnSecurityColumnName.CreateTime).localDateTimeValue()
            );
    /**
     * {@link TableNames#SECURITY_GROUP_AUTHORIZE_APPLICATIONS}
     */
    Table SecurityGroupAuthorizeApplications = Table
            .withTableName(TableNames.SECURITY_GROUP_AUTHORIZE_APPLICATIONS)
            .columns(
                    TableColumn.withColumnName(OnSecurityColumnName.GroupId).primaryKey(),
                    TableColumn.withColumnName(OnSecurityColumnName.ApplicationId),
                    TableColumn.withColumnName(OnSecurityColumnName.AuthorizeTime).localDateTimeValue().insertable(false)
            );
    /**
     * {@link TableNames#SECURITY_GROUP_AUTHORIZE_ROLES}
     */
    Table SecurityGroupAuthorizeRoles = Table
            .withTableName(TableNames.SECURITY_GROUP_AUTHORIZE_ROLES)
            .columns(
                    TableColumn.withColumnName(OnSecurityColumnName.GroupId).primaryKey(),
                    TableColumn.withColumnName(OnSecurityColumnName.RoleId),
                    TableColumn.withColumnName(OnSecurityColumnName.AuthorizeTime).localDateTimeValue().insertable(false)
            );
    /**
     * {@link TableNames#SECURITY_IDENTITY_PROVIDER}
     */
    Table SecurityIdentityProvider = Table
            .withTableName(TableNames.SECURITY_IDENTITY_PROVIDER)
            .columns(
                    TableColumn.withColumnName(OnSecurityColumnName.Id).primaryKey(),
                    TableColumn.withColumnName(OnSecurityColumnName.RegionId),
                    TableColumn.withColumnName(OnSecurityColumnName.DisplayName),
                    TableColumn.withColumnName(OnSecurityColumnName.ProtocolId),
                    TableColumn.withColumnName(OnSecurityColumnName.Describe),
                    TableColumn.withColumnName(OnSecurityColumnName.IssuerUri),
                    TableColumn.withColumnName(OnSecurityColumnName.AuthorizationUri),
                    TableColumn.withColumnName(OnSecurityColumnName.TokenUri),
                    TableColumn.withColumnName(OnSecurityColumnName.UserInfoUri),
                    TableColumn.withColumnName(OnSecurityColumnName.UserInfoAuthenticationMethod).typeMapper(TypeMappers.AUTHENTICATION_METHOD),
                    TableColumn.withColumnName(OnSecurityColumnName.UserNameAttribute),
                    TableColumn.withColumnName(OnSecurityColumnName.EndSessionUri),
                    TableColumn.withColumnName(OnSecurityColumnName.JwkSetUri),
                    TableColumn.withColumnName(OnSecurityColumnName.ClientAuthenticationMethod).typeMapper(TypeMappers.CLIENT_AUTHENTICATION_METHOD),
                    TableColumn.withColumnName(OnSecurityColumnName.AuthorizationGrantType).typeMapper(TypeMappers.AUTHORIZATION_GRANT_TYPE),
                    TableColumn.withColumnName(OnSecurityColumnName.Enabled).booleanValue(),
                    TableColumn.withColumnName(OnSecurityColumnName.CreateTime).localDateTimeValue().notOperate()
            );
    /**
     * {@link TableNames#SECURITY_IDENTITY_PROVIDER_SCOPES}
     */
    Table SecurityIdentityProviderScopes = Table
            .withTableName(TableNames.SECURITY_IDENTITY_PROVIDER_SCOPES)
            .columns(
                    TableColumn.withColumnName(OnSecurityColumnName.Id).primaryKey(),
                    TableColumn.withColumnName(OnSecurityColumnName.IdpId),
                    TableColumn.withColumnName(OnSecurityColumnName.Pid),
                    TableColumn.withColumnName(OnSecurityColumnName.Name),
                    TableColumn.withColumnName(OnSecurityColumnName.Describe),
                    TableColumn.withColumnName(OnSecurityColumnName.Enabled).booleanValue(),
                    TableColumn.withColumnName(OnSecurityColumnName.RequiredAuthorization).booleanValue()
            );
    /**
     * {@link TableNames#SECURITY_REGION}
     */
    Table SecurityRegion = Table
            .withTableName(TableNames.SECURITY_REGION)
            .columns(
                    TableColumn.withColumnName(OnSecurityColumnName.Id).primaryKey(),
                    TableColumn.withColumnName(OnSecurityColumnName.RegionId),
                    TableColumn.withColumnName(OnSecurityColumnName.DisplayName),
                    TableColumn.withColumnName(OnSecurityColumnName.Enabled).booleanValue(),
                    TableColumn.withColumnName(OnSecurityColumnName.Deleted).booleanValue(),
                    TableColumn.withColumnName(OnSecurityColumnName.CreateTime).localDateTimeValue().notOperate(),
                    TableColumn.withColumnName(OnSecurityColumnName.Describe)
            );
    /**
     * {@link TableNames#SECURITY_REGION_IDENTITY_PROVIDER}
     */
    Table SecurityRegionIdentityProvider = Table
            .withTableName(TableNames.SECURITY_REGION_IDENTITY_PROVIDER)
            .columns(
                    TableColumn.withColumnName(OnSecurityColumnName.Id).primaryKey(),
                    TableColumn.withColumnName(OnSecurityColumnName.RegionId),
                    TableColumn.withColumnName(OnSecurityColumnName.IdpId),
                    TableColumn.withColumnName(OnSecurityColumnName.UniqueIdentifier),
                    TableColumn.withColumnName(OnSecurityColumnName.RegistrationId),
                    TableColumn.withColumnName(OnSecurityColumnName.ApplicationId),
                    TableColumn.withColumnName(OnSecurityColumnName.ApplicationSecret),
                    TableColumn.withColumnName(OnSecurityColumnName.CallbackUrl),
                    TableColumn.withColumnName(OnSecurityColumnName.AuthorizationScopes).typeMapper(TypeMappers.STRING_SET),
                    TableColumn.withColumnName(OnSecurityColumnName.ExpandMetadata).typeMapper(TypeMappers.MAP),
                    TableColumn.withColumnName(OnSecurityColumnName.Describe),
                    TableColumn.withColumnName(OnSecurityColumnName.CreateTime).localDateTimeValue().notOperate()
            );
    /**
     * {@link TableNames#SECURITY_REGION_SECRETS}
     */
    Table SecurityRegionSecrets = Table
            .withTableName(TableNames.SECURITY_REGION_SECRETS)
            .columns(
                    TableColumn.withColumnName(OnSecurityColumnName.Id).primaryKey(),
                    TableColumn.withColumnName(OnSecurityColumnName.RegionId),
                    TableColumn.withColumnName(OnSecurityColumnName.RegionSecret),
                    TableColumn.withColumnName(OnSecurityColumnName.SecretExpiresAt).localDateTimeValue(),
                    TableColumn.withColumnName(OnSecurityColumnName.Deleted).booleanValue(),
                    TableColumn.withColumnName(OnSecurityColumnName.CreateTime).localDateTimeValue().notOperate(),
                    TableColumn.withColumnName(OnSecurityColumnName.DeleteTime).localDateTimeValue()
            );
    /**
     * {@link TableNames#SECURITY_RESOURCE}
     */
    Table SecurityResource = Table
            .withTableName(TableNames.SECURITY_RESOURCE)
            .columns(
                    TableColumn.withColumnName(OnSecurityColumnName.Id).primaryKey(),
                    TableColumn.withColumnName(OnSecurityColumnName.RegionId),
                    TableColumn.withColumnName(OnSecurityColumnName.ApplicationId),
                    TableColumn.withColumnName(OnSecurityColumnName.Name),
                    TableColumn.withColumnName(OnSecurityColumnName.Code),
                    TableColumn.withColumnName(OnSecurityColumnName.Type).typeMapper(TypeMappers.RESOURCE_TYPE),
                    TableColumn.withColumnName(OnSecurityColumnName.Describe),
                    TableColumn.withColumnName(OnSecurityColumnName.Deleted).booleanValue(),
                    TableColumn.withColumnName(OnSecurityColumnName.CreateTime).localDateTimeValue()
            );
    /**
     * {@link TableNames#SECURITY_RESOURCE_AUTHORIZE_ATTRIBUTES}
     */
    Table SecurityResourceAuthorizeAttributes = Table
            .withTableName(TableNames.SECURITY_RESOURCE_AUTHORIZE_ATTRIBUTES)
            .columns(
                    TableColumn.withColumnName(OnSecurityColumnName.Id).primaryKey(),
                    TableColumn.withColumnName(OnSecurityColumnName.RegionId),
                    TableColumn.withColumnName(OnSecurityColumnName.ResourceId),
                    TableColumn.withColumnName(OnSecurityColumnName.AttributeId),
                    TableColumn.withColumnName(OnSecurityColumnName.MatchMethod).typeMapper(TypeMappers.AUTHORIZE_MATCH_METHOD),
                    TableColumn.withColumnName(OnSecurityColumnName.AuthorizeTime).localDateTimeValue().insertable(false)

            );
    /**
     * {@link TableNames#SECURITY_RESOURCE_URIS}
     */
    Table SecurityResourceUris = Table
            .withTableName(TableNames.SECURITY_RESOURCE_URIS)
            .columns(
                    TableColumn.withColumnName(OnSecurityColumnName.Id).primaryKey(),
                    TableColumn.withColumnName(OnSecurityColumnName.ResourceId),
                    TableColumn.withColumnName(OnSecurityColumnName.Uri),
                    TableColumn.withColumnName(OnSecurityColumnName.CreateTime).localDateTimeValue().notOperate()
            );
    /**
     * {@link TableNames#SECURITY_ROLE}
     */
    Table SecurityRole = Table
            .withTableName(TableNames.SECURITY_ROLE)
            .columns(
                    TableColumn.withColumnName(OnSecurityColumnName.Id).primaryKey(),
                    TableColumn.withColumnName(OnSecurityColumnName.RegionId),
                    TableColumn.withColumnName(OnSecurityColumnName.ApplicationId),
                    TableColumn.withColumnName(OnSecurityColumnName.Name),
                    TableColumn.withColumnName(OnSecurityColumnName.Code),
                    TableColumn.withColumnName(OnSecurityColumnName.Describe),
                    TableColumn.withColumnName(OnSecurityColumnName.Deleted).booleanValue(),
                    TableColumn.withColumnName(OnSecurityColumnName.CreateTime).localDateTimeValue().notOperate()
            );
    /**
     * {@link TableNames#SECURITY_ROLE_AUTHORIZE_RESOURCES}
     */
    Table SecurityRoleAuthorizeResource = Table
            .withTableName(TableNames.SECURITY_ROLE_AUTHORIZE_RESOURCES)
            .columns(
                    TableColumn.withColumnName(OnSecurityColumnName.Id).primaryKey(),
                    TableColumn.withColumnName(OnSecurityColumnName.RegionId),
                    TableColumn.withColumnName(OnSecurityColumnName.RoleId),
                    TableColumn.withColumnName(OnSecurityColumnName.ResourceId),
                    TableColumn.withColumnName(OnSecurityColumnName.MatchMethod).typeMapper(TypeMappers.AUTHORIZE_MATCH_METHOD),
                    TableColumn.withColumnName(OnSecurityColumnName.AuthorizeTime).localDateTimeValue().insertable(false)
            );
    /**
     * {@link TableNames#SECURITY_SESSION}
     */
    Table SecuritySession = Table
            .withTableName(TableNames.SECURITY_SESSION)
            .columns(
                    TableColumn.withColumnName(OnSecurityColumnName.Id).primaryKey(),
                    TableColumn.withColumnName(OnSecurityColumnName.RegionId),
                    TableColumn.withColumnName(OnSecurityColumnName.ApplicationId),
                    TableColumn.withColumnName(OnSecurityColumnName.UserId),
                    TableColumn.withColumnName(OnSecurityColumnName.Username),
                    TableColumn.withColumnName(OnSecurityColumnName.State),
                    TableColumn.withColumnName(OnSecurityColumnName.SessionState).typeMapper(TypeMappers.SESSION_STATE),
                    TableColumn.withColumnName(OnSecurityColumnName.Attributes).typeMapper(TypeMappers.MAP),
                    TableColumn.withColumnName(OnSecurityColumnName.AuthorizationGrantType).typeMapper(TypeMappers.AUTHORIZATION_GRANT_TYPE),
                    TableColumn.withColumnName(OnSecurityColumnName.AuthorizationScopes).typeMapper(TypeMappers.STRING_SET),
                    TableColumn.withColumnName(OnSecurityColumnName.AuthorizationCodeValue),
                    TableColumn.withColumnName(OnSecurityColumnName.AuthorizationCodeIssuedAt).localDateTimeValue(),
                    TableColumn.withColumnName(OnSecurityColumnName.AuthorizationCodeExpiresAt).localDateTimeValue(),
                    TableColumn.withColumnName(OnSecurityColumnName.AuthorizationCodeMetadata).typeMapper(TypeMappers.MAP),
                    TableColumn.withColumnName(OnSecurityColumnName.AccessTokenValue),
                    TableColumn.withColumnName(OnSecurityColumnName.AccessTokenIssuedAt).localDateTimeValue(),
                    TableColumn.withColumnName(OnSecurityColumnName.AccessTokenExpiresAt).localDateTimeValue(),
                    TableColumn.withColumnName(OnSecurityColumnName.AccessTokenMetadata).typeMapper(TypeMappers.MAP),
                    TableColumn.withColumnName(OnSecurityColumnName.AccessTokenType).typeMapper(TypeMappers.ACCESS_TOKEN_TYPE),
                    TableColumn.withColumnName(OnSecurityColumnName.AccessTokenScopes).typeMapper(TypeMappers.STRING_SET),
                    TableColumn.withColumnName(OnSecurityColumnName.OidcIdTokenValue),
                    TableColumn.withColumnName(OnSecurityColumnName.OidcIdTokenIssuedAt).localDateTimeValue(),
                    TableColumn.withColumnName(OnSecurityColumnName.OidcIdTokenExpiresAt).localDateTimeValue(),
                    TableColumn.withColumnName(OnSecurityColumnName.OidcIdTokenMetadata).typeMapper(TypeMappers.MAP),
                    TableColumn.withColumnName(OnSecurityColumnName.RefreshTokenValue),
                    TableColumn.withColumnName(OnSecurityColumnName.RefreshTokenIssuedAt).localDateTimeValue(),
                    TableColumn.withColumnName(OnSecurityColumnName.RefreshTokenExpiresAt).localDateTimeValue(),
                    TableColumn.withColumnName(OnSecurityColumnName.RefreshTokenMetadata).typeMapper(TypeMappers.MAP),
                    TableColumn.withColumnName(OnSecurityColumnName.CreateTime).localDateTimeValue().notOperate()
            );
    /**
     * {@link TableNames#SECURITY_USER}
     */
    Table SecurityUser = Table
            .withTableName(TableNames.SECURITY_USER)
            .columns(
                    TableColumn.withColumnName(OnSecurityColumnName.Id).primaryKey(),
                    TableColumn.withColumnName(OnSecurityColumnName.RegionId),
                    TableColumn.withColumnName(OnSecurityColumnName.BusinessId),
                    TableColumn.withColumnName(OnSecurityColumnName.Username),
                    TableColumn.withColumnName(OnSecurityColumnName.Password),
                    TableColumn.withColumnName(OnSecurityColumnName.Avatar),
                    TableColumn.withColumnName(OnSecurityColumnName.Email),
                    TableColumn.withColumnName(OnSecurityColumnName.Phone),
                    TableColumn.withColumnName(OnSecurityColumnName.Name),
                    TableColumn.withColumnName(OnSecurityColumnName.Nickname),
                    TableColumn.withColumnName(OnSecurityColumnName.Birthday).typeMapper(TypeMappers.LOCAL_DATE),
                    TableColumn.withColumnName(OnSecurityColumnName.Gender).typeMapper(TypeMappers.USER_GENDER),
                    TableColumn.withColumnName(OnSecurityColumnName.ZipCode),
                    TableColumn.withColumnName(OnSecurityColumnName.Enabled).booleanValue(),
                    TableColumn.withColumnName(OnSecurityColumnName.Deleted).booleanValue(),
                    TableColumn.withColumnName(OnSecurityColumnName.Describe),
                    TableColumn.withColumnName(OnSecurityColumnName.Expand).typeMapper(TypeMappers.MAP),
                    TableColumn.withColumnName(OnSecurityColumnName.CreateTime).localDateTimeValue().notOperate()
            );
    /**
     * {@link TableNames#SECURITY_USER_AUTHORIZE_APPLICATIONS}
     */
    Table SecurityUserAuthorizeApplications = Table
            .withTableName(TableNames.SECURITY_USER_AUTHORIZE_APPLICATIONS)
            .columns(
                    TableColumn.withColumnName(OnSecurityColumnName.UserId).primaryKey(),
                    TableColumn.withColumnName(OnSecurityColumnName.ApplicationId),
                    TableColumn.withColumnName(OnSecurityColumnName.AuthorizeTime).localDateTimeValue().insertable(false)
            );
    /**
     * {@link TableNames#SECURITY_USER_AUTHORIZE_ATTRIBUTES}
     */
    Table SecurityUserAuthorizeAttributes = Table
            .withTableName(TableNames.SECURITY_USER_AUTHORIZE_ATTRIBUTES)
            .columns(
                    TableColumn.withColumnName(OnSecurityColumnName.UserId).primaryKey(),
                    TableColumn.withColumnName(OnSecurityColumnName.AttributeId),
                    TableColumn.withColumnName(OnSecurityColumnName.AuthorizeTime).localDateTimeValue().insertable(false)
            );
    /**
     * {@link TableNames#SECURITY_USER_AUTHORIZE_CONSENTS}
     */
    Table SecurityUserAuthorizeConsents = Table
            .withTableName(TableNames.SECURITY_USER_AUTHORIZE_CONSENTS)
            .columns(
                    TableColumn.withColumnName(OnSecurityColumnName.UserId).primaryKey(),
                    TableColumn.withColumnName(OnSecurityColumnName.Username),
                    TableColumn.withColumnName(OnSecurityColumnName.ApplicationId),
                    TableColumn.withColumnName(OnSecurityColumnName.AttributeId),
                    TableColumn.withColumnName(OnSecurityColumnName.Authorities),
                    TableColumn.withColumnName(OnSecurityColumnName.AuthorizeTime).localDateTimeValue().insertable(false)
            );
    /**
     * {@link TableNames#SECURITY_USER_AUTHORIZE_ROLES}
     */
    Table SecurityUserAuthorizeRoles = Table
            .withTableName(TableNames.SECURITY_USER_AUTHORIZE_ROLES)
            .columns(
                    TableColumn.withColumnName(OnSecurityColumnName.UserId).primaryKey(),
                    TableColumn.withColumnName(OnSecurityColumnName.RoleId),
                    TableColumn.withColumnName(OnSecurityColumnName.AuthorizeTime).localDateTimeValue().insertable(false)
            );
    /**
     * {@link TableNames#SECURITY_USER_GROUPS}
     */
    Table SecurityUserGroups = Table
            .withTableName(TableNames.SECURITY_USER_GROUPS)
            .columns(
                    TableColumn.withColumnName(OnSecurityColumnName.UserId).primaryKey(),
                    TableColumn.withColumnName(OnSecurityColumnName.GroupId),
                    TableColumn.withColumnName(OnSecurityColumnName.BindTime).localDateTimeValue().insertable(false)
            );
    /**
     * {@link TableNames#SECURITY_USER_LOGIN_LOG}
     */
    Table SecurityUserLoginLog = Table
            .withTableName(TableNames.SECURITY_USER_LOGIN_LOG)
            .columns(
                    TableColumn.withColumnName(OnSecurityColumnName.Id).primaryKey(),
                    TableColumn.withColumnName(OnSecurityColumnName.RegionId),
                    TableColumn.withColumnName(OnSecurityColumnName.ApplicationId),
                    TableColumn.withColumnName(OnSecurityColumnName.UserId),
                    TableColumn.withColumnName(OnSecurityColumnName.UserGroupId),
                    TableColumn.withColumnName(OnSecurityColumnName.SessionId),
                    TableColumn.withColumnName(OnSecurityColumnName.LoginTime).localDateTimeValue(),
                    TableColumn.withColumnName(OnSecurityColumnName.IpAddress),
                    TableColumn.withColumnName(OnSecurityColumnName.DeviceSystem),
                    TableColumn.withColumnName(OnSecurityColumnName.Browser),
                    TableColumn.withColumnName(OnSecurityColumnName.Country),
                    TableColumn.withColumnName(OnSecurityColumnName.Province),
                    TableColumn.withColumnName(OnSecurityColumnName.City)
            );
    /**
     * {@link TableNames#SECURITY_CONSOLE_MANAGER_SESSION}
     */
    Table SecurityConsoleManagerSession = Table
            .withTableName(TableNames.SECURITY_CONSOLE_MANAGER_SESSION)
            .columns(
                    TableColumn.withColumnName(OnSecurityColumnName.Id).primaryKey(),
                    TableColumn.withColumnName(OnSecurityColumnName.RegionId),
                    TableColumn.withColumnName(OnSecurityColumnName.AuthenticateType),
                    TableColumn.withColumnName(OnSecurityColumnName.ManagerId),
                    TableColumn.withColumnName(OnSecurityColumnName.RegionSecretId),
                    TableColumn.withColumnName(OnSecurityColumnName.ManageTokenValue),
                    TableColumn.withColumnName(OnSecurityColumnName.ManageTokenIssuedAt).localDateTimeValue(),
                    TableColumn.withColumnName(OnSecurityColumnName.ManageTokenExpiresAt).localDateTimeValue()
            );
}
