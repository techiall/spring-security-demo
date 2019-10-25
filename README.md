### Spring Security test

这是一个 Demo，可以你可以通过如下方式进行登录。

为了 Demo 简单化，删除了数据库的相关配置，可以使用 Git 回退查看代码。

* 使用用户名密码进行登录
* 使用微信网页授权，并获得用户信息。

~~用户信息保存在 Mysql 中，自行在 `application.yml` 文件中修改连接 Mysql 配置。~~

### 运行环境

* spring boot 2.1.4.RELEASE
* spring security 2.1.4.RELEASE
* spring-security-oauth2 2.1.4.RELEASE
* ……

### 登录地址

* [127.0.0.1/api/user/login/wechat/oauth2](127.0.0.1/api/user/login/wechat/oauth2)  微信登录
* [127.0.0.1/api/user/login](127.0.0.1/api/user/login) 用户名密码登录
  * 用户名：`root`，密码 `root`

登录完成后，[127.0.0.1/api/user/info](127.0.0.1/api/user/info) 获得当前登录用户的信息。


> 可以通过修改 `application.yml` 的 spring.security.user 的 user 和 password 字段，修改用户名和密码。
>
> 用户名密码登录，可以使用 Postman 进行测试。
>
> Post 请求
>
> Content-Type:application/x-www-form-urlencoded
>
> username: root
>
> password: root



项目需要跑起来，还需要一下配置微信公众号，具体配置如下。

### 微信公众平台测试号

#### 1. 微信登录测试号

打开以下这个链接，并用自己的微信登录。

[https://mp.weixin.qq.com/debug/cgi-bin/sandbox?t=sandbox/login](https://mp.weixin.qq.com/debug/cgi-bin/sandbox?t=sandbox/login)

这时候你可以获得一个测试号，测试号信息 `appID` 和 `appsecret` 填入 `application.yml` 文件中，只需要修改 `[appID]` 和 `[appsecret]` 这个值即可。



```yaml
wechat:
  client:
    clientId: [appID]
    clientSecret: [appsecret]
    accessTokenUri: https://api.weixin.qq.com/sns/oauth2/access_token
    userAuthorizationUri: https://open.weixin.qq.com/connect/oauth2/authorize
    tokenName: access_token
    authenticationScheme: query
    clientAuthenticationScheme: form
    scope: snsapi_userinfo
  resource:
    userInfoUri: https://api.weixin.qq.com/sns/userinfo
```



#### 2. 修改微信回调地址

在微信测试号管理页面，往下拉，找到 **网页授权获取用户基本信息**，旁边有一个修改按钮，将 **授权回调页面域名** 设置为 `127.0.0.1` ，不能加**端口号**。



### 3. 下载微信开发者工具

下载地址如下

[https://developers.weixin.qq.com/miniprogram/dev/devtools/download.html](https://developers.weixin.qq.com/miniprogram/dev/devtools/download.html)

 安装完成后启动，点击左边的 **公众号网页** 即可进入调试，这样子你就不用把地址复制到手机打开。

输入 微信授权地址 `http://127.0.0.1/api/user/login/wechat/oauth2`。就可以成功授权。



### 4. 其他

* 微信授权回调页面域名，如果你自己有公网的服务器，可以使用自己的的公网 IP 地址，或者使用 **已经备案的域名**。
* 你可以在 Spring Boot 里面修改启动端口号，`server.port=80` ；或者使用 Nginx 进行方向代理某一个端口转发到 80 或者 443，反向代理的时候记得加上 `proxy_set_header Host $host:$server_port;` 。



### 参考文档

微信授权登录根据官方 Spring OAuth2 demo 改造而来，可以将 Spring OAuth2 demo clone 本地测试运行。

* Spring OAuth2 demo [https://github.com/spring-guides/tut-spring-boot-oauth2](https://github.com/spring-guides/tut-spring-boot-oauth2)
* [GitHub OAuth 第三方登录示例教程](http://www.ruanyifeng.com/blog/2019/04/github-oauth.html)
* [理解OAuth 2.0](http://www.ruanyifeng.com/blog/2014/05/oauth_2_0.html)
* [微信网页授权](https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842)




### 运行

1. `mvn clean package`
2. `java -jar target/spring-security-test-0.0.1-SNAPSHOT.jar`
