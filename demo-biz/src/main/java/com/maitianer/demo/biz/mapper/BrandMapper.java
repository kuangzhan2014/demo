package com.maitianer.demo.biz.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maitianer.demo.model.data.BrandBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * User: Leo
 * Date: 2018/9/26 11:29 PM
 */
public interface BrandMapper extends BaseMapper<BrandBean> {

    List<BrandBean> listMemberBrandByMemberId(@Param("ew") Wrapper<BrandBean> wrapper);
}
