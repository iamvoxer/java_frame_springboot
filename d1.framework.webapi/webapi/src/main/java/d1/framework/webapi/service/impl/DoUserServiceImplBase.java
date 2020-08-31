package d1.framework.webapi.service.impl;

import d1.framework.webapi.entity.DoUserBaseEntity;

import java.util.Date;
import java.util.UUID;

public abstract class DoUserServiceImplBase extends DoServiceImpBase {

    public void signInWithoutSingle(DoUserBaseEntity user, int expire) {

        Date expireData = user.getTokenExpire();
        if (expireData == null || expireData.before(new Date())) {//token过期
            //生成用户的Token
            buildAccessTokenByLogin(user, expire);
        } else { //没过期，取出来存放内存里面，延续过期时间
            cache.setData(user.getAccessToken(), user, expire);
            user.setTokenExpire(new Date(System.currentTimeMillis() + expire * 1000));
        }
        user.setLastSignInTime(new Date());
        getDao().save(user);
    }

    public void signInWithSingle(DoUserBaseEntity user, int expire) {

        //不管过期不过期，重新生成token，以前的token就会失效
        if (cache.containsKey(user.getAccessToken()))
            cache.removeData(user.getAccessToken());
        buildAccessTokenByLogin(user, expire);
        user.setLastSignInTime(new Date());
        getDao().save(user);
    }

    private void buildAccessTokenByLogin(DoUserBaseEntity doUser, int expire) {
        String accessToken = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        long tokenExpire = System.currentTimeMillis() + expire * 1000;

        doUser.setAccessToken(accessToken);
        doUser.setTokenExpire(new Date(tokenExpire));
        cache.setData(accessToken, doUser, expire);
    }
}
