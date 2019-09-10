package com.maitianer.demo.model.sys;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.maitianer.demo.model.ApplicationData;
import com.maitianer.demo.model.DomainConstants;
import com.maitianer.demo.model.common.BaseModel;

import java.util.Date;
import java.util.List;

@TableName("sys_member")
public class Member extends BaseModel<Member> {

    private String memberName;
    private String encryptedPassword;
    private String passwordSalt;
    private String cellphone;
    private String phone;
    private String email;
    private String realName;
    // 用户角色名称
    @TableField(exist = false)
    private String roleName;
    private String avatar;
    private Date lastLoginDate;
    private String lastLoginIp;
    private Integer status;
    private String position;
    private String areaCode;
    private String province;
    private String city;
    private String district;
    private String street;

    @TableField(exist = false)
    private String provinceCode;
    @TableField(exist = false)
    private String cityCode;

    @TableField(exist = false)
    private List<Role> roles;

    public String getStatusLabel() {
        return ApplicationData.get().getDictLabel(DomainConstants.DICT_GROUP_MEMBER_STATUS, status);
    }

    public String getArea(){
        return province+" "+city+" "+district;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public Member setRoles(List<Role> roles) {
        this.roles = roles;
        return this;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getRoleName() {
        for(Role role:roles){
            roleName= role.getName();
        }
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
