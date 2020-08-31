package d1.framework.weixin.model;


//统一下单接口 完整model，每个端有细微差别
public class UnifiedOrderModel extends UnifiedOrderRequestModel {
    //应用ID	appid	是	String(32)	类似wxd678efh567hg6787	微信开放平台审核通过的应用APPID（请登录open.weixin.qq.com查看，注意与公众号的APPID不同）
    //小程序ID	appid	是	String(32)	wxd678efh567hg6787	微信分配的小程序ID
    private String appid;
    //商户号	mch_id	是	String(32)	类似1230000109	微信支付分配的商户号
    private String mch_id;
    //随机字符串，不长于32位。推荐随机数生成算法
    private String nonce_str;
    //商户订单号	out_trade_no	是	String(32)	类似20150806125346	商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*且在同一个商户号下唯一。详见商户订单号
    private String out_trade_no;
    //通知地址	notify_url	是	String(256)	类似http://www.weixin.qq.com/wxpay/pay.php	接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
    private String notify_url;
    //交易类型	trade_type	是	String(16)	APP	支付类型
    //小程序取值如下：JSAPI
    private String trade_type;

    private String sign;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

}
