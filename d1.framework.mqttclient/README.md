MqttClient实现

#### 0. Gradle

```gradle
repositories {
    mavenCentral()
    maven{ url 'http://你的nexus库地址/repository/d1-java/'}
}

compile('d1.framework:mqttclient:1.0.3')

```

#### 1. 使用服务

```java
// 实例化一个MqttClient
MQTTClient mqttClient = new MQTTClient(new MQTTClientListener() {
    
  // 连接成功回调函数  
  @Override
  public void connected() {}

  // 断开连接回调函数
  @Override
  public void disconnected() {}

  // 接收到消息
  @Override
  public void received(Message msg) {}
});

//连接Broker
 mqttClient.connect("javaTest", "175.1.18.17", 61613, "admin", "password");
```

#### 2. 接口定义

```java
/// <summary>
/// 连接Broker
/// </summary>
/// <param name="clientId">客户端唯一标识，服务端用于关联一个Session，只能包含这些 大写字母，小写字母 和 数字（0-9a-zA-Z），23个字符以内</param>
/// <param name="ip">Broker的IP</param>
/// <param name="port">Broker的端口，默认61613</param>
/// <param name="username">Broker登录用户</param>
/// <param name="password">Broker登录密码</param>
/// <returns></returns>
void connect(string clientId, string ip, int port, string username, string password);

/// <summary>
/// 关闭连接
/// </summary>
/// <returns></returns>
void disconnect();

/// <summary>
/// 订阅主题
/// </summary>
/// <param name="topic">主题</param>
/// <returns></returns>
void subscribe(string topic);

/// <summary>
/// 订阅多个主题
/// </summary>
/// <param name="topics">主题列表</param>
/// <returns></returns>
void unsubscribe(string topic);

/// <summary>
/// 取消订阅
/// </summary>
/// <param name="topic">主题</param>
/// <returns></returns>
void subscribe(String[] topics);

/// <summary>
/// 批量取消订阅主题
/// </summary>
/// <param name="topics">主题列表</param>
/// <returns></returns>
void unsubscribe(String[] topics);

/// <summary>
/// 判断是否在线
/// </summary>
/// <returns></returns>
boolean isConnected();

/// <summary>
/// 发布消息
/// 参考 https://blog.csdn.net/u011216417/article/details/69666752
/// </summary>
/// <param name="topic">主题</param>
/// <param name="msg">消息</param>
/// <param name="type">消息类型，0：最多一次，1：至少一次，2：有且仅有一次</param>
/// <returns></returns>
void publish(string topic, string msg, int type);

/// <summary>
/// 发布消息
/// </summary>
/// <param name="topic">主题</param>
/// <param name="msg">消息</param>
/// <returns></returns>
void publish(string topic, string msg);
```

