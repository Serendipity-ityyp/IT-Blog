package com.ityyp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ityyp.mapper")
public class ITBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(ITBlogApplication.class,args);
    }
}
