package com.maitianer.demo.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maitianer.demo.model.sys.Role;

import java.util.List;

/**
 * User: Leo
 * Date: 2018/1/27 下午4:54
 */
public interface RoleService extends IService<Role> {

    boolean deleteBatchIds(Long[] ids);

    boolean deleteData(Long id);

    List<Role> findByMemberId(Long memberId);

    Role findByCode(String code);

    Role saveRole(Role role, Long[] permissionIds);


}
