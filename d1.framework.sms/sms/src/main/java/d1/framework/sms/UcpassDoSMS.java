package d1.framework.sms;

import d1.framework.util.HttpHelper;

public class UcpassDoSMS implements IDoSMS {
    /***
     * POST /ol/sms/sendsms HTTP/1.1
     Host: open.ucpaas.com
     Content-Type: application/json
     Cache-Control: no-cache
     Postman-Token: be0f46a9-e660-aa54-b47e-a6d17e9629ee

     {
     "sid":"053308d91b5e169e2e856644f5bd468f",
     "token":"3b7ead851e3ffb43608cde1335dff397",
     "appid":"113f067fca4644978bb91a1e3a7ba3b1",
     "templateid":"368230",
     "param":"352556",
     "mobile":"18513197785"
     }
     */
    private String sid;
    private String token;
    private String appid;
    private String URL = "https://open.ucpaas.com/ol/sms/sendsms";

    public UcpassDoSMS(String sid, String token, String appid) {
        this.sid = sid;
        this.token = token;
        this.appid = appid;
    }

    @Override
    public boolean send(String code, String mobile, String templateId) throws Exception {
        if (code == null || code.isEmpty())
            throw new Exception("code不能为空");
        if (mobile == null || mobile.isEmpty())
            throw new Exception("手机号不能为空");
        if (templateId == null || templateId.isEmpty())
            throw new Exception("模板不能为空");
        SendBody body = new SendBody();
        body.appid = appid;
        body.mobile = mobile;
        body.sid = sid;
        body.token = token;
        body.param = code;
        body.templateId = templateId;
        ReturnResult returnResult = HttpHelper.<ReturnResult>postObjectAsJSON(URL, body, ReturnResult.class);
        if (!returnResult.code.equals("000000"))
            throw new Exception("发送短信失败:" + returnResult);
        return true;
    }
}

class SendBody {
    String sid;
    String token;
    String appid;
    String templateId;
    String param;
    String mobile;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String code) {
        this.param = code;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}

class ReturnResult {
    public String code;
    public String count;
    public String create_date;
    public String mobile;
    public String msg;
    public String smsid;
    public String uid;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSmsid() {
        return smsid;
    }

    public void setSmsid(String smsid) {
        this.smsid = smsid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}