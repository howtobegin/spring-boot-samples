package com.github.howtobegin.websocket.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * @author lhl
 * @date 2022/10/18 上午6:57
 */
@Component
public class BroadcastTask {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Scheduled(fixedRate = 3 * 1000)
    public void broadcast() {
        String channel = "/topic/subscribeTopic";
        simpMessagingTemplate.convertAndSend(channel, Instant.now());
    }
}
