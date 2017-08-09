package com.zgkj.user.Mail;

/**
 * Created by 韩明泽 on 2017/7/15.
 * 邮箱验证服务接口
 */
public interface MailService {
    void sendSimpleMail(String to, String subject, String content);
}
