package com.jwh.miaosha.Redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration("RedisConfig")
@PropertySource(value = {"classpath:redis.properties"}) //指明配置源文件位置
public class RedisConfig {

    @Value("${redis.host}")
    private String resdisHost;
    @Value("${redis.port}")
    private Integer redisPort;

    public String getResdisHost() {
        return resdisHost;
    }

    public void setResdisHost(String resdisHost) {
        this.resdisHost = resdisHost;
    }

    public Integer getRedisPort() {
        return redisPort;
    }

    public void setRedisPort(Integer redisPort) {
        this.redisPort = redisPort;
    }
}
