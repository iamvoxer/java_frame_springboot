package d1.project.sample.controller.app;

import d1.framework.webapi.DoBaseController;
import d1.framework.webapi.annotation.Auth;
import d1.framework.webapi.http.Result;
import d1.framework.webapi.http.ResultUtil;
import d1.framework.webapi.service.impl.DoServiceImpBase;
import d1.project.sample.entity.user.AppUser;
import d1.project.sample.model.SignInResult;
import d1.project.sample.service.user.AppUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Auth("appuser")
@RequestMapping("/app/user")
@Api(value = "/app/user", description = "App用户管理")
public class AppUserController extends DoBaseController<AppUser> {

    @Autowired
    private AppUserService userService;

    @Override
    protected DoServiceImpBase<AppUser> getBaseService() {
        return userService;
    }

    @Auth("noauth")//noauth表示这个接口不需要token就能访问
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
            return ResultUtil.fail("登录失败：", e);
        }
    }
}
