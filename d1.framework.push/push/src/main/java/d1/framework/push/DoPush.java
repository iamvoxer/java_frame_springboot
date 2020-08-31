package d1.framework.push;


public interface DoPush {

    int sendToRegistrationId(String registrationId, String notification_title, String msg_title, String msg_content, String extrasparam);

}
