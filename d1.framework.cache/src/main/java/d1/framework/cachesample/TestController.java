package d1.framework.cachesample;

import d1.framework.cache.IDoCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

import static java.lang.Thread.sleep;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    IDoCache ehcache;

    @Autowired
    IDoCache redis;

    @RequestMapping(method = RequestMethod.GET)
    public String test() throws InterruptedException {
//        test1();
        redisTest();
//        ehcacheTest();
        return "";
    }

    private void test1() {
        redis.setData("key1", "asd");
        redis.setData("key2", "aasdasd");
        redis.setData("key3", "asdasd");
        System.out.println("~~~~~~~~~~~~~test1~~~~~~~~~~~~");
        System.out.println(redis.containsKey("key1"));
        System.out.println(redis.containsKey("key2"));
        System.out.println(redis.containsKey("key3"));
        System.out.println(redis.getData("key1", String.class));

//        TestEntity testEntity1 = doCache.getData("key3", TestEntity.class);
//        System.out.println(testEntity1.getName() + testEntity1.getSex());
    }

    private void ehcacheTest() throws InterruptedException {
        System.out.println("start test ehcacheTest....");
        //一个8秒后过期的字符串值
        ehcache.setData("key1", "value1", 8);
        //设置一个11秒后过期的字符串值
        ehcache.setData("key2", "value2", 11);

        TestEntity testEntity = new TestEntity();
        testEntity.setName("hah哈");
        testEntity.setSex(1);
        testEntity.setMarry(false);
        //设置一个永不过期的对象值
        ehcache.setData("key3", testEntity);

        sleep(9000);
        System.out.println("10 秒...");
        System.out.println(ehcache.containsKey("key1"));
        System.out.println(ehcache.containsKey("key2"));
        System.out.println(ehcache.containsKey("key3"));

        System.out.println(ehcache.getData("key3", String.class));
        //从cache中取出这个对象
        TestEntity testEntity1 = ehcache.getData("key3", TestEntity.class);
        if (testEntity1 == null) {
            System.out.println("TestEntity不存在");
        } else {
            System.out.println(testEntity1.getName() + testEntity1.getSex());
        }

//        doCache.shutDown();
    }

    private void redisTest() throws InterruptedException {
        System.out.println("start test redisTest....");
        //设置一个8秒后过期的字符串值
        redis.setData("key1", "value1",8);
        //设置一个10秒后过期的字符串值
        redis.setData("key2", "value2", 10);

        TestEntity testEntity = new TestEntity();
        testEntity.setName("hah哈");
        testEntity.setSex(1);
        testEntity.setMarry(false);
        //设置一个永不过期的对象值
        redis.setData("key3", testEntity);

        sleep(9000);
        System.out.println("9 秒...");
        System.out.println(redis.containsKey("key1"));
        System.out.println(redis.containsKey("key2"));
        System.out.println(redis.containsKey("key3"));

        System.out.println(redis.getData("key3", String.class));
        //从cache中取出这个对象
        TestEntity testEntity1 = redis.getData("key3", TestEntity.class);
        if (testEntity1 == null) {
            System.out.println("TestEntity不存在");
        } else {
            System.out.println(testEntity1.getName() + testEntity1.getSex());
        }

//        doCache.shutDown();
    }
}

class TestEntity implements Serializable {
    private String name;
    private int sex;
    private boolean marry;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public boolean isMarry() {
        return marry;
    }

    public void setMarry(boolean marry) {
        this.marry = marry;
    }
}
