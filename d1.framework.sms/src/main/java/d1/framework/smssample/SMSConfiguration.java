package d1.framework.smssample;

import d1.framework.sms.AliyunDoSMS;
import d1.framework.sms.IDoSMS;
import d1.framework.sms.UcpassDoSMS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
