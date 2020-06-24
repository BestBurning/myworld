package com.di1shuai.aop.aop;

import com.di1shuai.aop.annotation.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * @author: Shea
 * @date: 2020/6/24
 * @description:
 */
@Aspect
@Component
public class AnnotationAOP {
    @Pointcut(value = "@annotation(log)", argNames = "log")
    public void pointcut(Log log) {
    }

    @Around(value = "pointcut(log)", argNames = "joinPoint,log")
    public Object around(ProceedingJoinPoint joinPoint, Log log) throws Throwable {


        try {
            System.out.println(log.value());
            System.out.println("around : " + LocalDateTime.now());
            Object proceed = joinPoint.proceed();
            System.out.println("proceed : "+proceed);
            return proceed;
        } catch (Throwable throwable) {
            throw throwable;
        } finally {

        }
    }

    @Before("@annotation(com.di1shuai.aop.annotation.Log)")
    public void before(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log log = method.getAnnotation(Log.class);
        System.out.println("注解式拦截 " + log.value());
    }
}
