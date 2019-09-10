package com.maitianer.demo.biz.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maitianer.demo.model.sys.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * User: Leo
 * Date: 2018/1/27 下午4:56
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission> selectByRoleId(Long roleId);

    List<Permission> selectAllWithParent(@Param("ew") Wrapper<Permission> wrapper);
}
