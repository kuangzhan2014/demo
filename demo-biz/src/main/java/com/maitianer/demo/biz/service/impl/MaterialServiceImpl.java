package com.maitianer.demo.biz.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maitianer.demo.biz.mapper.MaterialMapper;
import com.maitianer.demo.biz.service.CategoryMaterialService;
import com.maitianer.demo.biz.service.MaterialService;
import com.maitianer.demo.biz.service.MemberDownloadService;
import com.maitianer.demo.biz.service.ProductMaterialService;
import com.maitianer.demo.common.utils.lang.StringUtils;
import com.maitianer.demo.model.DomainConstants;
import com.maitianer.demo.model.data.CategoryMaterialBean;
import com.maitianer.demo.model.data.CategoryProductBean;
import com.maitianer.demo.model.data.MaterialBean;
import com.maitianer.demo.model.data.ProductMaterialBean;
import com.maitianer.demo.model.datatransfer.MaterialDTO;
import com.maitianer.demo.model.datatransfer.ProductDTO;
import com.maitianer.demo.model.query.MaterialQO;
import com.maitianer.demo.model.query.ProductQO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

import static jdk.nashorn.internal.objects.NativeArray.lastIndexOf;

/**
 * @Author Chen
 * @Date 2019/8/13 9:49
 */
@Service
public class MaterialServiceImpl extends ServiceImpl<MaterialMapper, MaterialBean> implements MaterialService {

    @Autowired
    private CategoryMaterialService categoryMaterialService;
    @Autowired
    private ProductMaterialService productMaterialService;

    @Override
    public IPage<MaterialDTO> pageData(Page<MaterialDTO> pageRequest, MaterialQO materialQO) {
        QueryWrapper<MaterialDTO> queryWrapper = new QueryWrapper();
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        if (StringUtils.isNotBlank(materialQO.getName())) {
            queryWrapper.like("m.name", materialQO.getName());
        }
        if (null != materialQO.getType() && materialQO.getType() > 0) {
            queryWrapper.eq("m.type", materialQO.getType());
        }
        if (null != materialQO.getBrandId() && materialQO.getBrandId() > 0) {
            queryWrapper.eq("brand_id", materialQO.getBrandId());
        }
        if (null != materialQO.getProductId() && materialQO.getProductId() > 0) {
            queryWrapper.exists("select 1 from t_product_material pm where pm.material_id = m.id and pm.product_id = " + materialQO.getProductId());
        } else {
            if (null != materialQO.getCategoryId() && materialQO.getCategoryId() > 0) {
                queryWrapper.exists("select 1 from t_category_material cm where cm.material_id = m.id and cm.category_id = " + materialQO.getCategoryId());
            }
        }
        queryWrapper.eq("md.year", year);
        queryWrapper.eq("md.month", month);
        queryWrapper.eq("m.status", DomainConstants.MaterialStatus.NORMAL)
                .groupBy("m.id");
        // 按字段排序
        String orderField = materialQO.getOrderField();
        if (StringUtils.isNotBlank(orderField)) {
            String column = StringUtils.toUnderScoreCase(orderField);
            boolean isAsc = DomainConstants.OrderDirection.ASC.equals(materialQO.getOrderDirection());
            queryWrapper.orderBy(true, isAsc, column);
        }
        IPage<MaterialDTO> page = baseMapper.pageData(pageRequest, queryWrapper);
        page.getRecords().forEach(bean -> {
            if (DomainConstants.MaterialType.TEXT != bean.getType()) {
                bean.setShowPicture(bean.getShowPictureUrl() + "?x-oss-process=image/resize,m_mfit,w_140,h_100");
            }
        });
        return page;
    }

    @Override
    public MaterialDTO saveData(MaterialDTO materialDTO) {
        materialDTO.setStatus(DomainConstants.MaterialStatus.NORMAL);
        if(DomainConstants.MaterialType.VIDEO == materialDTO.getType()){
            materialDTO.setShowPicture("image/"+materialDTO.getVideoUrl()+"jpg");
        }
        if (null != materialDTO.getId() && materialDTO.getId() > 0) {
            categoryMaterialService.remove(new QueryWrapper<CategoryMaterialBean>().eq("material_id", materialDTO.getId()));
            productMaterialService.remove(new QueryWrapper<ProductMaterialBean>().eq("material_id", materialDTO.getId()));
            updateById(materialDTO);
        } else {
            save(materialDTO);
        }
        if (null != materialDTO.getCategoryIds() && materialDTO.getCategoryIds().length > 0) {
            for (int i = 0; i < materialDTO.getCategoryIds().length; i++) {
                CategoryMaterialBean categoryProductBean = new CategoryMaterialBean(materialDTO.getCategoryIds()[i], materialDTO.getId());
                categoryMaterialService.save(categoryProductBean);
            }
        }
        if (null != materialDTO.getProductIds() && materialDTO.getCategoryIds().length > 0) {
            for (int i = 0; i < materialDTO.getProductIds().length; i++) {
                ProductMaterialBean productMaterialBean = new ProductMaterialBean(materialDTO.getProductIds()[i], materialDTO.getId());
                productMaterialService.save(productMaterialBean);
            }
        }
        return materialDTO;
    }
}
