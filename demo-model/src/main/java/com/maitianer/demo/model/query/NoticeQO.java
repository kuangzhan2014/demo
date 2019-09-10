package com.maitianer.demo.model.query;


import com.maitianer.demo.model.query.base.BaseQO;

/**
 * @Author Chen
 * @Date 2019/8/13 9:58
 */
public class NoticeQO extends BaseQO {

    private Long id;
    // 标题
    private String title;
    //内容
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
