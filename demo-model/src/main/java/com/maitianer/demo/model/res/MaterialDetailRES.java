package com.maitianer.demo.model.res;

import com.maitianer.demo.common.utils.lang.DateUtils;
import com.maitianer.demo.model.ApplicationData;
import com.maitianer.demo.model.data.MaterialBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @Author Chen
 * @Date 2019/8/30 12:14
 */
@ApiModel(description = "素材模型")
public class MaterialDetailRES {

    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("素材名称")
    private String name;
    @ApiModelProperty("图片")
    private String showPicture;
    @ApiModelProperty("视频url")
    private String videoUrl;
    @ApiModelProperty("话术内容")
    private String content;
    @ApiModelProperty("描述")
    private String description;
    @ApiModelProperty("日期")
    private Date createDate;

    public MaterialDetailRES(MaterialBean materialBean){
        BeanUtils.copyProperties(materialBean, this);
    }

    public String getShowPictureUrl(){
        if (showPicture == null) {
            return null;
        }
        return ApplicationData.get().getOssResourceUrl(showPicture);
    }
    public String getCreateDateLabel(){
        return DateUtils.formatDate(createDate,"yyyy-MM-dd");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShowPicture() {
        return showPicture;
    }

    public void setShowPicture(String showPicture) {
        this.showPicture = showPicture;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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
}
