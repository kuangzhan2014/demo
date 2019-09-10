package com.maitianer.demo.model.res;

import com.maitianer.demo.model.ApplicationData;
import com.maitianer.demo.model.data.ProductBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

/**
 * @Author Chen
 * @Date 2019/8/30 12:14
 */
@ApiModel(description = "产品模型")
public class ProductRES {

    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("素材名称")
    private String name;
    @ApiModelProperty("图片")
    private String productPicture;
    @ApiModelProperty("描述")
    private String description;

    public ProductRES(ProductBean productBean){
        BeanUtils.copyProperties(productBean, this);
    }

    public String getProductPictureUrl(){
        if (productPicture == null) {
            return null;
        }
        return ApplicationData.get().getOssResourceUrl(productPicture);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductPicture() {
        return productPicture;
    }

    public void setProductPicture(String productPicture) {
        this.productPicture = productPicture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
