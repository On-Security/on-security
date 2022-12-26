<img src="https://apiboot.minbox.org/img/on-security.png" style="width:260px"/>

# On-Security：安全的构建应用服务

[![Ci-Builder](https://github.com/On-Security/on-security/actions/workflows/release.yml/badge.svg)](https://github.com/On-Security/on-security/actions)
[![](https://img.shields.io/maven-central/v/org.minbox.framework/on-security.svg?label=Maven%20Central)](https://search.maven.org/search?q=a:on-security-bom)
![](https://img.shields.io/badge/JDK-17+-green.svg)

`On-Security`是一款集身份认证（Identity Authentication）、访问管理（Access Management）和SSO单点登录（Single Sign On）的安全服务开源框架，支持基于角色（RBAC）、基于属性（ABAC）来灵活控制资源的访问权限，框架基于`Spring Security`进行编写，支持`OpenID Connect1.0`、`OAuth2.1`、`SAML2.0`等协议。

## 特性功能基线

- 授权方式（Grant Type）
  - [x] 授权码（Authorization Code）
    - [x] 用户同意授权（User Consent）
  - [x] 客户端认证（Client Credentials）
  - [x] 刷新令牌（Refresh Token）
  - [x] 用户名密码（Username Password）
- [x] 令牌格式（Token Format）
  - [x] JWT
  - [x] Opaque
- [x] 客户端认证方式（Client Authentication）
  - [x] `client_secret_basic`
  - [x] `client_secret_post`
  - [x] `client_secret_jwt`
  - [x] `private_key_jwt`
  - [x] `none` (public clients)
- [x] 支持外部身份供应商（Identity Provider）
  - [x] Gitee
  - [x] GitHub
  - [x] FaceBook
  - [x] Google
  - [ ] ...
- [ ] 应用服务依赖（Application Service）
- [ ] 资源访问控制（Resource Access Control）
  - [ ] 基于角色（RBAC）
  - [ ] 基于属性（ABAC）
- [ ] 支持SAML2.0认证协议
- [ ] 支持CSA协议
- [ ] 支持单点登录（Single Sign On）
- [ ] 支持使用外部用户
- [ ] 支持用户组织架构概念
- [ ] 提供OpenApi
- [ ] 提供UI管理端
- [ ] 提供会话管理（Session Management）
- [ ] 数据分析
  - [ ] 提供用户行为分析
  - [ ] 提供用户登录分布分析
  - [ ] ...
- ...



## 开始使用

`On-Security`发布的每个版本都已上传到`Maven Central`，当然如果您想要使用快照版本（`Snapshot Version`）也可以通过`Maven Central`中找到，快照版本会通过`GitHub Actions`自动发布。

**Maven方式：**

```xml
<dependencies>
  <!--添加认证服务器依赖-->
  <dependency>
    <groupId>org.minbox.framework</groupId>
    <artifactId>on-security-authorization-server</artifactId>
  </dependency>
  //...
</dependencies>
<!--定义OnSecurity的统一版本依赖-->
<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>org.minbox.framework</groupId>
      <artifactId>on-security-bom</artifactId>
      <version>${on-security.version}</version>
      <scope>import</scope>
      <type>pom</type>
    </dependency>
  </dependencies>
</dependencyManagement>
```

> `${on-security.version}`是一个属性定义，可以再`properties`内指定版本号，也可以替换该占位符，
>
> 最新版本`on-security-bom`依赖请访问[OnSecurity Versions](https://central.sonatype.dev/artifact/org.minbox.framework/on-security-bom/0.0.2/versions)查看。

## 示例

我们提供了使用示例源代码，可以访问[on-security-samples](https://github.com/On-Security/on-security-samples)查看，也可以通过下面命令直接将源码clone到本地：

```bash
git clone https://github.com/On-Security/on-security-samples.git
```

> 注意：
>
> 1. 示例运行环境JDK版本为17，可以通过[sdkamn](https://sdkman.io/)来同时管理多个版本的JDK环境
> 2. 需要在本机`MySQL`数据库中创建`on-security`数据库，初始化[SQL脚本](https://github.com/On-Security/on-security/blob/master/on-security-authorization-server/src/main/resources/database-schema/on-security.sql)

## 遇到问题

如果您在使用过程中遇到了问题，可以通过[提交issues](https://github.com/On-Security/on-security/issues/new)的方式来寻求帮助，作者以及相关贡献者看到问题会做出解答。

## 开源许可

`On-Security` 是在 [ GPL-3.0](https://github.com/hengboy/on-security/blob/master/LICENSE) 许可下发布的开源软件。
