package d1.framework.webapi.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResultUtil {
    private static final Logger logger = LoggerFactory.getLogger(ResultUtil.class);

    /**
     * 成功且带数据
     **/
    public static Result success(String msg, Object object) {
        return result(ResultCode.SUCCESS.code, msg, object);
    }

    public static Result success(String msg) {
        return success(msg, null);
    }

    public static Result result(Integer code) {
        return result(code, "");
    }

    public static Result result(Integer code, String msg) {
        return result(code, msg, null);
    }

    public static Result result(Integer code, String msg, Object object) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        if (object != null) result.setData(object);
        errorLog(code, msg, object);
        return result;
    }

    //只有error才自动写日志
    private static void errorLog(Integer code, String msg, Object object) {
        if (code == ResultCode.FAIL.code && object instanceof Exception) {
            logger.error(msg, (Exception) object);
        }
    }

    /**
     * 成功但不带数据
     **/
    public static Result success() {
        return success("", null);
    }

    public static Result fail() {
        return fail("", null);
    }

    /**
     * 失败
     **/
    public static Result fail(String msg, Object object) {
        return result(ResultCode.FAIL.code, msg, object);
    }

    public static Result fail(String msg) {
        return result(ResultCode.FAIL.code, msg, null);
    }

    public static Result fail(Exception ex) {
        return fail(ex.getMessage(), ex);
    }
}
