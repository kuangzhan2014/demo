package com.maitianer.demo.biz.utils;

import com.maitianer.demo.common.utils.lang.StringUtils;
import com.maitianer.demo.core.filesystem.spring.FSProviderSpringFacade;
import com.maitianer.demo.model.ApplicationData;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Locale;

/**
 * @Author Chen
 * @Date 2018/11/29 17:02
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {
    private static ApplicationContext context = null;

    /* (non Javadoc)
     * @Title: setApplicationContext
     * @Description: spring获取bean工具类
     * @param applicationContext
     * @throws BeansException
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.context = applicationContext;
    }

    // 传入线程中
    public static <T> T getBean(String beanName) {
        return (T) context.getBean(beanName);
    }

    // 国际化使用
    public static String getMessage(String key) {
        return context.getMessage(key, null, Locale.getDefault());
    }

    /// 获取当前环境
    public static String getActiveProfile() {
        return context.getEnvironment().getActiveProfiles()[0];
    }

//    public static String getRealAmount(){
//        String profile = getActiveProfile();
//        if(StringUtils.isNoneBlank(profile)){
//            return profile;
//        }else {
//            return null;
//        }
//    }

    @Bean(name = "aliyunFSProvider")
    public FSProviderSpringFacade condition(){
        String profile = getActiveProfile();
        FSProviderSpringFacade fsProviderSpringFacade = new FSProviderSpringFacade();
        fsProviderSpringFacade.setProvider("aliyun");
        fsProviderSpringFacade.setPrivated(false);
        fsProviderSpringFacade.setEndpoint("oss-cn-hangzhou.aliyuncs.com");
        if("prod".equals(profile)) {
        }else {
            //测试环境
            fsProviderSpringFacade.setUrlPrefix("http://rpfc-library-prod.oss-cn-hangzhou.aliyuncs.com");
            fsProviderSpringFacade.setSecretKey("pnzdAs4PDbE67JBKbbj2HII9HlB4Ir");
            fsProviderSpringFacade.setGroupName("rpfc-library-prod");
            fsProviderSpringFacade.setAccessKey("LTAIPfUeH458kDCg");
        }
        // 设置进全局便于获取资源路径
        ApplicationData.get().setFsProviderSpringFacade(fsProviderSpringFacade);
        return fsProviderSpringFacade;
    }


}
