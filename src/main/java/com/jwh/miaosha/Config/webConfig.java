package com.jwh.miaosha.Config;

import com.jwh.miaosha.Annotation.handle.AspectRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2022/1/29 20:45
 */

@Component
public class webConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private AspectRegister Interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册自己的拦截器并设置拦截的请求路径
        super.addInterceptors(registry);
        registry.addInterceptor(Interceptor);
    }



}
