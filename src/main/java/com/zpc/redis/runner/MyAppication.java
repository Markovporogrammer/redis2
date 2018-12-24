package com.zpc.redis.runner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ComponentScan(basePackages = "com.zpc.redis")
@ImportResource({"classpath*:applicationContext*.xml"})
public class MyAppication {
    public static void main(String[] args) {
        SpringApplication.run(MyAppication.class, args);
    }
}
