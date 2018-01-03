package com.diyishuai.spring.annotation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

@Configuration
public class ComponentAnnotationTest {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ComponentAnnotationTest.class);
        context.scan("com.diyishuai.spring.annotation");


//        HelloService helloService = context.getBean(HelloService.class);
//        helloService.hello("bruce");

        Map<String, Object> beans = context.getBeansWithAnnotation(RpcService.class);
        for(Object serviceBean : beans.values()){
            String value = serviceBean.getClass().getAnnotation(RpcService.class).value();
            System.out.println("注解上的value : " + value);
            Method method = serviceBean.getClass().getMethod("hello",new Class[]{String.class});
            Object invoke = method.invoke(serviceBean, "bruce");
            System.out.println(invoke);
        }

    }
}
