package d1.framework.mqttclient;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.List;

public interface IMQTTClient {

    void connect(String clientId, String ip, int port, String username, String password) throws MqttException;

    void disconnect() throws MqttException;

    void subscribe(String topic) throws MqttException;

    void unsubscribe(String topic) throws MqttException;

    void subscribe(String[] topics) throws MqttException;

    void unsubscribe(String[] topics) throws MqttException;

    boolean isConnected();

    int publish(String topic, String msg, int type) throws MqttException;

    int publish(String topic, String msg) throws MqttException;

//    event EventHandler Connected;
//
//    event EventHandler Disconnected;
//
//    event EventHandler<Message> Received;

}
