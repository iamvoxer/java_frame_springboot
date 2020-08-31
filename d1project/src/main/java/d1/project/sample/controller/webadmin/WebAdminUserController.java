package d1.project.sample.controller.webadmin;

import d1.framework.webapi.DoBaseController;
import d1.framework.webapi.annotation.Auth;
import d1.framework.webapi.http.Result;
import d1.framework.webapi.http.ResultUtil;
import d1.framework.webapi.service.impl.DoServiceImpBase;
import d1.project.sample.entity.user.WebAdminUser;
import d1.project.sample.model.SignInResult;
import d1.project.sample.service.user.WebAdminUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Auth("webadmin")
@RestController
@RequestMapping("/webadmin/user")
@Api(value = "/webadmin/user", description = "管理用户管理")
public class WebAdminUserController extends DoBaseController<WebAdminUser> {

    @Autowired
    private WebAdminUserService userService;

    @Override
    protected DoServiceImpBase<WebAdminUser> getBaseService() {
        return userService;
    }

    @Auth("noauth")//无需授权
    @ApiOperation(value = "用户登录", notes = "根据用户名密码登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")
    })
    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    public Result<SignInResult> login(String username, String password) {
        try {
            SignInResult result = userService.signIn(username, password);
            return ResultUtil.success("登录成功", result);
        } catch (Exception e) {
            return ResultUtil.fail("登录失败：" + e.getMessage());
        }
    }
}
