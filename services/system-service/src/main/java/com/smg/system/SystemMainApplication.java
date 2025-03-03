package com.smg.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SystemMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemMainApplication.class, args);
    }
}
