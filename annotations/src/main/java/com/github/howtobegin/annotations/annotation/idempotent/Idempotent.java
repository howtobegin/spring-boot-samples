package com.github.howtobegin.annotations.annotation.idempotent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lhl
 * @date 2022/10/27 下午6:10
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {
    String key();

    String subKey() default "";

    /**
     * 单位秒
     * @return
     */
    int time();

    String msg() default "手速过快";
}
