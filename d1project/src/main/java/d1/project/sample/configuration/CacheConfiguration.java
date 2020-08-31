package d1.project.sample.configuration;

import d1.framework.cache.DoCacheEhcache;
import d1.framework.cache.IDoCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;

@Configuration //d1.framework.cache作为基础库必须要有，
public class CacheConfiguration {
    @Bean
    IDoCache cache() throws MalformedURLException {
        return new DoCacheEhcache();//缺省使用Ehcache，如果使用redis，需要修改这里
    }
}

