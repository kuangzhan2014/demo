package com.maitianer.demo.model.data;

import com.baomidou.mybatisplus.annotation.TableName;
import com.maitianer.demo.model.common.BaseModel;

/**
 * @Author Chen
 * @Date 2019/8/21 15:09
 */
@TableName("t_category_product")
public class CategoryProductBean extends BaseModel<CategoryProductBean> {

    private Long categoryId;

    private Long productId;

    public CategoryProductBean(Long categoryId, Long productId) {
        this.categoryId = categoryId;
        this.productId = productId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
