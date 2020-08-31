package d1.framework.webapisample;

import d1.framework.webapi.http.Result;
import d1.framework.webapi.http.ResultUtil;
import d1.framework.webapi.service.impl.DoServiceImpBase;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
//@Auth("webadmin")
@RequestMapping("/test")
@Api(value = "/test", description = "测试测试")
public class TestEntityController {
    @Autowired
    TestEntityService service;

    protected DoServiceImpBase getBaseService() {
        return service;
    }

    @ApiOperation(value = "获取数据详细信息", notes = "根据id来获取详细信息")
    @ApiImplicitParam(name = "useId", value = "数据ID", required = true, dataType = "String")
    @RequestMapping(value = "/{useId}", method = RequestMethod.GET)
    public Result<Page<TestEntity>> findByUserId(@PathVariable String useId) {
        TestEntity tt = new TestEntity();
        tt.setUserId(useId);
        Page<TestEntity> mUser = getBaseService().findAllWithPage(null, null, null, tt);
        return mUser != null ? ResultUtil.success("获取详细信息", mUser) : ResultUtil.fail("数据为空或获取失败!");
    }

    @ApiOperation(value = "获取数据详细信息", notes = "根据id来获取详细信息")
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public Result<TestEntity> test(HttpServletRequest request) {
        TestEntity mUser = service.findAAndB("aa", "string", 0);
        return mUser != null ? ResultUtil.success("获取详细信息", mUser) : ResultUtil.fail("数据为空或获取失败!");
    }

    @RequestMapping(value = "/testhmac", method = RequestMethod.GET)
    public String testhmac(HttpServletRequest request, String s, Integer i, String t) {
        TestModel test = new TestModel();
        test.setI(i);
        test.setS(s);
        test.setT(t);
        try {
            service.verfiySign(request, test);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "数据为空或获取失败!";
    }
}
