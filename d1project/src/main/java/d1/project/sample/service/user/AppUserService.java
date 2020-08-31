package d1.project.sample.service.user;

import d1.framework.webapi.service.impl.DoUserServiceImplBase;
import d1.project.sample.dao.user.AppUserDao;
import d1.project.sample.entity.user.AppUser;
import d1.project.sample.model.SignInResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AppUserService extends DoUserServiceImplBase {

    @Autowired
    private AppUserDao userDao;

    @Override
    protected JpaRepository<AppUser, String> getDao() {
        return userDao;
    }

    public AppUser findByName(String name) {
        return userDao.findByName(name);
    }

    public SignInResult signUp(String code, AppUser user) throws Exception {
        beforeInsert(user);
        user.setSignUpTime(new Date());
        userDao.save(user);
        return signIn(user.getName(), user.getPassword());//注册成功后直接登录
    }

    public SignInResult signIn(String username, String password) throws Exception {
        SignInResult result = new SignInResult();
        AppUser user = userDao.findByName(username);
        if (user == null) throw new Exception("用户：" + username + "不存在");
        if (!user.getPassword().equals(password)) throw new Exception("密码不对");
        signInWithoutSingle(user, 7 * 24 * 60 * 60);//有效期7天
        result.setId(user.getId());
        result.setToken(user.getAccessToken());
        result.setName(user.getName());
        return result;
    }

    @Override
    protected void beforeInsert(Object obj) throws Exception {
        super.beforeInsert(obj);
        AppUser user = (AppUser) obj;
        if (user.getName() == null) throw new Exception("用户登录名不能为空");
        if (user.getPassword() == null) throw new Exception("用户密码不能为空");
        if (user.getPhone() == null) throw new Exception("用户手机号不能为空");
        if (userDao.existsByPhoneOrName(user.getPhone(), user.getName())) throw new Exception("用户手机号或登录名已存在，不能注册");
        //if (!cache.containsKey(user.getPhone())) throw new Exception("验证码已失效");
    }

    @Override
    protected void beforeUpdate(Object obj) throws Exception {
        super.beforeUpdate(obj);
        AppUser user = (AppUser) obj;
        if (userDao.findOtherUserByNameOrPhone(user.getPhone(), user.getName(), user.getId()) > 0)
            throw new Exception("你要修改的用户名或手机号已经存在，不能修改");
        if (user.getSignUpTime() != null) throw new Exception("注册时间不能修改");
        if (user.getAccessToken() != null) throw new Exception("token不能修改");
        if (user.getPassword() != null) throw new Exception("用户密码不能修改");
    }
}
