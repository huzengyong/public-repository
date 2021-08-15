package com.example.manage;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

public class MyConfiguration {

    @ConfigurationProperties("spring.datasource")
    @Bean
    public DruidDataSource druidDataSource(){
        DruidDataSource dds = new DruidDataSource();
        return dds;
    }
}
