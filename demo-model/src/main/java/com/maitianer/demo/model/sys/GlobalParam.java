package com.maitianer.demo.model.sys;


import com.baomidou.mybatisplus.annotation.TableName;
import com.maitianer.demo.model.ApplicationData;
import com.maitianer.demo.model.DomainConstants;
import com.maitianer.demo.model.common.BaseModel;

/**
 * User: Leo
 * Date: 2018/9/26 11:26 PM
 */
@TableName("sys_global_param")
public class GlobalParam extends BaseModel<GlobalParam> {

    private Integer paramGroup;
    private String paramName;
    private String paramKey;
    private String paramValue;
    private String remark;

    public String getParamGroupLabel() {
        return ApplicationData.get().getDictLabel(DomainConstants.DICT_GROUP_PARAM_GROUP, paramGroup);
    }

    public Integer getParamGroup() {
        return paramGroup;
    }

    public GlobalParam setParamGroup(Integer paramGroup) {
        this.paramGroup = paramGroup;
        return this;
    }

    public String getParamName() {
        return paramName;
    }

    public GlobalParam setParamName(String paramName) {
        this.paramName = paramName;
        return this;
    }

    public String getParamKey() {
        return paramKey;
    }

    public GlobalParam setParamKey(String paramKey) {
        this.paramKey = paramKey;
        return this;
    }

    public String getParamValue() {
        return paramValue;
    }

    public GlobalParam setParamValue(String paramValue) {
        this.paramValue = paramValue;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public GlobalParam setRemark(String remark) {
        this.remark = remark;
        return this;
    }
}
