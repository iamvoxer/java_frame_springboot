推送相关，目前只要jpush

#### 0. Gradle

``` gradle
repositories {
    mavenCentral()
    maven{ url 'http://你的nexus库地址/repository/d1-java/'}
}

compile('d1.framework:push:1.0.0')
```
这个库没有依赖springboot，可以通过new来使用，但是建议使用时通过@Configuration和@Bean注解来实现
#### 1. 注册服务

```java
@Configuration
public class PushConfiguration {
    @Value("${d1.framework.push.DoPushJiGuang.masterSecret:1}")
    private String masterSecret;
    @Value("${d1.framework.push.DoPushJiGuang.appKey:1}")
    private String appKey;
    @Value("${d1.framework.push.DoPushJiGuang.production:1}")
    private Boolean production;

    @Bean
    DoPush push() {
        return new DoPushJiGuang(masterSecret, appKey, production);
    }
}
```

#### 2. 使用服务

``` java
@Autowired
DoPush push;
```

``` java

```

#### 3. 接口定义

``` java
registrationId     设备标识
notification_title 通知内容标题
msg_title          消息内容标题
msg_content        消息内容
extrasParam        扩展字段
返回:0推送失败，1推送成功
    
 int sendToRegistrationId(String registrationId, String notification_title, String msg_title, String msg_content, String extrasparam);
```

#### 4. 特殊
``` java

```