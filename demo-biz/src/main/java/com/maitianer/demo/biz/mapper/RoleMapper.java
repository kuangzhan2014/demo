package com.maitianer.demo.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maitianer.demo.model.sys.Role;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * User: Leo
 * Date: 2018/1/27 下午4:55
 */
@Component
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> selectByMemberId(Long memberId);
}
