package d1.framework.weixin.model;


public class UnifiedOrderRequestModel {

    //总金额 订单总金额，单位为分，详见支付金额
    //标价金额	total_fee	是	Int	88	订单总金额，单位为分，详见支付金额
    public int total_fee;
    //商品描述	 APP——需传入应用市场上的APP名字-实际商品名称，天天爱消除-游戏充值。
    //腾讯充值中心-QQ会员充值 商品简单描述，该字段请按照规范传递，
    private String body;

    //终端IP	 String(16)	123.12.12.123	用户端实际ip
    private String spbill_create_ip;

    public int getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(int total_fee) {
        this.total_fee = total_fee;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

}
