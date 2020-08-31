package d1.framework.mqttclient;

import org.eclipse.paho.client.mqttv3.*;

import java.util.Date;
import java.util.List;
import java.util.Random;

public class MQTTClient implements IMQTTClient {

    private MqttClient mqttClient;
    private MQTTClientListener mListener;

    public MQTTClient(MQTTClientListener listener) {
        this.mListener = listener;
    }

    /**
     * 连接Broker
     *
     * @param clientId 客户端唯一标识，服务端用于关联一个Session，只能包含这些 大写字母，小写字母 和 数字（0-9a-zA-Z），23个字符以内
     * @param ip       Broker的IP
     * @param port     Broker的端口，默认61613
     * @param username 登录用户
     * @param password Broker登录密码
     * @throws MqttException
     */
    @Override
    public void connect(String clientId, String ip, int port, String username, String password) throws MqttException {

        String serverURI = "tcp://" + ip + ":" + port;

        mqttClient = new MqttClient(serverURI, clientId);

        MqttConnectOptions options = new MqttConnectOptions();
        // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，
        // 这里设置为true表示每次连接到服务器都以新的身份连接
        options.setCleanSession(true);
        // 设置连接的用户名
        options.setUserName(username);
        // 设置连接的密码
        options.setPassword(password.toCharArray());
        // 设置超时时间 单位为秒
        options.setConnectionTimeout(0);
        // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
        options.setKeepAliveInterval(20);

        options.setAutomaticReconnect(true);
        // 回调
        mqttClient.setCallback(new MqttCallbackExtended() {

            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                mListener.connected();
            }

            @Override
            public void connectionLost(Throwable cause) {
                //连接丢失后，一般在这里面进行重连
                try {
                    mqttClient.reconnect();
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                // publish后会执行到这里
                System.out.println("deliveryComplete---------" + token.isComplete());
            }

            @Override
            public void messageArrived(String topicName, MqttMessage message) throws Exception {
                // subscribe后得到的消息会执行到这里面
                Message msg = new Message();
                msg.setTag(message.getId());
                msg.setTopic(topicName);
                msg.setMsg(message.toString());
                mListener.received(msg);
            }

        });

        // 链接
        mqttClient.connect(options);
    }

    @Override
    public void disconnect() throws MqttException {
        if (mqttClient == null || !mqttClient.isConnected()) {
            return;
        }
        mqttClient.disconnect();
    }

    @Override
    public void subscribe(String topic) throws MqttException {
        if (mqttClient == null) {
            return;
        }
        mqttClient.subscribe(topic);
    }


    @Override
    public void subscribe(String[] topics) throws MqttException {
        if (mqttClient == null) {
            return;
        }
        mqttClient.subscribe(topics);
    }


    @Override
    public void unsubscribe(String topic) throws MqttException {
        if (mqttClient == null) {
            return;
        }
        mqttClient.unsubscribe(topic);
    }


    @Override
    public void unsubscribe(String[] topics) throws MqttException {
        if (mqttClient == null) {
            return;
        }
        mqttClient.unsubscribe(topics);
    }

    @Override
    public boolean isConnected() {
        if (mqttClient == null) {
            return false;
        }
        return mqttClient.isConnected();
    }

    /**
     * 发布消息
     * 参考 https://blog.csdn.net/u011216417/article/details/69666752
     *
     * @param topic 主题
     * @param msg   消息
     * @param type  消息类型，0：最多一次，1：至少一次，2：有且仅有一次
     * @throws MqttException
     */
    @Override
    public int publish(String topic, String msg, int type) throws MqttException {
        if (mqttClient == null) {
            return -1;
        }

        MqttMessage message = new MqttMessage();
        message.setId(new Random().nextInt(10000));
        message.setPayload(msg.getBytes());

        switch (type) {
            case 0: //最多一次，没有回复，不需要存储。有可能丢失（网络异常断开，业务层繁忙或者错误）
                message.setQos(0);
                break;
            case 1://至少一次
                message.setQos(1);
                break;
            case 2://有且仅有一次
                message.setQos(2);
                break;
            default:
                message.setQos(1);
                break;
        }

        mqttClient.publish(topic, message);
        return message.getId();
    }

    @Override
    public int publish(String topic, String msg) throws MqttException {
       return publish(topic, msg, 1);
    }
}
