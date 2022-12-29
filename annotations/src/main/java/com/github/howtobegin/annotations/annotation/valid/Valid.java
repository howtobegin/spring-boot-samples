package com.github.howtobegin.annotations.annotation.valid;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hello
 * @date 2022/12/29 17:51
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Valid {
    /**
     * 只支持带String构造函数的异常类
     * @return
     */
    Class<? extends Throwable> exception();
}
