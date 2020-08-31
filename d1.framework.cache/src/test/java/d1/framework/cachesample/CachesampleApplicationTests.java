package d1.framework.cachesample;

import d1.framework.cache.DoCacheEhcache;
import d1.framework.cache.IDoCache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;

import static java.lang.Thread.sleep;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {})
public class CachesampleApplicationTests {


    @Test
    public void contextLoads() throws InterruptedException {
    }
}