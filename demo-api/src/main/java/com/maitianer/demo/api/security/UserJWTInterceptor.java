package com.maitianer.demo.api.security;

import com.maitianer.demo.api.WebConstants;
import com.maitianer.demo.biz.service.MemberService;
import com.maitianer.demo.common.errors.AuthenticationException;
import com.maitianer.demo.common.utils.lang.StringUtils;
import com.maitianer.demo.model.DomainConstants;
import com.maitianer.demo.model.sys.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户授权拦截器
 * @Author: Mr.Zhang
 * @Date: 2018-09-20 09:41
 */
@Component
public class UserJWTInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JWTTokenProvider jwtTokenProvider;

    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private MemberService memberService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if (StringUtils.isBlank(token)) {
            token = request.getParameter("token");
        }

        if (StringUtils.isBlank(token)) {
            throw new AuthenticationException();
        }

        token = token.replace(WebConstants.BEARER_PREFIX, "").trim();

        // 校验 TOKEN
        if (!jwtTokenProvider.validateToken(token)) {
            throw new AuthenticationException();
        }

        String subject = jwtTokenProvider.getSubject(token);


        if (null == subject) {
            throw new AuthenticationException();
        }

        Member member = memberService.getById(subject.toString());

        if (member == null) {
            throw new AuthenticationException();
        }

        if (DomainConstants.MEMBER_STATUS_NORMAL != member.getStatus()) {
            throw new AuthenticationException();
        }


        request.setAttribute(WebConstants.ATTR_NAME_CURRENT_USER, member);
        request.setAttribute(WebConstants.ATTR_NAME_USER_ID, member.getId());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }

    public static void main(String[] args) {

        Long userId = 1L;
        String phone = "13588031007";
        JWTTokenProvider jwtTokenProvider = new JWTTokenProvider();
        jwtTokenProvider.init();
        String token = jwtTokenProvider.createToken(userId, phone, true);
        System.out.println(token);
    }
}
