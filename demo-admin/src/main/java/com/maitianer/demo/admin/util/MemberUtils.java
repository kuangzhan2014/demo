package com.maitianer.demo.admin.util;

import com.maitianer.demo.admin.security.MemberSession;
import com.maitianer.demo.biz.mapper.MemberMapper;
import com.maitianer.demo.common.spring.SpringContextHolder;
import com.maitianer.demo.model.sys.Member;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.subject.Subject;

public class MemberUtils {

    private static MemberMapper memberMapper = SpringContextHolder.getBean(MemberMapper.class);

    /**
     * 获取授权主要对象
     */
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 获取当前登录者对象
     */
    public static MemberSession getMemberSession() {
        try {
            Subject subject = SecurityUtils.getSubject();
            MemberSession memberSession = (MemberSession) subject.getPrincipal();
            if (memberSession != null) {
                return memberSession;
            }
        } catch (UnavailableSecurityManagerException e) {

        } catch (InvalidSessionException e) {

        }
        return null;
    }

    /**
     * 获取当前用户，Member对象
     * @return
     */
    public static Member getCurrentMember() {
        MemberSession memberSession = getMemberSession();
        if (memberSession != null) {
            return memberMapper.selectById(memberSession.getMemberId());
        }
        // TODO 是否需要返回匿名用户？
        return new Member();
    }
}
