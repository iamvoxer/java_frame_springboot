package d1.project.sample;

import d1.framework.webapi.BaseApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//注意项目的package必须以‘d1.’开头
//项目主Application必须extends BaseApplication
@SpringBootApplication
public class SampleApplication extends BaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleApplication.class, args);
    }
}
