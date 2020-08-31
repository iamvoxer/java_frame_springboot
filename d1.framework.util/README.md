工具类汇集，包括http，string，常用算法等等


#### 0. Gradle

```gradle
repositories {
    mavenCentral()
    maven{ url 'http://你的nexus库地址/repository/d1-java/'}
}

compile('d1.framework:util:1.0.12')

```

#### 1. 使用方式
1.1 MiscHelper：杂七杂八的工具类
```java
//获取32位随机字符串
MiscHelper.getUUID32()
//获取多位随机全数字字符串，一般用于生成验证码
MiscHelper.getRandomNumbers(int places) 
//对象转Map,如果值为null，则不包含
MiscHelper.objectToMapWithoutNull(Object obj)
//对象转Map,如果值为null也包含
MiscHelper.objectToMapWithNull(Object obj)
//文件转base64，通常是图片文件
MiscHelper.file2Base64(String imgFile) 
```
1.2 HttpHelper：http相关的工具类
```java
//post一个对象，返回一个对象
HttpHelper.postObjectAsJSON(String url, Object obj, Class<T> tClass) 
HttpHelper.postObjectAsJSON(String url, Object obj, Class<T> tClass, Map<String, String> headers)
```
1.3 AlgorithmHelper:算法相关的工具
``java
//md5
AlgorithmHelper.md5_32(String source,boolean isUpper )
//hmac
AlgorithmHelper.hmacSign(Map<String, Object> maps,String keyName,String keyValue)
```
1.4 RSAHelper: RSA加密工具类