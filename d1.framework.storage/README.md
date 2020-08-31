文件存储相关，本地及七牛云存储等
#### 0. Gradle

```gradle
repositories {
    mavenCentral()
    maven{ url 'http://你的nexus库地址/repository/d1-java/'}
}

compile('d1.framework:storage:1.0.4')

```
这个库没有依赖springboot，可以通过new来使用，但是建议使用时通过@Configuration和@Bean注解来实现
#### 1. 注册服务

```Java
@Configuration
public class StorageConfiguration {
    @Value("${d1.framework.storage.qiniu.AK}")
    private String accessKey;
    @Value("${d1.framework.storage.qiniu.SK}")
    private String secretKey;
    @Value("${d1.framework.storage.qiniu.Bucket}")
    private String bucket;
    @Value("${d1.framework.storage.qiniu.DNS}")
    private String DNS;

    @Bean
    QiniuDoStorage qiniuDoStorage(){
        return new QiniuDoStorage(accessKey,secretKey,bucket,DNS);
    }

    @Bean
    LocalDoStorage localDoStorage(){
        return new LocalDoStorage();
    }
}
```
如果使用七牛，需要在application.properties增加以下配置
```ini
#application.properties
d1.framework.storage.qiniu.AK=XlEF_4DrKkJ4DKVQEAB-R5Bs1a0G
d1.framework.storage.qiniu.SK=V_hqefA9kP-TqW510UCaT_siIUAhqc
d1.framework.storage.qiniu.Bucket=d1project
#七牛云存储域名,正式环境不能使用七牛提供的临时域名，需绑定自己的域名,域名最后需包含/
d1.framework.storage.qiniu.DNS=http://owg7v.bkt.clouddn.com/
```

#### 2. 使用服务

```java
String test = "/temp/test.png";
byte[] content = doStorage.readFile(test);
doStorage.writeFile("/test/my.png",content);
doStorage.writeBigFile("/test/mybig.png",content);
System.out.println(doStorage.getAccessUrl("/test/mybig.png"));
System.out.println(doStorage.getUploadToken());

```

#### 3. 接口定义

```java
String writeFile(String _path, byte[] _content) throws IOException;

byte[] readFile(String _path) throws IOException;

//网络云存储写大文件支持断点续传
String writeBigFile(String _path, byte[] _content) throws IOException;

//云存储后获取访问的地址
String getAccessUrl(String _path);

//云存七牛token
String getUploadToken();
```