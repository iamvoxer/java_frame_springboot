package d1.framework.mqttclient;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MqttclientApplication {

    private static String host = "tcp://15.10.180.7:61613";
    private static String username = "admin";
    private static String password = "password";

    private static MQTTClient mqttClient;

    public static void main(String[] args) {

         mqttClient = new MQTTClient(new MQTTClientListener() {
            @Override
            public void connected() {
                System.out.println("connected === ");
                try {
                    mqttClient.subscribe("auction_topic_123456");
                    mqttClient.publish("show_topic","aaaaaa");
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void disconnected() {

            }

            @Override
            public void received(Message msg) {
                System.out.println("received === " + msg.getMsg() + " = " + msg.getTopic());
            }
        });
        try {
            mqttClient.connect("javaTest123", "175.102.18.107", 61613, username, password);
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }
}
