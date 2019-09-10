package com.maitianer.demo.admin;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.maitianer.demo.admin.util.CustomUtilsDialect;
import com.maitianer.demo.biz.DemoBizConfig;
import com.maitianer.demo.biz.utils.DictUtils;
import com.maitianer.demo.common.spring.SpringContextHolder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackageClasses = {DemoBizConfig.class, DemoAdminApplication.class})
@EnableScheduling
public class DemoAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoAdminApplication.class, args);
        DictUtils.loadDictData();
	}

    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

    @Bean
    @ConditionalOnMissingBean
    public CustomUtilsDialect customUtilsDialect() {
        return new CustomUtilsDialect();
    }

    @Bean(name = "shiroDialect")
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

//    @Bean(name = "aliyunFSProvider")
//    public FSProviderSpringFacade condition(){
//        FSProviderSpringFacade fsProviderSpringFacade = new FSProviderSpringFacade();
//        fsProviderSpringFacade.setProvider("aliyun");
//        fsProviderSpringFacade.setUrlPrefix("http://maitianer-test.oss-cn-beijing.aliyuncs.com");
//        fsProviderSpringFacade.setSecretKey("lOdRZhR39zf18jHLVOCFltkr1QXAPy");
//        fsProviderSpringFacade.setPrivated(false);
//        fsProviderSpringFacade.setGroupName("maitianer-test");
//        fsProviderSpringFacade.setEndpoint("oss-cn-beijing.aliyuncs.com");
//        fsProviderSpringFacade.setAccessKey("LTAIGv15vP0lLPk5");
//        return fsProviderSpringFacade;
//    }
}
