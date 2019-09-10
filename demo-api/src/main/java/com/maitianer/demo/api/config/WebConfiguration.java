package com.maitianer.demo.api.config;

import com.maitianer.demo.api.security.CurrentUserMethodArgumentResolver;
import com.maitianer.demo.api.security.UserJWTInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @Author: Mr.Zhang
 * @Date: 2018-09-20 09:56
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Autowired
    private UserJWTInterceptor userJWTInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userJWTInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/users/login")
                .excludePathPatterns("/webjars/springfox-swagger-ui/**")
                .excludePathPatterns("/swagger-resources/**")
                .excludePathPatterns("/swagger-ui.html")
                .excludePathPatterns("/actuator/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new CurrentUserMethodArgumentResolver());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

}
