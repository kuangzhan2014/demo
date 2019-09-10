package com.maitianer.demo.admin.config;

import com.maitianer.demo.admin.security.MemberRealm;
import com.maitianer.demo.admin.security.filter.CustomFormAuthenticationFilter;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * https://shiro.apache.org/spring-boot.html
 * @Author: Mr.Zhang
 * @Date: 2018-06-13 18:08
 */
@Configuration
public class ShiroConfig {

    /**
     * ShiroFilter主要配置
     *
     * @param defaultWebSecurityManager
     * @return
     */
    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        //自定义拦截器
        Map<String, Filter> filtersMap = new LinkedHashMap<>();
        filtersMap.put("customFormAuthenticationFilter", customFormAuthenticationFilter());
        shiroFilterFactoryBean.setFilters(filtersMap);

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //注意过滤器配置顺序 不能颠倒
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了，登出后跳转配置的loginUrl
        filterChainDefinitionMap.put("/sys/logout", "logout");

        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/sys/forget","anon");
        filterChainDefinitionMap.put("/sys/member/forget", "anon");
        filterChainDefinitionMap.put("/sys/member/checkEmail", "anon");


        // all other paths require a logged in user
        filterChainDefinitionMap.put("/**", "customFormAuthenticationFilter");
        //自动跳去登录的地址
        shiroFilterFactoryBean.setLoginUrl("/sys/login");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {

        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinition("/favicon.ico", "anon");
        chainDefinition.addPathDefinition("/static/**", "anon");
        chainDefinition.addPathDefinition("/sys/logout", "logout");
        chainDefinition.addPathDefinition("/sys/forget", "anon");
        chainDefinition.addPathDefinition("/sys/member/forget", "anon");
        chainDefinition.addPathDefinition("/sys/member/checkEmail", "anon");
        // all other paths require a logged in user
        chainDefinition.addPathDefinition("/**", "authc");

        return chainDefinition;
    }


    @Bean
    public Realm realm() {
        return new MemberRealm();
    }

    @Bean
    protected CacheManager cacheManager() {
        return new MemoryConstrainedCacheManager();
    }

    @Bean
    protected CustomFormAuthenticationFilter customFormAuthenticationFilter() {
        return new CustomFormAuthenticationFilter();
    }
    /**
     * cookie对象
     *
     * @return
     */
    public SimpleCookie rememberMeCookie() {
        // 设置cookie名称，对应login.html页面的<input type="checkbox" name="rememberMe"/>
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        // 设置cookie的过期时间，单位为秒，这里为30min
        cookie.setMaxAge(1800);
        return cookie;
    }

    /**
     * cookie管理对象
     *
     * @return
     */
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        // rememberMe cookie加密的密钥
        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }

    @Bean("defaultWebSecurityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager() {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(realm());
        defaultWebSecurityManager.setRememberMeManager(rememberMeManager());
        return defaultWebSecurityManager;
    }

    @Bean
    public FilterRegistrationBean registrationBean(CustomFormAuthenticationFilter customFormAuthenticationFilter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(customFormAuthenticationFilter);
        // 取消Filter自动注册,不会添加到FilterChain中
        registration.setEnabled(false);
        return registration;
    }
}
