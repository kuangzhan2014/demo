package com.maitianer.demo.model.data;

import com.baomidou.mybatisplus.annotation.TableName;
import com.maitianer.demo.model.ApplicationData;
import com.maitianer.demo.model.DomainConstants;
import com.maitianer.demo.model.common.BaseModel;

/**
 * @Author Chen
 * @Date 2019/8/13 9:54
 */
@TableName("t_category")
public class CategoryBean extends BaseModel<CategoryBean> {
    /**
     * 品牌id
     */
    private Long brandId;
    /**
     * 名称
     */
    private String name;
    /**
     * 分类类型
     */
    private Integer type;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 状态（0：删除，1：正常）
     */
    private boolean status;

    public String getTypeLabel() {
        return ApplicationData.get().getDictLabel(DomainConstants.DICT_GROUP_CATEGORY_TYPE, type);
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
