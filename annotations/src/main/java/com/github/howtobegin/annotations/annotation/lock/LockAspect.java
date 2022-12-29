package com.github.howtobegin.annotations.annotation.lock;

import com.github.howtobegin.annotations.common.ParameterTools;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author lhl
 * @date 2022/6/16 下午5:29
 */
@Aspect
@Component
public class LockAspect {
    @Autowired
    private ParameterTools parameterTools;
    @Autowired
    private RedissonClient redissonClient;

    public static final String REDIS_SEPARATOR = ":";

    @Around(value = "@annotation(lock)")
    public Object lock(ProceedingJoinPoint pjp, Lock lock) throws Throwable {
        RLock rlock = redissonClient.getLock(buildKey(pjp, lock));
        rlock.lock(lock.time(), TimeUnit.SECONDS);
        try {
            return pjp.proceed();
        } finally {
            rlock.unlock();
        }
    }

    public String buildKey(ProceedingJoinPoint pjp, Lock lock) {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        String lockKey = lock.key();
        if (StringUtils.isNotEmpty(lock.subKey())) {
            lockKey += REDIS_SEPARATOR + parameterTools.parseKey(lock.subKey(), method, pjp.getArgs());
        }
        return lockKey;
    }
}
