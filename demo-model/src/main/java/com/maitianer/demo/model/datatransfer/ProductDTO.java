package com.maitianer.demo.model.datatransfer;


import com.maitianer.demo.model.data.ProductBean;

/**
 * @Author Chen
 * @Date 2019/8/13 9:59
 */
public class ProductDTO extends ProductBean {

    private Long categoryId;

    private Long [] categoryIds;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long[] getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(Long[] categoryIds) {
        this.categoryIds = categoryIds;
    }
}
