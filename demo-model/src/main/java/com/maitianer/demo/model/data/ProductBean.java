package com.maitianer.demo.model.data;

import com.baomidou.mybatisplus.annotation.TableName;
import com.maitianer.demo.common.utils.lang.DateUtils;
import com.maitianer.demo.model.ApplicationData;
import com.maitianer.demo.model.common.BaseModel;

/**
 * @Author Chen
 * @Date 2019/8/13 9:59
 */
@TableName("t_product")
public class ProductBean  extends BaseModel<ProductBean> {
    /**
     * 品牌id
     */
    private Long brandId;
    /**
     * 名称
     */
    private String name;
    /**
     * 配方
     */
    private String formula;
    /**
     * 描述
     */
    private String description;
    /**
     * 产品图片
     */
    private String productPicture;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 状态（0：删除，1：正常）
     */
    private boolean status;

    public String getProductPictureUrl(){
        if (productPicture == null) {
            return null;
        }
        return ApplicationData.get().getOssResourceUrl(productPicture);
    }

    public String getCreateDateLabel(){
        return DateUtils.formatDate(getCreateDate(),"yyyy-MM-dd");
    }


    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductPicture() {
        return productPicture;
    }

    public void setProductPicture(String productPicture) {
        this.productPicture = productPicture;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
