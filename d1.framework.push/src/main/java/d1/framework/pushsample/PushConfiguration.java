package d1.framework.pushsample;

import d1.framework.push.DoPush;
import d1.framework.push.DoPushJiGuang;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
