package com.maitianer.demo.model.datatransfer;


import com.maitianer.demo.model.data.CategoryBean;

import java.util.List;

/**
 * @Author Chen
 * @Date 2019/8/13 9:54
 */
public class CategoryDTO extends CategoryBean {

    private List<ProductDTO> productList;

    public List<ProductDTO> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductDTO> productList) {
        this.productList = productList;
    }
}
