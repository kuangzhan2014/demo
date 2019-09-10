package com.maitianer.demo.model.datatransfer;


import com.maitianer.demo.model.data.MaterialBean;
import org.springframework.beans.BeanUtils;

/**
 * @Author Chen
 * @Date 2019/8/13 9:55
 */
public class MaterialDTO extends MaterialBean {

    private Long categoryId;

    private Long[] categoryIds;

    private Long productId;

    private Long[] productIds;

    private Integer listType;

    private Integer totalDownloadCount;

    private Integer theMonthDownloadCount;

    private String date;

    // 日期
    private Object[] dateArr;
    // 下载量
    private Object[] downloadCountArr;

    public MaterialDTO() {
    }

    public MaterialDTO(MaterialBean materialBean){
        BeanUtils.copyProperties(materialBean, this);
    }

    public Integer getListType() {
        return listType;
    }

    public void setListType(Integer listType) {
        this.listType = listType;
    }

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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long[] getProductIds() {
        return productIds;
    }

    public void setProductIds(Long[] productIds) {
        this.productIds = productIds;
    }

    public Integer getTotalDownloadCount() {
        return totalDownloadCount;
    }

    public void setTotalDownloadCount(Integer totalDownloadCount) {
        this.totalDownloadCount = totalDownloadCount;
    }

    public Integer getTheMonthDownloadCount() {
        return theMonthDownloadCount;
    }

    public void setTheMonthDownloadCount(Integer theMonthDownloadCount) {
        this.theMonthDownloadCount = theMonthDownloadCount;
    }

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

    public Object[] getDownloadCountArr() {
        return downloadCountArr;
    }

    public void setDownloadCountArr(Object[] downloadCountArr) {
        this.downloadCountArr = downloadCountArr;
    }
}
