package com.maitianer.demo.model.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import com.maitianer.demo.model.ApplicationData;
import com.maitianer.demo.model.DomainConstants;
import com.maitianer.demo.model.common.BaseModel;

import java.math.BigDecimal;

/**
 * @Author: Mr.Zhang
 * @Date: 2018-09-28 15:01
 */
@TableName("sys_area")
public class Area extends BaseModel<Area> {

    private String areaName;
    private String areaShortName;
    private Integer areaType;
    private String areaCode;
    private String areaPinyin;
    private String areaJianpin;
    private String areaFirstChar;
    private BigDecimal areaLng;
    private BigDecimal areaLat;
    private String cityCode;
    private String zipCode;
    private String parentCode;
    private String parentCodes;
    private String treeNames;
    private String remark;

    public String getAreaTypeLabel() {
        return ApplicationData.get().getDictLabel(DomainConstants.DICT_GROUP_AREA_TYPE, areaType);
    }

    public String getAreaName() {
        return areaName;
    }

    public Area setAreaName(String areaName) {
        this.areaName = areaName;
        return this;
    }

    public String getAreaShortName() {
        return areaShortName;
    }

    public Area setAreaShortName(String areaShortName) {
        this.areaShortName = areaShortName;
        return this;
    }

    public Integer getAreaType() {
        return areaType;
    }

    public Area setAreaType(Integer areaType) {
        this.areaType = areaType;
        return this;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public Area setAreaCode(String areaCode) {
        this.areaCode = areaCode;
        return this;
    }

    public String getAreaPinyin() {
        return areaPinyin;
    }

    public Area setAreaPinyin(String areaPinyin) {
        this.areaPinyin = areaPinyin;
        return this;
    }

    public String getAreaJianpin() {
        return areaJianpin;
    }

    public Area setAreaJianpin(String areaJianpin) {
        this.areaJianpin = areaJianpin;
        return this;
    }

    public String getAreaFirstChar() {
        return areaFirstChar;
    }

    public Area setAreaFirstChar(String areaFirstChar) {
        this.areaFirstChar = areaFirstChar;
        return this;
    }

    public BigDecimal getAreaLng() {
        return areaLng;
    }

    public Area setAreaLng(BigDecimal areaLng) {
        this.areaLng = areaLng;
        return this;
    }

    public BigDecimal getAreaLat() {
        return areaLat;
    }

    public Area setAreaLat(BigDecimal areaLat) {
        this.areaLat = areaLat;
        return this;
    }

    public String getCityCode() {
        return cityCode;
    }

    public Area setCityCode(String cityCode) {
        this.cityCode = cityCode;
        return this;
    }

    public String getZipCode() {
        return zipCode;
    }

    public Area setZipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public String getParentCode() {
        return parentCode;
    }

    public Area setParentCode(String parentCode) {
        this.parentCode = parentCode;
        return this;
    }

    public String getParentCodes() {
        return parentCodes;
    }

    public Area setParentCodes(String parentCodes) {
        this.parentCodes = parentCodes;
        return this;
    }

    public String getTreeNames() {
        return treeNames;
    }

    public Area setTreeNames(String treeNames) {
        this.treeNames = treeNames;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public Area setRemark(String remark) {
        this.remark = remark;
        return this;
    }
}
