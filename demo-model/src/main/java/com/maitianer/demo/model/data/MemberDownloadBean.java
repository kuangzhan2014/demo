package com.maitianer.demo.model.data;

import com.baomidou.mybatisplus.annotation.TableName;
import com.maitianer.demo.model.common.BaseModel;

/**
 * @Author Chen
 * @Date 2019/8/13 9:57
 */
@TableName("t_member_download")
public class MemberDownloadBean  extends BaseModel<MemberDownloadBean> {
    /**
     * 用户id
     */
    private Long memberId;
    /**
     * 素材id
     */
    private Long materialId;
    /**
     * 次数
     */
    private int count;
    /**
     * 年
     */
    private int year;
    /**
     * 月
     */
    private int month;
    /**
     * 日
     */
    private int day;

    public MemberDownloadBean(Long memberId, Long materialId, int count, int year, int month, int day) {
        this.memberId = memberId;
        this.materialId = materialId;
        this.count = count;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public MemberDownloadBean(){}

    public String getDayLabel(){
        if(day < 10){
            return "0"+String.valueOf(day);
        }
        return String.valueOf(day);
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
