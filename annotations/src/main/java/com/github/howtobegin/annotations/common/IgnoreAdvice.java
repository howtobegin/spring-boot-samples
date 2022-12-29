package com.github.howtobegin.annotations.common;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface IgnoreAdvice {
}
