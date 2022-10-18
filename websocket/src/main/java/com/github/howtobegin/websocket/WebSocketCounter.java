package com.github.howtobegin.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.LongAdder;

/**
 * @author lhl
 * @date 2022/10/18 上午10:25
 */
@Component
public class WebSocketCounter {
    private LongAdder connections = new LongAdder();

    @Autowired
    private SimpMessagingTemplate template;

    public void increment() {
        connections.increment();
        template.convertAndSend("/topic/subscribeTopic", String.valueOf(connections.sum()));
    }

    public void decrement() {
        connections.decrement();
        template.convertAndSend("/topic/subscribeTopic", String.valueOf(connections.sum()));
    }

    public long onlineUsers() {
        return connections.sum();
    }
}
