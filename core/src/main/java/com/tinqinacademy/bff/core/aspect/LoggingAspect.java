package com.tinqinacademy.bff.core.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Around("execution(* com.tinqinacademy.bff.core.processors..*.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) {

        String requestId = MDC.get("requestId");
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        String inputArgs = Arrays.toString(args);
        log.info("==> Start requestId: {}. Method: {}(). Input: {}", requestId, methodName, inputArgs);
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable exception) {
            String exceptionMessage = exception.getMessage();
            log.error("<!!!> Exception in requestId: {}. Method: {}(). Error: {}", requestId, methodName, exceptionMessage);
            result = exceptionMessage;
            return result;
        }
        log.info("<== End requestId: {}. Method: {}(). Output: {}", requestId, methodName, result);
        return result;
    }
}
