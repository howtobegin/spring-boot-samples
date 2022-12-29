package com.github.howtobegin.annotations.annotation.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author lhl
 * @date 2022/11/30 下午3:00
 */
@Slf4j
@Aspect
@Component
public class LogTimeAspect {

    @Around(value = "@annotation(logTime)")
    public Object lock(ProceedingJoinPoint pjp, LogTime logTime) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            return pjp.proceed();
        } finally {
            log.info("方法执行时间 {} {}", logTime.key(), (System.currentTimeMillis() - start));
        }
    }
}
