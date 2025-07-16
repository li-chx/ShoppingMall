package com.example.user.utils;

import com.example.user.config.MailProperties;
import jakarta.annotation.Resource;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class MailUtil {

    @Resource
    private MailProperties mailProperties;

    public void sendHtmlEmail(String to, String subject, String htmlContent) throws MessagingException {
        // 配置 SMTP 服务器
        Properties props = new Properties();
        props.put("mail.smtp.host", mailProperties.getHost());
        props.put("mail.smtp.port", String.valueOf(mailProperties.getPort()));
        props.put("mail.smtp.auth", String.valueOf(mailProperties.isAuth()));
        props.put("mail.smtp.starttls.enable", String.valueOf(mailProperties.isStarttls()));
        props.put("mail.smtp.ssl.enable", true);

        // 认证信息
        String username = mailProperties.getUsername();
        String password = mailProperties.getPassword();

        // 创建会话
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        // 创建邮件
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setContent(htmlContent, "text/html; charset=utf-8"); // 设置内容为 HTML 格式

        // 发送邮件
        Transport.send(message);
    }
}