package com.maitianer.demo.model.sys;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * User: Leo
 * Date: 2018/1/27 下午10:31
 */
@TableName("sys_role_permission")
public class RolePermission {

    private Long roleId;
    private Long permissionId;

    public Long getRoleId() {
        return roleId;
    }

    public RolePermission setRoleId(Long roleId) {
        this.roleId = roleId;
        return this;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public RolePermission setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
        return this;
    }
}
