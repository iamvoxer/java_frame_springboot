webapi框架基础封装，每个webapi项目都必须使用

#### 0. Gradle

``` gradle
repositories {
    mavenCentral()
    maven{ url 'http://你的nexus库地址/repository/d1-java/'}
}

compile('d1.framework:webapi:1.3.5)

```
#### 1. 注册服务

``` java
//do nothing
```

#### 2. 包含功能
注意：入口Application类需添加注解和继承BaseApplication

``` java
@SpringBootApplication
public class DemoApplication extends BaseApplication{

}
```

##### 2.1 封装Http的返回Result
``` java
SUCCESS(1),//成功
FAIL(0),//失败
UN_CATCH_ERROR(-1);//未处理异常

public static Result result(Integer code, String msg, Object object)
//成功返回数据
public static Result success(String msg, Object object);
//成功但不带数据
public static Result success() 
public static Result fail()
public static Result fail(String msg)
//失败
public static Result fail(String msg, Object object)

```
使用方式：

``` java
ResultUtil.result(10001,"自定义的消息",数据对象);
ResultUtil.fail("自定义的消息",数据对象);
ResultUtil.success("自定义的消息",数据对象);
```


##### 2.2 所有controller没有catch的错误最后会被统一拦截处理
##### 2.3 封装Swagger自动生成API文档

``` ini
#application.properties
d1.framework.webapi.swagger.enable=true #生成环境通常改成false
d1.framework.webapi.swagger.title=项目的标题
d1.framework.webapi.swagger.desc=项目的描述
d1.framework.webapi.swagger.version=项目API的版本
d1.framework.webapi.swagger.host=www.xxxx.com:8089
```
说明：
	1. 在contorller里使用注解标记API的方法参考[文档](https://blog.csdn.net/u014231523/article/details/76522486)
	2. 如果有乱码问题参考[文档]()

##### 2.4. 封装跨域设置
``` ini
#application.properties
#生成环境需要把*换成真实的域名，多个域名可以用逗号隔开
d1.framework.webapi.cors=*
```
##### 2.5 所有Entity的基类BaseEntity
##### 2.6.定义DoUserServiceImplBase和DoUserService接口，用户相关的service实现都可以继承DoUserServiceImplBase
##### 2.7.定义AuthFilter来验证用户的请求Header里的Authoriztion对应的值
##### 2.8.定义Auth注解，用来给Controller类和方法确定权限，自动判断的方法在AuthInterceptor类里
##### 2.9.定义JVM退出时先shutdown cache，确保内存里的cache内容正确写入本地文件
##### 2.10.封装DoServiceImpBase，包含service常用方法
##### 2.11.封装DoBaseController，包含controller常用方法
##### 2.12.在controller里的方法可以参数HttpServletRequest request和通过service来获取当前登录用户对象，类似如下：
``` java
@RequestMapping(value = "/test", method = RequestMethod.GET)
public String test(HttpServletRequest request) {
    JSONObject user = userService.getCurrentUser(request);
    String name = user.getString("name");
    return ""+userService.test1()+","+userService.test2()+name;
}
```
##### 2.13. ResultUtil.fail(exception)会自动写日志
##### 2.14. http://localhost:xxx/log/list会自动显示和下载日志文件
需要在application.properties里配置d1.framework.webapi.logcheck.host值指向当前网站的域名加nginx里配置的后缀，后面必须带/符号
##### 2.15. 添加HMAC校验签名的基类service HMACSignService
##### 2.16. 添加登录密码错误可重复次数校验，超过一定次数将不能再试了
需要在application.properties里添加2个配置：
``` ini
#登录密码错误重试的次数，没有这个值或值为0表示不限制
d1.framework.webapi.sign-in.retry-count=5
#登录密码错误重试到一定次数后，锁住用户一段时间，单位是分钟
d1.framework.webapi.sign-in.lock-period=60

```

使用这个服务通过3个函数：
``` java
if (user == null) throw new Exception("用户：" + username + "不存在");
if (retryService.verifyIsLocked(username)) throw new Exception("用户重试错误密码多次，导致用户被锁");
if (!user.getPassword().equals(password)) {
    retryService.signInWithWrongPwd(username);
    throw new Exception("密码不对");
}
retryService.signInSuccess(username);
```