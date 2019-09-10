package com.maitianer.demo.admin.security.filter;

import com.maitianer.demo.admin.security.token.CustomUsernamePasswordToken;
import com.maitianer.demo.common.utils.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @Author yuzhe
 * @Date 2019/5/20 12:40:47
 **/
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String username = getUsername(request);
        String password = getPassword(request);
        Long brandId =null;
        String brand = request.getParameter("brandId");
        if(StringUtils.isNotBlank(brand)){
            brandId = Long.valueOf(brand);
        }
        return new CustomUsernamePasswordToken(username, password, brandId);
    }

}
