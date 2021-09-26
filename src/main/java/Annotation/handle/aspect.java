package Annotation.handle;

import Annotation.annotation.Register;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;


@Component

public class aspect implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();
        List annotations = Arrays.asList(method.getAnnotations());
        if (annotations.contains(Register.class)){
            List<Parameter> parameters = Arrays.asList(method.getParameters());
            if (parameters.contains())

        }

    }
}
