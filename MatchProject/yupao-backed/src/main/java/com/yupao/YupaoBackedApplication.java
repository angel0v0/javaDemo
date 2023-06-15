package com.yupao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yupao.mapper")
public class YupaoBackedApplication {

    public static void main(String[] args) {
        SpringApplication.run(YupaoBackedApplication.class, args);
    }

}
