package d1.framework.osinfo;

import d1.framework.osinfo.service.OsInfoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author Kikki
 */
@Configuration
public class OsInfoConfiguration {
    @Value("${d1.framework.osinfo.service.OsInfoService.httpGetIp}")
    private String httpGetIp;

    @Bean
    OsInfoService osInfo() throws IOException {
        return new OsInfoService(httpGetIp);
    }
}
