package com.maitianer.demo.biz.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maitianer.demo.model.data.BrandBean;
import com.maitianer.demo.model.data.NoticeBean;
import com.maitianer.demo.model.datatransfer.NoticeDTO;
import org.apache.ibatis.annotations.Param;

/**
 * User: Leo
 * Date: 2018/9/26 11:29 PM
 */
public interface NoticeMapper extends BaseMapper<NoticeBean> {
    IPage<NoticeDTO> pageData(Page<NoticeDTO> pageRequest, @Param("ew") Wrapper<NoticeDTO> wrapper);
}
