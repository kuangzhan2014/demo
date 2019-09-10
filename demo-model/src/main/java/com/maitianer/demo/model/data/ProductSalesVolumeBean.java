package com.maitianer.demo.model.data;

import com.baomidou.mybatisplus.annotation.TableName;
import com.maitianer.demo.model.common.BaseModel;

/**
 * @Author Chen
 * @Date 2019/8/13 10:00
 */
@TableName("t_product_sales_volume")
public class ProductSalesVolumeBean  extends BaseModel<ProductSalesVolumeBean> {
    /**
     * 用户id
     */
    private Long memberId;
    /**
     * 产品id
     */
    private Long productId;
    /**
     * 年
     */
    private int year;
    /**
     * 月
     */
    private int month;
    /**
     * 日
     */
    private int day;
    /**
     * 销售量
     */
    private int salesVolume;

    public String getDayLabel(){
        if(day < 10){
            return "0"+String.valueOf(day);
        }
        return String.valueOf(day);
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(int salesVolume) {
        this.salesVolume = salesVolume;
    }
}
