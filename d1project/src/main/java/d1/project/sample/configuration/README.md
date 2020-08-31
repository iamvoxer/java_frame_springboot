### 自定义Configuration类
通常用于定义读取application.properties里的属性值的类，也用于注册一个bean对象，比如以下使用d1.framework.sms的例子

``` java
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