package d1.framework.webapisample;

import d1.framework.cache.IDoCache;
import d1.framework.webapi.service.impl.DoUserServiceImplBase;
import d1.framework.webapi.service.impl.SignInRetryLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WebAdminUserService extends DoUserServiceImplBase {

    @Autowired
    private WebAdminUserDao userDao;

    @Autowired
    private IDoCache cache;
    @Autowired
    private SignInRetryLimitService retryService;

    @Override
    protected JpaRepository<WebAdminUser, String> getDao() {
        return userDao;
    }

    @Override
    protected void beforeInsert(Object opt) throws Exception {
        super.beforeInsert(opt);
        WebAdminUser user = (WebAdminUser) opt;
        if (user.getName() == null) throw new Exception("用户登录名不能为空");
        if (user.getPassword() == null) throw new Exception("用户密码不能为空");
        if (userDao.existsByName(user.getName())) throw new Exception("用户登录名已存在，不能创建");
    }

    @Override
    protected void beforeUpdate(Object opt) throws Exception {
        super.beforeUpdate(opt);
        WebAdminUser user = (WebAdminUser) opt;
        if (userDao.findOtherUserByNameOrPhone(user.getPhone(), user.getName(), user.getId()) > 0)
            throw new Exception("你要修改的用户名或手机号已经存在，不能修改");
        if (user.getSignUpTime() != null) throw new Exception("注册时间不能修改");
        if (user.getAccessToken() != null) throw new Exception("token不能修改");
    }

    public int test1() {
        return userDao.findSame1();
    }

    public List<Map<String, Object>> test2() {
        return userDao.findSame2();
    }

    public SignInResult signIn(String username, String password) throws Exception {
        SignInResult result = new SignInResult();
        WebAdminUser user = userDao.findByName(username);
        if (user == null) throw new Exception("用户：" + username + "不存在");
        if (retryService.verifyIsLocked(username)) throw new Exception("用户重试错误密码多次，导致用户被锁");
        if (!user.getPassword().equals(password)) {
            retryService.signInWithWrongPwd(username);
            throw new Exception("密码不对");
        }
        retryService.signInSuccess(username);
        signInWithSingle(user, 30 * 24 * 60 * 60);
        result.setId(user.getId());
        result.setToken(user.getAccessToken());
        result.setName(user.getName());
        return result;
    }
}
