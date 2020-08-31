package d1.framework.weixin.service;

import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//https://developers.weixin.qq.com/miniprogram/dev/api/api-login.html#wxloginobject
//临时登录凭证code换取openid
public class OpenidService {
    private String URL = "https://api.weixin.qq.com/sns/jscode2session";
    private String appid;
    private String secret;

    public OpenidService(String appid, String secret) {
        this.secret = secret;
        this.appid = appid;
    }

    public String getOpenid(String code) throws Exception {
        String getURL = URL + "?appid=" + appid + "&secret=" + secret + "&js_code=" + code + "&grant_type=authorization_code";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(getURL)
                .build();
        Response response = client.newCall(request).execute();
        assert response.body() != null;
        String body = response.body().string(); // 返回的是string 类型，json
        JSONObject jsonObject = JSONObject.parseObject(body);
        if (jsonObject.containsKey("errcode"))
            throw new Exception(body);
        return jsonObject.getString("openid");
    }
}
