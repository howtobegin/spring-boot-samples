package com.github.howtobegin.annotations.service;

import com.alibaba.fastjson.JSON;
import com.github.howtobegin.annotations.annotation.lock.Lock;
import com.github.howtobegin.annotations.annotation.log.LogTime;
import com.github.howtobegin.annotations.annotation.valid.Valid;
import com.github.howtobegin.annotations.annotation.valid.ValidException;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * @author lhl
 * @date 2022/10/27 下午5:01
 */
@Slf4j
@Component
public class OrderService {


    @Lock(key = "submit", subKey = "#userNo + ':' + #skuId", time = 20)
    public String submit(String userNo, String skuId) throws InterruptedException {
        log.info("userNo {} skuId {}", userNo, skuId);
        TimeUnit.SECONDS.sleep(5);
        return "success";
    }

    /**
     * 限流策略 ： 1秒钟2个请求
     */
    private final RateLimiter limiter = RateLimiter.create(0.5);

    public String limit(String param) throws InterruptedException{
        boolean tryAcquire = limiter.tryAcquire(300, TimeUnit.MILLISECONDS);
        if (!tryAcquire) {
            log.info("速度过快");
            return "fail";
        }
        TimeUnit.MILLISECONDS.sleep(100);
        log.info("success");
        return "success";
    }

    public String lockstr(String value) throws InterruptedException {
        log.info("value {} {}", value, value.intern());
        synchronized (value.intern()) {
            log.info("{} sleep", value);
            TimeUnit.SECONDS.sleep(3);
        }
        return value;
    }

    @LogTime()
    public void finish() throws InterruptedException {
        log.info("start");
        TimeUnit.MILLISECONDS.sleep(100);
        log.info("end");
    }

    @Valid(exception = ValidException.class)
    public void valid(ValidBo bo, BigDecimal amount) {
        log.info("start");
        log.info("bo {}", JSON.toJSONString(bo));
        log.info("end");
    }
}
