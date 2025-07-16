package com.example.user.config;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "mail.smtp")
public class MailProperties {
    @Value("${smtp.host}")
    private String host;
    @Value("${smtp.port}")
    private int port;
    @Value("${smtp.username}")
    private String username;
    @Value("${smtp.password}")
    private String password;
    @Value("${smtp.auth}")
    private boolean auth;
    @Value("${smtp.starttls}")
    private boolean starttls;
    @Value("${mail.template}")
    private String mailTemplate;
}