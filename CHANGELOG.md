<a name="unreleased"></a>
## [Unreleased]


<a name="v0.0.1"></a>
## v0.0.1 - 2022-12-16
### Bug Fixes
- 解决查询token信息时，不传递tokenType报错的问题
- 验证客户端是否授权了password方式获取令牌
- 解决password授权方式无法刷新令牌问题
- 修复client_credentials存储会话问题
- 解决更新用户许可问题
- 解决更新用户许可异常问题
- 修复accessToken设置Scopes问题
- 解决调试过程中发现的问题
- 修改更新秘钥sql问题

### 代码重构
- 异常处理器在响应之前，如果description,helpUri为空时设置默认值
- 修改password授权方式认证逻辑
- 统一客户端认证时异常响应处理器
- 查看token信息以及撤销token时配置使用默认的认证异常处理器
- 自定义OnSecurity全部授权认证端点的路径格式
- 修改前置身份认证相关类名
- 修改前置身份认证提供者的判定逻辑
- 新增对安全域状态认证
- 修改数据判定
- 调整sql脚本位置
- 修改使用会话ID删除会话信息
- 修改类名
- 适配存在认证信息修改字段
- 修改使用公共类
- 调整参数映射
- 修改安全域实体类
- 添加类版本号

### 新特性
- password方式获取令牌时一并将oidc协议的IDToken生成
- 实现password授权方式
- 新增OnSecurity默认Web安全配置
- 提供默认授权认证服务器配置类OnSecurityOAuth2AuthorizationServerConfiguration
- 实现认证异常返回自定义统一格式
- 实现用户授权客户端数据查询
- 新增授权服务器模块
- 新增OnSecurityUserDetails反序列化定义
- 提供OAuth2AuthorizationConsentService自定义Jdbc方式数据存在
- 实现认证信息会话数据存储
- 提供On-SecurityJDBC方式的OAuth2AuthorizationService数据存储类
- 实现OnSecurityUserDetailsJdbcService，提供自定义查询用户信息
- 实现OnSecurity针对RegisteredClientRepository接口的实现类
- 实现客户端及相关数据保存
- 实现客户端跳转地址数据存储
- 实现客户端秘钥数据存储
- 实现客户端scope数据存储


[Unreleased]: https://github.com/On-Security/on-security/compare/v0.0.1...HEAD
