package d1.framework.webapi;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@ComponentScan("d1")
@EntityScan("d1")
@EnableJpaRepositories("d1")
public class BaseApplication {
}
