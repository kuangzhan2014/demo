package com.maitianer.demo.biz.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maitianer.demo.model.sys.Member;
import org.apache.ibatis.annotations.Param;

/**
 * User: Leo
 * Date: 2018/1/18 下午5:02
 */
public interface MemberMapper extends BaseMapper<Member> {
    IPage<Member> pageData(Page<Member> pageRequest, @Param("ew") Wrapper<Member> wrapper);
}
