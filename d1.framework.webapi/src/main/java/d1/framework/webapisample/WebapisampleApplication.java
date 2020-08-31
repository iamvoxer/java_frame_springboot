package d1.framework.webapisample;

import d1.framework.webapi.BaseApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class WebapisampleApplication extends BaseApplication {
    private static final Logger logger = LoggerFactory.getLogger(WebapisampleApplication.class);

    public static void main(String[] args) {
        logger.info("infosssss");
        logger.debug("debug.....");
        logger.warn("warnlslllll");
        logger.error("error......");
        SpringApplication.run(WebapisampleApplication.class, args);
    }
}
