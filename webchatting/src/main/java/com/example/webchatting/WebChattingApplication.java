package com.example.webchatting;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.example.mapper")
@ComponentScan({"com.example.webchatting","com.example.service"})
public class WebChattingApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebChattingApplication.class, args);
    }

}
