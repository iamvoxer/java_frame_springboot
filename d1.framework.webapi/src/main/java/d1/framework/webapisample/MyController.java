package d1.framework.webapisample;

import d1.framework.webapi.DoBaseController;
import d1.framework.webapi.service.impl.DoServiceImpBase;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@Auth("webadmin")
@RequestMapping("/mytest")
@Api(value = "/mytest", description = "操作日志管理")
public class MyController extends DoBaseController<MyEntity> {
    private static final Logger logger = LoggerFactory.getLogger(DoBaseController.class);
    @Autowired
    MyService myService;

    @Override
    protected DoServiceImpBase<MyEntity> getBaseService() {
        return myService;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {

        return "";
    }

    protected Sort withSort() {
        return Sort.by(Sort.Direction.DESC, "createTime");
    }
}
