package com.maitianer.demo.model.query;

import com.maitianer.demo.model.query.base.BaseQO;

/**
 * @Author: zhou
 * @Date: 2019/08/19 09:51
 */

public class MemberQO extends BaseQO {
    private String memberName;
    private String cellphone;
    private String email;
    private Integer status;

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
