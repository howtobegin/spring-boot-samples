package com.github.howtobegin.annotations.annotation.limit.distribute.annotation;

import java.lang.annotation.*;

/**
 * @author lhl
 * @date 2022/10/27 下午5:42
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DistributeLimiter {
	String key() default "my:limiter";

	/**
	 * 单位时间内限制数量
	 * @return
	 */
	long times() default 2;

	/**
	 * 过期时间
	 * @return
	 */
	long expire() default 1;
}
