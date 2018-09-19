package com.pachong;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@ServletComponentScan
@MapperScan("com.pachong.dbmapper")
public class PachongApplication {
    public static void main(String[] args) {
        SpringApplication.run(PachongApplication.class, args);
    }
}
