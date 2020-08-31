package d1.project.sample.service.user;

import d1.framework.webapi.service.impl.DoUserServiceImplBase;
import d1.project.sample.dao.user.WebAdminUserDao;
import d1.project.sample.entity.user.WebAdminUser;
import d1.project.sample.model.SignInResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class WebAdminUserService extends DoUserServiceImplBase {

    @Autowired
    private WebAdminUserDao userDao;

    @Override
    protected JpaRepository<WebAdminUser, String> getDao() {
        return userDao;
    }

    public SignInResult signIn(String username, String password) throws Exception {
        SignInResult result = new SignInResult();
        WebAdminUser user = userDao.findByName(username);
        if (user == null) throw new Exception("用户：" + username + "不存在");
        if (!user.getPassword().equals(password)) throw new Exception("密码不对");
        signInWithoutSingle(user, 30 * 24 * 60 * 60);//有效期30天
        result.setId(user.getId());
        result.setToken(user.getAccessToken());
        result.setName(user.getName());
        return result;
    }

    @Override
    protected void beforeInsert(Object obj) throws Exception {
        super.beforeInsert(obj);
        WebAdminUser user = (WebAdminUser) obj;
        if (user.getName() == null) throw new Exception("用户登录名不能为空");
        if (user.getPassword() == null) throw new Exception("用户密码不能为空");
        if (userDao.existsByName(user.getName())) throw new Exception("用户登录名已存在，不能创建");
    }

    @Override
    protected void beforeUpdate(Object obj) throws Exception {
        super.beforeUpdate(obj);
        WebAdminUser user = (WebAdminUser) obj;
        if (userDao.findOtherUserByNameOrPhone(user.getPhone(), user.getName(), user.getId()) > 0)
            throw new Exception("你要修改的用户名或手机号已经存在，不能修改");
        if (user.getSignUpTime() != null) throw new Exception("注册时间不能修改");
        if (user.getAccessToken() != null) throw new Exception("token不能修改");
    }
}
