package d1.framework.weixin.service;

import d1.framework.weixin.model.MiniprogramUnifiedOrderModel;
import d1.framework.weixin.model.UnifiedOrderModel;
import d1.framework.weixin.model.UnifiedOrderRequestModel;

public class MiniprogramPayService extends PayService {


    public MiniprogramPayService(String appid, String mch_id, String mch_key, String notify_url) {
        super(appid, mch_id, mch_key, notify_url);
    }

    @Override
    protected UnifiedOrderModel getUnifiedOrderModel(UnifiedOrderRequestModel model) {
        MiniprogramUnifiedOrderModel newModel = new MiniprogramUnifiedOrderModel();
        newModel.setTrade_type("JSAPI");
        newModel.setOpenid(((MiniprogramUnifiedOrderModel) model).getOpenid());
        return newModel;
    }
}


