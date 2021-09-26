package com.jwh.miaosha.Annotation.annotation;

import com.jwh.miaosha.Common.Constant;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cacheable {
    Constant prefixKey() ;
    String Key() default "";
    long expireTime() default 60L;
    Class className() default Object.class;
}
