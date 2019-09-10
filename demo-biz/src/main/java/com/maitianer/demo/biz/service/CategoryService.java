package com.maitianer.demo.biz.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maitianer.demo.model.data.CategoryBean;
import com.maitianer.demo.model.datatransfer.CategoryDTO;
import com.maitianer.demo.model.query.CategoryQO;

import java.util.List;

/**
 * @Author Chen
 * @Date 2019/8/13 9:54
 */
public interface CategoryService extends IService<CategoryBean> {

    /**
     * 分页查询分类列表
     *
     * @param pageRequest
     * @param categoryQO
     * @return
     */
    IPage<CategoryBean> pageDate(Page<CategoryBean> pageRequest, CategoryQO categoryQO);


    List<CategoryBean> categoryList(Long brandId);
}
