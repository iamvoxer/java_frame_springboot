package d1.framework.weixin.service;

import d1.framework.util.AlgorithmHelper;
import d1.framework.util.HttpHelper;
import d1.framework.util.MiscHelper;
import d1.framework.weixin.model.UnifiedOrderModel;
import d1.framework.weixin.model.UnifiedOrderRequestModel;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public abstract class PayService {
    private Logger logger = Logger.getLogger("d1.framework.weixin.service.PayService");
    private String appid;
    //商户号	mch_id	是	String(32)	类似1230000109	微信支付分配的商户号
    private String mch_id;
    private String mch_key;
    //通知地址	notify_url	是	String(256)	类似http://www.weixin.qq.com/wxpay/pay.php	接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
    private String notify_url;

    private String unifiedUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    public PayService(String appid, String mch_id, String mch_key, String notify_url) {
        this.appid = appid;
        this.mch_id = mch_id;
        this.mch_key = mch_key;
        this.notify_url = notify_url;
    }

    private Map<String, Object> fromXml(String xml) throws Exception {
        Map<String, Object> m_values = new HashMap<String, Object>();
        Document doc = new SAXReader().read(new StringReader(xml));
        Element element = doc.getRootElement();
        List<Element> list = element.elements();
        for (Element child : list) {
            m_values.put(child.getName(), child.getStringValue());//获取xml的键值对到WxPayData内部的数据中
        }


        Object returncode = m_values.get("return_code");
        if (returncode != null && returncode.toString().equals("SUCCESS")) {
            return m_values;
        } else {
            throw new Exception("返回失败" + xml);
        }
    }

    public Map<String, Object> appUnifiedOrder(UnifiedOrderRequestModel model) throws Exception {
        UnifiedOrderModel newModel = fillPayParameter(model);
        Map<String, Object> map = MiscHelper.objectToMapWithoutNull(newModel);
        String xml = toXML(map);

        String result = HttpHelper.postObjectAsJSON(unifiedUrl, xml, String.class);
        logger.info("UnifiedOrder response = \n" + result);
        Map<String, Object> result_map = fromXml(result);
        if (result_map != null && result_map.get("prepay_id") != null) {
            logger.info("UnifiedOrder prepay_id = " + result_map.get("prepay_id"));
            String prepay_id = result_map.get("prepay_id").toString();
            return buildResponse(prepay_id,newModel.getOut_trade_no());
        }
        return null;
    }

    private Map<String, Object> buildResponse(String prepayid,String out_trade_no) throws UnsupportedEncodingException {
         /*
        应用ID	appid	String(32)	是	wx8888888888888888	微信开放平台审核通过的应用APPID
        商户号	partnerid	String(32)	是	1900000109	微信支付分配的商户号
        预支付交易会话ID	prepayid	String(32)	是	WX1217752501201407033233368018	微信返回的支付交易会话ID
        扩展字段	package	String(128)	是	Sign=WXPay	暂填写固定值Sign=WXPay
        随机字符串	noncestr	String(32)	是	5K8264ILTKCH16CQ2502SI8ZNMTM67VS	随机字符串，不长于32位。推荐随机数生成算法
        时间戳	timestamp	String(10)	是	1412000000	时间戳，请见接口规则-参数规定
        签名	sign	String(32)	是	C380BEC2BFD727A4B6845133519F3AD6	签名，详见签名生成算法注意：签名方式一定要与统一下单接口使用的一致
        商户订单号	out_trade_no	是	String(32)	类似20150806125346	商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*且在同一个商户号下唯一。详见商户订单号
        */
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("appid", appid);
        map.put("partnerid", mch_id);
        map.put("prepayid", prepayid);
        map.put("package", "Sign=WXPay");
        map.put("noncestr", MiscHelper.getUUID32());
        map.put("timestamp", getTimeStampString());
        map.put("out_trade_no", out_trade_no);

        String sign = AlgorithmHelper.hmacSign(map, "key", mch_key);
        map.put("sign", sign);
        return map;
    }

    protected abstract UnifiedOrderModel getUnifiedOrderModel(UnifiedOrderRequestModel model);
    //补充app支付请求model剩余所有参数
    private UnifiedOrderModel fillPayParameter(UnifiedOrderRequestModel model) throws Exception {
        UnifiedOrderModel newModel = getUnifiedOrderModel(model);
        newModel.setAppid(appid);
        newModel.setMch_id(mch_id);
        newModel.setBody(model.getBody());
        newModel.setNonce_str(MiscHelper.getUUID32());
        newModel.setOut_trade_no(getTimeStampString());
        newModel.setTotal_fee(model.getTotal_fee());
        newModel.setSpbill_create_ip(model.getSpbill_create_ip());
        newModel.setNotify_url(notify_url);
        Map<String, Object> map = MiscHelper.objectToMapWithoutNull(newModel);
        newModel.setSign(AlgorithmHelper.hmacSign(map, "key", mch_key));
        return newModel;
    }

    private String getTimeStampString() {
        long timeStampSec = System.currentTimeMillis() / 1000;
        return String.format("%010d", timeStampSec);
    }

    private String toXML(Map<String, Object> m_values) throws Exception {
        //数据为空时不能转化为xml格式
        if (0 == m_values.size()) {
            throw new Exception("微信支付请求数据为空!");
        }

        StringBuffer xml = new StringBuffer("<xml>");
        for (String key : m_values.keySet()) {
            Object obj = m_values.get(key);
            if (obj instanceof Integer) {
                xml.append("<" + key + ">" + obj.toString() + "</" + key + ">");
            } else if (obj instanceof String) {
                xml.append("<" + key + ">" + "<![CDATA[" + obj + "]]></" + key + ">");
            } else//除了string和int类型不能含有其他数据类型
            {
                throw new Exception("微信支付字段数据类型错误!" + obj.getClass());
            }
        }
        xml.append("</xml>");
        return xml.toString();
    }
}


