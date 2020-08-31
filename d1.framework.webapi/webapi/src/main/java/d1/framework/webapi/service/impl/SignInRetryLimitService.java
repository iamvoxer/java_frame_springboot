package d1.framework.webapi.service.impl;

import d1.framework.cache.IDoCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
//限制登录密码错误次数，比如重试5次密码都失败就锁住用户2小时
public class SignInRetryLimitService {
    private final IDoCache cache;
    private String LOCK_USER_PREFIX = "LOCK_RETRY_PWD_USER";

    //登录密码错误重试的次数，没有这个值或值为0表示不限制
    @Value("${d1.framework.webapi.sign-in.retry-count:0}")
    private Integer retryCount;
    //登录密码错误重试到一定次数后，锁住用户一段时间，单位是分钟
    @Value("${d1.framework.webapi.sign-in.lock-period:300}")
    private Integer lockPeriod;

    @Autowired
    public SignInRetryLimitService(IDoCache cache) {
        this.cache = cache;
    }

    public boolean verifyIsLocked(String user) {
        if (retryCount <= 0) return false;

        boolean isContain = cache.containsKey(LOCK_USER_PREFIX + user);
        if (!isContain) return false;

        Integer count = cache.getData(LOCK_USER_PREFIX + user, Integer.class);
        return count >= retryCount;
    }

    public void signInWithWrongPwd(String user) {
        if (retryCount <= 0) return;

        Integer count = 0;
        boolean isContain = cache.containsKey(LOCK_USER_PREFIX + user);
        if (isContain)
            count = cache.getData(LOCK_USER_PREFIX + user, Integer.class);

        cache.setData(LOCK_USER_PREFIX + user, count + 1, lockPeriod * 60);
    }

    public void signInSuccess(String user) {
        if (retryCount <= 0) return;

        boolean isContain = cache.containsKey(LOCK_USER_PREFIX + user);
        if (isContain)
            cache.removeData(LOCK_USER_PREFIX + user);
    }
}
