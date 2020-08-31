package d1.framework.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.net.MalformedURLException;
import java.net.URL;

public class DoCacheEhcache implements IDoCache {
    private Cache doCache;
    private Object lock = new Object();

    public DoCacheEhcache() throws MalformedURLException {
        String path = "ehcache.xml";
        ClassLoader cl = getDefaultClassLoader();
        URL url = (cl != null ? cl.getResource(path) : ClassLoader.getSystemResource(path));
        CacheManager cacheManager = CacheManager.create(url);
        doCache = cacheManager.getCache("DoCache");
    }

    @Override
    public <E> E getData(String key, Class<E> eClass) {
        return JSON.parseObject(getString(key), eClass);
    }

    private String getString(String key) {
        if (!containsKey(key)) return null;
        String val = String.valueOf(doCache.get(key).getObjectValue());
        return val;
    }

    @Override
    public JSONObject getData(String key) {
        return JSON.parseObject(getString(key));
    }

    @Override
    public void setData(String key, Object data) {

        setData(key, data, 0);
    }

    @Override
    public void setData(String key, Object data, int cacheTime) {
        Element element = new Element(key, JSON.toJSONString(data));
        if (cacheTime <= 0)
            element.setEternal(true);
        else
            element.setTimeToLive(cacheTime);
        synchronized (lock) {
            if (containsKey(key))
                doCache.remove(key);
            doCache.put(element);
        }
    }

    @Override
    public void removeData(String key) {
        if (!containsKey(key)) return;
        synchronized (lock) {
            doCache.remove(key);
        }
    }

    @Override
    public boolean containsKey(String key) {
        return doCache.get(key) != null;
    }

    @Override
    public void shutDown() {
        doCache.getCacheManager().shutdown();
    }

    private ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        } catch (Throwable ex) {
            // Cannot access thread context ClassLoader - falling back...
        }
        if (cl == null) {
            // No thread context class loader -> use class loader of this class.
            cl = DoCacheEhcache.class.getClassLoader();
            if (cl == null) {
                // getClassLoader() returning null indicates the bootstrap ClassLoader
                try {
                    cl = ClassLoader.getSystemClassLoader();
                } catch (Throwable ex) {
                    // Cannot access system ClassLoader - oh well, maybe the caller can live with null...
                }
            }
        }
        return cl;
    }
}
