package d1.framework.weixin.model;


//参考 https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_1
//从小程序端统一下单接口 完整model，和其它端下单接口有细微差别
public class MiniprogramUnifiedOrderModel extends UnifiedOrderModel {
    //用户标识	openid	否	String(128)	oUpF8uMuAJO_M2pxb1Q9zNjWeS6o	trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识。openid如何获取，可参考【获取openid】
    String openid;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
