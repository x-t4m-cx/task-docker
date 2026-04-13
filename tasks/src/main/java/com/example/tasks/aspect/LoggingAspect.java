package com.example.tasks.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(public * com.examle.tasks.service.*..*(..))")
    public void allServicePublicMethods() {
    }

    @Around("allServicePublicMethods()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable{
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        log.info(">>{} () {}", methodName, formatArgs(args));

        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long duration = System.currentTimeMillis() - start;

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        if(methodSignature.getReturnType() == void.class) {
            log.info("<< {}() - executed in {}ms", methodName, duration);
        } else {
            log.info("<< {}() - result: {} ({}ms)", methodName, formatResult(result), duration);
        }

        return result;
    }

    private String formatArgs(Object[] args) {
        if (args == null || args.length == 0) {
            return "no args";
        }
        return Arrays.toString(args);
    }

    private String formatResult(Object result) {
        if (result == null) {
            return "null";
        }
        String resultStr = result.toString();
        if (resultStr.length() > 500) {
            return resultStr.substring(0, 500) + "...";
        }
        return resultStr;
    }
}
