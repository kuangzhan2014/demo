package com.maitianer.demo.biz.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maitianer.demo.model.data.BrandBean;
import com.maitianer.demo.model.data.MaterialBean;
import com.maitianer.demo.model.query.BrandQO;
import com.maitianer.demo.model.query.MaterialQO;

import java.util.List;

/**
 * @Author Chen
 * @Date 2019/8/13 9:47
 */
public interface BrandService extends IService<BrandBean> {

    /**
     * 根据当前登录用户获取品牌列表
     * @param memberId
     * @return
     */
    List<BrandBean> listMemberBrandByMemberId(Long memberId);

    IPage<BrandBean> pageData(Page<BrandBean> pageRequest, BrandQO brandQO);
}
