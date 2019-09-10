package com.maitianer.demo.biz.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maitianer.demo.biz.mapper.BrandMapper;
import com.maitianer.demo.biz.service.BrandService;
import com.maitianer.demo.biz.service.MemberBrandService;
import com.maitianer.demo.common.utils.lang.StringUtils;
import com.maitianer.demo.model.DomainConstants;
import com.maitianer.demo.model.data.BrandBean;
import com.maitianer.demo.model.data.MaterialBean;
import com.maitianer.demo.model.query.BrandQO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Chen
 * @Date 2019/8/13 9:49
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, BrandBean> implements BrandService {

    @Override
    public List<BrandBean> listMemberBrandByMemberId(Long memberId) {
        return baseMapper.listMemberBrandByMemberId(new QueryWrapper<BrandBean>()
                .eq("mb.member_id",memberId).eq("b.status",DomainConstants.BrandStatus.NORMAL)
                .orderByAsc("b.sort").orderByDesc("b.create_date"));
    }

    @Override
    public IPage<BrandBean> pageData(Page<BrandBean> pageRequest, BrandQO brandQO) {
        QueryWrapper<BrandBean> queryWrapper = new QueryWrapper();
        if (StringUtils.isNotBlank(brandQO.getName())) {
            queryWrapper.like("name", brandQO.getName());
        }
        queryWrapper.eq("status", DomainConstants.BrandStatus.NORMAL)
                .orderByAsc("sort").orderByDesc("create_date");
        IPage<BrandBean> page = page(pageRequest, queryWrapper);
        return page;
    }
}
