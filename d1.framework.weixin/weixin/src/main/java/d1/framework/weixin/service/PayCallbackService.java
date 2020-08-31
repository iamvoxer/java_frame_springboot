package d1.framework.weixin.service;

import java.util.logging.Logger;

public class PayCallbackService {
    private Logger logger = Logger.getLogger("d1.framework.weixin.service.PayCallbackService");
    protected void parseCallbackBody(String postBody){
        //子类重写
    }
    public String payCallback(String postBody) {
        logger.info("Callback=" + postBody);
        //if (postBody.contains("SUCCESS"))
        parseCallbackBody(postBody);
        return "<xml>\n" +
                "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                "</xml>";

    }
}
