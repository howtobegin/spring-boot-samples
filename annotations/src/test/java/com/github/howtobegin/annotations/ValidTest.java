package com.github.howtobegin.annotations;

import com.github.howtobegin.annotations.service.OrderService;
import com.github.howtobegin.annotations.service.ValidBo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

/**
 * @author lhl
 * @date 2022/11/30 下午3:11
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AnnotationsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ValidTest {
    @Autowired
    private OrderService orderService;

    @Test
    public void testValid() throws InterruptedException {
        orderService.valid(new ValidBo(), BigDecimal.ONE);
    }
}
