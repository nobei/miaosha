package com.jwh.miaosha;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages={"com.jwh.miaosha.Controller", "com.jwh.miaosha.Annotation", "com.jwh.miaosha.Redis", "com.jwh.miaosha.Service", "com.jwh.miaosha.Config", "com.jwh.miaosha.Mq"})
@MapperScan({"com/jwh/miaosha/Dao"})
public class applicationStart{
    public static void main(String[] args) {
        SpringApplication.run(applicationStart.class, args);
    }
}
