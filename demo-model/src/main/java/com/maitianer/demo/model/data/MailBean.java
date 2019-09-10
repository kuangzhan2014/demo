package com.maitianer.demo.model.data;

import java.io.Serializable;

/**
 * @Author: zhou
 * @Date: 2019/08/30 15:53
 */
public class MailBean implements Serializable {
    //邮件接收人
    private String recipient;
    //邮件主题
    private String subject;
    //邮件内容
    private String content;

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
