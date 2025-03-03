package com.smg.kbm;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableFeignClients
public class KbmMainApplication {
    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(KbmMainApplication.class, args);
    }
}
