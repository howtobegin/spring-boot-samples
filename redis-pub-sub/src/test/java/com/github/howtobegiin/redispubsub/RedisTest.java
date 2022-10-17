package com.github.howtobegiin.redispubsub;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author lhl
 * @date 2022/10/17 下午9:48
 */
@SpringBootTest
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void redisTest() throws InterruptedException {
        int i = 0;
        while (i < 10) {
            i++;
            stringRedisTemplate.convertAndSend("subscribe", "发布信息" + i);
            Thread.sleep(3000);
        }
    }
}
