获取不同操作系统的基本信息和实时信息
#### 0. Gradle
 
 ``` gradle
 repositories {
     mavenCentral()
     maven{ url 'http://我们内部的库地址/repository/d1-java/'}
 }

compile ('d1.framework:osinfo:1.0.4')

```
#### 1. 注册服务

``` java
/**
 * @author Kikki
 */
@Configuration
public class OsInfoConfiguration {
    @Value("${d1.framework.osinfo.service.OsInfoService.httpGetIp}")
    private String httpGetIp;

    @Bean
    OsInfoService onInfo() throws IOException {
        return new OsInfoService(httpGetIp);
    }
}
```
#### 2. OsInfo model
```
      /**
       * 操作系统版本
       **/
      private String osVersion;
      /**
       * 空闲cpu
       **/
      private String freeCpu;
      /**
       * 空闲内存
       **/
      private String freeMemory;
      /**
       * 空闲硬盘
       **/
      private String freeHardDisk;
      /**
       * 内网ip 外网ip通过getWanIP方法获取
       **/
      private String ip;

``` 
#### 3. application.properties里配置
```java
d1.framework.osinfo.service.OsInfoService.httpGetIp=https://www.taobao.com/help/getip.php
#d1.framework.osinfo.service.OsInfoService.httpGetIp=http://ip.6655.com/ip.aspx
```
