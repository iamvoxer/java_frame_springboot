package d1.framework.mqttclient;

public interface MQTTClientListener {


    void connected();

    void disconnected();

    void received(Message msg);
}
