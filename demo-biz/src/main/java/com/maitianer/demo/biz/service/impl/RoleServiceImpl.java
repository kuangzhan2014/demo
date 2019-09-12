package com.maitianer.demo.biz.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maitianer.demo.biz.mapper.MemberRoleMapper;
import com.maitianer.demo.biz.mapper.RoleMapper;
import com.maitianer.demo.biz.mapper.RolePermissionMapper;
import com.maitianer.demo.biz.service.RoleService;
import com.maitianer.demo.model.DomainConstants;
import com.maitianer.demo.model.sys.Member;
import com.maitianer.demo.model.sys.MemberRole;
import com.maitianer.demo.model.sys.Role;
import com.maitianer.demo.model.sys.RolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * User: Leo
 * Date: 2018/1/27 下午4:54
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private MemberRoleMapper memberRoleMapper;

    @Override
    public boolean deleteData(Long id) {
        Role role=getOne(new QueryWrapper<Role>().eq("id",id));
        if(DomainConstants.DEFAULT_SYSTEM_ADMIN_ROLE_ID!=id){
            role.setStatus(DomainConstants.ROLE_STATUS_DELETE);
            memberRoleMapper.delete(Wrappers.<MemberRole>lambdaQuery().eq(MemberRole::getRoleId, id));
        }
        Boolean result = updateById(role);
        return result;
    }

    @Override
    public boolean deleteBatchIds(Long[] ids) {
        for (Long id : ids) {
            if (DomainConstants.DEFAULT_SYSTEM_ADMIN_ROLE_ID == id) {
                continue;
            }
            memberRoleMapper.delete(Wrappers.<MemberRole>lambdaQuery().eq(MemberRole::getRoleId, id));
        }
        return super.removeByIds(Arrays.asList(ids));
    }

    @Override
    public List<Role> findByMemberId(Long memberId) {
        return baseMapper.selectByMemberId(memberId);
    }

    @Override
    public Role findByCode(String code) {
        return getOne(Wrappers.<Role>lambdaQuery().eq(Role::getCode, code), false);
    }

    @Override
    public Role saveRole(Role role, Long[] permissionIds) {

        saveOrUpdate(role);

        // 若更新先清空原权限
        if (role.getId() != null) {
            rolePermissionMapper.delete(Wrappers.<RolePermission>lambdaQuery().eq(RolePermission::getRoleId, role.getId()));
        }

        if (permissionIds != null) {
            for (Long permissionId : permissionIds) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRoleId(role.getId());
                rolePermission.setPermissionId(permissionId);
                rolePermissionMapper.insert(rolePermission);
            }
        }

        return role;
    }

}
