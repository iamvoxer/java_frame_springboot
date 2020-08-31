package d1.framework.weixinsample;

import d1.framework.weixin.service.AppPayService;
import d1.framework.weixin.service.OpenidService;
import d1.framework.weixin.service.PayCallbackService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeixinPayConfiguration {
    @Value("${d1.framework.weixin.app.appid}")
    private String appid;
    //商户号	mch_id	是	String(32)	类似1230000109	微信支付分配的商户号
    @Value("${d1.framework.weixin.pay.mchid}")
    private String mch_id;
    @Value("${d1.framework.weixin.pay.mchkey}")
    private String mch_key;
    //通知地址	notify_url	是	String(256)	类似http://www.weixin.qq.com/wxpay/pay.php	接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
    @Value("${d1.framework.weixin.pay.notifyurl}")
    private String notify_url;

    //小程序的 app secret
    @Value("${d1.framework.weixin.pay.secret")
    private String secret;
    @Bean
    AppPayService appPayService(){
        return new AppPayService(appid,mch_id,mch_key,notify_url);
    }

    @Bean
    PayCallbackService payCallbackService(){
        return new PayCallbackService();
    }

    @Bean
    OpenidService openidService(){
        return new OpenidService(appid,secret);
    }
}
