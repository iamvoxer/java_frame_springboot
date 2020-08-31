集成图像识别相关功能，阿里vin码和汽车牌照识别

#### 0. Gradle

```gradle
repositories {
    mavenCentral()
    maven{ url 'http://你的nexus库地址/repository/d1-java/'}
}

compile('d1.framework:ocr:1.0.0')

```

#### 1. 使用方式
1.1 AliyunOCRHelper：阿里云Ocr
在[这里](https://help.aliyun.com/document_detail/30402.html?spm=a2c4g.11174283.6.542.4da1b1fdJKeVOO)购买印刷文字识别-车牌识别和印刷文字识别-vin码识别，获取到APPCODE值
```java
//识别车牌号码
AliyunOCRHelper.recognitionVehiclePlate(String appCode, String url) 
//识别车牌vin号码
//对象转Map,如果值为null，则不包含
AliyunOCRHelper.recognitionVehicleVin(String appCode, String url)