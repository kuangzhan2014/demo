package com.maitianer.demo.admin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: Mr.Zhang
 * @Date: 2018-09-20 09:56
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {


//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        WebContentInterceptor interceptor = new WebContentInterceptor();
//        interceptor.setCacheSeconds(0);
//        interceptor.addCacheMapping(CacheControl.noCache(), "/sys/**");
//        registry.addInterceptor(interceptor);
//    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
    
}
