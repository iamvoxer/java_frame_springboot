package d1.framework.weixinsample;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WeixinsampleApplicationTests {

    @Test
    public void contextLoads() throws DocumentException {
        Map<String, Object> m_values = new HashMap<String, Object>();
        Document doc = new SAXReader().read(new StringReader(xml));
        Element element = doc.getRootElement();
        List<Element> list =  element.elements();
        for(Element child : list){
            m_values.put(child.getName(), child.getStringValue());//获取xml的键值对到WxPayData内部的数据中
        }
    }
    static String xml = "<xml>\n" +
            "   <appid>wx2421b1c4370ec43b</appid>\n" +
            "   <attach>支付测试</attach>\n" +
            "   <body>APP支付测试</body>\n" +
            "   <mch_id>10000100</mch_id>\n" +
            "   <nonce_str>1add1a30ac87aa2db72f57a2375d8fec</nonce_str>\n" +
            "   <notify_url>http://wxpay.wxutil.com/pub_v2/pay/notify.v2.php</notify_url>\n" +
            "   <out_trade_no>1415659990</out_trade_no>\n" +
            "   <spbill_create_ip>14.23.150.211</spbill_create_ip>\n" +
            "   <total_fee>1</total_fee>\n" +
            "   <trade_type>APP</trade_type>\n" +
            "   <sign>0CB01533B8C1EF103065174F50BCA001</sign>\n" +
            "</xml>";

}
