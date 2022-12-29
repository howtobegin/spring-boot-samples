package com.github.howtobegin.annotations.annotation.limit;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author lhl
 * @date 2022/10/27 下午5:42
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Limiter {
    /**
     * 资源的key,唯一
     * 作用：不同的接口，不同的流量控制
     */
    String key() default "";

    /**
     * 最多的访问限制次数
     */
    double permitsPerSecond () ;

    /**
     * 获取令牌最大等待时间
     */
    long timeout();

    /**
     * 获取令牌最大等待时间,单位(例:分钟/秒/毫秒) 默认:毫秒
     */
    TimeUnit timeunit() default TimeUnit.MILLISECONDS;

    /**
     * 得不到令牌的提示语
     */
    String msg() default "系统繁忙,请稍后再试.";
}
