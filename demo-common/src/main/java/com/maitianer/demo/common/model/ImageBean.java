package com.maitianer.demo.common.model;

/**
 * @Author yuzhe
 * @Date 2018/9/29 10:05
 **/
public class ImageBean {

    // 图片完整路径
    private String src;
    // 图片相对路径
    private String relativePath;
    // 图片名称
    private String title;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }
}
