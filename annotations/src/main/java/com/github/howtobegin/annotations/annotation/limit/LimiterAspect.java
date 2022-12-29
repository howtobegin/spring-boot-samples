package com.github.howtobegin.annotations.annotation.limit;

import com.github.howtobegin.annotations.annotation.limit.exception.LimitException;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author lhl
 * @date 2022/10/27 下午5:45
 */
@Slf4j
@Aspect
@Component
public class LimiterAspect {
    /**
     * 不同的接口，不同的流量控制
     * map的key为 Limiter.key
     */
    private final Map<String, RateLimiter> limitMap = Maps.newConcurrentMap();

    @Around("@annotation(com.github.howtobegin.annotations.annotation.limit.Limiter)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        //拿limit的注解
        Limiter limit = method.getAnnotation(Limiter.class);
        if (limit != null) {
            //key作用：不同的接口，不同的流量控制
            String key=limit.key();
            RateLimiter rateLimiter;
            //验证缓存是否有命中key
            if (!limitMap.containsKey(key)) {
                // 创建令牌桶
                rateLimiter = RateLimiter.create(limit.permitsPerSecond());
                limitMap.put(key, rateLimiter);
                log.info("新建了令牌桶={}，容量={}",key,limit.permitsPerSecond());
            }
            rateLimiter = limitMap.get(key);
            // 拿令牌
            boolean acquire = rateLimiter.tryAcquire(limit.timeout(), limit.timeunit());
            // 拿不到命令，直接返回异常提示
            if (!acquire) {
                throw new LimitException(limit.msg());
            }
        }
        return joinPoint.proceed();
    }
}
