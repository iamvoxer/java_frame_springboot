package d1.framework.webapi.configuration;

import d1.framework.webapi.http.Result;
import d1.framework.webapi.http.ResultCode;
import d1.framework.webapi.http.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class DoControllerAdvice {
    private static final Logger logger = LoggerFactory.getLogger(DoControllerAdvice.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Result all(Exception e) {
        logger.error("未处理异常", e);
        return ResultUtil.result(ResultCode.UN_CATCH_ERROR.code, e.getMessage(), e);
    }
}
