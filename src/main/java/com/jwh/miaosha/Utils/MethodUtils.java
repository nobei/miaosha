package com.jwh.miaosha.Utils;

import com.fasterxml.jackson.databind.type.TypeFactory;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

public class MethodUtils {
    public static Type getCollectionType(Collection collection) {
        Type clazz = collection.getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) clazz;
        return pt.getActualTypeArguments()[0];
    }

    public static Type getReturnType(Method method) {

        Type type = method.getGenericReturnType();    //判断是否带有泛型
        if (type instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();        //获取泛型类型
            Class rowClass = (Class) ((ParameterizedType) type).getRawType();
            Type[] javaTypes = new Type[actualTypeArguments.length];
            return actualTypeArguments[0];
        } else {        //简单类型直接用该类构建
            return type;
        }

    }
}
