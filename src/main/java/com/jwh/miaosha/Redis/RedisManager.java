package com.jwh.miaosha.Redis;

import com.jwh.miaosha.Logger.LoggerConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class RedisManager {
    @Autowired
    RedisConfig redisConfig;

    private Jedis redis = null;

    @PostConstruct
    public void initRedis(){
        redis = new Jedis(redisConfig.getResdisHost(), redisConfig.getRedisPort());
        log.info( LoggerConst.Redis.name(), "redis-start");
    }

    public Jedis getInstance(){
        if (redis == null){
            initRedis();
        }
        return redis;
    }


}
