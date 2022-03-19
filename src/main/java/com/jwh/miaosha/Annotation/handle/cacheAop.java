package com.jwh.miaosha.Annotation.handle;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jwh.miaosha.Annotation.annotation.Cacheable;
import com.jwh.miaosha.Common.Constant;
import com.jwh.miaosha.Redis.RedisUtils;
import com.jwh.miaosha.Utils.MethodUtils;
import com.rabbitmq.tools.json.JSONUtil;
import javassist.bytecode.SignatureAttribute;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.swing.text.html.HTMLDocument;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import javax.inject.Provider;
import java.util.*;

import static com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolver.iterator;

@Aspect
@Component
@Slf4j
public class cacheAop extends HandlerInterceptorAdapter {
    @Autowired
    private Provider<RedisUtils> redisUtils;

    @Pointcut("execution(public * *(..))")
        public void test() {

    }

    @Around("test()&&@annotation(cacheable)")
    public Object cacheData(ProceedingJoinPoint joinPoint, Cacheable cacheable) throws Throwable {
        Constant prefixKey = cacheable.prefixKey();
        String keyDes = cacheable.Key();
        Long expireTime = cacheable.expireTime();
        Object[] args = joinPoint.getArgs();
        List<String> realKeys = getKey(keyDes,args);
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        Type type = method.getGenericReturnType();
        Type returnType = MethodUtils.getReturnType(method);
        Object value = null;
        if (isExist(prefixKey, realKeys)) {
            if(type instanceof Collection){
                Class resClass = Class.forName(type.getTypeName());
                Constructor constructor = resClass.getConstructor();
                Collection collections = (Collection) constructor.newInstance();
                Class realClass = returnType.getClass();
                for (String realKey:realKeys){
                    collections.add( redisUtils.get().get(prefixKey, realKey, realClass));
                }
                return collections;
            }else if(!CollectionUtils.isEmpty(realKeys)
                    && realKeys.size() == 1 && !(type instanceof Map)){
                Class realClass = Class.forName(type.getTypeName());
                Object data = redisUtils.get().get(prefixKey,realKeys.get(0),realClass);
                return data;
            }
        } else {
            value = joinPoint.proceed();
        }
        if (value instanceof Collection) {
            if (!CollectionUtils.isEmpty(realKeys) && realKeys.size() == ((Collection) value).size()) {
                int nowIndex = 0;
                Iterator it = ((Collection) value).iterator();
                while (it.hasNext()) {
                    Object t = it.next();
                    redisUtils.get().set(expireTime, prefixKey, realKeys.get(nowIndex), Utils.JacksonUtils.transform(value));
                    nowIndex++;
                }
            }
        } else {
            if (!CollectionUtils.isEmpty(realKeys)
                    && realKeys.size() == 1 && !(value instanceof Map)) {
                redisUtils.get().set(expireTime, prefixKey, realKeys.get(0), Utils.JacksonUtils.transform(value));
            }
        }

        return value;
    }


    public boolean isExist(Constant prefixKey , List<String> key){
        for (String keyT:key){
            if(!redisUtils.get().exit(prefixKey+keyT)){
                return false;
            }
        }

        return true;

    }


    public List<String> getKey(String key, Object[] object) throws JsonProcessingException {
        if (null == object || object.length == 0) {
            return Arrays.asList("");
        }
        Object firstParam = object[0];
        if (firstParam instanceof Collection) {
            Collection param = (Collection) firstParam;
            Iterator it = param.iterator();
            List<String> res = new ArrayList<>();
            while (it.hasNext()) {
                try {
                    String realKey = (String) it.next();
                    res.add(key+realKey);
                } catch (ClassCastException e) {
                    log.info("cache transform error: {}", e.getMessage());
                }
            }
            return res;
        } else if (firstParam instanceof Integer) {
            return Arrays.asList(key+firstParam+"");
        } else {
            log.info("{} can not cache classType", Utils.JacksonUtils.transform(object));
            return Arrays.asList();
        }

    }
}
