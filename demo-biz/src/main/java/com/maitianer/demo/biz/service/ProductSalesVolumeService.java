package com.maitianer.demo.biz.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.maitianer.demo.model.data.ProductSalesVolumeBean;
import com.maitianer.demo.model.datatransfer.ProductSalesDTO;

import java.util.List;

/**
 * @Author Chen
 * @Date 2019/8/13 10:00
 */
public interface ProductSalesVolumeService extends IService<ProductSalesVolumeBean> {

    /**
     * 图表数据
     * @param memberId
     * @param productId
     * @param year
     * @param month
     * @return
     */
    List<ProductSalesVolumeBean>  echartsSales(Long memberId, Long productId, int year, int month);

    void saveOrUpdateData(ProductSalesDTO productSalesDTO);
}
