package com.maitianer.demo.model.data;

import com.baomidou.mybatisplus.annotation.TableName;
import com.maitianer.demo.model.ApplicationData;
import com.maitianer.demo.model.common.BaseModel;

/**
 * @Author Chen
 * @Date 2019/8/13 9:47
 */
@TableName("t_brand")
public class BrandBean extends BaseModel<BrandBean>{

    /**
     * 名称
     */
    private String name;
    /**
     * 小图
     */
    private String smallPicture;
    /**
     * 大图
     */
    private String bigPicture;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 状态（0：删除，1：正常）
     */
    private boolean status;

    public String getSmallPictureUrl(){
        if (smallPicture == null) {
            return null;
        }
        return ApplicationData.get().getOssResourceUrl(smallPicture);
    }
    public String getBigPictureUrl(){
        if (bigPicture == null) {
            return null;
        }
        return ApplicationData.get().getOssResourceUrl(bigPicture);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSmallPicture() {
        return smallPicture;
    }

    public void setSmallPicture(String smallPicture) {
        this.smallPicture = smallPicture;
    }

    public String getBigPicture() {
        return bigPicture;
    }

    public void setBigPicture(String bigPicture) {
        this.bigPicture = bigPicture;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
