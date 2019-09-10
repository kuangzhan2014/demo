package com.maitianer.demo.biz.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maitianer.demo.model.sys.Permission;

import java.util.List;

/**
 * User: Leo
 * Date: 2018/1/27 下午4:57
 */
public interface PermissionService extends IService<Permission> {

    boolean deleteBatchIds(Long[] ids);

    List<Permission> findByRoleId(Long roleId);

    List<Permission> findRootPermissions();

    Permission findByPermissionValue(String permissionValue);

    List<Permission> findAll(Wrapper<Permission> wrapper);


}
