package com.maitianer.demo.biz.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maitianer.demo.biz.mapper.CategoryMapper;
import com.maitianer.demo.biz.service.CategoryService;
import com.maitianer.demo.biz.utils.MessageUtils;
import com.maitianer.demo.common.errors.BadRequestAlertException;
import com.maitianer.demo.common.utils.lang.StringUtils;
import com.maitianer.demo.model.DomainConstants;
import com.maitianer.demo.model.data.CategoryBean;
import com.maitianer.demo.model.datatransfer.CategoryDTO;
import com.maitianer.demo.model.query.CategoryQO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Chen
 * @Date 2019/8/13 9:49
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, CategoryBean> implements CategoryService {

    @Override
    public IPage<CategoryBean> pageDate(Page<CategoryBean> pageRequest, CategoryQO categoryQO) {
        QueryWrapper<CategoryBean> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(categoryQO.getCategoryName())) {
            queryWrapper.like("name", categoryQO.getCategoryName());
        }
        if(categoryQO.getBrandId() != null){
            queryWrapper.eq("brand_id", categoryQO.getBrandId());
        }
        queryWrapper.eq("status", DomainConstants.CategoryStatus.NORMAL);
        // 按字段排序
        queryWrapper.orderByAsc("sort").orderByDesc("create_date");
        return baseMapper.selectPage(pageRequest, queryWrapper);
    }

    @Override
    public List<CategoryBean> categoryList(Long brandId) {
        return list(new QueryWrapper<CategoryBean>()
                .eq("brand_id", brandId)
                .eq("status", DomainConstants.CategoryStatus.NORMAL)
                .orderByAsc("sort").orderByDesc("create_date"));
    }
}
