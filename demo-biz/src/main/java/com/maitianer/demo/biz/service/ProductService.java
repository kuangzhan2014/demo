package com.maitianer.demo.biz.service;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maitianer.demo.model.data.CategoryBean;
import com.maitianer.demo.model.data.MaterialBean;
import com.maitianer.demo.model.data.ProductBean;
import com.maitianer.demo.model.datatransfer.ProductDTO;
import com.maitianer.demo.model.query.MaterialQO;
import com.maitianer.demo.model.query.ProductQO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author Chen
 * @Date 2019/8/13 9:59
 */
public interface ProductService  extends IService<ProductBean> {

    IPage<ProductDTO> pageData(Page<ProductDTO> pageRequest, ProductQO productQO);

    boolean saveData(ProductDTO productDTO);

    List<ProductBean> productList(Long brandId);
}
