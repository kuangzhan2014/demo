package com.maitianer.demo.model.sys;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * User: Leo
 * Date: 2018/1/27 下午10:31
 */
@TableName("sys_member_role")
public class MemberRole {

    private Long memberId;
    private Long roleId;

    public Long getMemberId() {
        return memberId;
    }

    public MemberRole setMemberId(Long memberId) {
        this.memberId = memberId;
        return this;
    }

    public Long getRoleId() {
        return roleId;
    }

    public MemberRole setRoleId(Long roleId) {
        this.roleId = roleId;
        return this;
    }
}
