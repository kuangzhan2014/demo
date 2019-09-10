package com.maitianer.demo.api.config;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maitianer.demo.model.sys.Member;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
//@Profile({"local", "dev"})
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
//                .apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.maitianer.demo.api.resource"))
                .paths(PathSelectors.any())
                .build()
                .ignoredParameterTypes(Member.class, Page.class, RedirectAttributes.class).securitySchemes(Arrays.asList(
                        new ApiKey("Authorization", "Authorization", "header")))
                .securityContexts(Arrays.asList(
                        SecurityContext.builder()
                                .securityReferences(defaultAuth())
                                .forPaths(PathSelectors.any())
                                .build()
                ));
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(
                new SecurityReference("Authorization", authorizationScopes));
    }

    // 构建 api文档的详细信息函数
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 页面标题
                .title("品牌小程序")
                .description("用户端 API")
                // 版本号
                .version("1.0")
                .build();
    }

}