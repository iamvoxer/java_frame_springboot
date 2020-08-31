package d1.framework.smssample;

import d1.framework.sms.IDoSMS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class SmsController {

    @Autowired
    IDoSMS ucpassDoSMS;
    @Autowired
    IDoSMS aliyunDoSMS;

    @RequestMapping(method = RequestMethod.GET)
    public String test() {
        try {
            aliyunDoSMS.send("1000","18513197785","SMS_143104");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ucpassDoSMS.send("1001","18513197785","368230");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
