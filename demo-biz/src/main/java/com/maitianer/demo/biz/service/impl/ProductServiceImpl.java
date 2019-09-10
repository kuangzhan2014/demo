package com.maitianer.demo.biz.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maitianer.demo.biz.mapper.ProductMapper;
import com.maitianer.demo.biz.service.CategoryProductService;
import com.maitianer.demo.biz.service.ProductService;
import com.maitianer.demo.common.utils.lang.StringUtils;
import com.maitianer.demo.model.DomainConstants;
import com.maitianer.demo.model.data.CategoryProductBean;
import com.maitianer.demo.model.data.ProductBean;
import com.maitianer.demo.model.datatransfer.ProductDTO;
import com.maitianer.demo.model.query.ProductQO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Chen
 * @Date 2019/8/13 9:49
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, ProductBean> implements ProductService {

    @Autowired
    private CategoryProductService categoryProductService;

    @Override
    public IPage<ProductDTO> pageData(Page<ProductDTO> pageRequest, ProductQO productQO) {
        QueryWrapper<ProductDTO> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(productQO.getName())) {
            queryWrapper.like("p.name", productQO.getName());
        }
        if (null != productQO.getBrandId() && productQO.getBrandId() > 0) {
            queryWrapper.eq("p.brand_id", productQO.getBrandId());
        }
        if (null != productQO.getCategoryId() && productQO.getCategoryId() > 0) {
            queryWrapper.exists("select 1 from t_category_product cp where cp.product_id = p.id and cp.category_id = " + productQO.getCategoryId());
        }
        queryWrapper.eq("p.status", DomainConstants.ProductStatus.NORMAL)
                .orderByAsc("p.sort").orderByDesc("p.create_date");
        IPage<ProductDTO> page = baseMapper.pageData(pageRequest, queryWrapper);
        page.getRecords().forEach(bean -> {
            bean.setProductPicture(bean.getProductPictureUrl()+"?x-oss-process=image/resize,m_mfit,w_140,h_100");
        });
        return page;
    }

    @Override
    public boolean saveData(ProductDTO productDTO) {
        boolean result;
        productDTO.setStatus(DomainConstants.ProductStatus.NORMAL);
        if (null != productDTO.getId()) {
            categoryProductService.remove(new QueryWrapper<CategoryProductBean>().eq("product_id", productDTO.getId()));
            result = updateById(productDTO);
        } else {
            result = save(productDTO);
        }

        if (null != productDTO.getCategoryIds() && productDTO.getCategoryIds().length > 0) {
            for (int i = 0; i < productDTO.getCategoryIds().length; i++) {
                CategoryProductBean categoryProductBean = new CategoryProductBean(productDTO.getCategoryIds()[i], productDTO.getId());
                categoryProductService.save(categoryProductBean);
            }
        }
        return result;
    }

    @Override
    public List<ProductBean> productList(Long brandId) {
        return list(new QueryWrapper<ProductBean>()
                .eq("brand_id", brandId)
                .eq("status", DomainConstants.CategoryStatus.NORMAL)
                .orderByAsc("sort").orderByDesc("create_date"));
    }
}
