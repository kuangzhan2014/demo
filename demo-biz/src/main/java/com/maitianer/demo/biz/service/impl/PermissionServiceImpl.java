package com.maitianer.demo.biz.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maitianer.demo.biz.mapper.PermissionMapper;
import com.maitianer.demo.biz.mapper.RolePermissionMapper;
import com.maitianer.demo.biz.service.PermissionService;
import com.maitianer.demo.model.sys.Permission;
import com.maitianer.demo.model.sys.RolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;

/**
 * User: Leo
 * Date: 2018/1/27 下午4:57
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    RolePermissionMapper rolePermissionMapper;

    @Override
    public boolean deleteBatchIds(Long[] ids) {
        Assert.notNull(ids, "ids not null");
        for (Long id : ids) {
            rolePermissionMapper.delete(Wrappers.<RolePermission>lambdaQuery().eq(RolePermission::getPermissionId, id));
        }
        return super.removeByIds(Arrays.asList(ids));
    }

    @Override
    public List<Permission> findByRoleId(Long roleId) {
        return baseMapper.selectByRoleId(roleId);
    }

    @Override
    public List<Permission> findRootPermissions() {
        List<Permission> permissions = list(Wrappers.<Permission>lambdaQuery().isNull(Permission::getParentId));
        for (Permission permission : permissions) {
            permission.setSubPermissions(list(Wrappers.<Permission>lambdaQuery().
                    eq(Permission::getParentId, permission.getId())));
        }
        return permissions;
    }

    @Override
    public Permission findByPermissionValue(String permissionValue) {
        return getOne(Wrappers.<Permission>lambdaQuery().eq(Permission::getPermissionValue, permissionValue), false);
    }

    @Override
    public List<Permission> findAll(Wrapper<Permission> wrapper) {
        return baseMapper.selectAllWithParent(wrapper);
    }
}
