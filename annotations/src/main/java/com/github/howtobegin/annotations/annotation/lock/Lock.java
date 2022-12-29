package com.github.howtobegin.annotations.annotation.lock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lhl
 * @date 2022/6/16 下午5:29
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Lock {

    String key();

    String subKey() default "";

    /**
     * 单位秒
     * @return
     */
    int time();
}
