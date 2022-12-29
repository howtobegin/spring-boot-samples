package com.github.howtobegin.annotations.common;


import com.github.howtobegin.annotations.annotation.limit.exception.LimitException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(value = "com.github.howtobegin.annotations")
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(LimitException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public CommonResp handleBizException(LimitException e) {
        return CommonResp.error("999999", e.getMessage());
    }
}
