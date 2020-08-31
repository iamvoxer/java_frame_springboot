package d1.framework.webapi.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class Swagger2 {
    @Value("${d1.framework.webapi.swagger.title:项目API文档}")
    private String title;
    @Value("${d1.framework.webapi.swagger.desc:基于swagger2的API自动文档}")
    private String desc;
    @Value("${d1.framework.webapi.swagger.version:1.0.0}")
    private String version;
    @Value("${d1.framework.webapi.swagger.host:localhost:8080}")
    private String host;

    @Bean
    @ConditionalOnProperty(prefix = "d1.framework.webapi.swagger", name = "enable", havingValue = "true", matchIfMissing = false)

    public Docket createRestApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .host(host)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("d1"))
                .paths(PathSelectors.any())
                .build().globalOperationParameters(buildHeaderParameter());
    }

    @Bean
    @ConditionalOnProperty(prefix = "d1.framework.webapi.swagger", name = "enable", havingValue = "false", matchIfMissing = true)
    public Docket createRestApiNone() {//正式环境一般不允许显示详细的API说明
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.none())
                .build().globalOperationParameters(buildHeaderParameter());
    }

    private List<Parameter> buildHeaderParameter() {
        //添加head参数start
        ParameterBuilder tokenPar = new ParameterBuilder();
        tokenPar.name("Authorization").description("header里的令牌,格式是token xxxx或sign xxxx").modelRef(new ModelRef("string")).parameterType("header").required(false).build();

        ParameterBuilder appidPar = new ParameterBuilder();
        appidPar.name("appid").description("header里的授权appid,Authorization为sign xxx才有意义").modelRef(new ModelRef("string")).parameterType("header").required(false).build();

        ParameterBuilder timestampPar = new ParameterBuilder();
        timestampPar.name("timestamp").description("header里的时间戳,Authorization为sign xxx才有意义").modelRef(new ModelRef("string")).parameterType("header").required(false).build();

        List<Parameter> pars = new ArrayList<Parameter>();
        pars.add(tokenPar.build());
        pars.add(appidPar.build());
        pars.add(timestampPar.build());
        return pars;
        //添加head参数end
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(desc)
                .version(version)
                .build();
    }

}
