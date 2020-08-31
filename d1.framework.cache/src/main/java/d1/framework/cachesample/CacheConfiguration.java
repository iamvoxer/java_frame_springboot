package d1.framework.cachesample;

import d1.framework.cache.DoCacheEhcache;
import d1.framework.cache.DoCacheRedis;
import d1.framework.cache.IDoCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;

@Configuration
public class CacheConfiguration {
    @Value("${d1.framework.cache.redis.url:1}")
    private String url;
    @Value("${d1.framework.cache.redis.port:1}")
    private int port;
    @Value("${d1.framework.cache.redis.pwd:1}")
    private String pwd;

//    @Bean
//    IDoCache cache() throws MalformedURLException {
//        return new DoCacheEhcache();
//    }

    @Bean
    IDoCache cache() {
        return new DoCacheRedis(url, port, pwd);

    }

}
