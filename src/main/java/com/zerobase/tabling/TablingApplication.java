package com.zerobase.tabling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TablingApplication {

    public static void main(String[] args) {
        SpringApplication.run(TablingApplication.class, args);
    }

}
