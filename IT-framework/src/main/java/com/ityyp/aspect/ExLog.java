/*
package com.ityyp.aspect;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ityyp.annotation.ExampleLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Slf4j
@Aspect
public class ExLog {
    @Pointcut("@annotation(com.ityyp.annotation.ExampleLog)")
    public void pt() {

    }

    @Around("pt()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object ret;
        try {
            handlerBefore(joinPoint);
            ret = joinPoint.proceed();
            handlerAfter(ret);
        } finally {
            log.info("======End======"+System.lineSeparator());
        }
        return ret;
    }

    private void handlerAfter(Object ret) {
        //打印出参
        log.info("Response      :{}",JSON.toJSONString(ret));
    }

    private void handlerBefore(ProceedingJoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        //获取被增强方法上的注解对象
        ExampleLog exampleLog = getExampleLog(joinPoint);
        log.info("=======Start========");
        //打印请求URL
        log.info("URL               :{}",request.getRequestURL());
        //打印描述信息
        log.info("BusinessName      :{}",exampleLog.business());
        //打印Http method
        log.info("HTTP Method       :{}",request.getMethod());
        //打印调用controller的全路径以及执行方法
        log.info("Class Method      :{}",joinPoint.getSignature().getDeclaringTypeName(),((MethodSignature)joinPoint.getSignature()).getDeclaringTypeName());
        //打印请求的IP
        log.info("IP                :{}",request.getRemoteHost());
        log.info("Request Args      :{}", JSON.toJSONString(joinPoint.getArgs()));
    }

    private ExampleLog getExampleLog(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        ExampleLog exampleLog = methodSignature.getMethod().getAnnotation(ExampleLog.class);
        return exampleLog;
    }
}
*/
