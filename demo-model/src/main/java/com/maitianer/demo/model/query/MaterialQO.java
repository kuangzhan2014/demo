package com.maitianer.demo.model.query;


import com.maitianer.demo.model.ApplicationData;
import com.maitianer.demo.model.DomainConstants;
import com.maitianer.demo.model.query.base.BaseQO;

/**
 * @Author Chen
 * @Date 2019/8/13 9:55
 */
public class MaterialQO extends BaseQO {

    private String name;

    private Long brandId;

    private Long categoryId;

    private Long productId;

    private Integer listType;

    private Integer type;

    public String getTypeLabel(){
        return ApplicationData.get().getDictLabel(DomainConstants.DICT_GROUP_MATERIAL_TYPE, type);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Long getBrandId() {
        return brandId;
    }

    @Override
    public void setBrandId(Long brandId) {
        this.brandId = brandId;
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

    public Integer getListType() {
        return listType;
    }

    public void setListType(Integer listType) {
        this.listType = listType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
