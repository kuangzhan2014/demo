package com.maitianer.demo.api.security;

import com.maitianer.demo.api.WebConstants;
import com.maitianer.demo.model.sys.Member;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 处理CurrentUser注解
 * @Author: Mr.Zhang
 * @Date: 2018-09-20 10:26
 */
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        Member member = (Member) nativeWebRequest.getAttribute(WebConstants.ATTR_NAME_CURRENT_USER, RequestAttributes.SCOPE_REQUEST);
        return member;
    }
}
