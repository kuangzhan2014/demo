package com.maitianer.demo.model.data;

import com.baomidou.mybatisplus.annotation.TableName;
import com.maitianer.demo.model.common.BaseModel;

/**
 * @Author Chen
 * @Date 2019/8/22 10:26
 */
@TableName("t_category_material")
public class CategoryMaterialBean extends BaseModel<CategoryMaterialBean> {

    private Long categoryId;

    private Long materialId;

    public CategoryMaterialBean(Long categoryId, Long materialId) {
        this.categoryId = categoryId;
        this.materialId = materialId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }
}
