package com.jwh.miaosha.Redis;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.io.JsonEOFException;
import com.jwh.miaosha.Common.Constant;
import com.jwh.miaosha.Expection.SystemException;
import com.jwh.miaosha.Logger.LoggerConst;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;

import static com.jwh.miaosha.Expection.SysExceptionErrorCode.JSONParserException;

@Component
@Slf4j
@DependsOn("RedisConfig")
//@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE,proxyMode = ScopedProxyMode.TARGET_CLASS) // 每次调用方法都会生成新对象
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RedisUtils {
    @Autowired
    RedisManager redisManager;

    @Autowired
    RedisConfig redisConfig;

    private JedisPool jPool = null;

    private Jedis redis = null;

    @PostConstruct
    public void init() {
        jPool = new JedisPool(redisConfig.getResdisHost(),redisConfig.getRedisPort());
        redis = jPool.getResource();
    }

    public <T> Object get(Constant prefix, String key, Class<T> className) {
        if (redis == null) {
            redis = redisManager.getInstance();
        }
        String realKey = prefix.name() + key;
        String object = redis.get(realKey);
        T result = null;
        try {
            String data = (String) JSON.parse(object);
            result = JSON.parseObject(data, className);
        }catch (Exception e){
            log.info(LoggerConst.Serialization.name(),"parse Object Error");
            throw new SystemException(JSONParserException,"解析异常");
        }
        return result;
    }

    public <T> Boolean set(long expire, Constant prefix, String key, T value) {
        if (redis == null) {
            redis = redisManager.getInstance();
        }
        String realKey = prefix.name() + key;
        String object = null;
        try {
            object = JSONObject.toJSONString(value);
        } catch (Exception e) {
            log.info(LoggerConst.Serialization.name(), "serializ Object Error");
            return false;
        }
        redis.set(realKey, object, "NX","EX",expire);
        return true;

    }

    public <T> Object set(Constant prefix, String key, T value) {
        if (redis == null) {
            redis = redisManager.getInstance();
        }
        String realKey = prefix.name() + key;
        String object = null;
        try {
            object = JSONObject.toJSONString(value);
        } catch (Exception e) {
            log.info(LoggerConst.Serialization.name(), "serializ Object Error");
            return false;
        }
        return redis.set(realKey, object);
    }

    public boolean exit(Constant prefix,String key){
        String realKey = prefix.name()+key;
        return exit(realKey);
    }

    public boolean exit(String key){
        if (redis == null){
            redis = redisManager.getInstance();
        }
        return redis.exists(key);
    }

    public Long derc(Constant prefix,String key,int num){
        if (redis == null){
            redis = redisManager.getInstance();
        }
        String realKey = prefix+key;
        return redis.decrBy(realKey,num);
    }
}
