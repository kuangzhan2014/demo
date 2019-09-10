package com.maitianer.demo.common.model;

import java.io.Serializable;

/**
 * User: Leo
 * Date: 2018/2/15 下午9:35
 */
@SuppressWarnings("serial")
public class FileInfo implements Serializable {
    private String orgName;
    private String fileName;
    private Long fileSize;

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }
}