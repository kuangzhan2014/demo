package com.maitianer.demo.model.sys;


import com.baomidou.mybatisplus.annotation.TableName;
import com.maitianer.demo.model.common.BaseModel;

@TableName("sys_dict")
public class Dict extends BaseModel<Dict> {

    private String dictGroup;
    private Integer code;
    private String codeLabel;
    private Integer sort;
    private String parentGroup;
    private Integer parentCode;

    public String getDictGroup() {
        return dictGroup;
    }

    public Dict setDictGroup(String dictGroup) {
        this.dictGroup = dictGroup;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public Dict setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getCodeLabel() {
        return codeLabel;
    }

    public Dict setCodeLabel(String codeLabel) {
        this.codeLabel = codeLabel;
        return this;
    }

    public Integer getSort() {
        return sort;
    }

    public Dict setSort(Integer sort) {
        this.sort = sort;
        return this;
    }

    public String getParentGroup() {
        return parentGroup;
    }

    public Dict setParentGroup(String parentGroup) {
        this.parentGroup = parentGroup;
        return this;
    }

    public Integer getParentCode() {
        return parentCode;
    }

    public Dict setParentCode(Integer parentCode) {
        this.parentCode = parentCode;
        return this;
    }
}
