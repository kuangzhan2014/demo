package com.maitianer.demo.biz.service.impl;

import com.maitianer.demo.biz.service.MailService;

import com.maitianer.demo.model.data.MailBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @Author: zhou
 * @Date: 2019/09/02 10:12
 */
@Component
@Service
public class MailServiceImpl implements MailService {
    //邮件发送者
    @Value("${spring.mail.username}")
    private  String mailSender;
    @Autowired
    private  JavaMailSender javaMailSender;

    private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    @Override
    public boolean sendSimpleMail(MailBean mailBean) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            //邮件发送人
            simpleMailMessage.setFrom(mailSender);
            //邮件接收人
            simpleMailMessage.setTo(mailBean.getRecipient());
            //邮件主题
            simpleMailMessage.setSubject(mailBean.getSubject());
            //邮件内容
            simpleMailMessage.setText(mailBean.getContent());
            javaMailSender.send(simpleMailMessage);
            return true;
        } catch (Exception e) {
            logger.error("邮件发送失败", e.getMessage());
            return false;
        }
    }

}
