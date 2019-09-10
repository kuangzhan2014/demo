package com.maitianer.demo.model.data;

import com.baomidou.mybatisplus.annotation.TableName;
import com.maitianer.demo.model.ApplicationData;
import com.maitianer.demo.model.DomainConstants;
import com.maitianer.demo.model.common.BaseModel;

import java.util.Date;

/**
 * @Author Chen
 * @Date 2019/8/13 9:49
 */
@TableName("t_brand_enter")
public class BrandEnterBean  extends BaseModel<BrandEnterBean>{
    /**
     *品牌id
     */
    private Long brandId;
    /**
     *品牌名称
     */
    private String brandName;
    /**
     *发起者id
     */
    private Long initiatorId;
    /**
     *发起者名称
     */
    private String initiatorName;
    /**
     *处理人id
     */
    private Long handlerId;
    /**
     *处理人名称
     */
    private String handlerName;
    /**
     *状态（0：未处理，1：已处理）
     */
    private Integer status;
    /**
     *结果（0：未通过，1：通过）
     */
    private Integer result;
    /**
     *备注
     */
    private String remark;
    /**
     *处理时间
     */
    private Date handleDate;

    public String getStatusLabel(){
        return ApplicationData.get().getDictLabel(DomainConstants.DICT_GROUP_BRAND_ENTER_STATUS, status);
    }

    public String getResultLabel(){
        return ApplicationData.get().getDictLabel(DomainConstants.DICT_GROUP_BRAND_ENTER_RESULT, result);
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Long getInitiatorId() {
        return initiatorId;
    }

    public void setInitiatorId(Long initiatorId) {
        this.initiatorId = initiatorId;
    }

    public String getInitiatorName() {
        return initiatorName;
    }

    public void setInitiatorName(String initiatorName) {
        this.initiatorName = initiatorName;
    }

    public Long getHandlerId() {
        return handlerId;
    }

    public void setHandlerId(Long handlerId) {
        this.handlerId = handlerId;
    }

    public String getHandlerName() {
        return handlerName;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getHandleDate() {
        return handleDate;
    }

    public void setHandleDate(Date handleDate) {
        this.handleDate = handleDate;
    }
}
