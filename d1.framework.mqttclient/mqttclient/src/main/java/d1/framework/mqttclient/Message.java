package d1.framework.mqttclient;

public class Message {
    private Integer tag;
    private String topic;
    private String msg;

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    public String getTopic() {
        return topic;
    }

    protected void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMsg() {
        return msg;
    }

    protected void setMsg(String msg) {
        this.msg = msg;
    }
}
