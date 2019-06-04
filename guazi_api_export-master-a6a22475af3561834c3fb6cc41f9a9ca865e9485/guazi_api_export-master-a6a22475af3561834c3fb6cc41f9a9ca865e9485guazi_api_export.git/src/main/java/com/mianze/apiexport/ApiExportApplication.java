package com.mianze.apiexport;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.mianze.apiexport.mapper")
@EnableScheduling
@EnableAsync
public class ApiExportApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiExportApplication.class, args);
    }
}
