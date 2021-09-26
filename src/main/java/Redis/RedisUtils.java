package Redis;

import Common.Constant;
import Logger.LoggerConst;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class RedisUtils {
    @Autowired
    RedisManager redisManager;

    private Jedis redis = null;

    @PostConstruct
    public void init() {
        redis = redisManager.getInstance();
    }

    public <T> Object get(Constant prefix, String key, Class<T> className) {
        if (redis == null) {
            redis = redisManager.getInstance();
        }
        String realKey = prefix.name() + key;
        String object = redis.get(realKey);
        T result = null;
        try {
            result = JSON.parseObject(object, className);
        }catch (Exception e){
            log.info(LoggerConst.Serialization.name(),"parse Object Error");
        }
        return result;
    }

    public <T> Boolean set(String expire, Constant prefix, String key, T value) {
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
        redis.set(realKey, object, expire);
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

}
