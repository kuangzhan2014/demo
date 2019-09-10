package com.maitianer.demo.biz.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maitianer.demo.model.sys.Area;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * User: Leo
 * Date: 2018/9/26 11:29 PM
 */
public interface AreaMapper extends BaseMapper<Area> {
    List<Area> selectArea(@Param("ew") Wrapper<Area> wrapper);

}
