package com.jwh.miaosha.Annotation.handle;

import com.jwh.miaosha.Annotation.annotation.Cacheable;
import com.jwh.miaosha.Common.Constant;
import com.jwh.miaosha.Redis.RedisUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Aspect
@Component
public class cacheAop extends HandlerInterceptorAdapter {
    @Autowired
    RedisUtils redisUtils;

    @Pointcut("@annotation(com.jwh.miaosha.Annotation.annotation.Cacheable)")
    public void test(){

    }

    @Around("test()&&@annotation(cacheable)")
    public Object cacheData(ProceedingJoinPoint joinPoint, Cacheable cacheable) throws Throwable {
        Class className = cacheable.className();
        Constant prefixKey = cacheable.prefixKey();
        String key = cacheable.Key();
        Long expireTime = cacheable.expireTime();
        Object value = null;
        if(redisUtils.exit(cacheable.prefixKey(), cacheable.Key())){
            value = redisUtils.get(prefixKey,key,className);

        }else{
            value = joinPoint.proceed();
        }
        redisUtils.set(expireTime,prefixKey,key, value);
        return value;
    }
}
