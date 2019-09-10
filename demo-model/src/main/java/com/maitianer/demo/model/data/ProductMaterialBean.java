package com.maitianer.demo.model.data;

import com.baomidou.mybatisplus.annotation.TableName;
import com.maitianer.demo.model.common.BaseModel;

/**
 * @Author Chen
 * @Date 2019/8/22 10:27
 */
@TableName("t_product_material")
public class ProductMaterialBean extends BaseModel<ProductMaterialBean> {

    private Long productId;

    private Long materialId;

    public ProductMaterialBean(Long productId, Long materialId) {
        this.productId = productId;
        this.materialId = materialId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }
}
