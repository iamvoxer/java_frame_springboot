package d1.framework.sms;

public interface IDoSMS{
    /**
     *
     * @param code 比如1234这种4位或6位的数字字符串，调用者确定
     * @param mobile 只支持一个手机号码
     * @param templateId 模板id必须要有
     * @return 是否发送成功
     * @throws Exception
     */
    boolean send(String code, String mobile, String templateId) throws Exception;
}
