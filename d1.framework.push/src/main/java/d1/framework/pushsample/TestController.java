package d1.framework.pushsample;

import d1.framework.push.DoPush;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private DoPush push;

    @RequestMapping(method = RequestMethod.GET)
    public String test() throws Exception {
        int pushPayload = push.sendToRegistrationId("120c83f7600feqd48c9", "1111", "2222", "3333", "asdasdasdasda");
        return pushPayload + "";
    }

}
