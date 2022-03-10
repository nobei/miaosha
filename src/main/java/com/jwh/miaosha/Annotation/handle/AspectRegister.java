package com.jwh.miaosha.Annotation.handle;

import com.jwh.miaosha.Annotation.annotation.Register;
import com.jwh.miaosha.Common.Constant;
import com.jwh.miaosha.Redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import javax.inject.Provider;


@Service
public class AspectRegister extends HandlerInterceptorAdapter {

    @Autowired
    private Provider<RedisUtils> redisUtilsProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            Annotation register = hm.getMethodAnnotation(Register.class);
            if (register != null) {
                if (!StringUtils.isEmpty(getToken(request))) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return true;

    }

//    @Override
//    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
//        Method method = methodInvocation.getMethod();
//        List annotations = Arrays.asList(method.getAnnotations());
//        HttpServletResponse httpServletResponse = null;
//        if (annotations.contains(Register.class)) {
//            Object[] ars = methodInvocation.getArguments();
//            for (Object ar : ars) {
//                if (ar instanceof HttpServletRequest) {
//                    if (!StringUtils.isEmpty(getToken((HttpServletRequest) ar))){
//                        return methodInvocation.proceed();
//                    }
//                }else if (ar instanceof  HttpServletResponse){
//                    httpServletResponse = (HttpServletResponse)ar;
//                }
//            }
//            httpServletResponse.sendRedirect("login");
//            return httpServletResponse;
//        }else {
//           return methodInvocation.proceed();
//        }
//
//    }

    private String getToken(HttpServletRequest httpServletRequest) {
        Cookie cookie[] = httpServletRequest.getCookies();
        if (cookie == null || cookie.length == 0) {
            return null;
        }
        for (Cookie cookie1 : cookie) {
            if (cookie1.getName().equals(Constant.User.name())) {
                if (redisUtilsProvider.get().exit(Constant.User, cookie1.getValue())) {
                    return cookie1.getValue();
                }

            }
        }
        return null;

    }
}
