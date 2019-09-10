package com.maitianer.demo.biz.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maitianer.demo.biz.mapper.ProductSalesVolumeMapper;
import com.maitianer.demo.biz.service.MemberService;
import com.maitianer.demo.biz.service.ProductSalesVolumeService;
import com.maitianer.demo.biz.service.RoleService;
import com.maitianer.demo.model.data.ProductSalesVolumeBean;
import com.maitianer.demo.model.datatransfer.ProductSalesDTO;
import com.maitianer.demo.model.sys.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Chen
 * @Date 2019/8/13 9:49
 */
@Service
public class ProductSalesVolumeServiceImpl extends ServiceImpl<ProductSalesVolumeMapper, ProductSalesVolumeBean> implements ProductSalesVolumeService {

    @Autowired
    private MemberService memberService;

    @Override
    public List<ProductSalesVolumeBean> echartsSales(Long memberId, Long productId, int year, int month) {
        QueryWrapper<ProductSalesVolumeBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("SUM(sales_volume) as salesVolume, day")
                .eq("product_id", productId).eq("year", year)
                .eq("month", month).groupBy("day");
        if(!memberService.isAdminAccount(memberId)){
            queryWrapper.eq("member_id",memberId);
        }
        return list(queryWrapper);
    }

    @Override
    public void saveOrUpdateData(ProductSalesDTO productSalesDTO) {
        ProductSalesVolumeBean productSales = getOne(new QueryWrapper<ProductSalesVolumeBean>()
                .eq("product_id", productSalesDTO.getProductId())
                .eq("member_id", productSalesDTO.getMemberId())
                .eq("year", productSalesDTO.getYear())
                .eq("month", productSalesDTO.getMonth())
                .eq("day", productSalesDTO.getDay()));
        if(null != productSales){
            productSales.setSalesVolume(productSales.getSalesVolume()+productSalesDTO.getSalesVolume());
            updateById(productSales);
        }else{
            save(productSalesDTO);
        }
    }
}
