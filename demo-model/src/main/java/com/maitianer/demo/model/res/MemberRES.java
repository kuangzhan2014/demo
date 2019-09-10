package com.maitianer.demo.model.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author Chen
 * @Date 2019/8/30 11:31
 */
@ApiModel(description = "用户模型")
public class MemberRES {

    @ApiModelProperty("名称")
    private String realName;
    @ApiModelProperty("电话")
    private String cellphone;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("职位")
    private String position;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
