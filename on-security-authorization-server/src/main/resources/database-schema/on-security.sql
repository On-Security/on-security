-- MySQL dump 10.13  Distrib 8.0.30, for macos12.5 (arm64)
--
-- Host: 127.0.0.1    Database: on_security
-- ------------------------------------------------------
-- Server version	5.5.5-10.10.2-MariaDB-1:10.10.2+maria~ubu2204

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

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gloabl_data_authorization_grant` (
                                                   `id` varchar(36) NOT NULL COMMENT '授权类型ID',
                                                   `name` varchar(10) NOT NULL COMMENT '授权类型名称',
                                                   `code` varchar(30) DEFAULT NULL COMMENT '授权类型Code',
                                                   `describe` varchar(100) DEFAULT NULL COMMENT '描述',
                                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='客户端认证授权类型';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gloabl_data_authorization_grant`
--

/*!40000 ALTER TABLE `gloabl_data_authorization_grant` DISABLE KEYS */;
INSERT INTO `gloabl_data_authorization_grant` VALUES ('3950e707-6b0b-11ed-b779-0242ac110003','授权码模式','authorization_code',NULL),('461ba332-6b0b-11ed-b779-0242ac110003','刷新令牌模式','refresh_token',NULL),('4b48c30a-6b0b-11ed-b779-0242ac110003','密码模式','password',NULL),('67e87832-6b0b-11ed-b779-0242ac110003','客户端模式','client_credentials',NULL),('6ee85c04-6b0b-11ed-b779-0242ac110003','简化模式','implicit',NULL);
/*!40000 ALTER TABLE `gloabl_data_authorization_grant` ENABLE KEYS */;

--
-- Table structure for table `global_data_authentication_method`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `global_data_authentication_method` (
                                                     `id` varchar(36) NOT NULL COMMENT 'ID',
                                                     `code` varchar(30) NOT NULL COMMENT '认证方式code',
                                                     `describe` varchar(100) DEFAULT NULL COMMENT '描述',
                                                     PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='客户端认证方式';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `global_data_authentication_method`
--

/*!40000 ALTER TABLE `global_data_authentication_method` DISABLE KEYS */;
INSERT INTO `global_data_authentication_method` VALUES ('5ad881f4-6b11-11ed-b779-0242ac110003','client_secret_post',NULL),('606a14b6-6b11-11ed-b779-0242ac110003','client_secret_basic',NULL),('6354cb0f-6b11-11ed-b779-0242ac110003','client_secret_jwt',NULL),('6655df1f-6b11-11ed-b779-0242ac110003','private_key_jwt',NULL),('69610d47-6b11-11ed-b779-0242ac110003','none',NULL);
/*!40000 ALTER TABLE `global_data_authentication_method` ENABLE KEYS */;

--
-- Table structure for table `global_data_client_protocol`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `global_data_client_protocol` (
                                               `id` varchar(36) NOT NULL COMMENT '协议编号',
                                               `name` varchar(20) NOT NULL COMMENT '协议名称',
                                               `enabled` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否启用',
                                               `describe` varchar(100) DEFAULT NULL COMMENT '描述',
                                               PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='客户端协议';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `global_data_client_protocol`
--

/*!40000 ALTER TABLE `global_data_client_protocol` DISABLE KEYS */;
INSERT INTO `global_data_client_protocol` VALUES ('39466096-80d4-11ed-b8e3-0242ac110003','OAuth2.0',_binary '','OAuth 2.0 是标准的授权协议'),('939b64bc-6b06-11ed-b779-0242ac110003','OpenID Connect1.0',_binary '','OpenID Connect1.0 是 OAuth 2.0 协议之上的简单身份协议'),('d2381524-6b06-11ed-b779-0242ac110003','SAML2.0',_binary '','安全断言标记语言（SAML）是一种用于在各方之间交换身份验证和授权数据的开放标准');
/*!40000 ALTER TABLE `global_data_client_protocol` ENABLE KEYS */;

--
-- Table structure for table `global_data_signature_algorithm`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `global_data_signature_algorithm` (
                                                   `id` varchar(36) NOT NULL COMMENT 'ID',
                                                   `algorithm` varchar(10) NOT NULL COMMENT '算法',
                                                   `describe` varchar(100) DEFAULT NULL COMMENT '描述',
                                                   PRIMARY KEY (`id`),
                                                   UNIQUE KEY `global_data_signature_algorithm_algorithm_uindex` (`algorithm`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='签名算法';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `global_data_signature_algorithm`
--

/*!40000 ALTER TABLE `global_data_signature_algorithm` DISABLE KEYS */;
INSERT INTO `global_data_signature_algorithm` VALUES ('216d76f6-6ba1-11ed-b5c2-0242ac110002','HS256',NULL),('231013a5-6ba1-11ed-b5c2-0242ac110002','RS256',NULL),('3e9f13e1-6ba1-11ed-b5c2-0242ac110002','ES256',NULL),('b6b83793-6ba1-11ed-b5c2-0242ac110002','HS384',NULL),('bafbc213-6ba1-11ed-b5c2-0242ac110002','HS512',NULL),('c5d35417-6ba1-11ed-b5c2-0242ac110002','RS384',NULL),('d2a6d2db-6ba1-11ed-b5c2-0242ac110002','RS512',NULL),('d8435c32-6ba1-11ed-b5c2-0242ac110002','ES256K',NULL),('da7e7841-6ba1-11ed-b5c2-0242ac110002','ES384',NULL),('dcadf341-6ba1-11ed-b5c2-0242ac110002','ES512',NULL),('df32f309-6ba1-11ed-b5c2-0242ac110002','PS256',NULL),('e14f421a-6ba1-11ed-b5c2-0242ac110002','PS384',NULL),('e3b56f64-6ba1-11ed-b5c2-0242ac110002','PS512',NULL);
/*!40000 ALTER TABLE `global_data_signature_algorithm` ENABLE KEYS */;

--
-- Table structure for table `security_attribute`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_attribute` (
                                      `id` varchar(36) NOT NULL COMMENT 'ID',
                                      `region_id` varchar(36) NOT NULL COMMENT '安全域ID，关联security_region#id',
                                      `key` varchar(50) NOT NULL COMMENT '属性Key',
                                      `value` varchar(200) NOT NULL COMMENT '属性Value',
                                      `create_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
                                      `mark` varchar(200) DEFAULT NULL COMMENT '备注',
                                      `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                      PRIMARY KEY (`id`),
                                      UNIQUE KEY `security_attribute_region_id_key_uindex` (`region_id`,`key`),
                                      UNIQUE KEY `security_attribute_value_key_uindex` (`value`,`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='安全属性定义表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `security_application`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_application` (
                                   `id` varchar(36) NOT NULL COMMENT 'ID',
                                   `application_id` varchar(20) NOT NULL COMMENT '应用ID',
                                   `region_id` varchar(36) NOT NULL COMMENT '所属安全域',
                                   `protocol_id` varchar(36) NOT NULL COMMENT '协议编号',
                                   `display_name` varchar(50) DEFAULT NULL COMMENT '显示名称',
                                   `describe` varchar(100) DEFAULT NULL COMMENT '描述',
                                   `enabled` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否启用',
                                   `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                   `create_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
                                   PRIMARY KEY (`id`),
                                   UNIQUE KEY `secuirty_client_application_id_uindex` (`application_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='客户端基本信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `security_application`
--

/*!40000 ALTER TABLE `security_application` DISABLE KEYS */;
INSERT INTO `security_application` VALUES ('a53055a0-72ba-11ed-aacb-0242ac110002','common','7f776d47-6b03-11ed-b779-0242ac110003','OpenID Connect','公共客户端',NULL,_binary '',_binary '\0','2022-12-03 11:29:07');
/*!40000 ALTER TABLE `security_application` ENABLE KEYS */;

--
-- Table structure for table `security_application_authentication`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_application_authentication` (
                                                  `id` varchar(36) NOT NULL COMMENT 'ID',
                                                  `application_id` varchar(36) NOT NULL COMMENT '应用ID',
                                                  `confidential` bit(1) NOT NULL DEFAULT b'0' COMMENT '客户端类型，是否为机密客户端',
                                                  `jwks_url` varchar(100) DEFAULT NULL COMMENT 'JWKS地址',
                                                  `authentication_methods` varchar(100) DEFAULT NULL COMMENT '客户端认证方式，数据取值参考：global_data_authentication_method#code',
                                                  `authentication_signing_algorithm` varchar(10) DEFAULT NULL COMMENT '为 private_key_jwt 和 client_secret_jwt 身份验证方法设置 JWS 算法，数据取值：global_data_signature_algorithm#algorithm',
                                                  `authorization_grant_types` varchar(100) DEFAULT NULL COMMENT '授权类型列表，数据取值参考：gloabl_data_authorization_grant#code',
                                                  `consent_required` bit(1) DEFAULT b'0' COMMENT '是否需要用户授权同意',
                                                  `id_token_signature_algorithm` varchar(10) DEFAULT NULL COMMENT 'id_token签名算法，数据取值：global_data_signature_algorithm#algorithm',
                                                  `authorization_code_expiration_time` int(11) NOT NULL DEFAULT 300 COMMENT '授权码过期时间，单位：秒',
                                                  `access_token_format` varchar(20) NOT NULL DEFAULT 'self-contained' COMMENT 'access_token生成格式',
                                                  `access_token_expiration_time` int(11) NOT NULL DEFAULT 300 COMMENT 'access_token过期时间，单位：秒',
                                                  `refresh_token_expiration_time` int(11) NOT NULL DEFAULT 900 COMMENT 'refresh_token过期时间，单位：秒',
                                                  `reuse_refresh_token` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否重用刷新令牌',
                                                  `create_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
                                                  PRIMARY KEY (`id`),
                                                  UNIQUE KEY `security_application_authentication_application_id_uindex` (`application_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='客户端认证配置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `security_application_authentication`
--

/*!40000 ALTER TABLE `security_application_authentication` DISABLE KEYS */;
INSERT INTO `security_application_authentication` VALUES ('5571e280-a89a-4759-be5f-7a435176d6dd','a53055a0-72ba-11ed-aacb-0242ac110002',_binary '\0',NULL,'client_secret_basic',NULL,'authorization_code,refresh_token,client_credentials,password',_binary '','RS256',300,'self-contained',3600,7200,_binary '\0','2022-12-01 17:55:25');
/*!40000 ALTER TABLE `security_application_authentication` ENABLE KEYS */;

--
-- Table structure for table `security_application_redirect_uris`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_application_redirect_uris` (
                                                 `id` varchar(36) NOT NULL COMMENT 'ID',
                                                 `application_id` varchar(36) NOT NULL COMMENT '应用ID',
                                                 `redirect_type` varchar(10) NOT NULL COMMENT '跳转类型，home：主页跳转地址，login：登录回调跳转地址，logout：登出回调跳转地址',
                                                 `redirect_uri` varchar(200) NOT NULL COMMENT '跳转地址',
                                                 `create_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
                                                 PRIMARY KEY (`id`),
                                                 KEY `security_application_redirect_uris_application_id_index` (`application_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='客户端跳转地址列表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `security_application_redirect_uris`
--

/*!40000 ALTER TABLE `security_application_redirect_uris` DISABLE KEYS */;
INSERT INTO `security_application_redirect_uris` VALUES ('6b561af1-75f5-11ed-a42c-0242ac110002','a53055a0-72ba-11ed-aacb-0242ac110002','login','http://127.0.0.1:8080/authorized','2022-12-07 14:07:26'),('c770bc3b-72ba-11ed-aacb-0242ac110002','a53055a0-72ba-11ed-aacb-0242ac110002','login','http://127.0.0.1:8080/login/oauth2/code/client-oidc','2022-12-03 11:30:05');
/*!40000 ALTER TABLE `security_application_redirect_uris` ENABLE KEYS */;

--
-- Table structure for table `security_application_scope`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_application_scope` (
                                         `id` varchar(36) NOT NULL COMMENT 'ID',
                                         `application_id` varchar(36) NOT NULL COMMENT '应用ID',
                                         `scope_name` varchar(20) DEFAULT NULL COMMENT '范围名称',
                                         `scope_code` varchar(20) DEFAULT NULL COMMENT '范围Code',
                                         `type` varchar(10) DEFAULT NULL COMMENT '范围类型，default：默认，optional：可选',
                                         `create_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
                                         PRIMARY KEY (`id`),
                                         KEY `security_application_scope_application_id_index` (`application_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='客户端范围';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `security_application_scope`
--

/*!40000 ALTER TABLE `security_application_scope` DISABLE KEYS */;
INSERT INTO `security_application_scope` VALUES ('4c68d0f7-75f5-11ed-a42c-0242ac110002','a53055a0-72ba-11ed-aacb-0242ac110002','openid','openid','default','2022-12-07 14:06:34'),('5174cf8b-75f5-11ed-a42c-0242ac110002','a53055a0-72ba-11ed-aacb-0242ac110002','profile','profile','default','2022-12-07 14:06:43'),('d43771ed-f553-401f-85fa-2df192ed91bd','a53055a0-72ba-11ed-aacb-0242ac110002','read','read','default','2022-12-01 17:55:25'),('d950dd4e-7e91-4e3b-bcb6-dd94e8e24641','a53055a0-72ba-11ed-aacb-0242ac110002','write','write','default','2022-12-01 17:55:25');
/*!40000 ALTER TABLE `security_application_scope` ENABLE KEYS */;

--
-- Table structure for table `security_application_secret`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_application_secret` (
                                          `id` varchar(36) NOT NULL COMMENT 'ID',
                                          `application_id` varchar(36) NOT NULL COMMENT '应用ID',
                                          `application_secret` varchar(100) NOT NULL COMMENT '客户端密钥',
                                          `secret_expires_at` datetime NOT NULL COMMENT '密钥过期时间',
                                          `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                          `create_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
                                          `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
                                          PRIMARY KEY (`id`),
                                          KEY `security_application_secret_application_id_index` (`application_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='客户端密钥';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `security_application_secret`
--

/*!40000 ALTER TABLE `security_application_secret` DISABLE KEYS */;
INSERT INTO `security_application_secret` VALUES ('87db669e-efd3-43da-b1f8-110b1704dfd3','a53055a0-72ba-11ed-aacb-0242ac110002','$2a$10$VtL5YbCcoZxIw4fCxAwm2.gfwNklqb/ltxSpqm7ox1yR78c5jCjr6','2022-12-30 17:55:25',_binary '\0','2022-12-01 17:55:25',NULL);
/*!40000 ALTER TABLE `security_application_secret` ENABLE KEYS */;

--
-- Table structure for table `security_group`
--

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='安全组基本信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `security_group`
--

/*!40000 ALTER TABLE `security_group` DISABLE KEYS */;
INSERT INTO `security_group` VALUES ('65ba5590-6ca2-11ed-8a6a-0242ac110002','7f776d47-6b03-11ed-b779-0242ac110003','测试组',NULL,'2022-11-25 09:20:26');
/*!40000 ALTER TABLE `security_group` ENABLE KEYS */;

--
-- Table structure for table `security_group_authorize_applications`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_group_authorize_applications` (
                                                    `group_id` varchar(36) NOT NULL COMMENT '安全组ID',
                                                    `application_id` varchar(36) NOT NULL COMMENT '应用ID',
                                                    `authorize_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '授权时间',
                                                    PRIMARY KEY (`group_id`,`application_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='安全组授权客户端列表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `security_group_authorize_applications`
--

/*!40000 ALTER TABLE `security_group_authorize_applications` DISABLE KEYS */;
/*!40000 ALTER TABLE `security_group_authorize_applications` ENABLE KEYS */;

--
-- Table structure for table `security_group_authorize_roles`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_group_authorize_roles` (
                                                  `group_id` varchar(36) NOT NULL COMMENT '安全组ID',
                                                  `role_id` varchar(36) NOT NULL COMMENT '角色ID',
                                                  `authorize_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '授权时间',
                                                  PRIMARY KEY (`group_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='安全组授权角色关系';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `security_group_authorize_roles`
--

/*!40000 ALTER TABLE `security_group_authorize_roles` DISABLE KEYS */;
/*!40000 ALTER TABLE `security_group_authorize_roles` ENABLE KEYS */;

--
-- Table structure for table `security_identity_provider`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_identity_provider` (
                                              `id` varchar(36) NOT NULL COMMENT '身份提供商ID',
                                              `region_id` varchar(36) DEFAULT NULL COMMENT '安全域ID，为空时为系统内置身份供应商',
                                              `display_name` varchar(50) NOT NULL COMMENT '显示名称',
                                              `protocol_id` varchar(20) NOT NULL COMMENT '协议ID，值参考global_data_client_protocol#name',
                                              `describe` varchar(200) DEFAULT NULL COMMENT '身份供应商描述',
                                              `issuer_uri` varchar(255) DEFAULT NULL COMMENT '用于OpenID Connect 协议发现端点或由 RFC 8414 定义的 OAuth 2.0 授权服务器元数据端点的地址',
                                              `authorization_uri` varchar(255) NOT NULL COMMENT '认证授权地址',
                                              `token_uri` varchar(255) NOT NULL COMMENT '获取令牌地址',
                                              `user_info_uri` varchar(255) DEFAULT NULL COMMENT '获取用户信息地址',
                                              `user_info_authentication_method` varchar(20) NOT NULL DEFAULT 'header' COMMENT '获取用户信息时请求令牌传递的位置，header、form、query',
                                              `user_name_attribute` varchar(50) NOT NULL COMMENT '将用于从对“userInfoUri”的调用中提取用户名的属性的名称',
                                              `end_session_uri` varchar(255) DEFAULT NULL COMMENT '注销session地址',
                                              `jwk_set_uri` varchar(255) DEFAULT NULL COMMENT 'Json Web Key Set地址，用于验证签名',
                                              `client_authentication_method` varchar(20) NOT NULL DEFAULT 'client_secret_post' COMMENT '客户端验证方式，数据取值参考：global_data_authentication_method#code',
                                              `authorization_grant_type` varchar(30) DEFAULT 'authorization_code' COMMENT '认证授权类型，值参考gloabl_data_authorization_grant#code',
                                              `enabled` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否启用',
                                              `create_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '添加时间',
                                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='安全认证身份提供商信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `security_identity_provider`
--

/*!40000 ALTER TABLE `security_identity_provider` DISABLE KEYS */;
INSERT INTO `security_identity_provider` VALUES ('3f303dbe-8048-11ed-b2b0-0242ac110002',NULL,'码云','OAuth2.0','Gitee.com（码云） 是OSCHINA.NET 推出的代码托管平台，支持Git 和SVN，提供免费的私有仓库托管',NULL,'https://gitee.com/oauth/authorize','https://gitee.com/oauth/token','https://gitee.com/api/v5/user','header','login',NULL,NULL,'client_secret_post','authorization_code',_binary '','2022-12-20 17:25:30'),('8069f1af-8052-11ed-b2b0-0242ac110002',NULL,'GitHub','OAuth2.0','GitHub是一个在线软件源代码托管服务平台，使用Git作为版本控制软件',NULL,'https://github.com/login/oauth/authorize','https://github.com/login/oauth/access_token','https://api.github.com/user','header','login',NULL,NULL,'client_secret_basic','authorization_code',_binary '','2022-12-20 18:38:54'),('8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'Facebook','OAuth2.0','Facebook（简称FB），民间常译为脸书、脸谱、面簿，是一个社交网路服务网站',NULL,'https://www.facebook.com/v2.8/dialog/oauth','https://graph.facebook.com/v2.8/oauth/access_token','https://graph.facebook.com/me?fields=id,name,email','header','email',NULL,NULL,'client_secret_post','authorization_code',_binary '','2022-12-21 09:48:19'),('f5db6e75-8052-11ed-b2b0-0242ac110002',NULL,'Google','OpenID Connect1.0','谷歌是一家位于美国的跨国科技企业，业务包括互联网搜索、云计算、广告技术等，同时开发并提供大量基于互联网的产品与服务','https://accounts.google.com','https://accounts.google.com/o/oauth2/v2/auth','https://www.googleapis.com/oauth2/v4/token','https://www.googleapis.com/oauth2/v3/userinfo','header','email',NULL,'https://www.googleapis.com/oauth2/v3/certs','client_secret_basic','authorization_code',_binary '','2022-12-20 18:42:11');
/*!40000 ALTER TABLE `security_identity_provider` ENABLE KEYS */;

--
-- Table structure for table `security_identity_provider_scopes`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_identity_provider_scopes` (
                                                     `id` varchar(36) NOT NULL COMMENT 'ID',
                                                     `idp_id` varchar(36) NOT NULL COMMENT '身份提供商ID',
                                                     `pid` varchar(36) DEFAULT NULL COMMENT '上级ScopeID',
                                                     `name` varchar(50) DEFAULT NULL COMMENT 'scope名称',
                                                     `describe` varchar(50) DEFAULT NULL COMMENT '描述',
                                                     `enabled` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否启用',
                                                     `required_authorization` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否必须授权',
                                                     PRIMARY KEY (`id`),
                                                     UNIQUE KEY `security_identity_provider_scopes_idp_id_name_uindex` (`idp_id`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='身份提供商授权scope定义';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `security_identity_provider_scopes`
--

/*!40000 ALTER TABLE `security_identity_provider_scopes` DISABLE KEYS */;
INSERT INTO `security_identity_provider_scopes` VALUES ('00311ecf-80cc-11ed-b8e3-0242ac110003','8069f1af-8052-11ed-b2b0-0242ac110002',NULL,'delete:packages','授予从 GitHub Packages 中删除包的权限',_binary '',_binary '\0'),('0123b275-80d3-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'publish_to_groups',NULL,_binary '',_binary '\0'),('0420654b-80d3-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'publish_video',NULL,_binary '',_binary '\0'),('07564ac5-80d3-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'read_insights',NULL,_binary '',_binary '\0'),('0a5b8d66-80d3-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'research_apis',NULL,_binary '',_binary '\0'),('0b00d7ed-80d2-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'ads_management','ads_management',_binary '',_binary '\0'),('0dc10ce7-80d3-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'user_age_range',NULL,_binary '',_binary '\0'),('0e6b85a1-80cc-11ed-b8e3-0242ac110003','8069f1af-8052-11ed-b2b0-0242ac110002',NULL,'admin:gpg_key','完全管理 GPG 密钥',_binary '',_binary '\0'),('0efeedb5-80d2-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'ads_read','ads_read',_binary '',_binary '\0'),('112836c7-80d3-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'user_birthday',NULL,_binary '',_binary '\0'),('151fd7b0-80d3-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'user_friends',NULL,_binary '',_binary '\0'),('180c5435-80d3-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'user_gender',NULL,_binary '',_binary '\0'),('188552a6-80cc-11ed-b8e3-0242ac110003','8069f1af-8052-11ed-b2b0-0242ac110002','0e6b85a1-80cc-11ed-b8e3-0242ac110003','write:gpg_key','创建、列出和查看 GPG 密钥的详细信息',_binary '',_binary '\0'),('1b3ebaf0-80d3-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'user_hometown',NULL,_binary '',_binary '\0'),('1d0c8039-80d2-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'attribution_read',NULL,_binary '',_binary '\0'),('1e502669-80d3-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'user_likes',NULL,_binary '',_binary '\0'),('20c623eb-80d2-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'business_management',NULL,_binary '',_binary '\0'),('20d6122d-80cc-11ed-b8e3-0242ac110003','8069f1af-8052-11ed-b2b0-0242ac110002','0e6b85a1-80cc-11ed-b8e3-0242ac110003','read:gpg_key','列出和查看 GPG 密钥的详细信息',_binary '',_binary '\0'),('2117a901-80d3-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'user_link',NULL,_binary '',_binary '\0'),('23936971-80d2-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'catalog_management',NULL,_binary '',_binary '\0'),('244af301-80d3-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'user_location',NULL,_binary '',_binary '\0'),('274284a1-80d2-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'email',NULL,_binary '',_binary ''),('27e92b3a-80cc-11ed-b8e3-0242ac110003','8069f1af-8052-11ed-b2b0-0242ac110002',NULL,'codespace','授予创建和管理代码空间的能力',_binary '',_binary '\0'),('2a131e25-80d3-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'user_messenger_contact',NULL,_binary '',_binary '\0'),('2a7e15d7-80d2-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'gaming_user_locale',NULL,_binary '',_binary '\0'),('2e084c40-80cc-11ed-b8e3-0242ac110003','8069f1af-8052-11ed-b2b0-0242ac110002',NULL,'workflow','授予添加和更新 GitHub Actions 工作流程文件的能力',_binary '',_binary '\0'),('2e215d2f-80d3-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'user_photos',NULL,_binary '',_binary '\0'),('31469b76-80d3-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'user_posts',NULL,_binary '',_binary '\0'),('346aa43a-80d3-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'user_videos',NULL,_binary '',_binary '\0'),('34e89af9-80cf-11ed-b8e3-0242ac110003','f5db6e75-8052-11ed-b2b0-0242ac110002',NULL,'openid','OpenID',_binary '',_binary ''),('38d96a7a-80d3-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'whatsapp_business_management',NULL,_binary '',_binary '\0'),('3b24964c-80cb-11ed-b8e3-0242ac110003','8069f1af-8052-11ed-b2b0-0242ac110002','5338a6bc-8054-11ed-b2b0-0242ac110002','read:org','对组织成员资格、组织项目和团队成员资格的只读访问权限',_binary '',_binary '\0'),('3c849a5a-80d3-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'whatsapp_business_messaging',NULL,_binary '',_binary '\0'),('43be10c4-80d3-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'instagram_graph_user_media',NULL,_binary '',_binary '\0'),('4717467a-80d3-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'instagram_graph_user_profile',NULL,_binary '',_binary '\0'),('48f3ec85-80cb-11ed-b8e3-0242ac110003','8069f1af-8052-11ed-b2b0-0242ac110002',NULL,'admin:public_key','完全管理公钥',_binary '',_binary '\0'),('490bc4ad-80cf-11ed-b8e3-0242ac110003','f5db6e75-8052-11ed-b2b0-0242ac110002',NULL,'profile','Profile',_binary '',_binary ''),('4e31b56a-80cf-11ed-b8e3-0242ac110003','f5db6e75-8052-11ed-b2b0-0242ac110002',NULL,'email','Email',_binary '',_binary ''),('5180f22b-80cb-11ed-b8e3-0242ac110003','8069f1af-8052-11ed-b2b0-0242ac110002','48f3ec85-80cb-11ed-b8e3-0242ac110003','write:public_key','创建、列出和查看公钥的详细信息',_binary '',_binary '\0'),('5338a6bc-8054-11ed-b2b0-0242ac110002','8069f1af-8052-11ed-b2b0-0242ac110002',NULL,'admin:org','全面管理组织及其团队、项目和成员资格',_binary '',_binary '\0'),('59c718bd-80cb-11ed-b8e3-0242ac110003','8069f1af-8052-11ed-b2b0-0242ac110002','48f3ec85-80cb-11ed-b8e3-0242ac110003','read:public_key','列出和查看公钥的详细信息',_binary '',_binary '\0'),('59da1a20-80cd-11ed-b8e3-0242ac110003','8069f1af-8052-11ed-b2b0-0242ac110002',NULL,'user','授予用户信息读写访问权限',_binary '',_binary '\0'),('5c0dfec6-8054-11ed-b2b0-0242ac110002','8069f1af-8052-11ed-b2b0-0242ac110002','5338a6bc-8054-11ed-b2b0-0242ac110002','write:org','对组织成员资格、组织项目和团队成员资格的读写权限',_binary '',_binary '\0'),('5fd6adb1-80d2-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'groups_access_member_info',NULL,_binary '',_binary '\0'),('63bdae01-80d2-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'instagram_basic',NULL,_binary '',_binary '\0'),('67393b85-80d2-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'instagram_content_publish',NULL,_binary '',_binary '\0'),('67426b2c-80cb-11ed-b8e3-0242ac110003','8069f1af-8052-11ed-b2b0-0242ac110002',NULL,'admin:org_hook','授予对组织挂钩的读取、写入、ping 和删除访问权限',_binary '',_binary '\0'),('68e1fc98-804e-11ed-b2b0-0242ac110002','3f303dbe-8048-11ed-b2b0-0242ac110002',NULL,'projects','查看、创建、更新用户的项目',_binary '',_binary '\0'),('6ab6f162-80d2-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'instagram_manage_comments',NULL,_binary '',_binary '\0'),('6d78a6a8-80d2-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'instagram_manage_insights',NULL,_binary '',_binary '\0'),('6f358839-80cb-11ed-b8e3-0242ac110003','8069f1af-8052-11ed-b2b0-0242ac110002',NULL,'gist','授予对要点的写入权限',_binary '',_binary '\0'),('6f8160aa-8048-11ed-b2b0-0242ac110002','3f303dbe-8048-11ed-b2b0-0242ac110002',NULL,'user_info','访问用户的个人信息、最新动态等',_binary '',_binary ''),('78f26ea6-80d2-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'instagram_shopping_tag_products',NULL,_binary '',_binary '\0'),('7b55ca15-80cb-11ed-b8e3-0242ac110003','8069f1af-8052-11ed-b2b0-0242ac110002',NULL,'notifications','授予用户通知权限',_binary '',_binary '\0'),('7c7a21a9-80d2-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'leads_retrieval',NULL,_binary '',_binary '\0'),('805f3b6c-804e-11ed-b2b0-0242ac110002','3f303dbe-8048-11ed-b2b0-0242ac110002',NULL,'pull_requests','查看、发布、更新用户的 Pull Request',_binary '',_binary '\0'),('80f953db-80d2-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'pages_events',NULL,_binary '',_binary '\0'),('83420c6c-8050-11ed-b2b0-0242ac110002','3f303dbe-8048-11ed-b2b0-0242ac110002',NULL,'notes','查看、发布、管理用户在项目、代码片段中的评论',_binary '',_binary '\0'),('83b2c85c-80d2-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'pages_manage_ads',NULL,_binary '',_binary '\0'),('869af382-80d2-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'pages_manage_cta',NULL,_binary '',_binary '\0'),('896f24e2-80d2-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'pages_manage_instant_articles',NULL,_binary '',_binary '\0'),('8d27976e-80d2-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'pages_manage_engagement',NULL,_binary '',_binary '\0'),('8f31b469-8050-11ed-b2b0-0242ac110002','3f303dbe-8048-11ed-b2b0-0242ac110002',NULL,'keys','查看、部署、删除用户的公钥',_binary '',_binary '\0'),('90f5a373-80d2-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'pages_manage_metadata',NULL,_binary '',_binary '\0'),('9130dcbb-804e-11ed-b2b0-0242ac110002','3f303dbe-8048-11ed-b2b0-0242ac110002',NULL,'issues','查看、发布、更新用户的 Issue',_binary '',_binary '\0'),('93dd92a5-80d2-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'pages_manage_posts',NULL,_binary '',_binary '\0'),('96a2ad20-80d2-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'pages_messaging',NULL,_binary '',_binary '\0'),('97e53bed-8050-11ed-b2b0-0242ac110002','3f303dbe-8048-11ed-b2b0-0242ac110002',NULL,'hook','查看、部署、更新用户的 Webhook',_binary '',_binary '\0'),('9978a6b0-80d2-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'pages_read_engagement',NULL,_binary '',_binary '\0'),('9f85686a-8053-11ed-b2b0-0242ac110002','8069f1af-8052-11ed-b2b0-0242ac110002',NULL,'repo','授予对公共和私有存储库的完全访问权限',_binary '',_binary '\0'),('a06c2e19-8050-11ed-b2b0-0242ac110002','3f303dbe-8048-11ed-b2b0-0242ac110002',NULL,'groups','查看、管理用户的组织以及成员',_binary '',_binary '\0'),('a752c8bd-80cb-11ed-b8e3-0242ac110003','8069f1af-8052-11ed-b2b0-0242ac110002','59da1a20-80cd-11ed-b8e3-0242ac110003','read:user','授予读取用户配置文件数据的权限',_binary '',_binary ''),('a878176d-8050-11ed-b2b0-0242ac110002','3f303dbe-8048-11ed-b2b0-0242ac110002',NULL,'gists','查看、删除、更新用户的代码片段',_binary '',_binary '\0'),('ab611416-8053-11ed-b2b0-0242ac110002','8069f1af-8052-11ed-b2b0-0242ac110002','9f85686a-8053-11ed-b2b0-0242ac110002','repo:status','授予对公共和私有存储库中提交状态的读/写访问权限',_binary '',_binary '\0'),('ae2c0615-80cb-11ed-b8e3-0242ac110003','8069f1af-8052-11ed-b2b0-0242ac110002','59da1a20-80cd-11ed-b8e3-0242ac110003','user:email','授予对用户电子邮件地址的读取权限',_binary '',_binary ''),('b06e00ad-8050-11ed-b2b0-0242ac110002','3f303dbe-8048-11ed-b2b0-0242ac110002',NULL,'enterprises','查看、管理用户的企业以及成员',_binary '',_binary '\0'),('b33b663b-8053-11ed-b2b0-0242ac110002','8069f1af-8052-11ed-b2b0-0242ac110002','9f85686a-8053-11ed-b2b0-0242ac110002','repo_deployment','授予对公共和私有存储库的部署状态的访问权限',_binary '',_binary '\0'),('b77e1f00-8050-11ed-b2b0-0242ac110002','3f303dbe-8048-11ed-b2b0-0242ac110002',NULL,'emails','查看用户的个人邮箱信息',_binary '',_binary ''),('b8b5fe9b-80cb-11ed-b8e3-0242ac110003','8069f1af-8052-11ed-b2b0-0242ac110002','59da1a20-80cd-11ed-b8e3-0242ac110003','user:follow','授予访问权限以关注或取消关注其他用户',_binary '',_binary '\0'),('b92515fd-8053-11ed-b2b0-0242ac110002','8069f1af-8052-11ed-b2b0-0242ac110002','9f85686a-8053-11ed-b2b0-0242ac110002','public_repo','限制对公共存储库的访问',_binary '',_binary '\0'),('c00f9ec8-80cb-11ed-b8e3-0242ac110003','8069f1af-8052-11ed-b2b0-0242ac110002',NULL,'project','授予对用户和组织项目的读/写访问权限',_binary '',_binary '\0'),('c11a670c-8053-11ed-b2b0-0242ac110002','8069f1af-8052-11ed-b2b0-0242ac110002','9f85686a-8053-11ed-b2b0-0242ac110002','repo:invite','授予接受/拒绝邀请以在存储库上进行协作的能力',_binary '',_binary '\0'),('c9b6d052-80cb-11ed-b8e3-0242ac110003','8069f1af-8052-11ed-b2b0-0242ac110002','c00f9ec8-80cb-11ed-b8e3-0242ac110003','read:project','授予对用户和组织项目的只读访问权限',_binary '',_binary '\0'),('cfc64f33-80cb-11ed-b8e3-0242ac110003','8069f1af-8052-11ed-b2b0-0242ac110002',NULL,'delete_repo','授予删除可管理存储库的权限',_binary '',_binary '\0'),('d55b761b-80cb-11ed-b8e3-0242ac110003','8069f1af-8052-11ed-b2b0-0242ac110002',NULL,'write:discussion','允许对团队讨论进行读写访问',_binary '',_binary '\0'),('dd384233-80cb-11ed-b8e3-0242ac110003','8069f1af-8052-11ed-b2b0-0242ac110002','d55b761b-80cb-11ed-b8e3-0242ac110003','read:discussion','允许团队讨论的读取权限',_binary '',_binary '\0'),('de54d4ee-8053-11ed-b2b0-0242ac110002','8069f1af-8052-11ed-b2b0-0242ac110002','9f85686a-8053-11ed-b2b0-0242ac110002','security_events','授权安全事件',_binary '',_binary '\0'),('e881f438-80cb-11ed-b8e3-0242ac110003','8069f1af-8052-11ed-b2b0-0242ac110002',NULL,'write:packages','授予在 GitHub Packages 中上传或发布包的权限',_binary '',_binary '\0'),('e89df611-8053-11ed-b2b0-0242ac110002','8069f1af-8052-11ed-b2b0-0242ac110002',NULL,'admin:repo_hook','授予对公共或私有存储库中的存储库挂钩的读取、写入、ping 和删除访问权限',_binary '',_binary '\0'),('ec391f91-80d2-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'pages_show_list',NULL,_binary '',_binary '\0'),('f0fad077-80d2-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'pages_user_gender',NULL,_binary '',_binary '\0'),('f17fb997-80cb-11ed-b8e3-0242ac110003','8069f1af-8052-11ed-b2b0-0242ac110002',NULL,'read:packages','授予从 GitHub Packages 下载或安装包的权限',_binary '',_binary '\0'),('f42d5b26-80d2-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'pages_user_locale',NULL,_binary '',_binary '\0'),('f50f9557-8053-11ed-b2b0-0242ac110002','8069f1af-8052-11ed-b2b0-0242ac110002','e89df611-8053-11ed-b2b0-0242ac110002','write:repo_hook','授予对公共或私有存储库中挂钩的读取、写入和 ping 访问权限',_binary '',_binary '\0'),('f72c82a2-80d2-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'pages_user_timezone',NULL,_binary '',_binary '\0'),('fa03007c-80d2-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'private_computation_access',NULL,_binary '',_binary '\0'),('fd522909-80d2-11ed-b8e3-0242ac110003','8b8e96e2-80d1-11ed-b8e3-0242ac110003',NULL,'public_profile',NULL,_binary '',_binary ''),('ffdac87b-8053-11ed-b2b0-0242ac110002','8069f1af-8052-11ed-b2b0-0242ac110002','e89df611-8053-11ed-b2b0-0242ac110002','read:repo_hook','授予对公共或私有存储库中挂钩的读取和 ping 访问权限',_binary '',_binary '\0');
/*!40000 ALTER TABLE `security_identity_provider_scopes` ENABLE KEYS */;

--
-- Table structure for table `security_region`
--

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='安全域基本信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `security_region`
--

/*!40000 ALTER TABLE `security_region` DISABLE KEYS */;
INSERT INTO `security_region` VALUES ('7f776d47-6b03-11ed-b779-0242ac110003','test','测试安全域',_binary '',_binary '\0','2022-11-23 07:50:28','测试安全域'),('default','default','默认安全域',_binary '',_binary '\0','2022-12-01 16:41:05','默认安全域');
/*!40000 ALTER TABLE `security_region` ENABLE KEYS */;

--
-- Table structure for table `security_region_identity_provider`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_region_identity_provider` (
                                                     `id` varchar(36) NOT NULL COMMENT 'ID',
                                                     `region_id` varchar(36) NOT NULL COMMENT '安全域ID',
                                                     `idp_id` varchar(36) NOT NULL COMMENT '身份提供商ID，关联security_identity_provider#id',
                                                     `unique_identifier` varchar(30) DEFAULT NULL COMMENT '唯一识别码',
                                                     `registration_id` varchar(24) NOT NULL COMMENT '唯一注册ID，用于参与构建IdP访问路径',
                                                     `application_id` varchar(200) NOT NULL COMMENT '应用ID',
                                                     `application_secret` varchar(255) NOT NULL COMMENT '客户端密钥',
                                                     `callback_url` varchar(255) NOT NULL COMMENT '认证回调地址',
                                                     `authorization_scopes` text DEFAULT NULL COMMENT '授权的scope列表，值参考security_identity_provider_scopes#alias',
                                                     `expand_metadata` text DEFAULT NULL COMMENT '扩展元数据',
                                                     `describe` varchar(200) DEFAULT NULL COMMENT '描述',
                                                     `create_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '配置添加时间',
                                                     PRIMARY KEY (`id`),
                                                     UNIQUE KEY `security_region_identity_provider_pk` (`registration_id`),
                                                     UNIQUE KEY `identity_provider_idp_id_unique_Identifier_region_id_uindex` (`idp_id`,`unique_identifier`,`region_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='安全域身份提供商配置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `security_resource`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_resource` (
                                     `id` varchar(36) NOT NULL COMMENT 'ID',
                                     `region_id` varchar(36) NOT NULL COMMENT '安全域ID',
                                     `application_id` varchar(36) DEFAULT NULL COMMENT '应用ID，为空时资源属于全域',
                                     `name` varchar(20) NOT NULL COMMENT '资源名称',
                                     `code` varchar(20) NOT NULL COMMENT '资源码',
                                     `type` varchar(5) NOT NULL DEFAULT 'api' COMMENT '资源类型，api：Api接口资源',
                                     `describe` varchar(50) DEFAULT NULL COMMENT '描述',
                                     `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                     `create_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
                                     PRIMARY KEY (`id`),
                                     KEY `security_resource_region_id_index` (`region_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='资源信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `security_resource`
--

/*!40000 ALTER TABLE `security_resource` DISABLE KEYS */;
INSERT INTO `security_resource` VALUES ('7abddcfc-6ca1-11ed-8a6a-0242ac110002','7f776d47-6b03-11ed-b779-0242ac110003','31917dee-6ba6-11ed-b5c2-0242ac110002','用户资源','user','api',NULL,_binary '\0','2022-11-25 09:13:52');
/*!40000 ALTER TABLE `security_resource` ENABLE KEYS */;

--
-- Table structure for table `security_resource_authorize_attributes`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_resource_authorize_attributes` (
                                                          `id` varchar(36) NOT NULL COMMENT 'ID',
                                                          `region_id` varchar(36) NOT NULL COMMENT '安全域ID',
                                                          `resource_id` varchar(36) NOT NULL COMMENT '资源ID',
                                                          `attribute_id` varchar(36) NOT NULL COMMENT '属性ID',
                                                          `match_method` varchar(10) NOT NULL COMMENT '权限匹配方式，allow：允许，reject：拒绝',
                                                          `authorize_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '授权时间',
                                                          PRIMARY KEY (`id`),
                                                          UNIQUE KEY `authorize_attributes_resource_id_attribute_id_uindex` (`resource_id`,`attribute_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='资源授权属性关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `security_resource_authorize_attributes`
--

/*!40000 ALTER TABLE `security_resource_authorize_attributes` DISABLE KEYS */;
INSERT INTO `security_resource_authorize_attributes` VALUES ('ced55eb8-8802-11ed-98df-0242ac110003','7f776d47-6b03-11ed-b779-0242ac110003','7abddcfc-6ca1-11ed-8a6a-0242ac110002','bbfcff2c-8749-11ed-9f38-0242ac110002','allow','2022-12-30 05:28:35'),('d783312a-8802-11ed-98df-0242ac110003','7f776d47-6b03-11ed-b779-0242ac110003','7abddcfc-6ca1-11ed-8a6a-0242ac110002','dcd3b3ec-8749-11ed-9f38-0242ac110002','allow','2022-12-30 05:28:50');
/*!40000 ALTER TABLE `security_resource_authorize_attributes` ENABLE KEYS */;

--
-- Table structure for table `security_resource_uris`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_resource_uris` (
                                          `id` varchar(36) NOT NULL COMMENT 'ID',
                                          `resource_id` varchar(36) NOT NULL COMMENT '资源ID',
                                          `uri` varchar(50) NOT NULL COMMENT '路径URI',
                                          `create_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
                                          PRIMARY KEY (`id`),
                                          KEY `security_resource_uris_resource_id_index` (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='资源uri列表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `security_resource_uris`
--

/*!40000 ALTER TABLE `security_resource_uris` DISABLE KEYS */;
INSERT INTO `security_resource_uris` VALUES ('d9298d44-6ca1-11ed-8a6a-0242ac110002','7abddcfc-6ca1-11ed-8a6a-0242ac110002','/user/**','2022-11-25 09:16:30');
/*!40000 ALTER TABLE `security_resource_uris` ENABLE KEYS */;

--
-- Table structure for table `security_role`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_role` (
                                 `id` varchar(36) NOT NULL COMMENT 'ID',
                                 `region_id` varchar(36) NOT NULL COMMENT '安全域ID',
                                 `application_id` varchar(36) DEFAULT NULL COMMENT '应用ID',
                                 `name` varchar(20) NOT NULL COMMENT '角色名称',
                                 `code` varchar(20) NOT NULL COMMENT '角色码',
                                 `describe` varchar(50) DEFAULT NULL COMMENT '描述',
                                 `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                 `create_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
                                 PRIMARY KEY (`id`),
                                 KEY `security_role_region_id_index` (`region_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='安全角色基本信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `security_role`
--

/*!40000 ALTER TABLE `security_role` DISABLE KEYS */;
INSERT INTO `security_role` VALUES ('96e4a3c1-6ca1-11ed-8a6a-0242ac110002','7f776d47-6b03-11ed-b779-0242ac110003','31917dee-6ba6-11ed-b5c2-0242ac110002','普通用户','user',NULL,_binary '\0','2022-11-25 09:14:39');
/*!40000 ALTER TABLE `security_role` ENABLE KEYS */;

--
-- Table structure for table `security_role_authorize_resources`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_role_authorize_resources` (
                                                     `id` varchar(36) NOT NULL COMMENT 'ID',
                                                     `region_id` varchar(36) NOT NULL COMMENT '安全域ID',
                                                     `role_id` varchar(36) NOT NULL COMMENT '角色ID',
                                                     `resource_id` varchar(36) NOT NULL COMMENT '资源ID',
                                                     `match_method` varchar(10) NOT NULL COMMENT '匹配方式，allow：允许，reject：拒绝',
                                                     `authorize_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '授权时间',
                                                     PRIMARY KEY (`id`),
                                                     UNIQUE KEY `authorize_resources_resource_id_role_id_uindex` (`resource_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色授权资源关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `security_role_authorize_resources`
--

/*!40000 ALTER TABLE `security_role_authorize_resources` DISABLE KEYS */;
INSERT INTO `security_role_authorize_resources` VALUES ('23502652-8803-11ed-98df-0242ac110003','7f776d47-6b03-11ed-b779-0242ac110003','96e4a3c1-6ca1-11ed-8a6a-0242ac110002','7abddcfc-6ca1-11ed-8a6a-0242ac110002','allow','2022-12-30 05:30:57');
/*!40000 ALTER TABLE `security_role_authorize_resources` ENABLE KEYS */;

--
-- Table structure for table `security_session`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_session` (
                                    `id` varchar(36) NOT NULL COMMENT 'ID',
                                    `region_id` varchar(36) NOT NULL COMMENT '安全域ID',
                                    `application_id` varchar(36) NOT NULL COMMENT '应用ID',
                                    `user_id` varchar(36) DEFAULT NULL COMMENT '用户ID',
                                    `username` varchar(30) NOT NULL COMMENT '用户名',
                                    `state` varchar(100) DEFAULT NULL,
                                    `session_state` varchar(15) NOT NULL DEFAULT 'normal' COMMENT '状态，normal：正常，forced_offline：强制下线，active_offline：主动下线',
                                    `attributes` text DEFAULT NULL COMMENT '属性',
                                    `authorization_grant_type` varchar(100) NOT NULL COMMENT '授权类型',
                                    `authorization_scopes` varchar(100) DEFAULT NULL COMMENT '授权范围',
                                    `authorization_code_value` text DEFAULT NULL COMMENT '授权码值',
                                    `authorization_code_issued_at` datetime DEFAULT NULL COMMENT '授权码发布时间',
                                    `authorization_code_expires_at` datetime DEFAULT NULL COMMENT '授权码过期时间',
                                    `authorization_code_metadata` text DEFAULT NULL COMMENT '授权码元数据',
                                    `access_token_value` text DEFAULT NULL COMMENT 'access_token令牌值',
                                    `access_token_issued_at` datetime DEFAULT NULL COMMENT 'access_token发布时间',
                                    `access_token_expires_at` datetime DEFAULT NULL COMMENT 'access_token过期时间',
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
                                    KEY `security_session_application_id_index` (`application_id`),
                                    KEY `security_session_region_id_index` (`region_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='会话信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `security_session`
--

/*!40000 ALTER TABLE `security_session` DISABLE KEYS */;
/*!40000 ALTER TABLE `security_session` ENABLE KEYS */;

--
-- Table structure for table `security_user`
--

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户基本信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `security_user`
--

/*!40000 ALTER TABLE `security_user` DISABLE KEYS */;
INSERT INTO `security_user` VALUES ('9010b4d0-6bcb-11ed-b5c2-0242ac110001','7f776d47-6b03-11ed-b779-0242ac110003','2','jnyuqy@gmail.com','$2a$10$ATP1q/YBjFe6kRZ2cNGEv.b8hsqCiYvAgLAmeqEM.APJ1eXgFuzrW','jnyuqy@gmail.com','17100000000','于起宇','恒宇少年','1994-05-21','man','250000',_binary '',_binary '\0','On-Security开源作者','2022-11-24 07:42:35'),('9010b4d0-6bcb-11ed-b5c2-0242ac110002','7f776d47-6b03-11ed-b779-0242ac110003','1','hengboy','$2a$10$ATP1q/YBjFe6kRZ2cNGEv.b8hsqCiYvAgLAmeqEM.APJ1eXgFuzrW','jnyuqy@gmail.com','17100000000','于起宇','恒宇少年','1994-05-21','man','250000',_binary '',_binary '\0','On-Security开源作者','2022-11-24 07:42:35');
/*!40000 ALTER TABLE `security_user` ENABLE KEYS */;

--
-- Table structure for table `security_user_authorize_attributes`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_user_authorize_attributes` (
                                                      `user_id` varchar(36) NOT NULL COMMENT '用户ID',
                                                      `attribute_id` varchar(36) NOT NULL COMMENT '属性ID',
                                                      `authorize_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '授权时间',
                                                      PRIMARY KEY (`user_id`,`attribute_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户属性定义';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `security_user_authorize_attributes`
--

/*!40000 ALTER TABLE `security_user_authorize_attributes` DISABLE KEYS */;
INSERT INTO `security_user_authorize_attributes` VALUES ('9010b4d0-6bcb-11ed-b5c2-0242ac110002','bbfcff2c-8749-11ed-9f38-0242ac110002','2022-12-30 05:29:33'),('9010b4d0-6bcb-11ed-b5c2-0242ac110002','dcd3b3ec-8749-11ed-9f38-0242ac110002','2022-12-30 05:29:38');
/*!40000 ALTER TABLE `security_user_authorize_attributes` ENABLE KEYS */;

--
-- Table structure for table `security_user_authorize_applications`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_user_authorize_applications` (
                                                   `user_id` varchar(36) NOT NULL COMMENT '用户ID',
                                                   `application_id` varchar(36) NOT NULL COMMENT '应用ID',
                                                   `authorize_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '授权时间',
                                                   PRIMARY KEY (`user_id`,`application_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户授权客户端关系';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `security_user_authorize_applications`
--

/*!40000 ALTER TABLE `security_user_authorize_applications` DISABLE KEYS */;
INSERT INTO `security_user_authorize_applications` VALUES ('9010b4d0-6bcb-11ed-b5c2-0242ac110002','a53055a0-72ba-11ed-aacb-0242ac110002','2022-11-25 09:19:14');
/*!40000 ALTER TABLE `security_user_authorize_applications` ENABLE KEYS */;

--
-- Table structure for table `security_user_authorize_consents`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_user_authorize_consents` (
                                                    `user_id` varchar(36) NOT NULL COMMENT '用户ID',
                                                    `username` varchar(30) NOT NULL COMMENT '用户名',
                                                    `application_id` varchar(36) NOT NULL COMMENT '应用ID',
                                                    `authorities` varchar(100) NOT NULL COMMENT '同意授权列表',
                                                    `authorize_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '授权时间',
                                                    PRIMARY KEY (`user_id`,`application_id`),
                                                    KEY `security_user_authorize_consents_user_id_index` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户授权同意信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `security_user_authorize_consents`
--

/*!40000 ALTER TABLE `security_user_authorize_consents` DISABLE KEYS */;
INSERT INTO `security_user_authorize_consents` VALUES ('9010b4d0-6bcb-11ed-b5c2-0242ac110002','hengboy','a53055a0-72ba-11ed-aacb-0242ac110002','SCOPE_write,SCOPE_openid,SCOPE_read,SCOPE_profile','2022-12-23 16:27:58');
/*!40000 ALTER TABLE `security_user_authorize_consents` ENABLE KEYS */;

--
-- Table structure for table `security_user_authorize_roles`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_user_authorize_roles` (
                                                 `user_id` varchar(36) NOT NULL COMMENT '用户ID',
                                                 `role_id` varchar(36) NOT NULL COMMENT '角色ID',
                                                 `authorize_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '授权时间',
                                                 PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户授权角色关系';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `security_user_authorize_roles`
--

/*!40000 ALTER TABLE `security_user_authorize_roles` DISABLE KEYS */;
INSERT INTO `security_user_authorize_roles` VALUES ('9010b4d0-6bcb-11ed-b5c2-0242ac110002','96e4a3c1-6ca1-11ed-8a6a-0242ac110002','2022-11-25 09:19:37');
/*!40000 ALTER TABLE `security_user_authorize_roles` ENABLE KEYS */;

--
-- Table structure for table `security_user_groups`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_user_groups` (
                                        `user_id` varchar(36) NOT NULL COMMENT '用户ID',
                                        `group_id` varchar(36) NOT NULL COMMENT '安全组ID',
                                        `bind_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '绑定时间',
                                        PRIMARY KEY (`user_id`,`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户与安全组的关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `security_user_groups`
--

/*!40000 ALTER TABLE `security_user_groups` DISABLE KEYS */;
INSERT INTO `security_user_groups` VALUES ('9010b4d0-6bcb-11ed-b5c2-0242ac110002','65ba5590-6ca2-11ed-8a6a-0242ac110002','2022-11-25 09:20:47');
/*!40000 ALTER TABLE `security_user_groups` ENABLE KEYS */;

--
-- Table structure for table `security_user_login_log`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_user_login_log` (
                                           `id` varchar(36) NOT NULL COMMENT 'ID',
                                           `region_id` varchar(36) NOT NULL COMMENT '安全域ID',
                                           `application_id` varchar(36) NOT NULL COMMENT '应用ID',
                                           `user_id` varchar(36) NOT NULL COMMENT '用户ID',
                                           `user_group_id` varchar(36) DEFAULT NULL COMMENT '用户组ID',
                                           `session_id` varchar(36) NOT NULL COMMENT '会话ID',
                                           `login_time` datetime NOT NULL COMMENT '登录时间',
                                           `ip_address` varchar(30) NOT NULL COMMENT 'IP地址',
                                           `device_system` varchar(10) DEFAULT NULL COMMENT '设备系统',
                                           `browser` varchar(20) DEFAULT NULL COMMENT '浏览器',
                                           `country` varchar(10) DEFAULT NULL COMMENT '国家',
                                           `province` varchar(10) DEFAULT NULL COMMENT '省份',
                                           `city` varchar(10) DEFAULT NULL COMMENT '城市',
                                           PRIMARY KEY (`id`),
                                           KEY `security_user_login_log_application_id_index` (`application_id`),
                                           KEY `security_user_login_log_user_id_index` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户登录日志';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `security_user_login_log`
--

/*!40000 ALTER TABLE `security_user_login_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `security_user_login_log` ENABLE KEYS */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-30 16:27:26
