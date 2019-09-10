package com.maitianer.demo.model.query;


import com.maitianer.demo.model.query.base.BaseQO;

/**
 * @Author Chen
 * @Date 2019/8/13 9:59
 */
public class ProductQO extends BaseQO {

    private String name;

    private Long categoryId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
