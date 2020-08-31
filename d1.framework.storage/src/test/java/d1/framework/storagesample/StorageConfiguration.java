package d1.framework.storagesample;

import d1.framework.storage.IDoStorage;
import d1.framework.storage.LocalDoStorage;
import d1.framework.storage.QiniuDoStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfiguration {
    @Value("${d1.framework.storage.qiniu.AK}")
    private String accessKey;
    @Value("${d1.framework.storage.qiniu.SK}")
    private String secretKey;
    @Value("${d1.framework.storage.qiniu.Bucket}")
    private String bucket;
    @Value("${d1.framework.storage.qiniu.DNS}")
    private String DNS;

    @Bean
    IDoStorage qiniuDoStorage() {
        return new QiniuDoStorage(accessKey, secretKey, bucket, DNS);
    }

    @Bean
    IDoStorage localDoStorage() {
        return new LocalDoStorage();
    }
}
