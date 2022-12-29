package com.github.howtobegin.annotations;

import com.github.howtobegin.annotations.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.*;

/**
 * @author lhl
 * @date 2022/10/27 下午5:04
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AnnotationsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LockTest {
    @Autowired
    private OrderService orderService;

    private CompletionService<String> txRequestService = new ExecutorCompletionService<>(new ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors(),
            Integer.MAX_VALUE,
            30,
            TimeUnit.SECONDS,
            new SynchronousQueue<>(),
            Executors.defaultThreadFactory()
    ));

    @Test
    public void test() {
        int num = 5;
        for (int i = 0; i < 5; i++) {
            txRequestService.submit(() -> orderService.submit("1", "2"));
        }

        while (num-- > 0) {
            try {
                txRequestService.take().get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testLockStr() {
        int num = 5;
        for (int i = 0; i < 5; i++) {
            String value = new String("abc");
            txRequestService.submit(() -> orderService.lockstr(value));
        }

        while (num-- > 0) {
            try {
                txRequestService.take().get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testLimit() {
        int num = 50;
        for (int i = 0; i < num; i++) {
            txRequestService.submit(() -> orderService.limit("100"));
        }

        while (num-- > 0) {
            try {
                txRequestService.take().get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
