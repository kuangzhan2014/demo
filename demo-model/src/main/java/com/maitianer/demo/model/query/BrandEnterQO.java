package com.maitianer.demo.model.query;


import com.maitianer.demo.model.query.base.BaseQO;

/**
 * @Author Chen
 * @Date 2019/8/13 9:49
 */
public class BrandEnterQO extends BaseQO {

    private String brandName;

    private String initiatorName;

    private Integer status;

    private Integer result;

    private String handlerName;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getInitiatorName() {
        return initiatorName;
    }

    public void setInitiatorName(String initiatorName) {
        this.initiatorName = initiatorName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getHandlerName() {
        return handlerName;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }
}
