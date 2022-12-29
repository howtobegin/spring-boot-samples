package com.github.howtobegin.annotations.annotation.limit.exception;

/**
 * @author lhl
 * @date 2022/10/27 下午11:58
 */
public class LimitException extends RuntimeException {
    public LimitException(String message) {
        super(message);
    }
}
