package com.maitianer.demo.biz.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maitianer.demo.model.data.BrandBean;
import com.maitianer.demo.model.data.BrandEnterBean;
import com.maitianer.demo.model.query.BrandEnterQO;
import com.maitianer.demo.model.query.BrandQO;

/**
 * @Author Chen
 * @Date 2019/8/13 9:49
 */
public interface BrandEnterService  extends IService<BrandEnterBean> {

    IPage<BrandEnterBean> pageData(Page<BrandEnterBean> pageRequest, BrandEnterQO brandEnterQO);

}
