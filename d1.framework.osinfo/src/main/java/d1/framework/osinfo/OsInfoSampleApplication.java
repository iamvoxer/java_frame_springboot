package d1.framework.osinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Kikki
 */
@SpringBootApplication
@ComponentScan({"d1"})
public class OsInfoSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(OsInfoSampleApplication.class, args);
    }

}
