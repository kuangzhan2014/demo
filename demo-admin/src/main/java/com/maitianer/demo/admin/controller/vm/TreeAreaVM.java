package com.maitianer.demo.admin.controller.vm;

import com.maitianer.demo.model.sys.Area;

/**
 * @Author: Mr.Zhang
 * @Date: 2018-09-28 16:15
 */
public class TreeAreaVM {

    private Long id;
    private String areaName;
    private String areaCode;
    private String parentCode;
    private Integer areaType;
    private String areaTypeLabel;

    public TreeAreaVM(Area area) {
        this.id = area.getId();
        this.areaName = area.getAreaName();
        this.areaCode = area.getAreaCode();
        this.parentCode = area.getParentCode();
        this.areaType = area.getAreaType();
        this.areaTypeLabel = area.getAreaTypeLabel();
    }

    public Long getId() {
        return id;
    }

    public TreeAreaVM setId(Long id) {
        this.id = id;
        return this;
    }

    public String getAreaName() {
        return areaName;
    }

    public TreeAreaVM setAreaName(String areaName) {
        this.areaName = areaName;
        return this;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public TreeAreaVM setAreaCode(String areaCode) {
        this.areaCode = areaCode;
        return this;
    }

    public String getParentCode() {
        return parentCode;
    }

    public TreeAreaVM setParentCode(String parentCode) {
        this.parentCode = parentCode;
        return this;
    }

    public Integer getAreaType() {
        return areaType;
    }

    public TreeAreaVM setAreaType(Integer areaType) {
        this.areaType = areaType;
        return this;
    }

    public String getAreaTypeLabel() {
        return areaTypeLabel;
    }

    public TreeAreaVM setAreaTypeLabel(String areaTypeLabel) {
        this.areaTypeLabel = areaTypeLabel;
        return this;
    }
}
