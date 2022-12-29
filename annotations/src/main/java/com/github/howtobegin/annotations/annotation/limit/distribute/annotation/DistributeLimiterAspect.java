package com.github.howtobegin.annotations.annotation.limit.distribute.annotation;

import com.github.howtobegin.annotations.annotation.limit.exception.LimitException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * 切面处理类
 * 
 * @author admin
 *
 */
@Slf4j
@Aspect
@Component
public class DistributeLimiterAspect {

	private DefaultRedisScript<Long> getRedisScript;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@PostConstruct
	public void init() {
		getRedisScript = new DefaultRedisScript<>();
		getRedisScript.setResultType(Long.class);
		getRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("limit.lua")));
	}
	
	@Around("@annotation(limiter)")
	public Object around(ProceedingJoinPoint pjp, DistributeLimiter limiter) throws Throwable {
		String limitKey = limiter.key();
		long limitTimes = limiter.times();
		long limitExpire = limiter.expire();

		List<String> keys = new ArrayList<>();
		keys.add(limitKey);
		List<String> args = new ArrayList<>();
		args.add(limitTimes + "");
		args.add(limitExpire + "");

		Long result = stringRedisTemplate.execute(getRedisScript, keys, args.toArray());
		if (result == 0D) {
			throw new LimitException("limit");
		}
		return pjp.proceed();
	}

}
