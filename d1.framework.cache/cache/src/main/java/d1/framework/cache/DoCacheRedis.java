package d1.framework.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


public class DoCacheRedis implements IDoCache {
    private Jedis jedis;
    private Object lock = new Object();
    private JedisPool pool;


    public DoCacheRedis(String redisUrl, int redisPort, String pwd) {
        pool = new JedisPool(redisUrl, redisPort);
        jedis = pool.getResource();
        jedis.auth(pwd);
    }

    @Override
    public <T> T getData(String key, Class<T> tClass) {
        return jedis.exists(key) ? JSON.parseObject(jedis.get(key), tClass) : null;
    }

    @Override
    public JSONObject getData(String key) {
        return JSON.parseObject(jedis.get(key));
    }

    @Override
    public void setData(String key, Object data) {
        setData(key, data, 0);
    }

    @Override
    public void setData(String key, Object data, int cacheTime) {
        synchronized (lock) {
            if (jedis.exists(key)) jedis.del(key);
            jedis.set(key, JSON.toJSONString(data));
            if (cacheTime > 0) jedis.expire(key, cacheTime);
        }
    }

    @Override
    public void removeData(String key) {
        if (!jedis.exists(key)) return;
        synchronized (lock) {
            jedis.del(key);
        }
    }

    @Override
    public boolean containsKey(String key) {
        return jedis.get(key) != null;
    }

    @Override
    public void shutDown() {
        if (jedis != null) jedis.close();
    }
}
