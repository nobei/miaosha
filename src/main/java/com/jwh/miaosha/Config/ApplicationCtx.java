package com.jwh.miaosha.Config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author ：jwh
 * @description：TODO
 * @date ：2022/2/21 22:29
 */
@Configuration
public class ApplicationCtx implements ApplicationContextAware {

    private ApplicationContext ctx;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;

    }

    @Bean(name = "ctx")
    public ApplicationContext getCtx(){
        return ctx;
    }



}
