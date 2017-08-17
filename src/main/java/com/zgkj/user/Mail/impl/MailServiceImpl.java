package com.zgkj.user.Mail.impl;

import com.zgkj.user.Mail.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import javax.mail.internet.MimeMessage;


/**
 * Created by 韩明泽 on 2017/7/15.
 * 邮箱服务接口实现类
 */
@Component
public class MailServiceImpl implements MailService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail.fromMail.addr}")
    private String from;

    @Override
    public void sendSimpleMail(String to, String subject, String content){
        MimeMessage message = mailSender.createMimeMessage();
                try {
                    MimeMessageHelper helper = new MimeMessageHelper(message, true,"utf-8");
                    helper.setFrom(this.from);
                    helper.setTo(to);
                    helper.setSubject(subject);
                    helper.setText(content, true);
                    helper.setFrom(this.from);
                    mailSender.send(message);
                    logger.info("简单邮件已经发送。");
                } catch (Exception e) {
                    logger.error("发送简单邮件时发生异常！", e);
                }

            }
}
