缓存相关，二种实现方式： 小规模项目，没有分布式考虑的可以使用ehcache，数据量大或有分布式可能的使用redis

#### 0. Gradle

``` gradle
repositories {
    mavenCentral()
    maven{ url 'http://你的nexus库地址/repository/d1-java/'}
}

compile('d1.framework:cache:1.0.6')
```
这个库没有依赖springboot，可以通过new来使用，但是建议使用时通过@Configuration和@Bean注解来实现
#### 1. 注册服务

```java
@Configuration
public class CacheConfiguration {
    @Bean
    IDoCache ehcache() throws MalformedURLException {
        return  new DoCacheEhcache();
    }
    @Value("${d1.framework.cache.redis.url:1}")
    private String url;
    @Value("${d1.framework.cache.redis.port:1}")
    private int port;
    @Value("${d1.framework.cache.redis.pwd:1}")
    private String pwd;
    
    @Bean
    IDoCache cache() {
        return new DoCacheRedis(url, port, pwd);

    }
}
```

#### 2. 使用服务

``` java
@Autowired
IDoCache ehcache;
```

``` java
//设置一个永不过期的字符串值
doCache.setData("key1","value1"); 
//设置一个20秒后过期的字符串值
doCache.setData("key2","value2",20);

TestEntity testEntity = new TestEntity();
testEntity.setName("hah哈");
testEntity.setSex(1);
testEntity.setMarry(false);
//设置一个永不过期的对象值
doCache.setData("key3",testEntity);

sleep(22000);
System.out.println("20 秒...");
System.out.println(doCache.containsKey("key1"));
System.out.println(doCache.containsKey("key2"));
System.out.println(doCache.containsKey("key3"));

System.out.println(doCache.getData("key1",String.class));
//从cache中取出这个对象
TestEntity testEntity1 = doCache.getData("key3",TestEntity.class);
System.out.println(testEntity1.getName()+testEntity1.getSex());
```

#### 3. 接口定义

``` java
    <T> T getData(String var1, Class<T> var2);

    JSONObject getData(String var1);
    //缺省无限长时间
    void setData(String var1, Object var2);
    //设置缓存值，cacheTime单位为：秒 0或<0表示无限长
    void setData(String var1, Object var2, int var3);

    void removeData(String var1);

    boolean containsKey(String var1);
    
    void shutDown();
```

#### 4. 特殊
为了确保ehcache在jvm结束时把内存数据缓存到本地硬盘，下一次jvm启动后可以自动加载硬盘数据到内存。需要在springboot程序被kill之前执行ehcache的shutdown方法
参考测试项目里的DestoryEhcacheBean 。如果ehcachetemp目录下有扩展名为index的文件，则表明成功缓存到本地硬盘了
``` java
@Component
public class DestroyEhcacheBean implements DisposableBean, ExitCodeGenerator {

    @Autowired
    IDoCache ehcache;
    @Override
    public void destroy() throws Exception {
        System.out.println("程序推出前先shutdown ehcache");
        if(ehcache!=null)
            ehcache.shutDown();
    }

    @Override
    public int getExitCode() {
        return 0;
    }
}
```
