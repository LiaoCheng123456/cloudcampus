package com.rh.cloudcampus.aspect;

import com.rh.cloudcampus.utils.CommonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Aspect
@Component
public class LogAspect {

    private Logger logger = LogManager.getLogger();

    /**
     * 定义一个切点,所有返回值,所有service,所有方法,所有请求参数
     */
    @Pointcut("execution(* com.rh.cloudcampus.service.*.*(..))")
    public void executeServiceMethod() {
    }

    /**
     * 记录所有service的日志信息
     * @param pjp 请求对象
     * @return Object
     * @throws Throwable
     */
    @Around("executeServiceMethod()")
    public Object logs(ProceedingJoinPoint pjp) throws Throwable {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        Object[] args = pjp.getArgs();
        StringBuilder buffer = new StringBuilder();
        for (Object a : args) {
            buffer.append(a);
        }
        logger.info(String.format("\n系统日志：方法:[%s], ID:[%s], 时间:[%s], 请求参数:[%s]", pjp.getSignature().getName(), uuid, CommonUtils.getNowFormatTime(), buffer.toString()));
        Object proceed = pjp.proceed();
        logger.info(String.format("\n系统日志：方法:[%s], ID:[%s], 时间:[%s], 响应参数:[%s]", pjp.getSignature().getName(), uuid, CommonUtils.getNowFormatTime(), proceed));
        return proceed;
    }
}
