package com.smg.log;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.smg.log.mapper")
@EnableScheduling
public class LogMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(LogMainApplication.class, args);
    }
}
