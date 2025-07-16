package com.example.business;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.example.mapper")
@EnableTransactionManagement  // 开启事务管理
public class BusinessServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusinessServiceApplication.class, args);
    }

} 