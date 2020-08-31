微信相关



#### 0. Gradle

```gradle
repositories {
    mavenCentral()
    maven{ url 'http://你的nexus库地址/repository/d1-java/'}
}

compile('d1.framework:weixin:1.0.9')

```
这个库没有依赖springboot，可以通过new来使用，但是建议使用时通过@Configuration和@Bean注解来实现
#### 1. 注册服务

```java
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

    @Bean
    AppPayService appPayService(){
        return new AppPayService(appid,mch_id,mch_key,notify_url);
    }

    @Bean
    PayCallbackService payCallbackService(){
        return new PayCallbackService();
    }
}
```
#### 2. App支付,小程序支付
主要提供3个基本功能：统一下单，统一回调，小程序临时登录凭证code换取openid
参考链接：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_1
          https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_1
使用方法：根据业务需要创建一个或多个controller
```java
@RestController
@RequestMapping("/testapppay")
public class TestController {
    @Autowired
    AppPayService appPayService;
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public String order(@RequestBody AppUnifiedOrderRequestModel model) throws Exception {
       return appPayService.appUnifiedOrder(model);
    }

    @Autowired
    PayCallbackService payCallbackService;
    @RequestMapping(value = "/callback", method = RequestMethod.POST)
    public String payCallback(@RequestBody String body) throws Exception {
        //这里可以overridePayCallbackService的parseCallbackBody方法
        return payCallbackService.payCallback("body");
    }
    
    @Autowired
    OpenidService openidService;
    @RequestMapping(value = "/getOpenid", method = RequestMethod.GET)
    public String getOpenid( String code) throws Exception {

        return openidService.getOpenid(code);
    }
}
```
需要在application.properties里配置
```json
d1.framework.weixin.app.appid=wxddddddddddddd6693c291
d1.framework.weixin.pay.mchid=1242445363
d1.framework.weixin.pay.mchkey=chrsddwhdhxt10
d1.framework.weixin.pay.notifyurl=http://www.xxx.com/testapppay/callback
d1.framework.weixin.pay.secret=xxxxxx

```