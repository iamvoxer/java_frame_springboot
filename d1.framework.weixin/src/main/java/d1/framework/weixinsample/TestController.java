package d1.framework.weixinsample;

import d1.framework.weixin.model.UnifiedOrderRequestModel;
import d1.framework.weixin.service.AppPayService;
import d1.framework.weixin.service.OpenidService;
import d1.framework.weixin.service.PayCallbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/testapppay")
public class TestController {
    @Autowired
    AppPayService appPayService;
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public Map<String, Object> order(@RequestBody UnifiedOrderRequestModel model) throws Exception {
       return appPayService.appUnifiedOrder(model);
    }

    @Autowired
    PayCallbackService payCallbackService;
    @RequestMapping(value = "/callback", method = RequestMethod.POST)
    public String payCallback(@RequestBody String body) throws Exception {

        return payCallbackService.payCallback("body");
    }
    @Autowired
    OpenidService openidService;
    @RequestMapping(value = "/getOpenid", method = RequestMethod.GET)
    public String getOpenid( String code) throws Exception {

        return openidService.getOpenid(code);
    }
}

