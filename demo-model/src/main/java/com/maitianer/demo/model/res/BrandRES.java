package com.maitianer.demo.model.res;

import com.maitianer.demo.model.data.BrandBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

/**
 * @Author Chen
 * @Date 2019/8/30 12:14
 */
@ApiModel(description = "品牌模型")
public class BrandRES {

    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("品牌名称")
    private String name;
    @ApiModelProperty("图片")
    private String smallPicture;
    @ApiModelProperty("（flase:未代理，ture：已代理）")
    private boolean status;

    public BrandRES(BrandBean brandBean){
        BeanUtils.copyProperties(brandBean, this);
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

    public String getSmallPicture() {
        return smallPicture;
    }

    public void setSmallPicture(String smallPicture) {
        this.smallPicture = smallPicture;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
