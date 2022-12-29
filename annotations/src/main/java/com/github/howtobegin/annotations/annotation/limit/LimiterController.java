package com.github.howtobegin.annotations.annotation.limit;

import com.github.howtobegin.annotations.annotation.limit.distribute.annotation.DistributeLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author lhl
 * @date 2022/10/27 下午5:48
 */
@Slf4j
@RestController
@RequestMapping("/limiter")
public class LimiterController {

    @Limiter(key = "local1", permitsPerSecond = 0.5, timeout = 300, msg = "请求太快了")
    @RequestMapping("local1")
    public String local1() {
        return "success";
    }

    @Limiter(key = "local2", permitsPerSecond = 0.5, timeout = 300, msg = "手速太快了")
    @RequestMapping("local2")
    public String local2() {
        return "success";
    }

    @DistributeLimiter(key = "dist", times = 1, expire = 2)
    @RequestMapping("dist")
    public String dist() {
        return "success";
    }

}
