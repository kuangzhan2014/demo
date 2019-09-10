package com.maitianer.demo.biz.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maitianer.demo.model.data.BrandBean;
import com.maitianer.demo.model.data.ProductBean;
import com.maitianer.demo.model.datatransfer.ProductDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * User: Leo
 * Date: 2018/9/26 11:29 PM
 */
public interface ProductMapper extends BaseMapper<ProductBean> {

    /**
     * 查询分类中的商品
     *
     * @param pageRequest
     * @param wrapper
     * @return
     */
    IPage<ProductDTO> pageData(Page<ProductDTO> pageRequest, @Param("ew") Wrapper<ProductDTO> wrapper);

}
