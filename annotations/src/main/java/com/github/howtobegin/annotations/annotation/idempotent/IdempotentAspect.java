package com.github.howtobegin.annotations.annotation.idempotent;

import com.github.howtobegin.annotations.annotation.limit.exception.LimitException;
import com.github.howtobegin.annotations.common.ParameterTools;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author lhl
 * @date 2022/10/27 下午6:11
 */
@Slf4j
@Aspect
@Component
public class IdempotentAspect {
    @Autowired
    private ParameterTools parameterTools;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public static final String REDIS_SEPARATOR = ":";

    private static Map<Integer, Cache<String, Object>> localCacheMap = Maps.newConcurrentMap();

    @Around(value = "@annotation(idempotent)")
    public Object idempotent(ProceedingJoinPoint pjp, Idempotent idempotent) throws Throwable {
        String key = buildKey(pjp, idempotent);
        if (!tryLocalIdempotent(key, idempotent)) {
            throw new LimitException(idempotent.msg());
        }
        return pjp.proceed();
    }

    public boolean tryLocalIdempotent(String key, Idempotent idempotent) {
        int time = idempotent.time();
        Cache<String, Object> cache = localCacheMap.get(time);
        if (cache == null) {
            synchronized (localCacheMap) {
                localCacheMap.computeIfAbsent(time, k -> CacheBuilder.newBuilder().maximumSize(10000).expireAfterWrite(time, TimeUnit.MILLISECONDS).build());
            }
        }
        if (cache.getIfPresent(key) == null) {
            cache.put(key, System.currentTimeMillis());
            return true;
        }
        return false;
    }

    public boolean tryIdempotent(String key, Idempotent idempotent) {
        try {
            Boolean hasKey = stringRedisTemplate.hasKey(key);
            if (hasKey != null && hasKey) {
                return false;
            } else {
                // 设置key，过期时间
                stringRedisTemplate.opsForValue().setIfAbsent(key, System.currentTimeMillis()+"", idempotent.time(), TimeUnit.MILLISECONDS);
            }
        } catch (Exception e) {
            //  redis可能出问题
            log.error("error {}", e.getMessage());
        }
        return true;
    }

    public String buildKey(ProceedingJoinPoint pjp, Idempotent idempotent) {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        String lockKey = idempotent.key();
        if (StringUtils.isNotEmpty(idempotent.subKey())) {
            lockKey += REDIS_SEPARATOR + parameterTools.parseKey(idempotent.subKey(), method, pjp.getArgs());
        }
        return lockKey;
    }
}
