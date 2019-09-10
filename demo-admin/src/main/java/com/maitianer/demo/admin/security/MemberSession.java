package com.maitianer.demo.admin.security;

import java.io.Serializable;

/**
 * User: Leo
 * Date: 16/4/9 下午4:48
 * @author ADMIN
 */
public class MemberSession implements Serializable {

    private Long memberId;
    private String loginName;
    private Long currentBrandId;
    // 系统环境：prod 生成环境 dev 开发测试环境
    private String systemEnvironment;

    public MemberSession() {
    }

    public MemberSession(Long memberId, String loginName) {
        this.memberId = memberId;
        this.loginName = loginName;
    }

    public MemberSession(Long memberId, String loginName, Long currentBrandId) {
        this(memberId, loginName);
        this.currentBrandId = currentBrandId;
    }

    public MemberSession(Long memberId, String loginName, Long currentBrandId, String systemEnvironment) {
        this(memberId, loginName, currentBrandId);
        this.systemEnvironment= systemEnvironment;
    }

    public Long getMemberId() {
        return memberId;
    }

    public MemberSession setMemberId(Long memberId) {
        this.memberId = memberId;
        return this;
    }

    public String getLoginName() {
        return loginName;
    }

    public MemberSession setLoginName(String loginName) {
        this.loginName = loginName;
        return this;
    }

    public Long getCurrentBrandId() {
        return currentBrandId;
    }

    public void setCurrentBrandId(Long currentBrandId) {
        this.currentBrandId = currentBrandId;
    }

    public String getSystemEnvironment() {
        return systemEnvironment;
    }

    public void setSystemEnvironment(String systemEnvironment) {
        this.systemEnvironment = systemEnvironment;
    }
}
