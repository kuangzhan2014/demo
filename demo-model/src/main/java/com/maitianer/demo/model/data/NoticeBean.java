package com.maitianer.demo.model.data;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.maitianer.demo.model.common.BaseModel;

/**
 * @Author Chen
 * @Date 2019/8/13 9:58
 */
@TableName("t_notice")
public class NoticeBean  extends BaseModel<NoticeBean> {
    /**
     * 品牌id
     */
    private Long brandId;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    private String content;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 状态（0：删除，1：正常）
     */
//    @TableLogic
//    private boolean status;
    private Integer status;

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

//    public boolean isStatus() {
//        return status;
//    }
//
//    public void setStatus(boolean status) {
//        this.status = status;
//    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
