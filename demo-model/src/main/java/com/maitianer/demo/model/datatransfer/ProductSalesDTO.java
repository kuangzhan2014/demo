package com.maitianer.demo.model.datatransfer;

import com.maitianer.demo.model.data.ProductSalesVolumeBean;

/**
 * @Author Chen
 * @Date 2019/9/5 9:06
 */
public class ProductSalesDTO extends ProductSalesVolumeBean{


    private String date;

    // 日期
    private Object[] dateArr;
    // 下载量
    private Object[] productSalesArr;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Object[] getDateArr() {
        return dateArr;
    }

    public void setDateArr(Object[] dateArr) {
        this.dateArr = dateArr;
    }

    public Object[] getProductSalesArr() {
        return productSalesArr;
    }

    public void setProductSalesArr(Object[] productSalesArr) {
        this.productSalesArr = productSalesArr;
    }
}
