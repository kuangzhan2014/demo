package com.maitianer.demo.common.web;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * Created by cosmo2097 on 16/3/24.
 */
public class Message implements Serializable{

    public enum Type {

        success,

        warn,

        error
    }

    private Type type;

    private String content;

    @JsonIgnore
    private boolean parentReload = false;   // 是否刷新父页面，仅用于子页面为模式窗口时

    public Message() {
    }

    public Message(Type type, String content) {
        this.type = type;
        this.content = content;
    }

//    public Message(Type type, String content, Object... args) {
//        this.type = type;
//        this.content = SpringContextHolder.getMessage(content, args);
//    }

//    public static Message successCode(String content, Object... args) {
//        return new Message(Type.success, content, args);
//    }

//    public static Message warnCode(String content, Object... args) {
//        return new Message(Type.warn, content, args);
//    }

//    public static Message errorCode(String content, Object... args) {
//        return new Message(Type.error, content, args);
//    }

    public static Message success(String content) {
        return new Message(Type.success, content);
    }

    public static Message warn(String content) {
        return new Message(Type.warn, content);
    }

    public static Message error(String content) {
        return new Message(Type.error, content);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isParentReload() {
        return parentReload;
    }

    public void setParentReload(boolean parentReload) {
        this.parentReload = parentReload;
    }

    @Override
    public String toString() {
        return content;
    }
}
