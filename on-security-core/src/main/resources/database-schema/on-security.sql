-- MySQL dump 10.13  Distrib 8.0.30, for macos12.5 (arm64)
--
-- Host: 127.0.0.1    Database: on_security
-- ------------------------------------------------------
-- Server version	5.5.5-10.9.3-MariaDB-1:10.9.3+maria~ubu2204

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `gloabl_data_authorization_grant`
--

DROP TABLE IF EXISTS `gloabl_data_authorization_grant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gloabl_data_authorization_grant` (
                                                   `id` varchar(36) NOT NULL COMMENT '授权类型ID',
                                                   `name` varchar(10) NOT NULL COMMENT '授权类型名称',
                                                   `code` varchar(30) DEFAULT NULL COMMENT '授权类型Code',
                                                   `describe` varchar(100) DEFAULT NULL COMMENT '描述',
                                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户端认证授权类型';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `global_data_authentication_method`
--

DROP TABLE IF EXISTS `global_data_authentication_method`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `global_data_authentication_method` (
                                                     `id` varchar(36) NOT NULL COMMENT 'ID',
                                                     `code` varchar(30) NOT NULL COMMENT '认证方式code',
                                                     `describe` varchar(100) DEFAULT NULL COMMENT '描述',
                                                     PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户端认证方式';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `global_data_client_protocol`
--

DROP TABLE IF EXISTS `global_data_client_protocol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `global_data_client_protocol` (
                                               `id` varchar(36) NOT NULL COMMENT '协议编号',
                                               `name` varchar(20) NOT NULL COMMENT '协议名称',
                                               `enabled` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否启用',
                                               `describe` varchar(100) DEFAULT NULL COMMENT '描述',
                                               PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户端协议';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `global_data_signature_algorithm`
--

DROP TABLE IF EXISTS `global_data_signature_algorithm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `global_data_signature_algorithm` (
                                                   `id` varchar(36) NOT NULL COMMENT 'ID',
                                                   `algorithm` varchar(10) NOT NULL COMMENT '算法',
                                                   `describe` varchar(100) DEFAULT NULL COMMENT '描述',
                                                   PRIMARY KEY (`id`),
                                                   UNIQUE KEY `global_data_signature_algorithm_algorithm_uindex` (`algorithm`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='签名算法';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `security_client`
--

DROP TABLE IF EXISTS `security_client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_client` (
                                   `id` varchar(36) NOT NULL COMMENT 'ID',
                                   `client_id` varchar(20) NOT NULL COMMENT '客户端ID',
                                   `region_id` varchar(36) NOT NULL COMMENT '所属安全域',
                                   `protocol_id` varchar(36) NOT NULL COMMENT '协议编号',
                                   `display_name` varchar(20) DEFAULT NULL COMMENT '显示名称',
                                   `describe` varchar(100) DEFAULT NULL COMMENT '描述',
                                   `enabled` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否启用',
                                   `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                   `create_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
                                   PRIMARY KEY (`id`),
                                   UNIQUE KEY `security_client_client_id_uindex` (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户端基本信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `security_client_authentication`
--

DROP TABLE IF EXISTS `security_client_authentication`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_client_authentication` (
                                                  `id` varchar(36) NOT NULL COMMENT 'ID',
                                                  `client_id` varchar(36) NOT NULL COMMENT '客户端ID',
                                                  `confidential` bit(1) NOT NULL DEFAULT b'0' COMMENT '客户端类型，是否为机密客户端',
                                                  `jwks_url` varchar(100) DEFAULT NULL COMMENT 'JWKS地址',
                                                  `authentication_methods` varchar(100) DEFAULT NULL COMMENT '客户端认证方式，数据取值参考：global_data_authentication_method#code',
                                                  `authentication_signing_algorithm` varchar(10) DEFAULT NULL COMMENT '为 private_key_jwt 和 client_secret_jwt 身份验证方法设置 JWS 算法，数据取值：global_data_signature_algorithm#algorithm',
                                                  `authorization_grant_types` varchar(100) DEFAULT NULL COMMENT '授权类型列表，数据取值参考：gloabl_data_authorization_grant#code',
                                                  `consent_required` bit(1) DEFAULT b'0' COMMENT '是否需要用户授权同意',
                                                  `id_token_signature_algorithm` varchar(10) DEFAULT NULL COMMENT 'id_token签名算法，数据取值：global_data_signature_algorithm#algorithm',
                                                  `authorization_code_expiration_time` int(11) NOT NULL DEFAULT 300 COMMENT '授权码过期时间，单位：秒',
                                                  `access_token_expiration_time` int(11) NOT NULL DEFAULT 300 COMMENT 'access_token过期时间，单位：秒',
                                                  `refresh_token_expiration_time` int(11) NOT NULL DEFAULT 900 COMMENT 'refresh_token过期时间，单位：秒',
                                                  `reuse_refresh_token` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否重用刷新令牌',
                                                  `create_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
                                                  PRIMARY KEY (`id`),
                                                  UNIQUE KEY `security_client_authentication_client_id_uindex` (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户端认证配置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `security_client_redirect_uris`
--

DROP TABLE IF EXISTS `security_client_redirect_uris`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_client_redirect_uris` (
                                                 `id` varchar(36) NOT NULL COMMENT 'ID',
                                                 `client_id` varchar(36) NOT NULL COMMENT '客户端ID',
                                                 `redirect_type` varchar(10) NOT NULL COMMENT '跳转类型，home：主页跳转地址，login：登录回调跳转地址，logout：登出回调跳转地址',
                                                 `redirect_uri` varchar(200) NOT NULL COMMENT '跳转地址',
                                                 `create_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
                                                 PRIMARY KEY (`id`),
                                                 KEY `security_client_redirect_uris_client_id_index` (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户端跳转地址列表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `security_client_scope`
--

DROP TABLE IF EXISTS `security_client_scope`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_client_scope` (
                                         `id` varchar(36) NOT NULL COMMENT 'ID',
                                         `client_id` varchar(36) NOT NULL COMMENT '客户端ID',
                                         `scope_name` varchar(20) DEFAULT NULL COMMENT '范围名称',
                                         `scope_code` varchar(20) DEFAULT NULL COMMENT '范围Code',
                                         `type` varchar(10) DEFAULT NULL COMMENT '范围类型，default：默认，optional：可选',
                                         `create_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
                                         PRIMARY KEY (`id`),
                                         KEY `security_client_scope_client_id_index` (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户端范围';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `security_client_secret`
--

DROP TABLE IF EXISTS `security_client_secret`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_client_secret` (
                                          `id` varchar(36) NOT NULL COMMENT 'ID',
                                          `client_id` varchar(36) NOT NULL COMMENT '客户端ID',
                                          `client_secret` varchar(100) NOT NULL COMMENT '客户端密钥',
                                          `secret_expires_at` datetime NOT NULL COMMENT '密钥过期时间',
                                          `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                          `create_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
                                          `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
                                          PRIMARY KEY (`id`),
                                          KEY `security_client_secret_client_id_index` (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户端密钥';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `security_group`
--

DROP TABLE IF EXISTS `security_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_group` (
                                  `id` varchar(36) NOT NULL COMMENT '用户组ID',
                                  `region_id` varchar(36) NOT NULL COMMENT '安全域ID',
                                  `name` varchar(20) NOT NULL COMMENT '安全组名称',
                                  `describe` varchar(50) DEFAULT NULL COMMENT '描述',
                                  `create_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
                                  PRIMARY KEY (`id`),
                                  KEY `security_user_group_region_id_index` (`region_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='安全组基本信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `security_group_authorize_clients`
--

DROP TABLE IF EXISTS `security_group_authorize_clients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_group_authorize_clients` (
                                                    `group_id` varchar(36) NOT NULL COMMENT '安全组ID',
                                                    `client_id` varchar(36) NOT NULL COMMENT '客户端ID',
                                                    `authorize_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '授权时间',
                                                    PRIMARY KEY (`group_id`,`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='安全组授权客户端列表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `security_group_authorize_roles`
--

DROP TABLE IF EXISTS `security_group_authorize_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_group_authorize_roles` (
                                                  `group_id` varchar(36) NOT NULL COMMENT '安全组ID',
                                                  `role_id` varchar(36) NOT NULL COMMENT '角色ID',
                                                  `authorize_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '授权时间',
                                                  PRIMARY KEY (`group_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='安全组授权角色关系';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `security_permission`
--

DROP TABLE IF EXISTS `security_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_permission` (
                                       `id` varchar(36) NOT NULL COMMENT 'ID',
                                       `region_id` varchar(36) NOT NULL COMMENT '安全域ID',
                                       `client_id` varchar(36) DEFAULT NULL COMMENT '客户端ID，为空时权限所属安全域',
                                       `name` varchar(20) NOT NULL COMMENT '名称',
                                       `match_method` varchar(10) NOT NULL DEFAULT 'allow' COMMENT '匹配方式，allow：允许，reject：拒绝',
                                       `describe` varchar(50) DEFAULT NULL COMMENT '描述',
                                       `create_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
                                       `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                       PRIMARY KEY (`id`),
                                       KEY `security_permission_region_id_index` (`region_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='安全权限基本信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `security_permission_authorize`
--

DROP TABLE IF EXISTS `security_permission_authorize`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_permission_authorize` (
                                                 `id` varchar(36) NOT NULL COMMENT 'ID',
                                                 `permission_id` varchar(36) NOT NULL COMMENT '权限ID',
                                                 `role_id` varchar(36) NOT NULL COMMENT '角色ID',
                                                 `resource_id` varchar(36) NOT NULL COMMENT '资源ID',
                                                 `authorize_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '授权时间',
                                                 PRIMARY KEY (`id`),
                                                 KEY `permission_authorize_role_id_permission_id_resource_id_index` (`role_id`,`permission_id`,`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限授权';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `security_region`
--

DROP TABLE IF EXISTS `security_region`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_region` (
                                   `id` varchar(36) NOT NULL COMMENT 'ID',
                                   `region_id` varchar(30) NOT NULL COMMENT '安全域ID',
                                   `display_name` varchar(15) DEFAULT NULL COMMENT '显示名称',
                                   `enabled` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否启用',
                                   `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                   `create_time` datetime NOT NULL COMMENT '创建时间',
                                   `describe` varchar(100) DEFAULT NULL COMMENT '描述',
                                   PRIMARY KEY (`id`),
                                   UNIQUE KEY `security_region_region_id_uindex` (`region_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='安全域基本信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `security_resource`
--

DROP TABLE IF EXISTS `security_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_resource` (
                                     `id` varchar(36) NOT NULL COMMENT 'ID',
                                     `region_id` varchar(36) NOT NULL COMMENT '安全域ID',
                                     `client_id` varchar(36) DEFAULT NULL COMMENT '客户端ID，为空时资源属于全域',
                                     `name` varchar(20) NOT NULL COMMENT '资源名称',
                                     `code` varchar(20) NOT NULL COMMENT '资源码',
                                     `type` varchar(5) NOT NULL DEFAULT 'api' COMMENT '资源类型，api：Api接口资源',
                                     `describe` varchar(50) DEFAULT NULL COMMENT '描述',
                                     `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                     `create_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
                                     PRIMARY KEY (`id`),
                                     KEY `security_resource_region_id_index` (`region_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资源信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `security_resource_uris`
--

DROP TABLE IF EXISTS `security_resource_uris`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_resource_uris` (
                                          `id` varchar(36) NOT NULL COMMENT 'ID',
                                          `resource_id` varchar(36) NOT NULL COMMENT '资源ID',
                                          `uri` varchar(50) NOT NULL COMMENT '路径URI',
                                          `create_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
                                          PRIMARY KEY (`id`),
                                          KEY `security_resource_uris_resource_id_index` (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资源uri列表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `security_role`
--

DROP TABLE IF EXISTS `security_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_role` (
                                 `id` varchar(36) NOT NULL COMMENT 'ID',
                                 `region_id` varchar(36) NOT NULL COMMENT '安全域ID',
                                 `client_id` varchar(36) DEFAULT NULL COMMENT '客户端ID',
                                 `name` varchar(20) NOT NULL COMMENT '角色名称',
                                 `code` varchar(20) NOT NULL COMMENT '角色码',
                                 `describe` varchar(50) DEFAULT NULL COMMENT '描述',
                                 `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                 `create_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
                                 PRIMARY KEY (`id`),
                                 KEY `security_role_region_id_index` (`region_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='安全角色基本信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `security_session`
--

DROP TABLE IF EXISTS `security_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_session` (
                                    `id` varchar(36) NOT NULL COMMENT 'ID',
                                    `user_id` varchar(36) NOT NULL COMMENT '用户ID',
                                    `region_id` varchar(36) NOT NULL COMMENT '安全域ID',
                                    `client_id` varchar(36) NOT NULL COMMENT '客户端ID',
                                    `state` varchar(15) NOT NULL DEFAULT 'normal' COMMENT '状态，normal：正常，forced_offline：强制下线，active_offline：主动下线',
                                    `attributes` text DEFAULT NULL COMMENT '属性',
                                    `authorization_grant_types` varchar(100) NOT NULL COMMENT '授权类型',
                                    `authorization_scopes` varchar(100) DEFAULT NULL COMMENT '授权范围',
                                    `authorization_code_value` text DEFAULT NULL COMMENT '授权码值',
                                    `authorization_code_issued_at` datetime DEFAULT NULL COMMENT '授权码发布时间',
                                    `authorization_code_expires_at` datetime DEFAULT NULL COMMENT '授权码过期时间',
                                    `authorization_code_metadata` text DEFAULT NULL COMMENT '授权码元数据',
                                    `access_token_value` text DEFAULT NULL COMMENT 'access_token令牌值',
                                    `access_token_issued_at` datetime DEFAULT NULL COMMENT 'access_token发布时间',
                                    `access_token_metadata` text DEFAULT NULL COMMENT 'access_token元数据',
                                    `access_token_type` varchar(20) DEFAULT NULL COMMENT 'access_token类型',
                                    `access_token_scopes` varchar(100) DEFAULT NULL COMMENT 'access_token范围',
                                    `oidc_id_token_value` text DEFAULT NULL COMMENT 'id_token值',
                                    `oidc_id_token_issued_at` datetime DEFAULT NULL COMMENT 'id_token发布时间',
                                    `oidc_id_token_expires_at` datetime DEFAULT NULL COMMENT 'id_token过期时间',
                                    `oidc_id_token_metadata` text DEFAULT NULL COMMENT 'id_token元数据',
                                    `refresh_token_value` text DEFAULT NULL COMMENT '刷新令牌值',
                                    `refresh_token_issued_at` datetime DEFAULT NULL COMMENT '刷新令牌发布时间',
                                    `refresh_token_expires_at` datetime DEFAULT NULL COMMENT '刷新令牌过期时间',
                                    `refresh_token_metadata` text DEFAULT NULL COMMENT '刷新令牌元数据',
                                    `create_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
                                    PRIMARY KEY (`id`),
                                    KEY `security_session_client_id_index` (`client_id`),
                                    KEY `security_session_region_id_index` (`region_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会话信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `security_user`
--

DROP TABLE IF EXISTS `security_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_user` (
                                 `id` varchar(36) NOT NULL COMMENT 'ID',
                                 `region_id` varchar(36) NOT NULL COMMENT '安全域ID',
                                 `business_id` varchar(50) DEFAULT NULL COMMENT '在业务系统的ID',
                                 `username` varchar(30) NOT NULL COMMENT '用户名',
                                 `password` varchar(100) NOT NULL COMMENT '密码',
                                 `email` varchar(30) DEFAULT NULL COMMENT '邮箱地址',
                                 `phone` varchar(11) DEFAULT NULL COMMENT '手机号',
                                 `name` varchar(10) DEFAULT NULL COMMENT '姓名',
                                 `nickname` varchar(20) DEFAULT NULL,
                                 `birthday` date DEFAULT NULL COMMENT '生日',
                                 `gender` varchar(5) DEFAULT NULL COMMENT '性别，man：男，woman：女，other：其他',
                                 `zip_code` varchar(6) DEFAULT NULL COMMENT '邮政编码',
                                 `enabled` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否启用',
                                 `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                 `describe` varchar(50) DEFAULT NULL COMMENT '描述',
                                 `create_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
                                 PRIMARY KEY (`id`),
                                 UNIQUE KEY `security_user_username_uindex` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户基本信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `security_user_authorize_clients`
--

DROP TABLE IF EXISTS `security_user_authorize_clients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_user_authorize_clients` (
                                                   `user_id` varchar(36) NOT NULL COMMENT '用户ID',
                                                   `client_id` varchar(36) NOT NULL COMMENT '客户端ID',
                                                   `authorize_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '授权时间',
                                                   PRIMARY KEY (`user_id`,`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户授权客户端关系';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `security_user_authorize_consent`
--

DROP TABLE IF EXISTS `security_user_authorize_consent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_user_authorize_consent` (
                                                   `id` varchar(36) NOT NULL COMMENT 'ID',
                                                   `user_id` varchar(36) NOT NULL COMMENT '用户ID',
                                                   `client_id` varchar(36) NOT NULL COMMENT '客户端ID',
                                                   `scope_id` varchar(36) NOT NULL COMMENT '同意授权范围ID',
                                                   `authorize_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '授权时间',
                                                   PRIMARY KEY (`id`),
                                                   KEY `security_user_authorize_consent_user_id_index` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户授权同意信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `security_user_authorize_roles`
--

DROP TABLE IF EXISTS `security_user_authorize_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_user_authorize_roles` (
                                                 `user_id` varchar(36) NOT NULL COMMENT '用户ID',
                                                 `role_id` varchar(36) NOT NULL COMMENT '角色ID',
                                                 `authorize_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '授权时间',
                                                 PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户授权角色关系';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `security_user_groups`
--

DROP TABLE IF EXISTS `security_user_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_user_groups` (
                                        `user_id` varchar(36) NOT NULL COMMENT '用户ID',
                                        `group_id` varchar(36) NOT NULL COMMENT '安全组ID',
                                        `bind_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '绑定时间',
                                        PRIMARY KEY (`user_id`,`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户与安全组的关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `security_user_login_log`
--

DROP TABLE IF EXISTS `security_user_login_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_user_login_log` (
                                           `id` varchar(36) NOT NULL COMMENT 'ID',
                                           `user_id` varchar(36) NOT NULL COMMENT '用户ID',
                                           `user_group_id` varchar(36) DEFAULT NULL COMMENT '用户组ID',
                                           `client_id` varchar(36) DEFAULT NULL COMMENT '客户端ID',
                                           `session_id` varchar(36) NOT NULL COMMENT '会话ID',
                                           `login_time` datetime DEFAULT NULL COMMENT '登录时间',
                                           `ip_address` varchar(30) DEFAULT NULL COMMENT 'IP地址',
                                           `device_system` varchar(10) DEFAULT NULL COMMENT '设备系统',
                                           `browser` varchar(20) DEFAULT NULL COMMENT '浏览器',
                                           `country` varchar(10) DEFAULT NULL COMMENT '国家',
                                           `province` varchar(10) DEFAULT NULL COMMENT '省份',
                                           `city` varchar(10) DEFAULT NULL COMMENT '城市',
                                           PRIMARY KEY (`id`),
                                           KEY `security_user_login_log_client_id_index` (`client_id`),
                                           KEY `security_user_login_log_user_id_index` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户登录日志';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
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

/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-28 17:58:56
