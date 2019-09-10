package com.maitianer.demo.biz.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maitianer.demo.biz.mapper.BrandEnterMapper;
import com.maitianer.demo.biz.service.BrandEnterService;
import com.maitianer.demo.common.utils.lang.StringUtils;
import com.maitianer.demo.model.DomainConstants;
import com.maitianer.demo.model.data.BrandBean;
import com.maitianer.demo.model.data.BrandEnterBean;
import com.maitianer.demo.model.query.BrandEnterQO;
import com.maitianer.demo.model.query.BrandQO;
import org.springframework.stereotype.Service;

/**
 * @Author Chen
 * @Date 2019/8/13 9:49
 */
@Service
public class BrandEnterServiceImpl extends ServiceImpl<BrandEnterMapper, BrandEnterBean> implements BrandEnterService {

    @Override
    public IPage<BrandEnterBean> pageData(Page<BrandEnterBean> pageRequest, BrandEnterQO brandEnterQO) {
        QueryWrapper<BrandEnterBean> queryWrapper = new QueryWrapper();
        if (StringUtils.isNotBlank(brandEnterQO.getBrandName())) {
            queryWrapper.like("brand_name", brandEnterQO.getBrandName());
        }
        if (StringUtils.isNotBlank(brandEnterQO.getHandlerName())) {
            queryWrapper.like("handler_name", brandEnterQO.getHandlerName());
        }
        if (StringUtils.isNotBlank(brandEnterQO.getInitiatorName())) {
            queryWrapper.like("initiator_name", brandEnterQO.getInitiatorName());
        }
        if (null != brandEnterQO.getStatus()) {
            queryWrapper.eq("status", brandEnterQO.getStatus());
        }
        if (null != brandEnterQO.getResult()) {
            queryWrapper.eq("result", brandEnterQO.getResult());
        }
        queryWrapper.orderByDesc("create_date");
        IPage<BrandEnterBean> page = page(pageRequest, queryWrapper);
        return page;
    }
}
