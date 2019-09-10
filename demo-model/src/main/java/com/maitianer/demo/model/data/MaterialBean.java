package com.maitianer.demo.model.data;

import com.baomidou.mybatisplus.annotation.TableName;
import com.maitianer.demo.common.utils.lang.DateUtils;
import com.maitianer.demo.model.ApplicationData;
import com.maitianer.demo.model.DomainConstants;
import com.maitianer.demo.model.common.BaseModel;

/**
 * @Author Chen
 * @Date 2019/8/13 9:55
 */
@TableName("t_material")
public class MaterialBean extends BaseModel<MaterialBean> {
    /**
     * 品牌id
     */
    private Long brandId;
    /**
     * 名称
     */
    private String name;
    /**
     * 类型（图片/视频/话术）
     */
    private Integer type;
    /**
     * 内容
     */
    private String content;
    /**
     * 展示图片
     */
    private String showPicture;
    /**
     * 视频地址
     */
    private String videoUrl;
    /**
     * 描述
     */
    private String description;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 状态（0：删除，1：正常）
     */
    private boolean status;

    public String getTypeLabel(){
        return ApplicationData.get().getDictLabel(DomainConstants.DICT_GROUP_MATERIAL_TYPE, type);
    }

    public String getCreateDateLabel(){
        return DateUtils.formatDate(getCreateDate(),"yyyy-MM-dd");
    }

    public String getShowPictureUrl(){
        if (showPicture == null) {
            return null;
        }
        return ApplicationData.get().getOssResourceUrl(showPicture);
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getShowPicture() {
        return showPicture;
    }

    public void setShowPicture(String showPicture) {
        this.showPicture = showPicture;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
