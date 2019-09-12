package com.maitianer.demo.model.query;

import com.maitianer.demo.model.query.base.BaseQO;

/**
 * @Author: zhou
 * @Date: 2019/09/12 14:36
 */
public class RoleQO extends BaseQO {
    private String name;
    private String code;
    private String description;
    private Integer status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
