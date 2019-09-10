package com.maitianer.demo.model.res;

import com.maitianer.demo.model.data.CategoryBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

/**
 * @Author Chen
 * @Date 2019/8/30 12:14
 */
@ApiModel(description = "分类模型")
public class CategoryRES {

    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("素材名称")
    private String name;

    public CategoryRES(CategoryBean categoryBean){
        BeanUtils.copyProperties(categoryBean, this);
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
}
