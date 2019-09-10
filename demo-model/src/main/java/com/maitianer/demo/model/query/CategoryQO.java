package com.maitianer.demo.model.query;


import com.maitianer.demo.model.query.base.BaseQO;

/**
 * @Author Chen
 * @Date 2019/8/13 9:54
 */
public class CategoryQO extends BaseQO {

    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
