package d1.framework.webapi.http;


/**
 * 响应码枚举，参考HTTP状态码的语义
 */
public enum ResultCode {
    SUCCESS(1),//成功
    FAIL(0),//失败
    UN_CATCH_ERROR(-1);//未处理异常
    public int code;

    ResultCode(int code) {
        this.code = code;
    }
}