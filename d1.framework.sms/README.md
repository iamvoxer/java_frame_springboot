短信相关，包括阿里云短信和云之讯等

#### 0. Gradle

```gradle
repositories {
    mavenCentral()
    maven{ url 'http://你的nexus库地址/repository/d1-java/'}
}

compile('d1.framework:sms:1.0.9')

```
这个库没有依赖springboot，可以通过new来使用，但是建议使用时通过@Configuration和@Bean注解来实现
#### 1. 注册服务

```java
@Configuration
public class SMSConfiguration {
    @Value("${d1.framework.sms.ucpass.sid:1}")
    private String sid;
    @Value("${d1.framework.sms.ucpass.token:1}")
    private String token;
    @Value("${d1.framework.sms.ucpass.appid:1}")
    private String appid;

    @Value("${d1.framework.sms.aliyun.AK_ID:1}")
    private String accessKeyId;
    @Value("${d1.framework.sms.aliyun.AK_Secret:1}")
    private String accessKeySecret;
    @Value("${d1.framework.sms.aliyun.Sign_Name:1}")
    private String signName;

    @Bean
    public IDoSMS ucpassDoSMS() {
        return new UcpassDoSMS(sid, token, appid);
    }

    @Bean
    public IDoSMS aliyunDoSMS() {
        return new AliyunDoSMS(accessKeyId, accessKeySecret, signName);
    }
}
```

#### 2. 使用方式
```java
@Autowired
IDoSMS ucpassDoSMS;
@Autowired
IDoSMS aliyunDoSMS;
aliyunDoSMS.send("1000","18513197785","SMS_143850104");
ucpassDoSMS.send("1001","18513197785","368230");
```
需要在application.properties里配置以下属性

```ini
#application.properties 
#阿里云的配置
d1.framework.sms.aliyun.AK_ID=OQjGELidf7Z
d1.framework.sms.aliyun.AK_Secret=5PHBnJYnG7eTYi9PbMh
d1.framework.sms.aliyun.Sign_Name=\u826f\u5fc3\u7f51

#云之讯的配置
d1.framework.sms.ucpass.sid=053308d91b544f5bd468f
d1.framework.sms.ucpass.token=3b75dff397
d1.framework.sms.ucpass.appid=113f06a7ba3b1
```
