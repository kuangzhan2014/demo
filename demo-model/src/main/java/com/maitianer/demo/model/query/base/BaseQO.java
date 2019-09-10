package com.maitianer.demo.model.query.base;

/**
 * @Author yuzhe
 * @Date 2019/1/25 11:08
 **/
public class BaseQO {
    public final static String ORDER_DIRECTION_ASC = "asc";

    // 排序字段
    private String orderField;
    // 排序顺序
    private String orderDirection;
    // 日期区间：可包含时间
    private String dateRange;
    // 品牌id
    private Long brandId;

    public Boolean isAsc() {
        return ORDER_DIRECTION_ASC.equalsIgnoreCase(orderDirection);
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public String getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
    }

    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }
}
