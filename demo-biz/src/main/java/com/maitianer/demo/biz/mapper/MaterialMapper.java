package com.maitianer.demo.biz.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maitianer.demo.model.data.BrandBean;
import com.maitianer.demo.model.data.MaterialBean;
import com.maitianer.demo.model.datatransfer.MaterialDTO;
import com.maitianer.demo.model.datatransfer.ProductDTO;
import org.apache.ibatis.annotations.Param;

/**
 * User: Leo
 * Date: 2018/9/26 11:29 PM
 */
public interface MaterialMapper extends BaseMapper<MaterialBean> {
    /**
     * 查询素材
     *
     * @param pageRequest
     * @param wrapper
     * @return
     */
    IPage<MaterialDTO> pageData(Page<MaterialDTO> pageRequest, @Param("ew") Wrapper<MaterialDTO> wrapper);

}
