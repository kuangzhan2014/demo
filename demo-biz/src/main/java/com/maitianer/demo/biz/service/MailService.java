package com.maitianer.demo.biz.service;

import com.maitianer.demo.model.data.MailBean;

/**
 * @Author: zhou
 * @Date: 2019/09/02 10:10
 */
public interface MailService {
    boolean sendSimpleMail(MailBean mailBean);
}
